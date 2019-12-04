/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resources;
import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Resource {

    public static Properties props = new Properties();
    public static String projectName = "Accuracy-Constrained Privacy-Preserving Access Control Mechanism for Relational Data";
    
    static {
        try {
            InputStream is = Resource.class.getClassLoader().getResourceAsStream("data.properties");
            props = new Properties();
            props.load(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getBackGround() {
        Random r = new Random();
        int Low = 1;
        int High = 5;
        int R = r.nextInt(High - Low) + Low;

        return "bg4.jpg";
    }

    public static String getMyUrl() {
        String myUrl = props.getProperty("myip");
        return myUrl;

    }

    public static String getmyIp() {
        String myUrl = props.getProperty("myip");
        myUrl = "http://" + getMyIp4() + myUrl;
        return "";

    }
    
    public static String getMyIp4() {
        InetAddress ipa;
        String ip = "";
        try {

            ipa = InetAddress.getLocalHost();
            ip = ipa.getHostAddress();
            //System.out.println("Current IP address : " + ip);

        } catch (UnknownHostException e) {
            return e.toString();
        }       
        return ip;
    }

    public static String getdbDriver() {
        String myUrl = props.getProperty("forName");
        return myUrl;
    }

    public static String getDbUrl() {
        String myUrl = props.getProperty("dburl");
        return myUrl;
    }

    public static String getDbUser() {
        String myUrl = props.getProperty("dbuser");
        return myUrl;
    }

    public static String getDbPass() {
        String myUrl = props.getProperty("dbpass");
        return myUrl;
    }

    public static String getDataSet() {
        String myUrl = props.getProperty("datapath");
        return myUrl;
    }

    public static String ppmString(String mail) {
        return getAnonChar();
    }

    public static String ppmStringz(String data) {
        String rez = "";
        for (int i = 0; i < data.length(); i++) {
            rez += getAnonChar();
        }
        return rez;
    }

    public static String ppmMail(String mail) {
        String parts[] = mail.split("@");
        String pre = "";
        for (int i = 0; i < parts[0].length(); i++) {
            pre += getAnonChar();
        }
        return pre + "@" + parts[1];
    }

    public static String ppmZip(String zip) {
        char pre[] = zip.toCharArray();
        String rez = "";
        for (int i = 0; i < pre.length; i++) {
            Random r = new Random();
            int Low = 0;
            int High = pre.length;
            int R = r.nextInt(High - Low) + Low;

            if (R % 2 == 0) {
                rez += getAnonChar();
            } else {
                rez += pre[i];
            }
        }
        return rez;
    }

    public static String[] dataPerturbation(String[] data, String type) {
        if (type.trim().equals("num")) {
            for (int i = 0; i < data.length; i++) {
                String rez = data[i];

                int R1 = Integer.parseInt("" + rez.trim().charAt(0)) + Integer.parseInt("" + rez.trim().charAt(2));
                int R2 = Integer.parseInt("" + rez.trim().charAt(1)) + Integer.parseInt("" + rez.trim().charAt(3));
                R1 = (R1 > 9 ? R1 - 9 : R1);
                R2 = (R2 > 9 ? R2 - 9 : R2);

                data[i] = rez.trim().substring(0, rez.length() - 2) + R1 + R2;
            }
        }
        return data;
    }

    public static String[] dataPerturbationBegin(String[] data, String type) {
        if (type.trim().equals("num")) {
            for (int i = 0; i < data.length; i++) {
                String rez = data[i];
                int R1 = Integer.parseInt("" + rez.trim().charAt(0)) + Integer.parseInt("" + rez.trim().charAt(2));
                int R2 = Integer.parseInt("" + rez.trim().charAt(1)) + Integer.parseInt("" + rez.trim().charAt(3));
                R1 = (R1 > 9 ? R1 - 9 : R1);
                R2 = (R2 > 9 ? R2 - 9 : R2);

                data[i] = R1 + R2 + rez.trim().substring(0, rez.length() - 2);
            }
        }
        return data;
    }

    public static String[] emailPerturbationBegin(String[] data, String[] name) {
        for (int i = 0; i < data.length; i++) {
            String rez = data[i];
            String parts[] = rez.split("@");
            data[i] = name[i].trim().toLowerCase() + "@" + parts[1];
        }

        return data;
    }

    public static String[] addressPerturbationBegin(String[] data) {
        for (int i = 0; i < data.length; i++) {
            String rez = data[i];

            Pattern patternBuilding = Pattern.compile("-?\\d+");

            Matcher matcherBuilding = patternBuilding.matcher(rez);

            int R1 = 0;
            int R2 = 0;
            int R3 = 0;

            String key = "";
            if (matcherBuilding.find()) {
                System.out.println("Building number is " + matcherBuilding.group());
                key = matcherBuilding.group();
                R1 = Integer.parseInt("" + key.trim().charAt(0)) + Integer.parseInt("" + key.trim().charAt(1));
                R2 = key.length();
                R3 = R1 + R2;
            }

            data[i] = rez.trim().replace(key, "" + R1 + R2 + R3 + key.charAt(0));
        }

        return data;
    }

    public static String getAnonChar() {
        Random r = new Random();
        int Low = 0;
        int High = 100;
        int R = r.nextInt(High - Low) + Low;
        if(R%4==0)
            return "!";
        if(R%2==0)
            return "?";
        if(R%2!=0)
            return "*";
        
        return "+";
    }
}
