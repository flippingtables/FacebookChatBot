package foo;

import com.sandagerdi.Calculator;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Test of calculate method, of class Calculator.
     */
    Calculator c = new Calculator();

    @org.testng.annotations.Test
    public void testAddition()
    {
        String str = "Calc: 222.22 + 2222222";
        String expResult = "2222444,22";
        String result = Calculator.calculate(str);
        assertEquals(expResult, result);

        String str2 = "Calc: 2 + 2";
        String expResult2 = "4";
        String result2 = Calculator.calculate(str2);
        assertEquals(expResult2, result2);

        String str3 = "Calc: 2.0 + 2.0";
        String expResult3 = "4";
        String result3 = Calculator.calculate(str3);
        assertEquals(expResult3, result3);

        String str4 = "Calc: 1. + 2";
        String expResult4 = "3";
        String result4 = Calculator.calculate(str4);
        assertEquals(expResult4, result4);

    }

    @org.testng.annotations.Test
    public void testExponentiation()
    {
        String str1 = "Calc: 2.0 ^ 7.0";
        String expResult1 = "128";
        String result1 = Calculator.calculate(str1);
        assertEquals(expResult1, result1);

        String str2 = "Calc: -1 ^ 2";
        String expResult2 = "1";
        String result2 = Calculator.calculate(str2);
        assertEquals(expResult2, result2);

        String str3 = "Calc: -6 ^ 6";
        String expResult3 = "46656";
        String result3 = Calculator.calculate(str3);
        assertEquals(expResult3, result3);
    }

    @org.testng.annotations.Test
    public void testMultiplication()
    {
        String str1 = "Calc: 50 * 50";
        String expResult1 = "2500";
        String result1 = Calculator.calculate(str1);
        assertEquals(expResult1, result1);

        String str2 = "Calc: 2 * 2.0";
        String expResult2 = "4";
        String result2 = Calculator.calculate(str2);
        assertEquals(expResult2, result2);

        String str3 = "Calc: 2. * 2.0";
        String expResult3 = "4";
        String result3 = Calculator.calculate(str3);
        assertEquals(expResult3, result3);

        String str4 = "Calc: -2.0 * -2.0";
        String expResult4 = "4";
        String result4 = Calculator.calculate(str4);
        assertEquals(expResult4, result4);

        String str5 = "Calc: -2.0 * 20.0";
        String expResult5 = "-40";
        String result5 = Calculator.calculate(str5);
        assertEquals(expResult5, result5);

        String str6 = "Calc: 2.0 * -20.0";
        String expResult6 = "-40";
        String result6 = Calculator.calculate(str6);
        assertEquals(expResult6, result6);
    }
}
