package driverScript;

import static io.restassured.RestAssured.given;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestClientException;
import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.github.wnameless.json.flattener.JsonFlattener;
import com.github.wnameless.json.unflattener.JsonUnflattener;

import reports.ReportsUtil;

public class Generic_Functions {
	
	public static int pass;
	public static int fail;
	public static String failendtim;
	public static long startTime1;
	public static String responseBodyPost;
	public static String responseBodyGet;
	public static String responseBodydelete;
	public static String responseBodyupdate;
    public static String[] expectedValue;
    public static String[] actualValue;
    public static String[] MissmatchedValue;
    public static String service;
    public static String type;

	public static String[] idvalue;
	public static String[] keyvalue;
	public static String[]keyparam;
	public static String[]valueparam;

	public static String uniqueField;
	public static String Endpoint;

	public static String urlNew;
	public static String [] old1=null;	
	public static String [] new1=null;

	public Response res;

	private static Object value;
	static Map<String, Object> flattenedJsonMap;
	
	public static int noOfColumns=0;
	
	private static Logger LOG = Logger.getLogger(Generic_Functions.class.getName());

	public static int passTestCase() {
		return pass;
	}

	public static int failTestCase() {
		return fail;
	}
	
	private static String time_calculate()
	{
		// Get end time and calculate total time in secs
		long endTimePass = System.currentTimeMillis();
		long totalTimefail = endTimePass - startTime1;
		long totfail = totalTimefail / 1000;
		String strLongfail = Long.toString(totfail);
		return strLongfail;
	}
		
    void func_Post_Microservice(String txtpath, String url, String txtpath1,String jsonpath,String type1 , String[] oldtext1,
			Object[] newtext1, int noOfcol, String idqp,String idkey,String service_type1, String soapdesc1, String bimServices,
			int statusCode,String UniqueField,String testCasePostId) throws JSONException, FileNotFoundException, IOException, ParseException

	{
			uniqueField=UniqueField;
			noOfColumns=noOfcol;
			Endpoint=idkey;
			service=service_type1;
			type=type1;
	//	try {
			// Get start time
			startTime1 = System.currentTimeMillis();
			//disableSslVerification();


			modifyJson(txtpath, txtpath1,jsonpath, oldtext1, newtext1,service_type1);
			LOG.info("Got the JSON Object from JSONObject func_getJson_Object Function ");


			File file= new File(jsonpath);
			
			
			RestAssured.baseURI = url;
			Response res=given()
			.accept("application/json")
			.contentType("application/json")
			.relaxedHTTPSValidation()
			.body(file)
			.when()
			.post()
			.then()
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract().response();
			
			responseBodyPost = res.asString();
			System.out.println("BODY:"+responseBodyPost);
			
			JsonPath jsonRes = new JsonPath(responseBodyPost);
			if(!(jsonRes.get("response")==null))
				{
					UniqueFiledValue(UniqueField,idkey,idqp);
				}
							 
			
			if (res.getStatusCode() == statusCode) {
				System.out.println("Status: Passed");
				String passendtim = time_calculate() + "Sec";
				// count pass test cases
				pass++;
				ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBodyPost, passendtim,
						"Pass",testCasePostId);

			}
			else {
				System.out.println("Status: Failed");

				failendtim = time_calculate() + "Sec";
				// count fail test cases
				fail++;
				
				ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBodyPost, failendtim,
						"Fail",testCasePostId);
			}
		/*} catch (Exception e) {
			
			failendtim = time_calculate() + "Sec";
			// count fail test cases
			fail++;
			String exception = e.toString();
			System.out.println("Exception: " + exception);
			ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, exception, failendtim, "Fail",testCasePostId);
			
		}*/

	}
		
	@SuppressWarnings({ "resource" })	
	private static void modifyJson(String txtpath,String txtpathupdate,String jsonpath, String[] oldtext,Object[] newtext,String service_type)
			throws IOException, ParseException {
	
		if(!(service_type.equalsIgnoreCase("GET_POST"))){
		old1= new String[oldtext.length];		
		new1= new String[oldtext.length];	}
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(txtpath));
			Map<String, Object> flattenedJsonMap = JsonFlattener.flattenAsMap(jsonObject.toString());
			Map<String, Object> flattenedUpdatedMap = new LinkedHashMap<>();

			for (int i = 0; i < oldtext.length; i++) {
				final int tempi=i;

			flattenedJsonMap.forEach((k, v) -> {
				String actualKey = k;
				String modifiedKey = k;//.replaceAll("\\[(.*?)\\]", "");
				if(!((oldtext[tempi]=="NA") && (newtext[tempi].toString()=="NA")))
				{	
					//System.out.println("OLD:"+oldtext[tempi]+":"+"NEW:"+newtext[tempi].toString());
					//System.out.println(oldtext[tempi]+":"+modifiedKey);
				if (oldtext[tempi].equalsIgnoreCase(modifiedKey)) 
				{
					if(!(service_type.equalsIgnoreCase("GET_POST"))){
							old1[tempi]=oldtext[tempi];
							}
					value = newtext[tempi];
					//System.out.println(old1[tempi]+":"+value);
					
					if(value.toString().equalsIgnoreCase("NUL"))
					{
						if(!(service_type.equalsIgnoreCase("GET_POST")))
						{new1[tempi]="";}
					}
					else
					{
						if(!(service_type.equalsIgnoreCase("GET_POST")))
							{new1[tempi]=splitString(value.toString());}
					}
					flattenedUpdatedMap.put(actualKey, value);
					
				} else {

					flattenedUpdatedMap.put(actualKey, v);

				}
			}
				flattenedJsonMap.putAll(flattenedUpdatedMap);

			});

			}
			String unflattenJson = JsonUnflattener.unflatten(unFlattenedMap(flattenedUpdatedMap).toString());

			String modifiedJson = prettyJsonConvertor(unflattenJson);

			//System.out.println(modifiedJson);
			try  {
	        	FileWriter file = new FileWriter(jsonpath);
	        	file.write(modifiedJson);            
	            file.flush();
	        	if(!(txtpathupdate.equalsIgnoreCase("NA"))){
	        		FileWriter file1 = new FileWriter(txtpathupdate);
	        		file1.write(modifiedJson);
	        		file1.flush();

	        	}         

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}

	private static void UniqueFiledValue(String uniqueField,String idkey, String idqp) 
	{
		JsonPath jsonRes = new JsonPath(responseBodyPost);

        String[] strtype=type.split("#");

		keyvalue= new String[uniqueField.length()];
		keyparam= new String[idqp.length()];
		valueparam=new String[idqp.length()];
		idvalue=new String[idkey.length()];
		
		
		if(!(idqp.equalsIgnoreCase("NA")))
		{
			keyparam=idqp.split("#");

			for(int j=0;j<keyparam.length;j++)
			{
				String key="response."+keyparam[j];
				valueparam[j]=jsonRes.get(key).toString();
			}
			
		}
		
		String[] temp1=idkey.split("#");
		for (int j = 0; j < temp1.length; j++) 
		{
			if(strtype[0].equalsIgnoreCase("single"))
			{
	            temp1[j]=temp1[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");
				String key = "response." + temp1[j];
				idvalue[j] = jsonRes.get(key).toString();
				idvalue[j]=idvalue[j].replaceAll("[\\[\\]\\(\\)]","");
	            
	        }
			else if(strtype[0].equalsIgnoreCase("multiple"))
			{
	            temp1[j]=temp1[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");
	            String str,aa;
				String key = "response." + temp1[j];
				str = jsonRes.get(key).toString();
				aa=str.replaceAll("[\\[\\]\\(\\)]","");										
				String[] idString=aa.split(",");
				String [] str1=new String[idString.length];
				str1[j]= idString[j].replaceAll("[\\[\\]\\(\\)]", "").trim();
				idvalue[j]=str1[j];
	            		
			}
			else{
				String key = "response." + temp1[j];
				idvalue[j] = jsonRes.get(key).toString();
				idvalue[j]=idvalue[j].replaceAll("[\\[\\]\\(\\)]","");
	            
			}
						
		}
	
		
		String[] temp=uniqueField.split("#");
		for (int j = 0; j < temp.length; j++) 
		{
			if(strtype[0].equalsIgnoreCase("single"))
			{
	            temp[j]=temp[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");
				String key = "response." + temp[j];
	            keyvalue[j] = jsonRes.get(key).toString();
	            keyvalue[j]=keyvalue[j].replaceAll("[\\[\\]\\(\\)]","");
	            
	        }
			else if(strtype[0].equalsIgnoreCase("multiple"))
			{
	            temp[j]=temp[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");
	            String str,aa;
				String key = "response." + temp[j];
				str = jsonRes.get(key).toString();
				aa=str.replaceAll("[\\[\\]\\(\\)]","");										
				String[] idString=aa.split(",");
				String [] str1=new String[idString.length];
				str1[j]= idString[j].replaceAll("[\\[\\]\\(\\)]", "").trim();
				keyvalue[j]=str1[j];
	            		
			}
			else{
				String key = "response." + temp[j];
	            keyvalue[j] = jsonRes.get(key).toString();
	            keyvalue[j]=keyvalue[j].replaceAll("[\\[\\]\\(\\)]","");
	            
			}
						
		} 								
	}

	void func_Get_Post_Microservice(String url, String txtpath1,String jsonpath1,String service_type1, String soapdesc,
			String bimServices,int statusCode,String testCaseId,String method,String type1) throws Exception{
		
//try {
		int a=0;
			System.out.println("URL:"+url);

			if(method.equalsIgnoreCase("NA"))
			{
				String[] temp=Endpoint.split("#");
				modifyJson( txtpath1, "NA", jsonpath1, temp, idvalue,service_type1);
				//method=Endpoint;
			}
			else
			{
				String[] temp=method.split("#");
				String[] negval={"ABC","ABC"};
				String[] nullval= {"nul","nul"};
				
				if(method.equalsIgnoreCase("NUL"))
				{
					modifyJson( txtpath1, "NA", jsonpath1, temp, nullval,service_type1);

				}
				else if(method.equalsIgnoreCase("Invalid"))
				{
					

					modifyJson( txtpath1, "NA", jsonpath1, temp, negval,service_type1);
				}
				else{
					modifyJson( txtpath1, "NA", jsonpath1, temp, idvalue,service_type1);

				}
				
			}	
			
			
			if(type.equalsIgnoreCase("NA"))
			{
				type=type1;
			}
			LOG.info("Got the JSON File from func_deleteJson_Object Function");

			File fstream = new File(jsonpath1);
			
			RestAssured.baseURI = url;
			Response res=given()
					.accept("application/json")
					.contentType("application/json")
					.relaxedHTTPSValidation()
					.body(fstream)
					.when()
					.post()
					.then()
					.assertThat()
					.contentType(ContentType.JSON)
					.and()
					.extract().response();
			responseBodyGet=res.asString();		
			LOG.info("Body :" + responseBodyGet);
			System.out.println("BODY:"+responseBodyGet);
			
			reports_get(a,method,bimServices);
			
			if (res.getStatusCode() == statusCode)
			{
			System.out.println("Status: Passed");
			
			String passendtim = time_calculate() + "Sec";
			
			// count pass test cases
			pass++;		
			
			ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc, responseBodyGet, passendtim, "Pass",testCaseId);
			
		}  else {
			if(res.getStatusCode() != statusCode){
			System.out.println("Status: Failed");
			
			String failendtim = time_calculate() + "Sec";
			// count fail test cases
			fail++;
			ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc, responseBodyGet, failendtim, "Fail",testCaseId);
			}
		}
		
		if(responseBodyPost!=null) 
		{
			responseBodyPost=null;
		}
		else if (responseBodyupdate!=null)
		{
			responseBodyupdate=null;

		}
		/*} catch (Exception e) {

		failendtim = time_calculate() + "Sec";
		// count fail test cases
		fail++;

		String exception = e.toString();
		System.out.println("Exception: " + exception);
		ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc, exception, failendtim, "Fail",testCaseId);
	}*/
	}

	void func_Get_Microservice(String url,String service_type, String soapdesc, String bimServices, int statusCode,
			String method,String testCaseGetId) throws IOException, RestClientException, ParseException {
		
		//try {
			// Get start time
						startTime1 = System.currentTimeMillis();	
						urlNew = url;
						int a=0;
						/*//map for multiple query paramaters
						 Map m1 = new HashMap();
						for(int i=0;i<keyparam.length;i++){
							m1.put(keyparam[i], valueparam[i]);
						}*/
						
								if(((responseBodyPost==null) || (responseBodyupdate==null)) && (!(method.equalsIgnoreCase("NA"))))
								{
									System.out.println(method);

									if(method.equalsIgnoreCase("NUL"))
									{
										method="";
									}
									urlNew=url+method;					
									
									System.out.println("URL:"+urlNew);
									LOG.info("Url :" + urlNew);
												RestAssured.baseURI = urlNew;
												 res= given()
														.relaxedHTTPSValidation()
														.when()
														.get()
														.then()
														.contentType(ContentType.JSON)
														.and()
														.extract().response();
														
												 	responseBodyGet=res.asString();		
													LOG.info("Body :" + responseBodyGet);
													System.out.println("BODY:"+responseBodyGet);
													
													reports_get(a,method,bimServices);
											
												
								}
								
								
							else if(keyparam[0]!=null)
								{	
								System.out.println("URL:"+urlNew);
								LOG.info("Url :" + urlNew);
									RestAssured.baseURI = urlNew;
									res= given()
											//.params(m1)
											.queryParam(keyparam[0], valueparam[0])
											.relaxedHTTPSValidation()
											.when()
											.get()
											.then()
											.contentType(ContentType.JSON)
											.and()
											.extract().response();
											
									responseBodyGet=res.asString();		
									LOG.info("Body :" + responseBodyGet);
									System.out.println("BODY:"+responseBodyGet);
									reports_get(a,method,bimServices);

									
									
								}
								else
								{
									String[] idString=idvalue[0].split(",");
									String [] str1=new String[idString.length];

									for(a=0;a<idString.length;a++)
									{
									//	System.out.println(idString[a].toString());	
										str1[a]= idString[a].replaceAll("[\\[\\]\\(\\)]", "").trim();
									if((keyparam[0]==null) && (idvalue==null))
									{
											responseBodyPost=null;
									} 
									else if(idvalue!=null)
									{
										urlNew=url+str1[a];
									}
									System.out.println("URL:"+urlNew);
									LOG.info("Url :" + urlNew);
									RestAssured.baseURI = urlNew;
									 res= given()
											.relaxedHTTPSValidation()
											.when()
											.get()
											.then()
											.statusCode(statusCode)
											.and()
											.contentType(ContentType.JSON)
											.and()
											.extract().response();
											
									responseBodyGet=res.asString();		
									LOG.info("Body :" + responseBodyGet);
									System.out.println("BODY:"+responseBodyGet);
									reports_get(a,method,bimServices);
									
								}
							}	
											
				if (res.getStatusCode() == statusCode)
				{
				System.out.println("Status: Passed");
				
				String passendtim = time_calculate() + "Sec";
				
				// count pass test cases
				pass++;		
				
				ReportsUtil.addTestCase1(service_type, bimServices, urlNew, soapdesc, responseBodyGet, passendtim, "Pass",testCaseGetId);
				
			}  else {
				if(res.getStatusCode() != statusCode){
				System.out.println("Status: Failed");
				
				String failendtim = time_calculate() + "Sec";
				// count fail test cases
				fail++;
				ReportsUtil.addTestCase1(service_type, bimServices, urlNew, soapdesc, responseBodyGet, failendtim, "Fail",testCaseGetId);
				}
			}
			
			if(responseBodyPost!=null) 
			{
				responseBodyPost=null;
			}
			else if (responseBodyupdate!=null)
			{
				responseBodyupdate=null;

			}
			/*} catch (Exception e) {

			failendtim = time_calculate() + "Sec";
			// count fail test cases
			fail++;

			String exception = e.toString();
			System.out.println("Exception: " + exception);
			ReportsUtil.addTestCase1(service_type, bimServices, url, soapdesc, exception, failendtim, "Fail",testCaseGetId);
		}*/
	}
	
	private static void reports_get(int a,String method, String domain) throws ParseException
	{
		if(responseBodydelete!=null)
		{
	        JsonPath jsonRes = new JsonPath(responseBodyGet);
			String str=jsonRes.get("messages.message").toString();
			String msg = str.replaceAll("[\\[\\]\\(\\)]", ""); 
			System.out.println("Message:"+msg);
			responseBodydelete=null;
		}	
		else if (((responseBodyPost!=null) || (responseBodyupdate!=null)))
		{
			if(!(!(method.equalsIgnoreCase("NA")) && (!(domain.contains("BMS")))))
			{
				validateResponses(responseBodyGet,a);
			}			
		}
		
		
		if((responseBodyPost!=null||responseBodyupdate!=null))
		{	
			if(!(!(method.equalsIgnoreCase("NA")) && (!(domain.contains("BMS")))))
				
			{								
			for(int j=0;j<expectedValue.length;j++)
			{
				if(expectedValue[j]!=null)
				{

					if(j==0)
					{									
						responseBodyGet=responseBodyGet+'\n'+"Expected Value-"+expectedValue[j]+'\n'+"Actual Value-"+actualValue[j];
						
					}
									
					else
					{	
						responseBodyGet=responseBodyGet+'\n'+"Expected Value-"+expectedValue[j]+'\n'+"Actual Value-"+actualValue[j];				
						//System.out.println("BODY!:"+responseBodyGet);
					}

				}
				
			}
		}
	}
		else if((responseBodyPost!=null ||responseBodyupdate!=null) && MissmatchedValue!=null){
			
			for(int j=0;j<expectedValue.length;j++)
			{
				if(expectedValue[j]!=null)
				{
					if(j==0)
					{
							responseBodyGet=responseBodyGet+'\n'+"Expected Value-"+expectedValue[j]+'\n'+"Actual Value-"+MissmatchedValue[j];
					}
					else
					{
						responseBodyGet=responseBodyGet+'\n'+"Expected Value-"+expectedValue[j]+'\n'+"Actual Value-"+MissmatchedValue[j];

					}
				}
			}
			
		}
	}
	
	private static void validateResponses(String response, int a) throws ParseException{
		
		expectedValue= new String[old1.length];
		MissmatchedValue= new String[old1.length];
		actualValue= new String[old1.length];

        JsonPath jsonRes = new JsonPath(response);
        String[] strtype=type.split("#");
          for (int j = 0; j <old1.length ; j++) {

        	if(old1[j]!=null)
        	 	{								

        		if(strtype[0].equalsIgnoreCase("single"))
        		{
                	old1[j]=old1[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");  

        		}
        		else if(strtype[0].equalsIgnoreCase("multiple"))
        		{
        			if(old1[j].contains("["+a+"]"))
        			{
            			if(strtype[1]!=null)
        				old1[j]=old1[j].replaceAll(strtype[1]+"\\[(.*?)\\]"+".", "");   
                	}
        			else{
        				continue;
        			}
        		}
        		else
        		{
        			old1[j]=old1[j];
        		}
        			String value="response."+old1[j];     
        			String temp=jsonRes.get(value).toString();        		
        			String str=temp.replaceAll("[\\[\\]\\(\\)]", "");
            		
        			if(str.equalsIgnoreCase(new1[j])){
        				expectedValue[j]=old1[j]+":"+new1[j];
						actualValue[j]=old1[j]+":"+str;
        				System.out.println("Expected Response:-"+old1[j]+":"+new1[j]);
						System.out.println("Actual Response:-"+old1[j]+":"+str);
        			//System.out.println("Key:"+old12[j] +":"+"Value:"+jsonRes.get(old12[j]));}

        			}
						else
							{
							System.out.println("Expected Response:-"+old1[j]+":"+new1[j]);
							System.out.println("Missmatched Response:-"+old1[j]+":"+str);
	        				expectedValue[j]=old1[j]+":"+new1[j];
							MissmatchedValue[j]=old1[j]+":"+str;

							}
        			
        	 	}
			}
			
}
	
	void func_Update_Microservice(String url, String txtpath1,String jsonpath1,String type1,String[] oldtext1,
			Object[] newtext1, int noOfcol, String service_type, String soapdesc1, String bimServices,
		int statusCode,String UniqueField,String method,String testCasePostId) throws IOException, ParseException, InterruptedException
	{
		//try {
			service=service_type;
			// Get start time
			startTime1 = System.currentTimeMillis();
			type=type1;
			//int count = oldtext1.length;
								
			String[] temp=UniqueField.split("#");
			int alen= noOfcol+temp.length;

			String[] tempold= new String[alen];
			Object[] tempnew=new Object[alen];			
			for(int i=0;i<noOfcol;i++){
				tempold[i]=oldtext1[i];
				tempnew[i]=newtext1[i];
				//System.out.println("old:"+tempold[i]+" "+"NEW:"+tempnew[i]);

			}			
			int j=0;	
		//	System.out.println("A:"+noOfcol+":"+"B:"+alen);
			for(int a=noOfcol;a<alen;a++,j++)
			{
				//System.out.println(method);
				tempold[a]=temp[j];			
				if(method.equalsIgnoreCase("NUL"))
				{
					tempnew[a]="";
				}
				else if(method.equalsIgnoreCase("Invalid"))
				{
					tempnew[a]= "ABC";
				}
				else if(method.equalsIgnoreCase("NA"))
				{
					//System.out.println("DATA:"+keyvalue[j]);
					tempnew[a]=keyvalue[j];

				}				
				
				//System.out.println("old:"+tempold[a]+" "+"NEW:"+tempnew[a]);
				
			}
			
			modifyJson( txtpath1, "NA", jsonpath1, tempold, tempnew,service_type);
			//func_updateJson_Object(txtpath1,txtpath2,jsonpath1, tempold, tempnew,noOfcol);			

			LOG.info("Got the JSON File from func_updateJson_Object Function");

			System.out.println("URL:"+url);
			File fstream = new File(jsonpath1);

			RestAssured.baseURI = url;
			Response response=given()
			.accept("application/json")
			.contentType("application/json")
			.relaxedHTTPSValidation()
			.body(fstream)
			.when()
			.put()
			.then()
			.assertThat()
			.contentType(ContentType.JSON)
			.and()
			.extract().response();
			
			responseBodyupdate = response.asString();
			
			System.out.println("BODY:"+responseBodyupdate);
			
			Thread.sleep(1000);
				
			if (response.getStatusCode() == statusCode) {
				System.out.println("Status: Passed");
				String passendtim = time_calculate() + "Sec";
				// count pass test cases
				pass++;
				
				ReportsUtil.addTestCase1(service_type, bimServices, url, soapdesc1, responseBodyupdate, passendtim,
						"Pass",testCasePostId);

			}else {
				System.out.println("Status: Failed");

				failendtim = time_calculate() + "Sec";
				// count fail test cases
				fail++;
				ReportsUtil.addTestCase1(service_type, bimServices, url, soapdesc1, responseBodyupdate, failendtim,
						"Fail",testCasePostId);

			}
			/*} catch (Exception e) {

			
			failendtim = time_calculate() + "Sec";
			// count fail test cases
			fail++;

			String exception = e.toString();
			System.out.println("Exception: " + exception);
			
			ReportsUtil.addTestCase1(service_type, bimServices, url, soapdesc1, exception, failendtim, "Fail",testCasePostId);
		}
*/

	}
	
	void func_Delete_Microservice(String url, String txtpath1,String jsonpath1,String service_type1, String soapdesc1,
			String bimServices,int statusCode,String testCasePostId,String method,String unique)
	{
		try {
			
			System.out.println("URL:"+url);
		//disableSslVerification();
			//modifyJsonDELETE(txtpath1,jsonpath1);
			
			if(method.equalsIgnoreCase("NA"))
			{
				String[]  temp=unique.split("#");
				modifyJson( txtpath1, "NA", jsonpath1, temp, keyvalue,service_type1);
			}
			else
			{
				String[] temp=unique.split("#");
				String[] negval={"ABC","ABC","ABC"};
				String[] nullval= {"nul","nul","nul"};
				
				if(method.equalsIgnoreCase("NUL"))
				{
					modifyJson( txtpath1, "NA", jsonpath1, temp, nullval,service_type1);

				}
				else if(method.equalsIgnoreCase("Invalid"))
				{
					modifyJson( txtpath1, "NA", jsonpath1, temp, negval,service_type1);
				}
				
			}	
			
			LOG.info("Got the JSON File from func_deleteJson_Object Function");

			File fstream = new File(jsonpath1);
			Response response=null;
			if(service_type1.contains("#"))		
				{
					service_type1="DELETE";
					RestAssured.baseURI = url;
					response=given()
						.accept("application/json")
						.contentType("application/json")
						.relaxedHTTPSValidation()
						.body(fstream)
						.when()
						.delete()
						.then()
						.assertThat()
						.contentType(ContentType.JSON)
						.and()
						.extract().response();
				}
			

			else
				{				
					RestAssured.baseURI = url;
					response=given()
							.accept("application/json")
							.contentType("application/json")
							.relaxedHTTPSValidation()
							.body(fstream)
							.when()
							.put()
							.then()
							.assertThat()
							.contentType(ContentType.JSON)
							.and()
							.extract().response();
			
				}
		responseBodydelete = response.asString();
		
		System.out.println("BODY:"+responseBodydelete);

		if (response.getStatusCode() == statusCode) {
			System.out.println("Status: Passed");
			
			String passendtim = time_calculate() + "Sec";
			// count pass test cases
			pass++;
			ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBodydelete, passendtim,
					"Pass",testCasePostId);

		}
		else {
			System.out.println("Status: Failed");

			failendtim = time_calculate() + "Sec";
			// count fail test cases
			fail++;
			ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBodydelete, failendtim,
					"Fail",testCasePostId);

		}
			
		}  catch (IOException e) {		
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}
	
	private static JsonObject unFlattenedMap(Map<String, Object> flattenedUpdatedMap) {

			JsonObject jsonObj = Json.object();
			for (Entry<String, Object> mem : flattenedUpdatedMap.entrySet()) {
				String key = mem.getKey();
				Object val = mem.getValue();
				
				if (val instanceof Boolean) {
					jsonObj.add(key, (Boolean) val);
				} else if (val instanceof String) {
					
					//########## Modified (Start)###################################
					//System.out.println(val.toString());
					if("int".equals(determineDataType((String) val))) {
						
						jsonObj.add(key, Integer.parseInt(splitString(val.toString())));
								
					}else if("bool".equals(determineDataType((String) val))) {
						
						jsonObj.add(key, Boolean.parseBoolean(splitString(val.toString())));
							
					}else
					{
						if( ((String) val).equalsIgnoreCase("NUL"))
						{
							jsonObj.add(key, "");

						}
						else
						{										
							jsonObj.add(key, (String) val);
						}
					}
					
					//########## Modified (End)###################################	
					
					
				} else if (val instanceof Number) {
					
				//Need to change here for integer data type
				jsonObj.add(key, val instanceof Integer ? (Integer) val : Double.parseDouble(val.toString()));
					
				} else {
					jsonObj.add(key, Json.NULL);
				}
			}

			return jsonObj;

		}

	private static String prettyJsonConvertor(String unflattenJson) throws IOException {

			ObjectMapper mapper = new ObjectMapper();

			return mapper.writerWithDefaultPrettyPrinter()
					.writeValueAsString(mapper.readValue(unflattenJson, Object.class));

		}
		
	private static String splitString(String data) 
	{
			
			if(data.contains("#"))
				
			{
				return data.substring(0, data.lastIndexOf('#'));
			}
			else
			{
				return data;
			}

	}
		
	private static String determineDataType(String data) 
	{
			String dataType = "string";
			
			if(data.contains("#int") || data.contains("#bool")) {
				
				dataType = data.substring(data.lastIndexOf('#') + 1);
			}
			
		    return dataType;
				
		}

	
}


/*
else if (res.getStatusCode() == 201) {
System.out.println("Site created. Status: Passed");

String passendtim = time_calculate() + "Sec";
// count pass test cases
pass++;

String responseBody1 = "Site created.%nResponse body" + responseBodyPost;
ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBody1, passendtim, "Pass",testCasePostId);

} else if (res.getStatusCode() == 204) {
System.out.println("If the record doesn’t exist or already deleted. Status: Passed");

String passendtim = time_calculate() + "Sec";
// count pass test cases
pass++;

String responseBody1 = "If the record doesn’t exist or already deleted.%nResponse body"
		+ responseBodyPost;
ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBody1, passendtim, "Pass",testCasePostId);

} else if (res.getStatusCode() == 409) {
System.out.println("Status: Passed");

String passendtim = time_calculate() + "Sec";
// count pass test cases
pass++;

String responseBody1 = "If Site Id/Site name already exists in cache OR if the request is for a deleted site i.e. Status is “inactive”.%nResponse body"
		+ responseBodyPost;
ReportsUtil.addTestCase1(service_type1, bimServices, url, soapdesc1, responseBody1, passendtim, "Pass",testCasePostId);

} */