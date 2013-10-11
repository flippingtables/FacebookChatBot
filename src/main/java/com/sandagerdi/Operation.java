package com.sandagerdi;

/**
 * User: joannes
 * Date: 11/10/13
 * Time: 23.40
 */

import java.text.DecimalFormat;

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
}