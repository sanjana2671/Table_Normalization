/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Table_Normalization;
import java.lang.*;
import java.util.*;
import java.util.Map.Entry;
/**
 *
 * @author Sanjana
 */
public class Decomp {
    static String Norm = "";
    static String C_KEY = "";
    static String dec = "";
    static String attributes;
    static String fd;
    public static void init(int visited[]){
		for(int i=0;i<26;i++){
			visited[i] = 0;
		}
    }
    static int alpha_check(String s1,String s2){
		int visited[] = new int[26];
		for(int i=0;i<26;i++){
			visited[i] = 0;
		}

		int n1 = s1.length();
		int n2 = s2.length();

		for(int i=0;i<n1;i++){
			char ch = s1.charAt(i);
			visited[ch-'a'] = 1;
		}
		int check_flag = 0;
		for(int i=0;i<n2;i++){
			char ch = s2.charAt(i);
			if(visited[ch-'a']==0){
				check_flag = 1;
				break;
			}
		}

		if(check_flag==1){
			return 0;
		}
		return 1;
	}
    
    
    public static void f(String prefix, String chars, ArrayList<String> result) 
    {
        for (int i = 0; i < chars.length(); i++) 
        {
            result.add(prefix + chars.charAt(i));
            f(prefix + chars.charAt(i), chars.substring(i + 1), result);
        }
    }
    
    
    public static ArrayList<String> getCombinations(ArrayList<String> chars) 
    {
        ArrayList<String> result = new ArrayList<String>();
        String str = new String();
        for (String c : chars) 
        {
            str = str + c;
        }
        f("", str, result);
        return result;
    }
    
    public static boolean checkSubstring(String a, String b)
    {
        int i = 0;
        int j = 0;
        for (i=0; i<a.length()-b.length(); i++)
        {
            for (j = 0; j<b.length(); j++)
            {
               if(b.charAt(j) != a.charAt(i+j))
                  break;
            }

            if (j == b.length())
               return true;
        }

        return false;
    }
    
    public static HashSet<String> closure(HashSet<String> attributes, Map<HashSet<String>, HashSet<String>> dependencies) 
    {
        HashSet<String> closureSet = new HashSet<String>(attributes);

        for (Entry<HashSet<String>, HashSet<String>> dependency : dependencies.entrySet()) 
        {
            if (closureSet.containsAll(dependency.getKey()) && !closureSet.containsAll(dependency.getValue())) 
            {
                closureSet.addAll(dependency.getValue());
            }
        }
        return closureSet;
    }
    
    public static String sortString(String inputString) { 
        // convert input string to char array 
        char tempArray[] = inputString.toCharArray(); 
          
        // sort tempArray 
        Arrays.sort(tempArray); 
          
        // return new sorted string 
        return new String(tempArray); 
    }
    String[] total_array;
    Vector<String>left = new Vector<String>();    // vector storing leftside of each FD
     Vector<String>right = new Vector<String>();   // vector storing rightside of each FD
     
    
    
    public static Vector<String> printSubsets(char set[]){ 
    	Vector<String> answer = new Vector<String>();
        int n = set.length; 
  
        // Run a loop for printing all 2^n 
        // subsets one by one 
        for (int i = 0; i < (1<<n); i++) 
        { 
        	String sub = "";
             
  
            // Print current subset 
            for (int j = 0; j < n; j++){ 
                if ((i & (1 << j)) > 0){ 
                    sub = sub+set[j];
                } 
  			}
  			answer.add(sub);
    	}
    	return answer;
    }
    public static void deco(Vector<String> left,Vector<String> right,String[] total_array) {
        
		FindKey FK = new FindKey();
            //Scanner commandLine = new Scanner(System.in);
            System.out.println("Program is being run");
             

              System.out.print("Enter the attributes as: A,B,... or Type Exit to quit the program : ");
                //attributes = commandLine.next();
                
                FK.addAttributes(attributes);

                System.out.print("Enter the FDs as: A->B,AC->B,... or Type Exit to quit the program : ");
                //fd = commandLine.next();
                

                FK.addFD(fd);
                System.out.println(" \n\n ==> The candidate keys are: " + FK.getCandidateKeys() + "\n\n");
                C_KEY = C_KEY + FK.getCandidateKeys();
                Vector<String> ckey = new Vector<String>();
                for(String i : FK.getCandidateKeys())
                {
                    ckey.add(i);
                }


            String[] split3 = attributes.split(",");
            String total_attributes = "";

            for(int i=0;i<split3.length;i++){
                total_attributes = total_attributes + split3[i];
            }
            

            

            String[] split1 = fd.split(",");

            for( String s : split1){
                String[] split2 = s.split("->");
                if(split2[0].equals(split2[1])==false){
                    left.add(split2[0]);
                    right.add(split2[1]);
                }
            }

            total_attributes = sortString(total_attributes);

            for(int i=0;i<left.size();i++){
                String temp1 = sortString(left.get(i));
                String temp2 = sortString(right.get(i));
                left.set(i,temp1);
                right.set(i,temp2);
            }

            for(int i=0;i<ckey.size();i++){
                String temp1 = sortString(ckey.get(i));
                ckey.set(i,temp1);
            }

            String non_ckey = total_attributes;

            for(int i =0;i<ckey.size();i++){
                String temp = ckey.get(i);
                for(int j=0;j<temp.length();j++){
                    String ch = temp.substring(j,j+1);
                    non_ckey = non_ckey.replace(ch,"");
                }
            }
            // System.out.println("Left "+left);
            // System.out.println("Right "+right);
            // System.out.println("non_essential is "+non_essential);

            Vector<String>skey = new Vector<String>();

            char[] non_ckey_arr = non_ckey.toCharArray();

            Vector<String> non_combo = new Vector<String>();

            non_combo = printSubsets(non_ckey_arr);

            for(int i=0;i<ckey.size();i++){
                for(int j=0;j<non_combo.size();j++){
                    String temp1 = ckey.get(i);
                    String temp2 = non_combo.get(j);
                    temp1 = temp1+temp2;
                    temp1 = sortString(temp1);
                    skey.add(temp1);
                }
            }

            int prime_attributes[] = new int[26];
            init(prime_attributes);
            for(int i=0;i<ckey.size();i++){
                String ck = (ckey.get(i)).toString();
                for(int j=0;j<ck.length();j++){
                    prime_attributes[(int)(ck.charAt(j)-'a')] = 1;
                }
            }

            // At this point, we have all super keys, candidate keys and prime attributes and non-prime attributes.

//////////////////////////////////////////////////////// Finding Normal Forms /////////////////////////////////////////////////

            int max_normal[] = new int[left.size()];
        for(int i=0;i<left.size();i++){
            max_normal[i] = 1;
        }

        for(int i=0;i<left.size();i++){
            String left_fd = (left.get(i)).toString();
            String right_fd = (right.get(i)).toString();

        //  System.out.println("Checking for "+left_fd+" and "+right_fd);

            Normal obj2 = new Normal(left_fd,right_fd,ckey,skey,prime_attributes);
            if(obj2.check_2nf(left_fd,right_fd,ckey,prime_attributes)==1){
            //  System.out.println("Setting 2");
                max_normal[i]  = 2;
            }
            if(obj2.check_3nf(left_fd,right_fd,skey,prime_attributes)==1){
            //  System.out.println("Setting 3");
                max_normal[i] = 3;
            }
            if(obj2.check_bcnf(left_fd,right_fd,skey)==1){
            //  System.out.println("Setting 4");
                max_normal[i] = 4;
            }
        }

        System.out.println(" ");
        for(int i=0;i<left.size();i++){
            System.out.print(max_normal[i]+" ");
        }
        // Now we have max_normal forms of all fds in max_normal array

        
        int normal_form = max_normal[0];
        for(int i=1;i<left.size();i++){
            if(max_normal[i]<normal_form){
                normal_form = max_normal[i];
            }
        }
        if(normal_form==4){
            System.out.println("The Normal Form: BCNF");
            Norm = "BCNF";
        }
        else{
            System.out.println("The Normal Form: "+normal_form);
            Norm = Integer.toString(normal_form);
        }



        /////////////////////////////////////////////////////Decomposition into x+1////////////////////////////////////////////////////////////////
        if(normal_form == 4){
            dec = dec + "Already in Highest Normal Form";
        }    
        if(normal_form<4){
            String duplicate_attr  = new String();                 // duplicate string containing all attributes.
            duplicate_attr = total_attributes;
            Vector<String> Decomposition = new Vector<String>();
            Vector<String>Keys = new Vector<String>();
            for(int i=0;i<left.size();i++){
                if(max_normal[i]==normal_form){
                    String lt_temp = (left.get(i)).toString();
                    String rt_temp = (right.get(i)).toString();
                    for(int j=0;j<rt_temp.length();j++){
                        duplicate_attr = duplicate_attr.replace(Character.toString(rt_temp.charAt(j)),"");
                    }
                    String total_temp = lt_temp + rt_temp;
                    Decomposition.add(total_temp);
                    Keys.add(lt_temp);
                }
            }
            int r = 1;

            System.out.println("Decomposition:");
            System.out.println(duplicate_attr);
            dec = dec + "R"+ r++ +": (" + duplicate_attr + ")\n";

            for(int i=0;i<ckey.size();i++){
                if(alpha_check(duplicate_attr,ckey.get(i)) == 1){
                    //C_KEY = C_KEY + ckey.get(i);
                    System.out.println("Key: "+ckey.get(i));
                    if(ckey.get(i).equals("") == false){
                        dec = dec + "Key: " + ckey.get(i) + "\n\n";
                    }
                    else{
                        dec = dec + duplicate_attr + "\n\n";
                    }
                    break;
                }
            }

            for(int i=0;i<Decomposition.size();i++){
               // System.out.println(Decomposition.get(i));
                dec = dec + "R" + r++ +": (" + Decomposition.get(i)+")" + "\n";
                //System.out.println("Key : "+Keys.get(i));
                if(Keys.get(i).equals("")){
                    dec = dec  + Decomposition.get(i) + "\n\n";
                }
                else{    
                    dec = dec + "Key: " + Keys.get(i) + "\n\n";
                }
            }
        }
    }
}
    

