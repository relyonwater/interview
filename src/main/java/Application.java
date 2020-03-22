import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author yhp on 2020-03-19.
 */
public class Application {


    public static void main(String[] args) throws Exception {
        RpnCalculator rpnCalculator = new RpnCalculator();
        System.out.print("RPN calculator started, waiting for input : (Type 'exit' to end)\n");
        while (true) {
            String inputStr = (new BufferedReader(new InputStreamReader(System.in))).readLine();
            if (inputStr.equals("exit")) {
                break;
            }
            String result = rpnCalculator.calculate(inputStr);
            System.out.println(result);
        }
        System.out.println("bye-bye");
        System.exit(0);
    }

}
