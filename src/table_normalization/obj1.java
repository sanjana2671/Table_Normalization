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
public class obj1 {

	String str;
	int visited[];
	int check[];
	int global_flag = 0;
	int result;
	Vector<String>left;
	Vector<String>right;
	public obj1(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		this.str = str;
		this.visited = visited;
		this.check = check;
		this.left = left;
		this.right = right;
		init(visited);
		init(check);
		global_flag = 0;
		result = start(str,visited,check,left,right);
	}
	public void init(int arr[]){
		for(int i=0;i<26;i++){
			arr[i] = 0;
		}
	}
	public void change(char ch,int visited[],int check[],Vector<String>left,Vector<String>right){
		check[(int)(ch-'a')] = 1;
	//	System.out.println("Setting Check Array");
		// for(int i=0;i<26;i++){
		// 	System.out.print(check[i]+" ");
		// }
		int temp_flag = 0;
		int row = 0;
		int index = 0;
		for(int i=0;i<right.size();i++){
			int flag = 0;
			String b = (right.get(i)).toString();
			for(int j=0;j<b.length();j++){
				if(b.charAt(j)==ch){
					flag = 1;
					row = i;
					index = j;
					break;
				}
			}
			if(flag==1){
				break;
			}
		}
		//System.out.println("Found "+ch+" at "+row+" "+index);
		String a = (left.get(row)).toString();
		//System.out.print("Corresesponding left "+a);
		//System.out.println(" ");
		for(int i=0;i<a.length();i++){
			if(visited[(int)(a.charAt(i)-'a')]==0){
				temp_flag = 1;
			//	System.out.println(a.charAt(i)+" is also not visited");
				if(check[(int)(a.charAt(i)-'a')]==1){
				//	System.out.println(a.charAt(i)+" has check==1. Hecnce fucked");
					global_flag = 1;
					return;
				}
				else{
					//System.out.println("Calling change on "+a.charAt(i));
					change(a.charAt(i),visited,check,left,right);
				}
			}
		}
		if(temp_flag==0 && global_flag==0){
			//System.out.println("Full left is visited, Hence right also visit for "+ch);
			visited[(int)(ch-'a')] = 1;
		}
		return;
	}
	public int iterate(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		int temp_flag = 0;
		for(int i=0;i<left.size();i++){
			String a = (left.get(i)).toString();
		//	System.out.println("Checking left fd: "+a);
			for(int j=0;j<a.length();j++){
				if(visited[(int)(a.charAt(j)-'a')]==0){
					if(check[(int)(a.charAt(j)-'a')]==0){
					//	System.out.println(a.charAt(j)+" not visited yet,hence calling check");
						change(a.charAt(j),visited,check,left,right);
					}
					if(global_flag==1){
						return 0;
					}
				}
			}
			for(int j=0;j<a.length();j++){
				if(visited[(int)(a.charAt(j)-'a')]==0){
					temp_flag = 1;
					break;
				}
			}
			if(temp_flag==0){
				String b = (right.get(i)).toString();
				//System.out.println("Setting "+b+" visited to 1");
				for(int j=0;j<b.length();j++){
					visited[(int)(b.charAt(j)-'a')] = 1;
				}
			}
		}
		return 1;
	}
	public int start(String str,int visited[],int check[],Vector<String>left,Vector<String>right){
		int flag = 0;
		//System.out.println("initial visited:");
		// for(int i=0;i<26;i++){
		// 	System.out.print(visited[i]+" ");
		// }
		for(int i=0;i<str.length();i++){
			visited[(int)(str.charAt(i)-'a')] = 1;
		}
	//	System.out.println("Edited Visited:");
		return iterate(str,visited,check,left,right);
	}

}
