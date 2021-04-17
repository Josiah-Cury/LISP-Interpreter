import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpFactory;

import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {

        Executioner executor;
        Scanner userInput = new Scanner(System.in);
        String input;

        System.out.println("Welcome to my LISP interpreter! Type in LISP commands.");

        do {

            System.out.print(">");

            input = userInput.nextLine();
            //System.out.println(input);
            Parser parser = new Parser(input);
            parser.printParsedString();
            executor = new Executioner(parser.getTokenList());
            System.out.println();

        } while (!input.equals("(quit)"));

        System.out.println("bye");

    }
}
