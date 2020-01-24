package utilities;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.io.Reader;
import java.util.Locale;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ScannerIO {
	private static final Object readMonitor = new Object();
	private static volatile Scanner scanner = scanner(new InputStreamReader(System.in));

	private static Scanner scanner(Reader in) {
		Scanner scanner = new Scanner(in);
		scanner.useLocale(Locale.US);
		return scanner;
	}

	public static Scanner openIn (String name) {
		try {
			FileInputStream fis = new FileInputStream(name); 
			Scanner scanner = scanner(new InputStreamReader(fis));
			return scanner;
		}
		catch (java.io.FileNotFoundException e) {
			return scanner(new InputStreamReader(System.in));
		}
	}

	public static void closeIn (Scanner s) {
		s.close();
	}

	public static int readIntFromFile (Scanner s) {
		synchronized (readMonitor) {
			return s.nextInt();
		}
	}

	public static int readInt() {
		return readIntFromFile (scanner);
	}

	public static double readDoubleFromChar(Scanner s) {
		synchronized (readMonitor) {
			return s.nextDouble();
		}
	}

	public static double readDouble() {
		return readDoubleFromChar(scanner);
	}

	private static final Pattern DOT = Pattern.compile(".",Pattern.DOTALL);  

	public static String readCharacterFromFile(Scanner s) {
		synchronized (readMonitor) {
			return String.valueOf(s.findWithinHorizon(DOT,1).charAt(0));
		}
	}

	public static String readCharacter() {
		return readCharacterFromFile(scanner);
	}
	
	public static String readLine() {
		return readLineFromFile(scanner);
	}
	
	public static String readLineFromFile(Scanner s) {
		synchronized (readMonitor) {
			return s.nextLine();
		}
	}
}
