package launcher;

import java.io.IOException;

import org.json.JSONException;

public class Launcher {
	private static boolean takeLast = false;
	private static int nbSticks = 12;
	private static String plays = "1,2,3";

	public static void main(String[] args) throws IOException, JSONException {
		Nim nim = new Nim(nbSticks, takeLast, plays);
		nim.play();
	}
}