package driverScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.springframework.web.client.RestClientException;

import reports.ReportsUtil;
import util.TestUtil;

public class Main_Class_Generic {
	//public static String sheetname1;
	public static Logger LOG = Logger.getLogger(Main_Class_Generic.class.getName());

	public static void main(String[] args) throws Exception {

		try {
			Properties prop = new Properties();
			InputStream input = null;
			input = new FileInputStream("D:\\BIMAutomationFramework\\src\\resources\\config.properties");
			prop.load(input);

			DOMConfigurator.configure(prop.getProperty("loggerxml"));

			//// APPICATION_LOGS = Logger.getLogger("devpinoyLogger");
			TestUtil.now("dd.MMMM.yyyy hh.mm.ss aaa");

			TestUtil.now("dd.MMMM.yyyy hh.mm.ss aaa");
			String so = prop.getProperty("Report.html");

			String zmq = so + TestUtil.now("dd.MMM.yyyy hh.mm.ss aaa") + " " + "Report.html";

			String envrment = prop.getProperty("Environment");

			// EAI Dev Sumeru SIT
			ReportsUtil.startTesting(zmq, TestUtil.now("dd.MMMM.yyyy hh.mm.ss aaa"), envrment);

			Thread.sleep(1000);

			ReportsUtil.startSuite("Test Status");

			// CREATE FILE on specified path
			File src = new File(prop.getProperty("xlpath"));
			LOG.info("Opening Excel File");
			XSSFRow xlRow;
			XSSFCell xlCell;

			// Load the file
			FileInputStream fs;
			try {
				fs = new FileInputStream(src);

				// load the workbook
				@SuppressWarnings("resource")
				XSSFWorkbook wb= new XSSFWorkbook(fs);

				// Load sheet- Here we are loading first sheet i.e.
				// Main_BIMOrchestrator only
				XSSFSheet sh1 = wb.getSheet(prop.getProperty("sheetname"));

				// gives row count in sheet
				int noOfRows = sh1.getPhysicalNumberOfRows();

				// gives column count in sheet
				xlRow = sh1.getRow(0);
				int noOfColumns = xlRow.getLastCellNum();

				String[][] excelData = new String[noOfRows][noOfColumns];
				// String[][] newData = new String[noOfRows][noOfColumns];

				LOG.info("Getting Excel Data into a String array");
				for (int row = 0; row < noOfRows; row++) {
					for (int column = 0; column < noOfColumns; column++) {
						xlRow = sh1.getRow(row);
						xlCell = xlRow.getCell(column);

						if (xlCell.getCellTypeEnum() == CellType.STRING) {
							excelData[row][column] = xlCell.getStringCellValue();
						} else if (xlCell.getCellTypeEnum() == CellType.NUMERIC) {
							excelData[row][column] = String.valueOf((xlCell.getNumericCellValue()));
						} else if (xlCell.getCellTypeEnum() == CellType.BOOLEAN) {
							excelData[row][column] = String.valueOf(xlCell.getBooleanCellValue());
						}
					}
				}
				LOG.info("Selecting sheet(domain) that has run mode as YES");
				for (int row = 1; row < noOfRows; row++) {
					if (excelData[row][1].equalsIgnoreCase("Yes")) {
						String sheetname1 = excelData[row][0].toString();
						System.out.println("Domain: " + sheetname1);
						// Load sheet- Here we are loading first sheet i.e.
						// Main_BIMOrchestrator only
						XSSFSheet sh2 = wb.getSheet(sheetname1);

						// gives row count in sheet
						int noOfRows1 = sh2.getPhysicalNumberOfRows();

						if (noOfRows1 == 0) {
							System.out.println(sheetname1 + " is empty");
							LOG.info(sheetname1 + " is empty");
						} else {

							// gives column count in sheet
							xlRow = sh2.getRow(0);
							int noOfColumns1 = xlRow.getLastCellNum();

							// *****changes here

							// System.out.println("Row:"+noOfRows+"Column:"+noOfColumns);

							String[][] excelData1 = new String[noOfRows1][noOfColumns1];
							String[][] newData1 = new String[noOfRows1][noOfColumns1];

							int countofrows = 0;

							LOG.info("Getting Excel Data into a String array");
							for (int row1 = 0; row1 < noOfRows1; row1++) {
								for (int column = 0; column < noOfColumns1; column++) {
									xlRow = sh2.getRow(row1);
									xlCell = xlRow.getCell(column);

									if (xlCell.getCellTypeEnum() == CellType.STRING) {
										excelData1[row1][column] = xlCell.getStringCellValue();
									} else if (xlCell.getCellTypeEnum() == CellType.NUMERIC) {
										excelData1[row1][column] = String.valueOf((xlCell.getNumericCellValue()));
									} else if (xlCell.getCellTypeEnum() == CellType.BOOLEAN) {
										excelData1[row1][column] = String.valueOf(xlCell.getBooleanCellValue());
									}
								}
							}
							LOG.info("Selecting data that has run mode as YES");
							for (int row2 = 1; row2 < noOfRows1; row2++) {
								// changes need according to column no.
								if (excelData1[row2][11].trim().equalsIgnoreCase("Yes")) {
									countofrows++;
									for (int column = 0; column < noOfColumns1; column++) {
										// prints all the rows where isExecuted
										// column has Yes
										newData1[countofrows][column] = excelData1[row2][column].trim();
										//System.out.println(newData1[countofrows][column]);
									}
									
								}
							}
							@SuppressWarnings("unused")
							int count1=0;
							LOG.info("Selecting data that has values other than NULL or NA");

							for (int i = 1; i <= countofrows; i++) {
								// changes need according to column no.

								int counternew = 15;
								int colcountlast = noOfColumns1 - counternew;

								String oldText[] = new String[colcountlast];

								Object newText[] = new Object[colcountlast];

								String testcaseid = newData1[i][0].trim();
								//String Negative_Positive = newData1[i][1];
								//String Description_NegativePositive = newData1[i][2];
								String service_type = newData1[i][3].trim();
								String bimServices = newData1[i][4].trim();
								String idkey = newData1[i][5].trim();
								String QueryParam=newData1[i][6].trim();
								String uniqueField=newData1[i][7].trim();
								String type=newData1[i][8].trim();
								String testdescription = newData1[i][9].trim();
								String url = newData1[i][10].trim();
								String txtpart1 = prop.getProperty("txtpath1");
								String txtpart12 = prop.getProperty("txtpath12");
								String txtpart123 = prop.getProperty("txtpath123");
								String txtpart1234= prop.getProperty("txtpath1234");
								String statusCode = newData1[i][12].trim();
								float code1 = Float.parseFloat(statusCode);
								int code = Math.round(code1);

								String txtpart2 = newData1[i][13].trim();
								String jsonpathpart2 = newData1[i][14].trim();

								String txtpathpost = txtpart1 + txtpart2 + ".txt";
								String txtpath1delete = txtpart12 + txtpart2 + ".txt";
								String txtpath1update=txtpart123+jsonpathpart2+ ".txt";
								String txtpath1update1=txtpart123+txtpart2+ ".txt";
								String txtpath1get=txtpart1234+txtpart2+".txt";
								
								String jsonpathpart1 = prop.getProperty("jsonpath1");
								String jsonpathpart12=prop.getProperty("jsonpath12");
								String jsonpathpart123=prop.getProperty("jsonpath123");
								String jsonpathpart1234=prop.getProperty("jsonpath1234");

								String jsonpathpost = jsonpathpart1 + jsonpathpart2+".json";
								String jsonpathdelete= jsonpathpart12+jsonpathpart2+".json";
								String jsonpathupdate= jsonpathpart123+jsonpathpart2+".json";
								String jsonpathget= jsonpathpart1234+jsonpathpart2+".json";

								int noOfcol=0; 									
								for (noOfcol = 0; noOfcol < colcountlast; noOfcol++) {
									
									if (!(newData1[i][counternew] == null)) {
										if (!(newData1[i][counternew].equalsIgnoreCase("na"))) {
											oldText[noOfcol] = excelData1[0][counternew].trim();
											newText[noOfcol] = newData1[i][counternew].trim();
											//System.out.println(oldText[noOfcol]+":"+newText[noOfcol]);

											counternew++;
										} else {
											newText[noOfcol] = "NA";
											oldText[noOfcol] = "NA";

											// deleting this line
											count1++;
											counternew++;

										}
									} else {
										noOfcol--;

										break;
									}

								}
										
								Generic_Functions generic_function = new Generic_Functions();
								
								if (service_type.equalsIgnoreCase("get")) {
									LOG.info("Test Case Id:" + testcaseid);
									LOG.info("Test Case Description:" + testdescription);
									System.out.println("Test Case Id:" + testcaseid);
									System.out.println("Test Case Description:" + testdescription);
									LOG.info("Starting the GET Function");

									System.out.println("GET start");
									generic_function.func_Get_Microservice(url,service_type, testdescription,
											bimServices, code, idkey,testcaseid);
									System.out.println("Get End");
									LOG.info("Ending the GET Function");
									System.out.println("------------------------------------------------------------------------------"
						+ "-------------------------------------------------------------------");
								} else if (service_type.equalsIgnoreCase("post")) {
									LOG.info("Test Case Id:" + testcaseid);
									LOG.info("Test Case Description:" + testdescription);
									System.out.println("Test Case Id:" + testcaseid);
									System.out.println("Test Case Description:" + testdescription);
									System.out.println("Post start");
									LOG.info("Starting the POST Function");
									// changing noOfcol-count1 to noOfcol

									generic_function.func_Post_Microservice(txtpathpost, url, txtpath1update,jsonpathpost,
											type,oldText, newText, noOfcol,QueryParam, idkey,service_type, testdescription,
											bimServices, code,uniqueField,testcaseid);
									System.out.println("Post End");
									LOG.info("Ending the POST Function");
									System.out.println("------------------------------------------------------------------------------"
											+ "-------------------------------------------------------------------");
								}
								else if (service_type.equalsIgnoreCase("DELETE") || service_type.equalsIgnoreCase("DELETE#")) {
									LOG.info("Test Case Id:" + testcaseid);
									LOG.info("Test Case Description:" + testdescription);
									System.out.println("Test Case Id:" + testcaseid);
									System.out.println("Test Case Description:" + testdescription);
									System.out.println("DELETE start");
									LOG.info("Starting the DELETE Function");
									// changing noOfcol-count1 to noOfcol
									generic_function.func_Delete_Microservice(url,txtpath1delete,jsonpathdelete,
											 service_type, testdescription,
											bimServices, code,testcaseid,idkey,uniqueField);
									System.out.println("DELETE End");
									LOG.info("Ending the DELETE Function");
									System.out.println("------------------------------------------------------------------------------"
											+ "-------------------------------------------------------------------");
								}
								else if (service_type.equalsIgnoreCase("put")) {
									LOG.info("Test Case Id:" + testcaseid);
									LOG.info("Test Case Description:" + testdescription);
									System.out.println("Test Case Id:" + testcaseid);
									System.out.println("Test Case Description:" + testdescription);
									System.out.println("Update start");
									LOG.info("Starting the Update Function");
									// changing noOfcol-count1 to noOfcol

									generic_function.func_Update_Microservice(url,txtpath1update1,jsonpathupdate,type,
											oldText, newText, noOfcol, service_type, testdescription,
											bimServices, code,uniqueField,idkey,testcaseid);
									System.out.println("Update End");
									LOG.info("Ending the Update Function");
									System.out.println("------------------------------------------------------------------------------"
											+ "-------------------------------------------------------------------");
								}
								else if(service_type.equalsIgnoreCase("GET_POST")){
									LOG.info("Test Case Id:" + testcaseid);
									LOG.info("Test Case Description:" + testdescription);
									System.out.println("Test Case Id:" + testcaseid);
									System.out.println("Test Case Description:" + testdescription);
									LOG.info("Starting the GET Function");

									System.out.println("GET start");
									generic_function.func_Get_Post_Microservice(url,txtpath1get,jsonpathget,
											 service_type, testdescription,bimServices, code,testcaseid,idkey,type);
									System.out.println("Get End");
									LOG.info("Ending the GET Function");
									System.out.println("------------------------------------------------------------------------------"
						+ "-------------------------------------------------------------------");
								}
							}
						}
					}
				}
				fs.close();
				LOG.info("Closing Excel File");

				LOG.info("------------------------------------------------------------------------------"
						+ "-------------------------------------------------------------------");

				ReportsUtil.endSuite();
				Thread.sleep(1000);
				int pass = Generic_Functions.passTestCase();
				int fail = Generic_Functions.failTestCase();
				ReportsUtil.updateEndTime(pass, fail, pass + fail, TestUtil.now("dd.MMMM.yyyy hh.mm.ss aaa"));

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				LOG.info("File not found");
			} catch (IOException e) {
				e.printStackTrace();
				LOG.info("IO Exception");
			} catch (ParseException e) {
				e.printStackTrace();
				LOG.info("Parse Exception");
			}
		} catch (RestClientException e1) {
			e1.printStackTrace();
			LOG.info("Rest client Exception");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			LOG.info("Interrupted Exception");
		}

	}
}
