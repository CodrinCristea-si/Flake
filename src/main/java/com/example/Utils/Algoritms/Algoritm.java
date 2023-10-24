package com.example.Utils.Algoritms;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Algoritm {
    public static String getFullPath(String whatToFind) throws IOException, InterruptedException {
        String sir="where /r "+System.getProperty("user.dir")+" "+whatToFind +" >"+System.getProperty("user.dir")+"\\"+"src\\main\\java\\com\\example\\Utils\\Algoritms\\a.txt";
        System.out.println(sir);
        ProcessBuilder builder = new ProcessBuilder("cmd.exe","/c",sir);
        builder.redirectErrorStream(true);
        Process p = builder.start();
        p.waitFor();
        //System.out.println(Runtime.getRuntime().exec(sir));
        File file=new File(System.getProperty("user.dir")+"\\"+"src\\main\\java\\com\\example\\Utils\\Algoritms\\a.txt");
        Scanner scan=new Scanner(file);
        String fullPath=new String();
        while(scan.hasNextLine()){
            fullPath= scan.nextLine();
        }
        return fullPath;
    }

    public static boolean verifyForMain(String path){
        path.replace('\\','/');
        List<String> listEl= Arrays.asList(path.split("/"));
        for(String el: listEl){
            System.out.println(el);
            if(el.equals("build") || el.equals("out"))
                return false;
        }
        return true;
    }

    public static String hashPassword(String pass) throws NoSuchAlgorithmException {

        MessageDigest messageDigest= MessageDigest.getInstance("SHA-512");
        byte[] hash = messageDigest.digest(pass.getBytes());
        BigInteger no = new BigInteger(1, hash);
        String hashText = no.toString(16);

        while (hashText.length() < 32) {
            hashText = "0" + hashText;
        }

        //byte[] digest = messageDigest.digest();
        //StringBuffer hexString = new StringBuffer();
        //for (int i=0;i<digest.length;i++) {
        //    hexString.append(Integer.toHexString(0xFF & digest[i]));
        //}
        //System.out.println(hexString.toString()) ;

        //System.out.println(hashText);
        //System.out.println(hash1);
        //return hexString.toString();
        return hashText;
    }

}
