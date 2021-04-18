import java.util.Scanner;

public class Interpreter {
    public static void main(String[] args) {

        Executioner executor;
        Scanner userInput = new Scanner(System.in);
        String input = "";

        System.out.println("Welcome to my LISP interpreter! Type in LISP commands.");

        while(true) {

            System.out.print(">");

            input = userInput.nextLine();

            if (input.equals("(quit)")) {
                break;
            }

            //System.out.println(input);
            Parser parser = new Parser(input);
            //parser.printParsedString();

            System.out.println(parser.getTokenList().printToken());

            executor = new Executioner(parser.getTokenList());

            Token result = executor.eval();

            System.out.println(result.printToken());

            //System.out.println();

        };

        System.out.println("bye");

    }
}
