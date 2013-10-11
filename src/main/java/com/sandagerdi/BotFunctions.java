package com.sandagerdi;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: joannes
 * Date: 10/10/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class BotFunctions {

    public String reverseString(String str) {
        String split = str.split(":")[1];
        return StringUtils.reverse(split);
    }

    public String calculate(String str){
        String split = str.split(": ")[1];

        //This is going to split a math function like this in to 3: "33,3-5,6"
        String REGEX = "(\\d+[\\,\\.]?\\d+)([\\+\\*\\-\\/\\%\\^])(\\d+[\\,\\.]?\\d+)";
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(split);
        String num1Str = "";
        String num2Str = "";
        String separator = "";
        String result = "Incorrect values entered, format is: 'Calc: 33,2+33,3'";
        if (m.find()){
            num1Str = m.group(1);
            separator = m.group(2);
            num2Str = m.group(3);

        }
        System.out.println(num1Str + ", '" + separator + "', " + num2Str);
        double num1 = 0;
        double num2 = 0;


        char format = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
        System.out.println(format);
        if (format == '.'){
            num1Str = num1Str.replace(',', '.');
            num2Str = num2Str.replace(',', '.');
            num1 = Double.parseDouble(num1Str);
            num2 = Double.parseDouble(num2Str);
        }else if (format == ','){
            num1Str = num1Str.replace(',', '.');
            num2Str = num2Str.replace(',', '.');
            num1 = Double.parseDouble(num1Str);
            num2 = Double.parseDouble(num2Str);
        }
        if (separator.equals("*")){
            result = new DecimalFormat("##.##").format(num1*num2);
        } if (separator.equals("+")) {
            result = new DecimalFormat("##.##").format(num1+num2);
        } if (separator.equals("-")) {
            result = new DecimalFormat("##.##").format(num1-num2);
        } if (separator.equals("/")) {
            if (num2==0 || num2 == 0.0){
                result = "Fuck off, divide by zero.";
            } else{
                result = new DecimalFormat("##.##").format(num1/num2);
            }

        } if (separator.equals("%")) {
            result = new DecimalFormat("##.##").format(num1%num2);
        } if (separator.equals("^")) {
            result = new DecimalFormat("##.##").format(Math.pow(num1, num2));
        }
        return result;
    }

}
