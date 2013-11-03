package com.sandagerdi;

/**
 * User: joannes
 * Date: 11/10/13
 * Time: 23.40
 */

import java.io.StringReader;
import java.text.DecimalFormat;
import java.io.StreamTokenizer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;


class Operation {

    public static String addition(double num1, double num2) {
        return new DecimalFormat("##.##").format(num1 + num2);
    }

    public static String substraction(double num1, double num2) {
        return new DecimalFormat("##.##").format(num1 - num2);
    }

    public static String multiplication(double num1, double num2) {
        return new DecimalFormat("##.##").format(num1 * num2);
    }

    public static String division(double num1, double num2) {
        if (num2 == 0 || num2 == 0.0) {
            return "Divide by zero.";
        } else {
            return new DecimalFormat("##.##").format(num1 / num2);
        }
    }

    public static String modulo(double num1, double num2) {
        return new DecimalFormat("##.##").format(num1 % num2);
    }

    public static String exponentiation(double num1, double num2) {
        return new DecimalFormat("##.##").format(Math.pow(num1, num2));
    }

    public static List<String> tokenize(String s) throws java.io.IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(new StringReader(s));
        tokenizer.ordinaryChar('-');  // Don't parse minus as part of numbers.
        List<String> tokBuf = new ArrayList<String>();
        while (tokenizer.nextToken() != StreamTokenizer.TT_EOF) {
            switch(tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    tokBuf.add(String.valueOf(tokenizer.nval));
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


}