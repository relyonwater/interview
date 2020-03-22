package commander;

import java.math.BigDecimal;
import java.util.Stack;

/**
 * @author yhp
 * @date 2020/3/22
 */
public interface Command {

    boolean calculate(Stack<BigDecimal> nums, Stack<Object> backup);

    void undo(Stack<BigDecimal> nums, Stack<Object> backup);
}
