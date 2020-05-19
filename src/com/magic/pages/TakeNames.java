package com.magic.pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.magic.utils.ExcelUtil;

public class TakeNames {

	public static void main(String[] args) throws FileNotFoundException, IOException {

		File file=new File("C:/Users/Nishant sharma/Desktop/Inventory test case");

		File []listoffiles=file.listFiles();
		String filename="";
		for(int i=0;i<listoffiles.length;i++) 
		{
			if(listoffiles[i].isFile()) 
			{
				filename=listoffiles[i].getName();
				System.out.println("filename::"+filename);
				System.out.println("");
				ExcelUtil.writeData("./result/listoffilename", "Sheet1", (i+1), 0, Integer.toString(i+1));
				ExcelUtil.writeData("./result/listoffilename", "Sheet1", (i+1), 1, filename);

			}
		}

	}

}
