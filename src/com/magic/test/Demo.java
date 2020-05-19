package com.magic.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Demo {
	public static void main(String[]args) //program to count max digit in a 10 digit mobile number
	{


		//		HashMap<Integer, String> map=new HashMap<Integer, String>();
		//		
		//		map.put(1, "ram");
		//		map.put(2, "rakesh");
		//		map.put(3, "sita");
		//		Iterator itr=map.entrySet().iterator();
		//		while(itr.hasNext()) {
		//			Map.Entry m=(Map.Entry)itr.next();
		//			
		//			System.out.println("key is: "+m.getKey()+" value is "+m.getValue());
		//					}
		//		System.out.println(map);

		/*
		 * StringTokenizer ob=new StringTokenizer(str1);
		 * 
		 * while(ob.hasMoreElements()) {
		 * 
		 * System.out.print(ob.nextElement()); }
		 * 
		 */
		Map<String, Integer> map=new HashMap<String, Integer>();
		String s="ram is talk to shyam";

		String []array=s.trim().split("");



		Set<String> set=new HashSet<String>();

		for(String str:array) 
		{
			if(set.add(str)==false) 
			{
				System.out.println("duplicate::"+str);
			}

		}

		for(int i=0;i<array.length;i++) 
		{
			if(map.containsKey(array[i])) 
			{
				map.put(array[i], map.get(array[i])+1);
			}
			else {
				map.put(array[i],1);
			}
		}
		Set<Entry<String, Integer>> dupelicat=map.entrySet();
		for(Entry<String, Integer> temp:dupelicat) {

			if(temp.getValue()>1) 
			{
				System.out.println("using map::"+temp.getKey());	
			}
		}
		
		int []a= {1,2,3,1,2,4,5,6,4};
		List<Integer> list=new ArrayList<Integer>();
		for(int data:a) 
		{
			list.add(data);
		}
		System.out.println("Array without duplicate::"+findduplicate(list));
	}
	
	public static Set<Integer> findduplicate(List<Integer> list)
	{
		final Set<Integer> result=new HashSet<Integer>();
		final Set<Integer> temp=new HashSet<Integer>();
		
		for(int a:list) 
		{
			if(!temp.contains(a)) 
			{
				result.add(a);
			}
		}
		return result;
	}

}

