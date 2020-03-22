package commander;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Stack;

/**
 * @author yhp
 * @date 2020/3/22
 */
public class SqrtCommand implements Command {

    public static final String command = "sqrt";

    @Override
    public boolean calculate(Stack<BigDecimal> nums,  Stack<Object> backup) {
        if (nums == null || nums.size() == 0) {
            return false;
        }
        BigDecimal peek = nums.peek();
        if (peek.compareTo(BigDecimal.ZERO) < 0 ) {
            return false;
        }
        BigDecimal num = nums.pop();
        nums.push(sqrt(num, 15));
        backup.push(num);
        backup.push(command);
        return true;
    }

    private static BigDecimal sqrt(BigDecimal value, int scale){
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

    @Override
    public void undo(Stack<BigDecimal> nums, Stack<Object> backup) {
        backup.pop();
        nums.pop();
        nums.push((BigDecimal) backup.pop());
    }
}
