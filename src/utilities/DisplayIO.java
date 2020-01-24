package utilities;

public class DisplayIO {

	public static void displayMoves(String moves) {
		System.out.println("Playable moves : " + moves.toString());
	}

	public static void displayPlayerTurn(int playerNumber) {
		System.out.println("Player " + playerNumber);
	}

	public static void displayGameStatus(String gameStatus) {
		System.out.println("Game status : " + gameStatus + " sticks");
	}

	public static void displayWinner(int winner) {
		System.out.println("Player " + winner + " win !");
	}

	public static void displayGame(String game) {
		// TODO Auto-generated method stub
		System.out.println(game);
	}

	public static void displayRule(boolean takeLast) {
		if(takeLast) {
			System.out.println("Take the 0 position");
		} else {
			System.out.println("Don't take the 0 position");
		}
		
	}
}
