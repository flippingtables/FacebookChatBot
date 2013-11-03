package com.sandagerdi;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
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


    final static Charset ENCODING = StandardCharsets.UTF_8;

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
        //System.out.println(num1Str + ", '" + separator + "', " + num2Str);
        double num1 = 0;
        double num2 = 0;


        char format = new DecimalFormatSymbols(Locale.getDefault(Locale.Category.FORMAT)).getDecimalSeparator();
        //System.out.println(format);
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

    public String randomSaying() throws IOException {


        BufferedReader br = new BufferedReader(new FileReader("begin.txt"));
        String result = "";


        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            ArrayList<String> all = new ArrayList<String>();

            while (line != null) {
                all.add(line);
                sb.append(line);
                sb.append('\n');
                line = br.readLine();
            }

            Random r1 = new Random(all.size());
            Random r2 = new Random(all.size());
            result = all.get(r1.nextInt())+" "+all.get(r2.nextInt());

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            br.close();
        }

        String[] begin = {"Does the pope","Dont always jump where","The grass is greener","As sure as the sky is","Does the bear"};
        String[] end = {"shit in the woods.","the grass is greenest","the fence is lowest", "on the other side","is blue"};
        Random r1 = new Random(begin.length);
        Random r2 = new Random(end.length);

        //String result = WordUtils.capitalize(begin[r1.nextInt()]+" "+end[r2.nextInt()]);
        System.out.println(result);
        System.out.println("Wtf: "+result);
        return WordUtils.capitalize(result);
    }

    public String readLargerTextFile(String aFileName) throws IOException {
        Path path = Paths.get(aFileName);

        ArrayList<String> all = new ArrayList<String>();

        String result = "";


        Scanner scanner =  new Scanner(path, ENCODING.name());
            while (scanner.hasNextLine()){
                all.add(scanner.nextLine());
            }

        Random rn = new Random();
        int size = all.size();
        int minimum = 0;
        if(size % 2 == 1) size = size + 1; // turn right bound to even
        if(minimum % 2 == 0) minimum = minimum - 1; // turn left bound to odd
        int range = (size - minimum + 1) / 2;
        int randomNum =  rn.nextInt(range) * 2 + minimum;


        int num = (new Random().nextInt(size) & ~1) + 2;

        if (num>=size) num=-1;
        if (num<1) num=+1;
        if (randomNum<1) randomNum=+1;
        if (randomNum>=size) randomNum=-1;
        System.out.println("Odd: "+num);
        System.out.println("Even:" +randomNum);
        System.out.println(size);
        result = all.get(num)+" "+all.get(randomNum);
        result = result.toLowerCase();
        String output = result.substring(0, 1).toUpperCase() + result.substring(1);
        return "'"+output+"'";
    }
}
