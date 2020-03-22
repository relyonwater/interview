package commander;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class SwapCommand implements Command {

    public static final String command = "swap";

    @Override
    public boolean calculate(Stack<BigDecimal> nums, Stack<Object> backup) {
        if (nums == null || nums.size() == 0) {
            return true;
        }
        if (nums.size() < 2) {
            return false;
        }
        BigDecimal first = nums.pop();
        BigDecimal second = nums.pop();
        nums.push(first);
        nums.push(second);
        backup.push(command);
        return true;
    }

    @Override
    public void undo(Stack<BigDecimal> nums, Stack<Object> backup) {
        backup.pop();
        nums.pop();
        nums.pop();
        BigDecimal second = (BigDecimal) backup.pop();
        BigDecimal first = (BigDecimal) backup.pop();
        nums.push(first);
        nums.push(second);
    }
}
