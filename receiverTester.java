import java.io.IOException;
public class receiverTester { 
	public static void main(String[] args) throws IOException, InterruptedException {
		receiver r = new receiver(); 
		r.run();
	}
}