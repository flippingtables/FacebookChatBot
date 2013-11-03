package foo;

import com.sandagerdi.BotFunctions;
import com.sandagerdi.Calculator;
import junit.framework.TestCase;
import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    @org.testng.annotations.Test
    public void testMultiplications()
    {
        String str1 = "Calc: 50 * 50 * 50";
        String expResult1 = "2500";
        String result1 = Calculator.calculate(str1);
        assertEquals(expResult1, result1);

        String str2 = "Calc: 50 * 50 * 0";
        String expResult2 = "0";
        String result2 = Calculator.calculate(str1);
        assertEquals(expResult2, result2);

        String str3 = "Calc: 5.0 * 50 * 1.0";
        String expResult3 = "250.0";
        String result3 = Calculator.calculate(str1);
        assertEquals(expResult2, result2);
    }
    @org.testng.annotations.Test
    public void testTok()
    {
        String str1 = "50 * 50 / 50.0 + 32 - 12 ^ (1 + 3)";
        List<Object> tokenize = null;
        try {
            tokenize = Calculator.tokenize(str1);

        } catch(IOException e){

        }
        //System.out.println(tokenize.toString());
        Double a = (Double)tokenize.get(0);
        Double b = (Double)tokenize.get(2);
        //System.out.println(a+b);
        assertEquals(100.0, a+b);
    }
    @org.testng.annotations.Test
    public void testRPN()
    {
        String str1 = "Calc: (1+2) * ( 3 / 4 ) ^ ( 5 + 6 )"; //0,1267054081
//        str1=str1.replaceAll(" ", "");
//        String[] input = str1.split("");
//        String[] input1 = new String[input.length-1];
//        System.arraycopy(input,1,input1,0,input.length-1);
//        String[] output = Calculator.infixToRPN(input1);
//        String a = "";
//        for (String token : output) {
//            System.out.print(token + " ");
//            a += token + " ";
//        }
//        System.out.println();
//        System.out.println(a);
//        Double d = Calculator.evalRPN(a);
        //String [] a = Calculator.cleanupBeforeInfixToRPN(str1)  ;
        Double a = Calculator.calc1(str1);
        System.out.println("M: "+a);
        assertEquals(0.126, a, 0.01);
    }

    @org.testng.annotations.Test
    public void testRPN1()
    {
        String str1 = "Calc: 5 + ((1 + 2) * 4) − 3"; // 14
        Double a = Calculator.calc1(str1);
        System.out.println("A1: "+a);
        assertEquals(14.0, a, 0.01);
    }

    @org.testng.annotations.Test
    public void testRPN2()
    {
        String str1 = "Calc: 5 + ((1 + 2) * 4) − 3";  // = 14
        Double a = Calculator.calc1(str1);
        System.out.println("A2: "+a);
        assertEquals(14.0, a, 0.01);
    }

    @org.testng.annotations.Test
    public void testRPN3()
    {
        String str1 = "Calc: (5 - 2) * 4 / 3"; // = 4
        Double a = Calculator.calc1(str1);
        System.out.println("A3: "+a);
        assertEquals(4.0, a, 0.01);
    }

    @org.testng.annotations.Test
    public void testRPN4()
    {
        String str1 = "Calc: 1 / 3 - (2 ^ 3)"; // = -7.667
        Double a = Calculator.calc1(str1);
        System.out.println("A4: "+a);
        System.out.println("Len: "+str1.charAt(str1.length()-1));
        assertEquals(-12.667, a, 0.01);
    }

    @org.testng.annotations.Test
    public void randomSaying() throws IOException {


        BufferedReader br = new BufferedReader(new FileReader("begin.txt"));
//        String result = "";
//        try {
//            StringBuilder sb = new StringBuilder();
//            String line = br.readLine();
//
//            ArrayList<String> all = new ArrayList<String>();
//
//            while (line != null) {
//                all.add(line);
//                sb.append(line);
//                sb.append('\n');
//                line = br.readLine();
//            }
//
//            Random r1 = new Random(all.size());
//            Random r2 = new Random(all.size());
//            result = all.get(r1.nextInt())+" "+all.get(r2.nextInt());
//
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        } finally {
//            br.close();
//        }
//
//        String[] begin = {"Does the pope","Dont always jump where","The grass is greener","As sure as the sky is","Does the bear"};
//        String[] end = {"shit in the woods.","the grass is greenest","the fence is lowest", "on the other side","is blue"};


        //String result = WordUtils.capitalize(begin[r1.nextInt()]+" "+end[r2.nextInt()]);

        //return WordUtils.capitalize(result);
        BotFunctions b = new BotFunctions();
        String result = "";
        try {
            for (int i =0;i<20;i++){
                result =b.readLargerTextFile("begin.txt");
                System.out.println(result);
            }

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        assertEquals("Murtur",result);
    }
}
