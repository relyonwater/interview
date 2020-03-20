import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * @author yhp on 2020-03-14.
 *
 * 逆波兰计算器
 * 1. command-line caculator online tool
 * 2. basic operators  + - * /
 * 3. sqrt, undo, clear, swap
 *
 * stack实现
 * 输入用空格隔开
 * 展示栈内容
 * 返回都展示string，精度15位，展示的时候去除后面的0
 * 违规操作warning，并返回栈内容
 */
public class RpnCalculator {

    private static final List<String> OPERATORS = Arrays.asList("sqrt", "+", "-", "*", "/", "undo", "clear", "swap");

    private Stack<BigDecimal> nums;

    private Stack<Object> backup;

    /**
     * 构造器初始化stack，方法内部操作栈，不需要传递参数
     */
    public RpnCalculator() {
        this.nums = new Stack<>();
        this.backup = new Stack<>();
    }

    public String calculate(String inputStr) {
        String[] inputArr = inputStr.split(" ");
        List<String> inputList = Arrays.asList(inputArr);
        return calculate(inputList);
    }

    private String calculate(List<String> inputList) {
        // 合法校验
        if (inputList == null || inputList.size() == 0) {
            return printStack();
        }
        // 数字 和 操作符处理
        for (int i = 0; i < inputList.size(); i++) {
            String input = inputList.get(i);
            // 数字入栈
            if (numeric(input)) {
                BigDecimal num = new BigDecimal(input);
                nums.push(num);
                backup.push(num);
                continue;
            }
            // 操作符处理 1.1合法性校验
            if (!OPERATORS.contains(input)) {
                return invalidOperation(input, i);
            }
            switch (input) {
                case "sqrt":
                    if (nums == null || nums.size() == 0) {
                        return invalidOperation(input, i);
                    }
                    BigDecimal num = nums.peek();
                    if (num.compareTo(BigDecimal.ZERO) < 0 ) {
                        return invalidOperation(input, i);
                    }
                    sqrt();
                    backup.push(input);
                    break;
                case "clear":
                    clear();
                    break;
                case "undo":
                    undo();
                    break;
                case "swap":
                    if (nums == null || nums.size() == 0) {
                        break;
                    }
                    if (nums.size() == 1) {
                        return invalidOperation(input, i);
                    }
                    swap();
                    backup.push(input);
                    break;
                default:
                    if (nums == null || nums.size() < 2) {
                        return invalidOperation(input, i);
                    }
                    if ("/".equals(input) && nums.peek().compareTo(BigDecimal.ZERO) == 0) {
                        return invalidOperation(input, i);
                    }
                    basicOperate(input);
                    backup.push(input);
                    break;
            }
        }
        return printStack();
    }

    private boolean numeric(String num) {
        if (num == null || num.length() == 0) {
            return false;
        }
        return num.matches("^-?\\d+(\\.\\d+)?$");
    }

    private void basicOperate(String operator) {
        BigDecimal num2 = nums.pop();
        BigDecimal num1 = nums.pop();
        switch (operator) {
            case "+":
                nums.push(num1.add(num2));
                break;
            case "-":
                nums.push(num1.subtract(num2));
                break;
            case "*":
                nums.push(num1.multiply(num2));
                break;
            case "/":
                nums.push(num1.setScale(15,  RoundingMode.HALF_UP).divide(num2,  RoundingMode.HALF_UP));
                break;
            default:
                break;
        }
        backup.push(num2);
        backup.push(num1);
    }

    private void undo() {
        if (nums == null || nums.size() == 0) {
            return;
        }
        Object obj = backup.peek();
        if (obj instanceof String) {
            backup.pop();
            if ("sqrt".equals(obj)) {
                nums.pop();
                nums.push((BigDecimal) backup.pop());
            } else if ("swap".equals(obj)) {
                nums.pop();
                nums.pop();
                BigDecimal second = (BigDecimal) backup.pop();
                BigDecimal first = (BigDecimal) backup.pop();
                nums.push(first);
                nums.push(second);
            } else {
                nums.pop();
                nums.push((BigDecimal) backup.pop());
                nums.push((BigDecimal) backup.pop());
            }
        } else if (obj instanceof BigDecimal) {
            nums.pop();
        }
    }

    private void clear() {
        nums.clear();
        backup.clear();
    }

    private void swap() {
        BigDecimal first = nums.pop();
        BigDecimal second = nums.pop();
        nums.push(first);
        nums.push(second);
    }

    private void sqrt() {
        BigDecimal num = nums.pop();
        nums.push(sqrt(num, 15));
        backup.push(num);
    }

    public static BigDecimal sqrt(BigDecimal value, int scale){
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            return value;
        }
        BigDecimal num = BigDecimal.valueOf(2);
        int precision = 100;
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        BigDecimal deviation = value;
        int cnt = 0;
        while (cnt < precision) {
            deviation = (deviation.add(value.divide(deviation, mc))).divide(num, mc);
            cnt++;
        }
        deviation = deviation.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return deviation;
    }

    private String invalidOperation(String operator, int position) {
        String msg = String.format("operator %s (position: %d): insufficient parameters\n", operator, position * 2 + 1);
        return msg + printStack();
    }

    /**
     * 展示10位, 尾部去0
     */
    private String printStack() {
        StringBuilder str = new StringBuilder("stack:");
        for (BigDecimal num : nums) {
            str.append(' ');
            str.append(num.setScale(10, BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString());
        }
        return str.toString();
    }

}
