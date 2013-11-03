package com.sandagerdi;

import java.awt.event.KeyEvent;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: joannes
 * Date: 12/10/13
 * Time: 00.15
 */
public class Calculator {

    public static List<Object> tokenize(String s) throws java.io.IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-');  // Don't parse minus as part of numbers.
        tokenizer.ordinaryChar('/');
        List<Object> tokBuf = new ArrayList<Object>();
        tokenizer.commentChar(KeyEvent.VK_DIVIDE);
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    //System.out.println("nval: " + tokenizer.nval);
                    tokBuf.add(tokenizer.nval);
                    break;
                case StreamTokenizer.TT_WORD:
                    tokBuf.add(tokenizer.sval);
                    break;

                default:  // operator
                    tokBuf.add(String.valueOf((char) tokenizer.ttype));
            }
        }
        return tokBuf;
    }

    public static Double rpn(List<Object> input){
        ArrayDeque<Double> s = new ArrayDeque<Double>();

        for (Object o : input){

            if (o.getClass().equals("java.lang.Double")){
                s.push((Double)o);
            }


        }

        return 1.0;
    }


    // Associativity constants for operators
    private static final int LEFT_ASSOC = 0;
    private static final int RIGHT_ASSOC = 1;

    // Supported operators
    private static final Map<String, int[]> OPERATORS = new HashMap<String, int[]>();
    static {
        // Map<"token", []{precendence, associativity}>
        OPERATORS.put("+", new int[] { 0, LEFT_ASSOC });
        OPERATORS.put("-", new int[] { 0, LEFT_ASSOC });
        OPERATORS.put("*", new int[] { 5, LEFT_ASSOC });
        OPERATORS.put("/", new int[] { 5, LEFT_ASSOC });
        OPERATORS.put("%", new int[] { 5, LEFT_ASSOC });
        OPERATORS.put("^", new int[] { 10, RIGHT_ASSOC });
    }

    /**
     * Test if a certain is an operator .
     * @param token The token to be tested .
     * @return True if token is an operator . Otherwise False .
     */
    private static boolean isOperator(String token) {
        return OPERATORS.containsKey(token);
    }

    /**
     * Test the associativity of a certain operator token .
     * @param token The token to be tested (needs to operator).
     * @param type LEFT_ASSOC or RIGHT_ASSOC
     * @return True if the tokenType equals the input parameter type .
     */
    private static boolean isAssociative(String token, int type) {
        if (!isOperator(token)) {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
        if (OPERATORS.get(token)[1] == type) {
            return true;
        }
        return false;
    }

    /**
     * Compare precendece of two operators.
     * @param token1 The first operator .
     * @param token2 The second operator .
     * @return A negative number if token1 has a smaller precedence than token2,
     * 0 if the precendences of the two tokens are equal, a positive number
     * otherwise.
     */
    private static final int cmpPrecedence(String token1, String token2) {
        if (!isOperator(token1) || !isOperator(token2)) {
            throw new IllegalArgumentException("Invalied tokens: " + token1
                    + " " + token2);
        }
        return OPERATORS.get(token1)[0] - OPERATORS.get(token2)[0];
    }

    public static String[] cleanupBeforeInfixToRPN(String str){
        String math = str.split(":")[1];
        //String str1 = "(1+2) * ( 3 / 4 ) ^ ( 5 + 6 )";
        math=math.replaceAll(" ", "");
        String[] input = math.split("");
        String[] input1 = new String[input.length-1];
        System.arraycopy(input,1,input1,0,input.length-1);
        return input1;
    }
    public static String[] infixToRPN(String[] inputTokens) {
        ArrayList<String> out = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        // For all the input tokens [S1] read the next token [S2]
        for (String token : inputTokens) {
            if (isOperator(token)) {
                // If token is an operator (x) [S3]
                while (!stack.empty() && isOperator(stack.peek())) {
                    // [S4]
                    if ((isAssociative(token, LEFT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) <= 0)
                    || (isAssociative(token, RIGHT_ASSOC) && cmpPrecedence(
                            token, stack.peek()) < 0)) {
                        out.add(stack.pop()); 	// [S5] [S6]
                        continue;
                    }
                    break;
                }
                // Push the new operator on the stack [S7]
                stack.push(token);
            } else if (token.equals("(")) {
                stack.push(token); 	// [S8]
            } else if (token.equals(")")) {
                // [S9]
                while (!stack.empty() && !stack.peek().equals("(")) {
                    out.add(stack.pop()); // [S10]
                }
                stack.pop(); // [S11]
            } else {
                out.add(token); // [S12]
            }
        }
        while (!stack.empty()) {
            out.add(stack.pop()); // [S13]
        }
        String[] output = new String[out.size()];
        return out.toArray(output);
    }

    private static String cleanExpr(String expr){
        //remove all non-operators, non-whitespace, and non digit chars
        //expr = expr.replaceAll(" ", "");
        expr = expr.replaceAll("[^\\^\\*\\+\\-\\d/\\s]", "");
        return expr;
    }
    public static Double evalRPN(String expr){
        String cleanExpr = cleanExpr(expr);
        LinkedList<Double> stack = new LinkedList<Double>();
        System.out.println("Input\tOperation\tStack after");
        for(String token : cleanExpr.split("\\s")){
            System.out.print(token+"\t");
            Double tokenNum = null;
            try{
                tokenNum = Double.parseDouble(token);
            }catch(NumberFormatException e){}
            if(tokenNum != null){
                System.out.print("Push\t\t");
                stack.push(Double.parseDouble(token+""));
            }else if(token.equals("*")){
                System.out.print("Operate\t\t");
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand * secondOperand);
            }else if(token.equals("/")){
                System.out.print("Operate\t\t");
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand / secondOperand);
            }else if(token.equals("-")){
                System.out.print("Operate\t\t");
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand - secondOperand);
            }else if(token.equals("+")){
                System.out.print("Operate\t\t");
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(firstOperand + secondOperand);
            }else if(token.equals("^")){
                System.out.print("Operate\t\t");
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                stack.push(Math.pow(firstOperand, secondOperand));
            }else{//just in case
                System.out.println("Error: "+ token);
                break;
            }
            System.out.println(stack);
        }
        System.out.println("Final answer: " + stack.peek());
        return stack.pop();
    }

    public static Double calc1(String str){
        String[] arr = Calculator.cleanupBeforeInfixToRPN(str);
//        String math = str.split(":")[1];
//        //String str1 = "(1+2) * ( 3 / 4 ) ^ ( 5 + 6 )";
//        math=math.replaceAll(" ", "");
//        String[] input = math.split("");
//        String[] input1 = new String[input.length-1];
//        System.arraycopy(input,1,input1,0,input.length-1);
        String[] output = Calculator.infixToRPN(arr);
        String a = "";
        for (String token : output) {
            //System.out.print(token + " ");
            a += token + " ";
        }
        System.out.println("X:" + a);
        Double d = Calculator.evalRPN(a);
        return d;
    }

    public static String calculate(String str) {
        String split = str.split(": ")[1];

        //This is going to split a math function like this in to 3: "33,3-5,6"
        String REGEX = "(-?\\d+[\\,\\.]?\\d*)(\\s*?[\\+\\*\\-\\/\\%\\^]\\s*?)(-?\\d+[\\,\\.]?\\d*)";

        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(split);
        String num1Str = "";
        String num2Str = "";
        String operator = "";
        String result = "Incorrect values entered, format is: 'Calc: 33,2+33,3'";
        if (m.find()) {
            num1Str = m.group(1);
            operator = m.group(2);
            operator = operator.replaceAll("\\s+", ""); // Remove whitespace
            num2Str = m.group(3);

        }
        //System.out.println(num1Str + ", '" + operator + "', " + num2Str);
        double num1 = 0;
        double num2 = 0;


        char format = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
        //System.out.println(format);
        if ((format == '.') || (format == ',')) {
            num1Str = num1Str.replace(',', '.');
            num2Str = num2Str.replace(',', '.');
            num1 = Double.parseDouble(num1Str);
            num2 = Double.parseDouble(num2Str);
        }

        if (operator.equals("+")) {
            result = Operation.addition(num1, num2);
        }
        if (operator.equals("-")) {
            result = Operation.substraction(num1, num2);
        }
        if (operator.equals("/")) {
            result = Operation.division(num1, num2);
        }
        if (operator.equals("*")) {
            result = Operation.multiplication(num1, num2);
        }
        if (operator.equals("%")) {
            result = Operation.modulo(num1, num2);
        }
        if (operator.equals("^")) {
            result = Operation.exponentiation(num1, num2);
        }

        return result;
    }
}