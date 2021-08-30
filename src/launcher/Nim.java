package launcher;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utilities.DisplayIO;
import utilities.ScannerIO;

public class Nim {
	private JSONObject game = new JSONObject();
	private int startPosition;
	private String currentPosition;
	private int playerNumber;
	private boolean takeLast;

	/**
	 * Set the default Nim game
	 * 
	 * @throws JSONException
	 */
	public Nim() throws JSONException {
		this(10, true, "1,2,3");
	}

	/**
	 * Initialize the game
	 * 
	 * @param nbSticks : the size (number of sticks) of the game
	 * @param takeLast : if true, each player must take the last stick
	 * @param plays    : the plays each player can do (example : take 1, 2 or 3
	 *                 sticks each)
	 * @throws JSONException
	 */
	public Nim(int nbSticks, boolean takeLast, String plays) throws JSONException {
		setGameStartPosition(nbSticks);
		setTakeLast(takeLast);
		game.put("0", new JSONArray());

		List<Integer> playList = getPlayList(plays);
		buildStickGame(playList);
	}

	private List<Integer> getPlayList(String plays) {
		List<Integer> playList = new ArrayList<>();
		for (String play : plays.split(",")) {
			playList.add(Integer.parseInt(play));
		}
		return playList;
	}

	public void setTakeLast(boolean takeLast) {
		this.takeLast = takeLast;
	}

	public void setGameStartPosition(int startPosition) {
		this.startPosition = startPosition;
	}

	public void setGame(JSONObject game) {
		this.game = game;
	}

	/**
	 * Reset the game
	 */
	private void reset() {
		startPosition = game.length() - 1;
		playerNumber = 0;
		currentPosition = Integer.toString(startPosition);
	}

	/**
	 * Start the game
	 * 
	 * @throws JSONException
	 */
	public void play() throws JSONException {
		reset();
		DisplayIO.displayGame(game.toString());
		DisplayIO.displayRule(takeLast);
		do {
			incrementPlayer();
			JSONArray moves = getPlayableMoves(currentPosition);
			DisplayIO.displayPlayerTurn(playerNumber);
			DisplayIO.displayMoves(moves.toString());
			String move = readMove();
			playMove(move);
			if (isWinMove(move)) {
				System.err.println("Win position !");
			}
		} while (!currentPosition.equals("0"));
		if (!takeLast) {
			incrementPlayer();
		}
		DisplayIO.displayWinner(playerNumber);
	}

	/**
	 * Check if the played move allow the player to win
	 * 
	 * @param currentMove : the move played by the player
	 * @return true if the move allow him to win
	 * @throws JSONException
	 */
	private boolean isWinMove(String currentMove) throws JSONException {
		if (currentMove.equals("0")) {
			return takeLast;
		} else {
			JSONArray moves = getPlayableMoves(currentMove);
			boolean winMove = true;
			for (int moveNum = 0; moveNum < moves.length(); moveNum++) {
				String move = moves.getString(moveNum);
				winMove = !isWinMove(move) && winMove;
			}
			return winMove;
		}
	}

	/**
	 * Switch player 1 and 2
	 */
	private void incrementPlayer() {
		playerNumber = playerNumber % 2 + 1;
	}

	/**
	 * Build a basic stick game where you can take 1, 2 or 3 sticks
	 * 
	 * @throws JSONException
	 */
	private void buildStickGame(List<Integer> moves) throws JSONException {
		for (Integer position = 0; position <= startPosition; position++) {
			String positionStr = position.toString();
			for (int moveNum = 0; moveNum < moves.size(); moveNum++) {
				Integer nextPosition = position + moves.get(moveNum);
				if (nextPosition <= startPosition) {
					String nextPositionStr = nextPosition.toString();
					JSONArray nextPositionOptions = getOrDefaultJsonArray(nextPositionStr);
					nextPositionOptions.put(positionStr);
					game.put(nextPositionStr, nextPositionOptions);
				}
			}
		}
		System.out.println();
	}

	/**
	 * Get a JSONArray using a key. If not found, return a new JSONArray
	 * 
	 * @param key : The key string to find the JSONArray
	 * @return the JSONArray that correspond to the key, and a new JSONArray if not
	 *         found.
	 * @throws JSONException
	 */
	private JSONArray getOrDefaultJsonArray(String key) throws JSONException {
		return game.has(key) ? getPlayableMoves(key) : new JSONArray();
	}

	/**
	 * Get all the moves that the player can do
	 * 
	 * @param position : The position to look for
	 * @return a JSONArray containing the moves that the player can do
	 * @throws JSONException
	 */
	private JSONArray getPlayableMoves(String position) throws JSONException {
		return game.getJSONArray(position);
	}

	/**
	 * Ask the player to select a move until he enter a correct one
	 * 
	 * @return the played move
	 * @throws JSONException
	 */
	private String readMove() throws JSONException {
		String move;
		do {
			move = ScannerIO.readLine();
		} while (!isPlayableMove(move));
		return move;
	}

	/**
	 * Check if the move can be play.
	 * 
	 * @param playedMove : the move the user want to play
	 * @return true if the move can be play
	 * @throws JSONException
	 */
	private boolean isPlayableMove(String playedMove) throws JSONException {
		JSONArray moves = getPlayableMoves(currentPosition);
		for (int moveNum = 0; moveNum < moves.length(); moveNum++) {
			String move = moves.getString(moveNum);
			if (move.equals(playedMove)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Affect the current move to the current position
	 * 
	 * @param move : the move to use to update the position
	 */
	private void playMove(String move) {
		currentPosition = move;
	}
}
