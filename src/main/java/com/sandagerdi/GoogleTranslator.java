package com.sandagerdi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class GoogleTranslator {
    public GoogleTranslator() {

    }

    public String translate(String toTranslate)
            throws UnsupportedEncodingException {
        String tr = toTranslate.split(": ")[1];
        tr = tr.replace(" ", "+");
        String a = "http://translate.google.com/m?hl=en&sl=auto&tl=es&ie=UTF-8&prev=_m&q="
                + tr;
        String b = getHTML(a);

        String newString = new String(b.getBytes("UTF-8"), "UTF-8");
        String pattern = "<div dir=\"ltr\" class=\"t0\">(.*?)</div>";
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(newString);
        String result = "";
        if (matcher.find()) {
            result = new String(matcher.group(1).getBytes("UTF-8"), "UTF-8");
            System.out.println(new String(result.getBytes("UTF-8"), "UTF-8"));
            //byte[] ptext = result.getBytes("UTF-8");
            // result = matcher.group(1);
        }
        // String newString = new String(result.getBytes("UTF-8"), "UTF-8");
        return result;
    }

    String getHTML(String urlToRead) {
        URL url;
        HttpURLConnection conn;
        BufferedReader rd;
        String line;
        String result = "";
        try {
            url = new URL(urlToRead);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9) AppleWebKit/537.71 (KHTML, like Gecko) Version/7.0 Safari/537.71");
            rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            while ((line = rd.readLine()) != null) {
                result += line;
            }
            rd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
