package launcher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Launcher {

	public static void main(String[] args) throws IOException, JSONException {
		boolean takeLast = false;
		Nim nim = new Nim();
		nim.play();
		JSONObject game = new JSONObject("{\"0\":[],\"1\":[\"0\"],\"2\":[\"0\",\"1\"],\"3\":[\"0\",\"2\"],\"4\":[\"1\",\"2\"],\"5\":[\"3\"],\"6\":[\"3\",\"5\"],\"7\":[\"3\",\"4\"],\"8\":[\"4\",\"7\"],\"9\":[\"6\",\"7\",\"8\"]}");
		nim.setGame(game);
		nim.setTakeLast(takeLast);
		nim.play();
	}
}