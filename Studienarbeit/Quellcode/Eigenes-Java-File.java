import java.util.Scanner;

public class CrossTotal {
	static String nutzerEingabe;
	static int ergebnis = 0;

	// Methode berechnet die Quersumme der Zahl
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Bitte Zahl eingeben:");
		nutzerEingabe = scan.next();
		scan.close();
		for (char c : nutzerEingabe.toCharArray()) {
			ergebnis = ergebnis + Character.getNumericValue(c);
		}
		System.out.println("Die Quersumme von " + nutzerEingabe + " ist " + ergebnis);
	}
}
