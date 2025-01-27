package reports;

import java.io.*;
import java.util.ArrayList;
import util.TestUtil;



public class ReportsUtil {
	public static int scriptNumber=1;
	public static String indexResultFilename;
	public static String currentDir;
	public static String currentSuiteName;
	public static int tcid;
	//public static String currentSuitePath;
	
	public static double passNumber;
	public static double failNumber;
	public static boolean newTest=true;
	public static ArrayList<String> description=new ArrayList<String>();;
	public static ArrayList<String> keyword=new ArrayList<String>();;
	public static ArrayList<String> teststatus=new ArrayList<String>();;
	public static ArrayList<String> screenShotPath=new ArrayList<String>();;

	
	public static void startTesting(String filename,String testStartTime,String env)
	  {
		indexResultFilename = filename;
		//currentDir = indexResultFilename.substring(0,indexResultFilename.lastIndexOf("//"));
		
		FileWriter fstream =null;
		 BufferedWriter out =null;
	      try{
	    // Create file 
	   
	     fstream = new FileWriter(filename);
	     out = new BufferedWriter(fstream);

        String RUN_DATE = TestUtil.now("dd.MMMM.yyyy").toString();
	    
	    String ENVIRONMENT = env;//SeleniumServerTest.ConfigurationMap.getProperty("environment");
	  //  String RELEASE = rel;//SeleniumServerTest.ConfigurationMap.getProperty("release");
	    
	    out.newLine();
	  
	    out.write("<html>\n");
	    out.write("<HEAD>\n");
	    	    
	    out.write(" <TITLE>BIM Automation Test Results</TITLE>\n");
	     out.write("</HEAD>\n");
	     out.write("<body bgcolor='#d8e4dd'>");
	     
	   //  out.write("<body>");
	    
	     out.write("<h2 align=center><FONT COLOR=660066 FACE=AriaL SIZE=6><b><u>BIM Automation Test Results</u></b></h2>\n");
	     out.write("<table width='100%' valign='top'><tr><td>");
	     out.write("<table  border=1 cellspacing=1 cellpadding=1 align='left' valign='top'>\n");
	     out.write("<tr>\n");
	  //   out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td>\n");
	        
	           out.write("<h4><FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Test Run Details :</u></h4>\n");
	           out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run Date</b></td>\n");
	           out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"+RUN_DATE+"</b></td>\n");
	     out.write("</tr>\n");
	     out.write("<tr>\n");
	           
	           out.write("<td width=150 align=left bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Run StartTime</b></td>\n");

	           out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"+testStartTime+"</b></td>\n");
	     out.write("</tr>\n");
	     out.write("<tr>\n");
	    // out.newLine();   
	           out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Run EndTime</b></td>\n");
	           out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>END_TIME</b></td>\n");
	     out.write("</tr>\n");
	     out.write("<tr>\n");
	   //  out.newLine();
	           
	           out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Environment</b></td>\n");
	           out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"+ENVIRONMENT+"</b></td>\n");
	     out.write("</tr>\n");
	     /*out.write("<tr>\n");
	           
	           out.write("<td width=150 align= left  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2.75><b>Release</b></td>\n");
	           out.write("<td width=150 align= left ><FONT COLOR=#153E7E FACE= Arial  SIZE=2.75><b>"+RELEASE+"</b></td>\n");
	     out.write("</tr>\n");
*/
	     out.write("</table></td>\n");
	     
	  
	       // out.write("<br>");
	       // out.write("<br>");
	        out.write("<td><table  border=1 cellspacing=1 cellpadding=1 align=right valign='top'  >\n");
		     out.write("<tr>\n");
		     out.write("<td width=150 colspan='2' bgcolor=#153E7E style='text-align:center'><FONT COLOR=#E0E0E0 FACE=Arial SIZE=3.75 align='center'><b>Test Summary Report</b></td></tr>\n");
		     out.write("<tr><td width=150 style='text-align:center' bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Pass</b></td>\n");
		     out.write("<td width=150 style='text-align:center' bgcolor='#008000'><FONT COLOR=#FFFFFF FACE=Arial SIZE=2.75><b>pass_case</b></td></tr>\n");
		     out.write("<tr><td width=150 style='text-align:center' bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Fail</b></td>\n");
		     out.write("<td width=150 style='text-align:center' bgcolor='#FF0000' ><FONT COLOR=#FFFFFF FACE=Arial SIZE=2.75><b>fail_case</b></td></tr>\n");
		     out.write("<tr><td width=150 style='text-align:center' bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total</b></td>\n");
		     out.write("<td width=150 style='text-align:center'><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>total</b></td>\n");
		     out.write("</tr></table></td></tr></table>");
	     

	    //Close the output stream
	    out.close();
	    }catch (Exception e){//Catch exception if any
	      System.err.println("Error: " + e.getMessage());
	    }finally{
	    	
		    fstream=null;
		    out=null;
	    }
	  }
	
    public static void startSuite(String suiteName){

	    FileWriter fstream =null;
		BufferedWriter out =null;
		currentSuiteName = suiteName.replaceAll(" ", "_");
		tcid=1;
	    try{
	    	// build the suite folder
	    //	currentSuitePath = currentDir; //+"//"+suiteName.replaceAll(" ","_");
	    //	currentSuiteDir = suiteName.replaceAll(" ","_");
	    //	File f = new File(currentSuitePath);
		//	f.mkdirs();
	    	

	    fstream = new FileWriter(indexResultFilename,true);
	  	out = new BufferedWriter(fstream);
	      
    	out.write("<h4> <FONT COLOR=660000 FACE= Arial  SIZE=4.5> <u>"+suiteName+" Report :</u></h4>\n");
        out.write("<table  border=1 cellspacing=1 cellpadding=1 width=100%>\n");
    	out.write("<tr>\n");
        out.write("<td width=5%  align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Automation_Id</b></td>\n");
        out.write("<td width=5% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Service Type</b></td>\n");
        out.write("<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>BIM Services</b></td>\n");
        out.write("<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>URL</b></td>\n");
        out.write("<td width=30% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>TestCase Description</b></td>\n");
        out.write("<td width=10% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Test Status</b></td>\n");
        out.write("<td width=20% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Actual Test Result</b></td>\n");
        out.write("<td width=5% align= center  bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE= Arial  SIZE=2><b>Time</b></td>\n");

        out.write("</tr>\n");
        out.close();
	    }catch(Exception e){
		      System.err.println("Error: " + e.getMessage());
	    }finally{
	    	
		    fstream=null;
		    out=null;
	    }
	}
    
    public static void endSuite(){
    	 FileWriter fstream =null;
 		BufferedWriter out =null;
 		
 	    try{
 	    fstream = new FileWriter(indexResultFilename,true);
 	  	out = new BufferedWriter(fstream);
        out.write("</table>");
        out.close();
 	    }catch(Exception e){
		      System.err.println("Error: " + e.getMessage());
	    }finally{
	    	
		    fstream=null;
		    out=null;
	    }

    }
	
	public static void addTestCase(String SrType,String testCaseName,String ConnDetails,String tcdesp, String testCaseStartTime,String testCaseEndTime,String status){
		newTest=true;
		FileWriter fstream=null;
		BufferedWriter out=null;
		
	

		try {
			newTest=true;
			// build the keywords page
		   if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip")){
			   
		   }else
		   
		   {
			
				/*File f = new File(currentSuiteName+testCaseName.replaceAll(" ", "_")+".html");
				
				f.createNewFile();
				*/
			  // fstream.write(currentSuiteName);
				fstream = new FileWriter(currentSuiteName,false);
				out = new BufferedWriter(fstream);
				out.write("<html>");
				out.write("<head>");
			
		 	 if(description!=null)
		 	 {
		 		 for(int i=0;i<description.size();i++)
		 		 {
		 			
		 			 	 			
		 			 out.write("<tr> ");

		 			 out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>TS"+(i+1)+"</b></td>");
		 			 out.write("<td align=center width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"+description.get(i)+"</b></td>");
		 			 out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"+keyword.get(i)+"</b></td>");
		 			if(teststatus.get(i).startsWith("Pass"))
		 			     out.write("<td width=20% align= center  bgcolor=#008000><FONT COLOR=#FFFFFF FACE=Arial SIZE=2><b>"+teststatus.get(i)+"</b></td>\n");
		 			else if(teststatus.get(i).startsWith("Fail"))
		 			  	 out.write("<td width=20% align= center  bgcolor=Red><FONT COLOR=#FFFFFF FACE= Arial  SIZE=2><b>"+teststatus.get(i)+"</b></td>\n");
		 			     
		 				 else
		 				out.write("<td align=center width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>&nbsp;</b></td>");	 
		 			 out.write("</tr>");
		 	 }
		 	 }
			
		 	 
		 	 
		 	 out.close();
			
			
		   }
			
			fstream = new FileWriter(indexResultFilename,true);
			out = new BufferedWriter(fstream);
			
			 fstream = new FileWriter(indexResultFilename,true);
			 out = new BufferedWriter(fstream);
			// out.newLine();
			
			 out.write("<tr>\n");
			 //System.out.println(currentSuitePath);
		     out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+scriptNumber+"</b></td>\n");
		     if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
		    	
		    	 out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseName+"</b></td>\n");
		    	
		    	 else
		    	
		    	out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+SrType+"</b></td>\n");
		    	 //out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseName+"</b></td>\n");
		    	 out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='20' readonly='readonly'>"+testCaseName+"</textarea></b></td>\n");
			 		
		    	 out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='30' readonly='readonly'>"+ConnDetails+"</textarea></b></td>\n");
		 		
		    	out.write("<td width=30% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+tcdesp+"</b></td>\n");
		     tcid++;
		     if(status.startsWith("Pass"))
		     out.write("<td width=10% align= center  bgcolor=#008000><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"+status+"</b></td>\n");
		     else if(status.startsWith("Fail"))
		    	 out.write("<td width=10% align= center  bgcolor=Red><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+status+"</b></td>\n");
		     else if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
			     out.write("<td width=10% align= center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+status+"</b></td>\n");
		     
		     out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='50' readonly='readonly'>"+testCaseStartTime+"</textarea></b></td>\n");
		     out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseEndTime+"</b></td>\n");

		     out.write("</tr>\n");
		     
		     scriptNumber++;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		newTest=false;
	}
	
	
//	public static void addTestCase1(String testCaseName,String testCaseStartTime,String testCaseEndTime,String status)
	public static void addTestCase1(String SrType,String testCaseName,String ConnDetails,String tcdesp,String testCaseStartTime,String testCaseEndTime,String status,String testCaseId){
	
		newTest=true;
		FileWriter fstream=null;
		BufferedWriter out=null;
		
		
			try {
			newTest=true;
			// build the keywords page
		   if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip")){
			   
		   }else
		   
		   {
			
				fstream = new FileWriter(currentSuiteName,false);
				out = new BufferedWriter(fstream);
				out.write("<html>");
				out.write("<head>");
				
		 	 if(description!=null)
		 	 {
		 		 for(int i=0;i<description.size();i++)
		 		 {
		 			
		 			 	 			
		 			 out.write("<tr> ");

		 			 out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>TS"+(i+1)+"</b></td>");
		 			 out.write("<td align=center width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"+description.get(i)+"</b></td>");
		 			 out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>"+keyword.get(i)+"</b></td>");
		 			if(teststatus.get(i).startsWith("Pass"))
		 			     out.write("<td width=20% align= center  bgcolor=#008000><FONT COLOR=#FFFFFFE FACE=Arial SIZE=2><b>"+teststatus.get(i)+"</b></td>\n");
		 			else if(teststatus.get(i).startsWith("Fail"))
		 			  	 out.write("<td width=20% align= center  bgcolor=Red><FONT COLOR=#FFFFFFE FACE= Arial  SIZE=2><b>"+teststatus.get(i)+"</b></td>\n");
		 			     
		 				 else
		 				out.write("<td align=center width=20%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>&nbsp;</b></td>");	 
		 			 out.write("</tr>");
		 	 }
		 	 }
			
		 	 
		 	 
		 	 out.close();
			
			
		   }
			
			fstream = new FileWriter(indexResultFilename,true);
			out = new BufferedWriter(fstream);
			
			 fstream = new FileWriter(indexResultFilename,true);
			 out = new BufferedWriter(fstream);
			// out.newLine();
			
			 out.write("<tr>\n");
			 
		     out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseId+"</b></td>\n");
		     if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
		    	
		    	 out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseName+"</b></td>\n");
		    	
		    	 else
		    	
		    	out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+SrType+"</b></td>\n");
		    	 //out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseName+"</b></td>\n");
		       out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='20' readonly='readonly'>"+testCaseName+"</textarea></b></td>\n");
			 	
		    	 out.write("<td width=10% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='30' readonly='readonly'>"+ConnDetails+"</textarea></b></td>\n");
			 		
		    	out.write("<td width=30% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+tcdesp+"</b></td>\n");
		     tcid++;
		     if(status.startsWith("Pass"))
		     out.write("<td width=10% align= center  bgcolor=#008000><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"+status+"</b></td>\n");
		     else if(status.startsWith("Fail"))
		    	 out.write("<td width=10% align= center  bgcolor=Red><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+status+"</b></td>\n");
		     else if(status.equalsIgnoreCase("Skipped") || status.equalsIgnoreCase("Skip"))
			     out.write("<td width=10% align= center  bgcolor=yellow><FONT COLOR=153E7E FACE=Arial SIZE=2><b>"+status+"</b></td>\n");
		     
		  
		     out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b><textarea style='font-family:Arial,Bold;color:#153E7E' rows='5' cols='50' readonly='readonly'>"+testCaseStartTime+"</textarea></b></td>\n");
			 
		     out.write("<td width=20% align= center ><FONT COLOR=#153E7E FACE= Arial  SIZE=2><b>"+testCaseEndTime+"</b></td>\n");

		     out.write("</tr>\n");
		     
		     scriptNumber++;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		newTest=false;
		
		
	}
	
	public static void addKeyword(String desc,String key,String stat,String path){
		
		
			
		
		description.add(desc);
		keyword.add(key);
		teststatus.add(stat);
		screenShotPath.add(path);
		
}

	public static void updateEndTime(int pass,int fail,int total,String endTime) {
		StringBuffer buf = new StringBuffer();
		try{
		    // Open the file that is the first 
		    // command line parameter
		    FileInputStream fstream = new FileInputStream(indexResultFilename);
		    // Get the object of DataInputStream
		    DataInputStream in = new DataInputStream(fstream);
		        BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    
		    String pass1=pass+"";
		    String fail1=fail+"";
		    String total1=total+"";
		    
		    
		    //Read File Line By Line
		    
		    
		    
		    while ((strLine = br.readLine()) != null)   {
		    	
		    
		    	
		     if(strLine.indexOf("END_TIME") !=-1){
		    	 strLine=strLine.replace("END_TIME", endTime);
		     }
		     if(strLine.indexOf("pass_case") !=-1){
		    	 
		    	 strLine=strLine.replace("pass_case", pass1);
		     }
             if(strLine.indexOf("fail_case") !=-1){
		    	 
		    	 strLine=strLine.replace("fail_case", fail1);
		     }
              if(strLine.indexOf("total") !=-1){
		    	 
		    	 strLine=strLine.replace("total", total1);
		     }
             
		     buf.append(strLine);
		     
		     
		    }
		  //Close the input stream
		    in.close();
		   // System.out.println(buf);
		    FileOutputStream fos=new FileOutputStream(indexResultFilename);
			 DataOutputStream   output = new DataOutputStream (fos);	 
	    	 output.writeBytes(buf.toString());
	    	 fos.close();
		    
		    }catch (Exception e){//Catch exception if any
		      System.err.println("Error: " + e.getMessage());
		    }
	
		
	}
	
	
}
