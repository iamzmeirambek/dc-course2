package org.example;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Hamming {
    public static String dataBlocks = new String();

    public static String main(String a, String b) {
        dataBlocks="";
        expandBlocks = "";
        String data = b;
        System.out.println("Our code");
        System.out.println(data);
        System.out.println("Step 2");
        System.out.println("---------------------------------------------------------------------------------");
        String[] arrSplit = data.split("(?<=\\G.{11})");
        for (int i=0; i < arrSplit.length; i++)
        {
            dataBlocks +=("Data matrix: "+(i+1) + ":" +arrSplit[i])+"\n";
        }
        System.out.println(dataBlocks);
        System.out.println("Step 3");
        System.out.println("---------------------------------------------------------------------------------");
        for(int i=0; i<arrSplit.length; i++){
            arrSplit[i]=hammingcode(arrSplit[i]);
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter("C://Users//iamzm//IdeaProjects//dc-course-master//data.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String str: arrSplit) {
            try {
                writer.write(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Step 5");
        System.out.println("---------------------------------------------------------------------------------");
        File myObj2 = new File("C://Users//iamzm//IdeaProjects//dc-course-master//data.txt");
        Scanner myReader2 = null;
        try {
            myReader2 = new Scanner(myObj2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String data2 = myReader2.nextLine();
        return data2;
    }

    public static String getDataBlocks() {
        return dataBlocks;
    }
    public static String expandBlocks = new String();
    private static String hammingcode(String s) {
        System.out.println(s+":");
        char[] ch = new char[11];
        for (int i = 0; i < s.length(); i++) {
            ch[i] = s.charAt(i);
        }
        if(s.length()<11){
            ch=untildead(ch,s.length());
            System.out.println(ch);
        }
        expandBlocks +=(("Expand the block to 16 bits:___"+ch[0]+"_"+ch[1]+ch[2]+ch[3]+"_"+ch[4]+ch[5]+ch[6]+ch[7]+ch[8]+ch[9]+ch[10]))+"\n";
        int p1;
        int p2;
        int p3;
        int p4;
        int p0;
        p1 = (int)ch[1]^(int)ch[4]^(int)ch[8]^(int)ch[0]^(int)ch[3]^(int)ch[6]^((int)ch[10]-48);
        p2 = (int)ch[2]^(int)ch[5]^(int)ch[9]^(int)ch[0]^(int)ch[3]^(int)ch[6]^((int)ch[10]-48);
        p3 = (int)ch[1]^(int)ch[2]^(int)ch[3]^(int)ch[7]^(int)ch[8]^(int)ch[9]^((int)ch[10]-48);
        p4 = (int)ch[4]^(int)ch[5]^(int)ch[6]^(int)ch[7]^(int)ch[8]^(int)ch[9]^((int)ch[10]-48);
        p0=p1^p2^(int)ch[0]^p3^(int)ch[1]^((int)ch[2]-48)^((int)ch[3]-48)^p4^((int)ch[4]-48)^((int)ch[5]-48)^((int)ch[6]-48)^((int)ch[7]-48)^((int)ch[8]-48)^((int)ch[9]-48)^((int)ch[10]-48);
        expandBlocks +=("p1: b5+b9+b13+b3+b7+b11+b15 " + ch[1] + ch[4] + ch[8] + ch[0] + ch[3] + ch[6] + ch[10] + "= " +p1)+"\n";
        expandBlocks +=("p2: b2+b6+b10+b14+b3+b7+b11+b15" + ch[2] + ch[5] + ch[9] + ch[0] + ch[3] + ch[6] + ch[10] + "= " +p2)+"\n";
        expandBlocks +=("p3: b5+b6+b7+b12+b13+b14+b15" + ch[1] + ch[2] + ch[3] + ch[7] + ch[8] + ch[9] + ch[10] + "= " +p3)+"\n";
        expandBlocks +=("p4: b8+b9+b10+b11+b12+b13+b14+b15" + ch[4] + ch[5] + ch[6] + ch[7] + ch[8] + ch[9] + ch[10] + "= " +p4)+"\n";
        expandBlocks +=("p0: b1+b2+b3+b4+b5+b6+b7+b8+b9+b10+b11+b12+b13+b14+b15" + p1 + p2 + ch[0] + p3 + ch[1] + ch[2] + ch[3] + p4 + ch[4] + ch[5] + ch[6] + ch[7] + ch[8] + ch[9] + ch[10] + "= " +p0)+"\n";
        s= Integer.toString(p0)+Integer.toString(p1)+Integer.toString(p2)+ch[0]+Integer.toString(p3)+ch[1]+ch[2]+ch[3]+Integer.toString(p4)+ch[4]+ch[5]+ch[6]+ch[7]+ch[8]+ch[9]+ch[10];
        expandBlocks +=("Encoded bitstring:" + s)+ "\n";
        return s;
    }

    public static String getExpandBlocks() {
        return expandBlocks;
    }

    private static char[] untildead(char[] ch, int length) {
        for(int i=length; i < 11; i++){
            ch[i]='0';
        }
        return ch;
    }


}

