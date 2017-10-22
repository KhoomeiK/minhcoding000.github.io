import java.io.IOException;
public class receiverTester { 
	public static void main(String[] args) throws IOException, InterruptedException {
		receiver r = new receiver(); 
		
		String path = "/Users/rohan/Desktop/receiverData.txt";
		// set this to the path to the file you want to log data to
		
		r.setPath(path); 
		r.run();
	}
}