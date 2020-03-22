package commander;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class AddCommand implements Command {

    public static final String command = "+";

    @Override
    public boolean calculate(Stack<BigDecimal> nums, Stack<Object> backup) {
        if (nums == null || nums.size() < 2) {
            return false;
        }
        BigDecimal num2 = nums.pop();
        BigDecimal num1 = nums.pop();
        nums.push(num1.add(num2));
        backup.push(num2);
        backup.push(num1);
        backup.push(command);
        return true;
    }

    @Override
    public void undo(Stack<BigDecimal> nums, Stack<Object> backup) {
        backup.pop();
        nums.pop();
        nums.push((BigDecimal) backup.pop());
        nums.push((BigDecimal) backup.pop());
    }
}
