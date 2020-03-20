import org.junit.Assert;
import org.junit.Test;
/**
 * @author yhp on 2020-03-20.
 * pdf提供的自测example
 */

public class RpnCalculatorTest {

    @Test
    public void example1() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 5 2", rpnCalculator.calculate("5 2"));
    }


    @Test
    public void example2() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 1.4142135624", rpnCalculator.calculate("2 sqrt"));
        Assert.assertEquals("stack: 3", rpnCalculator.calculate("clear 9 sqrt"));
    }

    @Test
    public void example3() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 3", rpnCalculator.calculate("5 2 -"));
        Assert.assertEquals("stack: 0", rpnCalculator.calculate("3 -"));
        Assert.assertEquals("stack:", rpnCalculator.calculate("clear"));
    }

    @Test
    public void example4() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("5 4 3 2"));
        Assert.assertEquals("stack: 20", rpnCalculator.calculate("undo undo *"));
        Assert.assertEquals("stack: 100", rpnCalculator.calculate("5 *"));
        Assert.assertEquals("stack: 20 5", rpnCalculator.calculate("undo"));
    }

    @Test
    public void example5() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 7 6", rpnCalculator.calculate("7 12 2 /"));
        Assert.assertEquals("stack: 42", rpnCalculator.calculate("*"));
        Assert.assertEquals("stack: 10.5", rpnCalculator.calculate("4 /"));
    }

    @Test
    public void example6() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 1 2 3 4 5", rpnCalculator.calculate("1 2 3 4 5"));
        Assert.assertEquals("stack: 1 2 3 20", rpnCalculator.calculate("*"));
        Assert.assertEquals("stack: -1", rpnCalculator.calculate("clear 3 4 -"));
    }

    @Test
    public void example7() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 1 2 3 4 5", rpnCalculator.calculate("1 2 3 4 5"));
        Assert.assertEquals("stack: 120", rpnCalculator.calculate("* * * *"));
    }

    @Test
    public void example8() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("operator * (position: 15): insufficient parameters\nstack: 11", rpnCalculator.calculate("1 2 3 * 5 + * * 6 5"));
    }

    @Test
    public void inputValid() {
        // 不支持的操作符
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("operator ? (position: 7): insufficient parameters\nstack: 1 2 3", rpnCalculator.calculate("1 2 3 ? 5"));
    }

    @Test
    public void bigDecimal() {
        // 大数存取和计算,基础运算及精度
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 52187392813.8236789123", rpnCalculator.calculate("52187392813.823678912329287321"));
        Assert.assertEquals("stack: 52187392813.8236789122", rpnCalculator.calculate("0.000000000099287321 -"));
        Assert.assertEquals("stack: 114432914032048409.2848930001", rpnCalculator.calculate("2192731 *"));
        Assert.assertEquals("stack: -52187392813.8236789122", rpnCalculator.calculate("-2192731 /"));
    }

    @Test
    public void undo() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        // undo
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("5 4 3 2"));
        Assert.assertEquals("stack: 20", rpnCalculator.calculate("undo undo *"));
        Assert.assertEquals("stack: 100", rpnCalculator.calculate("5 *"));
        // * undo
        Assert.assertEquals("stack: 20 5", rpnCalculator.calculate("undo"));
        Assert.assertEquals("stack: 20", rpnCalculator.calculate("undo"));
        Assert.assertEquals("stack:", rpnCalculator.calculate("undo"));

        // sqrt undo
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("5 4 3 2"));
        Assert.assertEquals("stack: 5 4 3 1.4142135624", rpnCalculator.calculate("sqrt"));
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("undo"));

        // swap undo
        Assert.assertEquals("stack: 5 4 2 3", rpnCalculator.calculate("swap"));
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("undo"));
    }

    @Test
    public void clear() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("5 4 3 2"));
        Assert.assertEquals("stack:", rpnCalculator.calculate("clear"));
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("5 4 3 2"));
        Assert.assertEquals("stack: 5 4 3", rpnCalculator.calculate("undo"));
    }

    @Test
    public void swap() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 5 4", rpnCalculator.calculate("5 4"));
        Assert.assertEquals("stack: 4 5", rpnCalculator.calculate("swap"));
        Assert.assertEquals("stack: 5 4", rpnCalculator.calculate("swap"));
        Assert.assertEquals("stack: 5 4 3 2", rpnCalculator.calculate("3 2"));
        Assert.assertEquals("stack: 5 4 2 3", rpnCalculator.calculate("swap"));
    }

    @Test
    public void sqrt() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        // 0
        Assert.assertEquals("stack: 5 0", rpnCalculator.calculate("5 0"));
        Assert.assertEquals("stack: 5 0", rpnCalculator.calculate("sqrt"));
        // 正数
        Assert.assertEquals("stack: 5 0 2", rpnCalculator.calculate("4 sqrt"));
        // 负数
        Assert.assertEquals("operator sqrt (position: 3): insufficient parameters\nstack: 5 0 2 -4", rpnCalculator.calculate("-4 sqrt"));
        Assert.assertEquals("stack: 5 0 2 -4 2.6457513111", rpnCalculator.calculate("7 sqrt"));
    }

    @Test
    public void basicOperator() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack: 5 4", rpnCalculator.calculate("5 4"));
        // +
        Assert.assertEquals("stack: 9", rpnCalculator.calculate("+"));
        Assert.assertEquals("stack: 6", rpnCalculator.calculate("-3 +"));
        // -
        Assert.assertEquals("stack: 9", rpnCalculator.calculate("-3 -"));
        Assert.assertEquals("stack: 5", rpnCalculator.calculate("4 -"));
        // * 0
        Assert.assertEquals("stack: 5 4", rpnCalculator.calculate("2 2 *"));
        Assert.assertEquals("stack: 20", rpnCalculator.calculate("*"));
        Assert.assertEquals("stack: -40", rpnCalculator.calculate("-2 *"));
        Assert.assertEquals("stack: 40", rpnCalculator.calculate("-1 *"));
        Assert.assertEquals("stack: 0", rpnCalculator.calculate("0 *"));
        // / 0
        Assert.assertEquals("stack: 0", rpnCalculator.calculate("2 /"));
        Assert.assertEquals("operator / (position: 5): insufficient parameters\nstack: 0 2 0", rpnCalculator.calculate("2 0 /"));
        Assert.assertEquals("stack: 0 2 0 -4", rpnCalculator.calculate("12 -3 /"));
        Assert.assertEquals("stack: 0 2 0 -1.3333333333", rpnCalculator.calculate("3 /"));
    }

    @Test
    public void emptyStack() {
        RpnCalculator rpnCalculator = new RpnCalculator();
        Assert.assertEquals("stack:", rpnCalculator.calculate("clear"));
        Assert.assertEquals("stack:", rpnCalculator.calculate("swap"));
        Assert.assertEquals("stack:", rpnCalculator.calculate("undo"));
        Assert.assertEquals("operator + (position: 1): insufficient parameters\nstack:", rpnCalculator.calculate("+"));
    }



}