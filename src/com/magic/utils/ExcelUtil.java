package com.magic.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class ExcelUtil
{
    static XSSFWorkbook wb;
    static XSSFSheet sh;
    static XSSFRow rw;
    static XSSFCell cell;
    static String data;

    static
    {
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hhmmss");
	System.setProperty("current.date", dateFormat.format(new Date()));
    }

    private static Logger log=Logger.getLogger(ExcelUtil.class);


    public static void createExcelFile(String folderName,String fileName,String sheetName) throws IOException, InterruptedException
    {
	Thread.sleep(500);
	FileOutputStream fileOutputStream = new FileOutputStream(new File("./"+folderName+"/"+fileName+".xlsx"));
	wb = new XSSFWorkbook();
	sh=wb.createSheet(sheetName);
	wb.write(fileOutputStream);
	fileOutputStream.flush();
	fileOutputStream.close();
    }

    @SuppressWarnings({ "deprecation", "static-access", "unused" })
    public static String readData(String filePath, String sheetName,int rownum ,int cellnum ) throws IOException
    {

	FileInputStream fis = null;
	try 
	{
	    fis = new FileInputStream(new File(filePath+".xlsx"));
	    wb = new XSSFWorkbook(fis);
	    sh=wb.getSheet(sheetName);
	    XSSFCell cell = sh.getRow(rownum).getCell(cellnum);

	    if(sh.getRow(rownum).getCell(cellnum)!=null)
	    {
		if(sh.getRow(rownum).getCell(cellnum).getCellType()==Cell.CELL_TYPE_STRING)
		{
		    data=sh.getRow(rownum).getCell(cellnum).getStringCellValue();
		}
		else if(sh.getRow(rownum).getCell(cellnum).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
		    if (DateUtil.isCellDateFormatted(sh.getRow(rownum).getCell(cellnum)))
		    {
			SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			data=sdf.format(sh.getRow(rownum).getCell(cellnum).getDateCellValue());
		    }
		    else
		    {
			double ndata = sh.getRow(rownum).getCell(cellnum).getNumericCellValue();	
			if(ndata%1==0)
			{
			    long datanew = (long)ndata;	
			    data=String.valueOf(datanew);
			}
			else
			{
			    data=String.valueOf(ndata);	
			}
		    }
		}
		else if(sh.getRow(rownum).getCell(cellnum).getCellType()==Cell.CELL_TYPE_BLANK)
		{
		    data=sh.getRow(rownum).getCell(cellnum).getStringCellValue();	
		}
	    }
	    else
	    {
		data="";
	    }
	} 
	catch (Exception e) 
	{
	    data="";
	}
	return data;
    }

    public static void writeData(String filename, String sheetname, int rownum, int column, String value) throws FileNotFoundException, IOException
    {
	try
	{
	    wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	    sh=wb.getSheet(sheetname);
	    if(sh.getRow(rownum)==null)
	    {
		sh.createRow(rownum).createCell(column).setCellValue(value);
	    }
	    sh.getRow(rownum).createCell(column).setCellValue(value);
	    FileOutputStream fos= new FileOutputStream(filename+".xlsx");
	    wb.write(fos);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    log.error("error writing data to file "+filename+ "and sheetname "+sheetname+ " "+e);
	}

    }


    @SuppressWarnings("deprecation")
    public static void writeAllData(Map<Integer, List<String>> pptData, List<String> header, String fileName, String sheetName) throws IOException
    {

	try {
	    if(header.size()>0)
	    {
		int rowCounter=1;
		int cellCounter=0;
		wb=new XSSFWorkbook();
		sh=wb.createSheet(sheetName);
		rw=sh.createRow(0);
		CellStyle style = wb.createCellStyle();//Create style
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		Font font = wb.createFont();//Create font
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
		style.setFont(font);//set it to bold

		for(int i=0; i<header.size();i++)
		{
		    rw.createCell(i).setCellStyle(style);
		    rw.getCell(i).setCellValue(header.get(i));
		}
		if(pptData.size()>0)
		{
		    for(int keyset:pptData.keySet())
		    {
			List<String> pptValues = pptData.get(keyset);
			rw=sh.createRow(rowCounter);

			for(String value : pptValues)
			{
			    if(value!=null)
			    {
				rw.createCell(cellCounter).setCellValue(value);	
			    }
			    else
			    {
				rw.createCell(cellCounter).setCellValue("Null");
			    }
			    cellCounter++;
			}
			cellCounter=0;
			rowCounter++;
		    }
		}
		for(int i=0; i<header.size();i++)
		{
		    sh.autoSizeColumn(i);
		}
	    }
	    FileOutputStream fos=new FileOutputStream(new File(fileName+".xlsx"));
	    wb.write(fos);
	    fos.flush();
	    wb.close();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }


    @SuppressWarnings("deprecation")
    public static Map<Integer, String> readExcelData(String filename, String sheetname, int column) throws FileNotFoundException, IOException
    {
	Map<Integer,String> values =new HashMap<Integer,String>(); 		
	wb=new XSSFWorkbook(new FileInputStream("./input/"+filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int lastrow=sh.getLastRowNum();
	int counter=0;
	for(int i=1;i<=lastrow;i++)
	{
	    try
	    {
		if(sh.getRow(i).getCell(column).getCellType()==Cell.CELL_TYPE_STRING)
		{
		    String assetName = sh.getRow(i).getCell(column).getStringCellValue();			
		    values.put(counter, assetName);	
		    counter++;
		}
		else if(sh.getRow(i).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
		    long assetName = (long)(sh.getRow(i).getCell(column).getNumericCellValue());	
		    values.put(counter, String.valueOf(assetName));	
		    counter++;
		}
	    }
	    catch(Exception e)
	    {
		//e.printStackTrace();
	    }
	}
	return values;
    }


    public static void writeDataExistingSheet(String filename, String sheetname, int rownum, int column, String value) throws FileNotFoundException, IOException, NullPointerException
    {
	try
	{		
	    wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	    sh=wb.getSheet(sheetname);
	    rw=sh.getRow(rownum);
	    CellStyle style = wb.createCellStyle();//Create style
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    Font font = wb.createFont();//Create font
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
	    style.setFont(font);//set it to bold
	    rw.createCell(column).setCellStyle(style);
	    rw.getCell(column).setCellValue(value);

	    FileOutputStream fos= new FileOutputStream(filename+".xlsx");
	    wb.write(fos);
	}
	catch(Exception e)
	{
	    rw=sh.createRow(rownum);
	    CellStyle style = wb.createCellStyle();//Create style
	    style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	    Font font = wb.createFont();//Create font
	    font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
	    style.setFont(font);//set it to bold

	    rw.createCell(column).setCellStyle(style);
	    rw.getCell(column).setCellValue(value);

	    FileOutputStream fos= new FileOutputStream(filename+".xlsx");
	    wb.write(fos);
	    //e.printStackTrace();
	    //log.error("error writing data to file "+filename+ "and sheetname "+sheetname+ " "+e);
	}
    }


    @SuppressWarnings("deprecation")
    public  static Map<Integer, List<String>> readAllData(String filename, String sheetname,int startrow) throws FileNotFoundException, IOException, InterruptedException
    {
	Map<Integer,List<String>> alldata =new HashMap<Integer,List<String>>(); 
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int lastrow=sh.getLastRowNum();
	int counter=0;
	for(int i=startrow;i<=lastrow;i++)
	{
	    List<String> values=new ArrayList<String>();
	    int cellno=sh.getRow(i).getLastCellNum();
	    for (int j=0;j<cellno;j++) 
	    {
	    
		if(sh.getRow(i).getCell(j)!=null)
		{
		    if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_STRING)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_NUMERIC)
		    {
			if (DateUtil.isCellDateFormatted(sh.getRow(i).getCell(j)))
			{
			    SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
			    String date=sdf.format(sh.getRow(i).getCell(j).getDateCellValue());
			    System.out.println(date);
			    values.add(date);
			}
			else
			{
			    double data = sh.getRow(i).getCell(j).getNumericCellValue();	
			    if(data%1==0)
			    {
				long datanew = (long)data;	
				values.add(String.valueOf(datanew));
			    }
			    else
			    {
				values.add(String.valueOf(data));
			    }
			}
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());	
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());	
		    }
		}
		else
		{
		    values.add("");
		}
	    }	

	    alldata.put(counter, values);
	    counter++;

	}

	return alldata;
    }


    @SuppressWarnings("deprecation")
    public  static Map<Integer, List<String>> readRangeData(String filename, String sheetname,int startrow, int column) throws FileNotFoundException, IOException, InterruptedException
    {
	Map<Integer,List<String>> alldata =new LinkedHashMap<Integer,List<String>>(); 
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int lastrow=sh.getLastRowNum();
	int counter=0;
	for(int i=startrow;i<=lastrow;i++)
	{
	    List<String> values=new ArrayList<String>();
	    int cellno=sh.getRow(i).getLastCellNum();
	    for (int j=column;j<cellno;j++) 
	    {
		if(sh.getRow(i).getCell(j)!=null)
		{
		    if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_STRING)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_NUMERIC)
		    {
			if (DateUtil.isCellDateFormatted(sh.getRow(i).getCell(j)))
			{
			    SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
			    String date=sdf.format(sh.getRow(i).getCell(j).getDateCellValue());
			    System.out.println(date);
			    values.add(date);
			}
			else
			{
			    double data = sh.getRow(i).getCell(j).getNumericCellValue();	
			    if(data%1==0)
			    {
				long datanew = (long)data;	
				values.add(String.valueOf(datanew));
			    }
			    else
			    {
				values.add(String.valueOf(data));
			    }
			}
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());	
		    }
		    else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		    {
			values.add(sh.getRow(i).getCell(j).getStringCellValue());	
		    }
		}
		else
		{
		    values.add("");
		}
	    }	

	    alldata.put(counter, values);
	    counter++;

	}

	return alldata;
    }


    @SuppressWarnings("deprecation")
    public static Map<Integer, String> readColumnData(String filename, String sheetname, String columnname) throws Exception
    {

	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int lastrow=sh.getLastRowNum();
	int counter=0;
	int column=0;
	boolean flag = false;
	rw=sh.getRow(0);
	int lastcellno=rw.getLastCellNum();
	for(int j=0; j<lastcellno;j++)
	{
	    if (rw.getCell(j) == null)
	    {
		//System.out.println("Cell is Empty in Column:" + columnname);

	    } else if (rw.getCell(j).getStringCellValue().equalsIgnoreCase(columnname))
	    {
		flag=true;
		column=j;
		break;
	    }
	    /*if(rw.getCell(j).getStringCellValue().equalsIgnoreCase(columnname))
			{
				flag=true;
				column=j;
				break;
			}*/
	}

	if(flag)
	{
	    Map<Integer,String> values =new LinkedHashMap<Integer,String>(); 		
	    for(int i=1;i<=lastrow;i++)
	    {

		try
		{
		    if(sh.getRow(i).getCell(column).getCellType()==Cell.CELL_TYPE_STRING)
		    {
			String assetName = sh.getRow(i).getCell(column).getStringCellValue();			
			values.put(counter, assetName);	
			//System.out.println(String.valueOf(assetName));

		    }
		    else if(sh.getRow(i).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC)
		    {
			if (DateUtil.isCellDateFormatted(sh.getRow(i).getCell(column)))
			{
			    SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
			    String datanew=sdf.format(sh.getRow(i).getCell(column).getDateCellValue());
			    //System.out.println(datanew);
			    values.put(counter, String.valueOf(datanew));	

			}
			else
			{
			    double ndata = sh.getRow(i).getCell(column).getNumericCellValue();	
			    if(ndata%1==0)
			    {
				long datanew = (long)ndata;	
				String data=String.valueOf(datanew);
				values.put(counter, String.valueOf(data));
			    }
			    else
			    {
				values.put(counter, String.valueOf(ndata));	
			    }
			}
		    }
		    else if(sh.getRow(i).getCell(column).getCellType()==Cell.CELL_TYPE_BLANK)
		    {
			values.put(counter,sh.getRow(i).getCell(column).getStringCellValue());	
		    }
		    else
		    {
			values.put(counter,"");	
		    }
		}
		catch(Exception e)
		{
		    values.put(counter,"");	
		    //e.printStackTrace();

		}
		counter++;
	    }

	    return values;

	}
	else
	{
	    throw new Exception(columnname+" not found in excel "+filename+" and "+sheetname);

	}

    }

    @SuppressWarnings("deprecation")
    public static void generateheader(String filename, String sheetname, int cellcount, String cellvalue) throws FileNotFoundException, IOException
    {
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	rw=sh.getRow(0);
	CellStyle style = wb.createCellStyle();//Create style
	style.setFillForegroundColor(IndexedColors.BLACK.index);
	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	Font font = wb.createFont();//Create font
	font.setColor(IndexedColors.WHITE.index);
	font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
	style.setFont(font);//set it to bold
	rw.createCell(cellcount).setCellStyle(style);
	rw.getCell(cellcount).setCellValue(cellvalue);
	FileOutputStream fos= new FileOutputStream(filename+".xlsx");
	wb.write(fos);
	fos.close();
    }


    public static boolean verifyTemplateFile(String filename, String sheetname, List<String> headers) throws FileNotFoundException, IOException
    {
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int headerCount=sh.getRow(0).getLastCellNum();
	boolean flag=false;
	if(headerCount==headers.size())
	{
	    int counter=0;
	    for (String headername : headers) 
	    {
		if(headername.equalsIgnoreCase(sh.getRow(0).getCell(counter).getStringCellValue()))
		{
		    flag=true;

		}
		else
		{
		    flag=false;
		}
		counter++;
	    }
	}
	else
	{
	    flag=false;
	}
	return flag;		
    }

    public static int getColumnCount(String fileName,String sheet,int rownum) throws IOException
    {	
	int numOFcolumn = 0;
	try {	
	    File file = new File(fileName+".xlsx");
	    FileInputStream fileInputStream = new FileInputStream(file);
	    @SuppressWarnings("resource")
	    Workbook wb = new XSSFWorkbook(fileInputStream);
	    numOFcolumn= wb.getSheet(sheet).getRow(rownum).getLastCellNum();					
	}catch (NullPointerException e) {
	    e.printStackTrace();
	} catch (EncryptedDocumentException e) {
	    e.printStackTrace();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return (numOFcolumn);
    }

    public static int getRowCount(String fileName,String sheet) throws IOException
    {	
	int numOFRows = 0;
	try {	
	    File file = new File(fileName+".xlsx");
	    FileInputStream fileInputStream = new FileInputStream(file);
	    ZipSecureFile.setMinInflateRatio(-1.0d); 
	    @SuppressWarnings("resource")
	    Workbook wb = new XSSFWorkbook(fileInputStream);
	    numOFRows= wb.getSheet(sheet).getLastRowNum();					
	}catch (NullPointerException e) {
	    e.printStackTrace();
	} catch (EncryptedDocumentException e) {
	    e.printStackTrace();
	}catch (Exception e) {
	    e.printStackTrace();
	}
	return (numOFRows);
    }


    @SuppressWarnings("deprecation")
    public static Map<Integer, String> readRowData(String filename, String sheetname,int row) throws Exception
    {

	Map<Integer, String> alldata =new LinkedHashMap<Integer,String>(); 
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	String values="";
	int i=row;
	int cellno=sh.getRow(i).getLastCellNum();
	for (int j=0;j<cellno;j++) 
	{
	    if(sh.getRow(i).getCell(j)!=null)
	    {
		if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_STRING)
		{
		    values=sh.getRow(i).getCell(j).getStringCellValue();
		}
		else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_NUMERIC)
		{
		    if (DateUtil.isCellDateFormatted(sh.getRow(i).getCell(j)))
		    {
			SimpleDateFormat sdf=new SimpleDateFormat("d/M/yyyy");
			String date=sdf.format(sh.getRow(i).getCell(j).getDateCellValue());
			System.out.println(date);
			values=date;
		    }
		    else
		    {
			double data = sh.getRow(i).getCell(j).getNumericCellValue();	
			if(data%1==0)
			{
			    long datanew = (long)data;	
			    values=String.valueOf(datanew);
			}
			else
			{
			    values=String.valueOf(data);
			}
		    }
		}
		else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		{
		    values=sh.getRow(i).getCell(j).getStringCellValue();	
		}
		else if(sh.getRow(i).getCell(j).getCellType()==Cell.CELL_TYPE_BLANK)
		{
		    values=sh.getRow(i).getCell(j).getStringCellValue();	
		}
	    }
	    else
	    {
		values="";
	    }
	    alldata.put(j, values);
	}
	return alldata;

    }


    public static void makeEmptycellyellow(String filename,String sheetname,String columnname) throws FileNotFoundException, IOException
    {
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);
	int lastrow=sh.getLastRowNum();
	int column=0;
	boolean flag = false;
	rw=sh.getRow(0);
	int lastcellno=rw.getLastCellNum();
	for(int j=0; j<lastcellno;j++)
	{
	    if (rw.getCell(j) == null)
	    {
		//System.out.println("Cell is Empty in Column:" + columnname);

	    } else if (rw.getCell(j).getStringCellValue().equalsIgnoreCase(columnname))
	    {
		flag=true;
		column=j;
		break;
	    }
	}
	if(flag)
	{	
	    for(int i=1;i<=lastrow;i++)
	    {
		try
		{
		    String value=sh.getRow(i).getCell(column).getStringCellValue();
		    if(value.equalsIgnoreCase(""))
		    {
			XSSFCellStyle style = wb.createCellStyle();
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			sh.getRow(i).getCell(column).setCellStyle(style);
		    }
		}
		catch(Exception e)
		{

		}
	    }
	}

	FileOutputStream out = new FileOutputStream(filename+".xlsx");
	wb.write(out);
	out.close();

    }


    public static void makecellRed(String filename,String sheetname,int row,int column) throws FileNotFoundException, IOException
    {

	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);


	String value=sh.getRow(row).getCell(column).getStringCellValue();

	XSSFCellStyle style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.RED.getIndex());
	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	sh.getRow(row).getCell(column).setCellStyle(style);


	FileOutputStream out = new FileOutputStream(filename+".xlsx");
	wb.write(out);
	out.close();

    }
    public static void makecellOrange(String filename,String sheetname,int row,int column) throws FileNotFoundException, IOException
    {

	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.getSheet(sheetname);


	String value=sh.getRow(row).getCell(column).getStringCellValue();

	XSSFCellStyle style = wb.createCellStyle();
	style.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	sh.getRow(row).getCell(column).setCellStyle(style);


	FileOutputStream out = new FileOutputStream(filename+".xlsx");
	wb.write(out);
	out.close();

    }


    public static void writeAllDataInNewsheetInexistingfile(String filename,String sheetName,Map<Integer, List<String>> pptData, List<String> header) throws FileNotFoundException, IOException
    {
	wb=new XSSFWorkbook(new FileInputStream(filename+".xlsx"));
	sh=wb.createSheet(sheetName);
	int rowCounter=1;
	int cellCounter=0;
	rw=sh.createRow(0);
	CellStyle style = wb.createCellStyle();//Create style
	style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
	style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	Font font = wb.createFont();//Create font
	font.setBoldweight(Font.BOLDWEIGHT_BOLD);//Make font bold
	style.setFont(font);//set it to bold

	for(int i=0; i<header.size();i++)
	{
	    rw.createCell(i).setCellStyle(style);
	    rw.getCell(i).setCellValue(header.get(i));
	}
	for(int keyset:pptData.keySet())
	{
	    List<String> pptValues = pptData.get(keyset);
	    rw=sh.createRow(rowCounter);

	    for (String value : pptValues)
	    {

		if(value!=null)
		{
		    rw.createCell(cellCounter).setCellValue(value);	
		}
		else
		{
		    rw.createCell(cellCounter).setCellValue("Null");
		}

		cellCounter++;
	    }
	    cellCounter=0;
	    rowCounter++;
	}
	for(int i=0; i<header.size();i++)
	{
	    sh.autoSizeColumn(i);
	}

	FileOutputStream fos=new FileOutputStream(new File(filename+".xlsx"));
	wb.write(fos);
	fos.flush();
	wb.close();

    }




}