package commander;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class ClearCommand implements Command {

    public static final String command = "clear";

    @Override
    public boolean calculate(Stack<BigDecimal> nums, Stack<Object> backup) {
        if (nums == null) {
            return false;
        }
        nums.clear();
        backup.clear();
        return true;
    }

    @Override
    public void undo(Stack<BigDecimal> nums, Stack<Object> backup) {
    }
}
