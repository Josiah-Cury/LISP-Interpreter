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
            //parser.printParsedString();

            //System.out.println(parser.getTokenList().printToken());

            executor = new Executioner(parser.getTokenList());
            executor.eval().printToken();
            System.out.println();

        } while (!input.equals("(quit)"));

        System.out.println("bye");

    }
}
