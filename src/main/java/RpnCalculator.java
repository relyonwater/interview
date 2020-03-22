import com.sun.org.apache.xpath.internal.operations.Div;
import commander.*;

import java.math.BigDecimal;
import java.util.*;

import static util.NumberUtils.numeric;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class RpnCalculator {

    private Stack<BigDecimal> nums;

    private Stack<Object> backup;

    private Map<String, Command> operatorMap;

    /**
     * 构造器初始化stack，方法内部操作栈，不需要传递参数
     */
    public RpnCalculator() {
        this.nums = new Stack<>();
        this.backup = new Stack<>();
        this.operatorMap = new HashMap<>(8, 1);
        operatorMap.put(AddCommand.command, new AddCommand());
        operatorMap.put(SubtractCommand.command, new SubtractCommand());
        operatorMap.put(MultiplyCommand.command, new MultiplyCommand());
        operatorMap.put(DivideCommand.command, new DivideCommand());
        operatorMap.put(SwapCommand.command, new SwapCommand());
        operatorMap.put(SqrtCommand.command, new SqrtCommand());
        operatorMap.put(ClearCommand.command, new ClearCommand());
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
            boolean result = operatorHandler(input);
            if (!result) {
                return invalidOperation(input, i);
            }
        }
        return printStack();
    }

    private boolean operatorHandler(String input) {
        if ("undo".equals(input)) {
            return undo();
        } else {
            Command command = operatorMap.get(input);
            if (command == null) {
                return false;
            }
            return command.calculate(nums, backup);
        }
    }

    private boolean undo() {
        if (nums == null || nums.size() == 0) {
            return true;
        }
        Object obj = backup.peek();
        if (obj instanceof String) {
            Command command = operatorMap.get(obj);
            if (command == null) {
                return false;
            }
            command.undo(nums, backup);
        } else {
            nums.pop();
        }
        return true;
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
