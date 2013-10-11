package com.sandagerdi;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: joannes
 * Date: 12/10/13
 * Time: 00.15
 */
public class Calculator {

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
        System.out.println(num1Str + ", '" + operator + "', " + num2Str);
        double num1 = 0;
        double num2 = 0;


        char format = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
        System.out.println(format);
        if (format == '.') {
            num1Str = num1Str.replace(',', '.');
            num2Str = num2Str.replace(',', '.');
            num1 = Double.parseDouble(num1Str);
            num2 = Double.parseDouble(num2Str);
        } else if (format == ',') {
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