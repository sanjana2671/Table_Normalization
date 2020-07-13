/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Table_Normalization;
import java.lang.*;
import java.util.*;

/**
 *
 * @author Sanjana
 */
public class Normal {
    	String s1;
    String s2;
    Vector<String>ckey;
    Vector<String>skey;
    int prime_attributes[];

    Normal(String s1,String s2,Vector<String>ckey,Vector<String>skey,int prime_attributes[]){
        this.s1 = s1;
        this.s2 = s2;
        this.ckey = ckey;
        this.skey = skey;
        this.prime_attributes = prime_attributes;
    }

    int check_2nf(String s1,String s2,Vector<String>ckey,int prime_attributes[]){
        //System.out.println("Checking for 2NF");
        int s1_flag = 0;
        for(int i=0;i<ckey.size();i++){
            if(s1.equals((ckey.get(i)).toString())==true){
            //  System.out.println("Match Found in ckey");
                s1_flag = 1;
                break;
            }
        }
        if(s1_flag==1){
        //  System.out.println("IN 2NF");
            return 1;
        }
        int s2_flag = 0;
        for(int i=0;i<s2.length();i++){
            if(prime_attributes[(int)(s2.charAt(i)-'a')]==0){
            //  System.out.println(s2+ " not prime attributes");
                s2_flag = 1;
                break;
            }
        }
        if(s2_flag==0){
        //  System.out.println("IN 2NF");
            return 1;
        }
        s1_flag = 0;
        for(int i=0;i<s1.length();i++){
            if(prime_attributes[(int)(s1.charAt(i)-'a')]==0){
            //  System.out.println("Not subset of ckey");
                s1_flag = 1;
            }
        }
        if(s1_flag==1){
        //  System.out.println("IN 2NF");
            return 1;
        }
        return 0;
    }
    int check_3nf(String s1,String s2,Vector<String>skey,int prime_attributes[]){
    //  System.out.println("Checing 3nf");
        int t_flag = 0;
        for(int i=0;i<skey.size();i++){
            if(s1.equals((skey.get(i)).toString())==true){
            //  System.out.println("Found match in skey");
                t_flag = 1;
                break;
            }
        }
        if(t_flag==1){
        //  System.out.println("In 3nf");
            return 1;
        }
        int s2_flag = 0;
        for(int i=0;i<s2.length();i++){
            if(prime_attributes[(int)(s2.charAt(i)-'a')]==0){
            //  System.out.println("Not prime attr");
                s2_flag = 1;
                break;
            }
        }
        if(s2_flag==0){
        //  System.out.println("In 3nf");
            return 1;
        }
    //  System.out.println("NOt 3nf");
        return 0;
    }
    int check_bcnf(String s1,String s2,Vector<String>skey){
        int t_flag = 0;
        for(int i=0;i<skey.size();i++){
            if(s1.equals((skey.get(i)).toString())==true){
                //System.out.println("Found match in skey");
                t_flag = 1;
                break;
            }
        }
        if(t_flag==1){
            //System.out.println("IN BCNF");
            return 1;
        }
        //System.out.println("Not in bcnf");
        return 0;
    }
}
