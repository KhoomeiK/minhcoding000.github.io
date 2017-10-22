import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class receiver {
	public boolean b = true;

	int getDate(String str, char f) { // returns ints for each queried time
										// value
		switch (f) {
		case 'y':
			str = str.substring(0, 4);
			int year = Integer.parseInt(str);
			return year;
		case 'm':
			str = str.substring(5, 7);
			int month = Integer.parseInt(str);
			return month;
		case 'd':
			str = str.substring(8, 10);
			int date = Integer.parseInt(str);
			return date;
		case 'h':
			str = str.substring(11, 13);
			int hour = Integer.parseInt(str);
			return hour;
		case 'i':
			str = str.substring(14, 16);
			int minute = Integer.parseInt(str);
			return minute;
		case 's':
			str = str.substring(17, 19);
			int second = Integer.parseInt(str);
			return second;
		default:
			return 0;
		}
	}

	String replace(String str, boolean b) { // deletes all symbols and
											// whitespace
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!((c >= '0' && c <= '9') || // ASCII locations of symbols
					(c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
				str = str.replace(c, ' ');
			}
		}

		if (b)
			str = str.replace(" ", "");
		else
			str = str.trim();
		return str;
	}

	String log(String str, Calendar cal) throws IOException { // logs ID and its
															// time to a text
															// file
		receiver r = new receiver();
		String check = r.getLastLine();
		String output = str + "  |  " + cal.getTime();
		if (!output.equals(check)) {
			FileWriter Writer = new FileWriter("/Users/rohan/Desktop/receiverData.txt", true);
			BufferedWriter bWriter = new BufferedWriter(Writer);
			bWriter.write(output + "\n");
			bWriter.close();
			return ("ID: " + str + " at time: " + cal.getTime() + " has been logged");
		} else
			return ("ID " + str + " already logged");
	}

	String getLastLine() throws IOException { // prints last line of ID file
		BufferedReader in = new BufferedReader(new FileReader("/Users/rohan/Desktop/receiverData.txt"));
		String inputLine = in.readLine();
		String outLine = "";
		while ((inputLine) != null) {
			inputLine = in.readLine();
			if (inputLine != null)
				outLine = inputLine; // last non-null line
		}
		in.close();
		return outLine;
	}

	void stop(receiver rec, boolean bool) throws IOException, InterruptedException {
		rec.b = bool;
	}

	void run() throws IOException, InterruptedException {
		BufferedReader in;
		URL dweetSource = new URL("https://dweet.io/get/latest/dweet/for/schoolscan");
		Calendar c = Calendar.getInstance();
		receiver r = new receiver();

		while (b) {
			in = new BufferedReader(new InputStreamReader(dweetSource.openStream()));
			String inputLine = in.readLine(); // reads data from dweet

			// gets time
			String div1 = ":\"201";
			String div2 = ",\"content";
			int index1 = inputLine.indexOf(div1);
			int index2 = inputLine.indexOf(div2);
			String timeLine = "";
			try {
				timeLine = inputLine.substring(index1, index2);
			} catch (Exception e) {
				System.out.println("Unable to reach URL");
			}

			// gets content
			String div3 = ":{";
			String div4 = ":\"\"";
			int index3 = inputLine.indexOf(div3);
			int index4 = inputLine.indexOf(div4);
			String line = "";
			try {
				line = inputLine.substring(index3, index4);				
			}
			catch (StringIndexOutOfBoundsException e) {
				System.out.println("No ID available");
			}

			in.close();

			String ID = line;
			ID = r.replace(ID, true);
			timeLine = r.replace(timeLine, false);

			// gets ints for time values and sets calendar values to them
			int year = r.getDate(timeLine, 'y');
			int month = r.getDate(timeLine, 'm') - 1;
			int date = r.getDate(timeLine, 'd');
			int hour = r.getDate(timeLine, 'h') - 7;
			int minute = r.getDate(timeLine, 'i');
			int second = r.getDate(timeLine, 's');
			c.set(year, month, date, hour, minute, second);

			System.out.println((r.log(ID, c)));
			TimeUnit.SECONDS.sleep(5); // delays for 5 seconds
		}
	}
}