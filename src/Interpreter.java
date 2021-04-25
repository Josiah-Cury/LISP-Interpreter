import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The main lisp interpreter.
 *
 * This class will take in user input strings and creates an output file as well as print the user input to the terminal.
 *
 * The interpreter is structured so that the input will be passed to a parser and then the parsed output will
 * be passed to the evaluator. The evaluator will evaluate and return the answer and the interpreter will print the answer.
 *
 * This is done the same way lisp works essentially with a parse, evaluate, and print loop until the user exits.
 */
public class Interpreter {
    public static void main(String[] args) throws IOException {

        /**
         * Executioner executor: is an object that takes in a list of tokens to evaluate.
         * Scanner userInput: is an object that reads user input.
         * String input: will be set to the string the user enters.
         * HashMap variables: a hashmap that stores all defined variables and their values.
         */
        Executioner executor;
        Scanner userInput = new Scanner(System.in);
        String input = "";
        HashMap<String, Token> variables = new HashMap<>();

        try {
            File output = new File("Lisp_Output.txt");
            output.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedWriter bufferWriter = new BufferedWriter(new FileWriter("Lisp_Output.txt"));

        System.out.println("Welcome to my LISP interpreter! Type in LISP commands.");
        bufferWriter.write("Welcome to my LISP interpreter! Type in LISP commands.\n\n");

        /**
         * This is the while loop reading in user input, parsing the input, and evaluating the parsed input
         * until the user enters '(quit)'.
         */
        while(true) {

            System.out.print(">");
            input = userInput.nextLine();
            bufferWriter.write(">" + input + "\n");

            if (input.equals("(quit)")) {
                break;
            }


            Parser parser = null;
            /**
             * This will catch an exception thrown by parser.
             */
            try {
                parser = new Parser(input);

                //parser.printParsedString();
                //System.out.println(parser.getTokenList().printToken());

                executor = new Executioner(parser.getTokenList());

                Token result = null;

                /**
                 * This will catch any exception thrown by executor (if the parameters entered by the user are wrong).
                 */
                try {
                    result = executor.eval(variables);
                    System.out.println(result.printToken());
                    bufferWriter.write(result.printToken() + "\n\n");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    bufferWriter.write(e.getMessage() + "\n\n");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                bufferWriter.write(e.getMessage() + "\n\n");
            }
        }

        System.out.println("bye");
        bufferWriter.write("bye\n");

        bufferWriter.close();

    }
}
