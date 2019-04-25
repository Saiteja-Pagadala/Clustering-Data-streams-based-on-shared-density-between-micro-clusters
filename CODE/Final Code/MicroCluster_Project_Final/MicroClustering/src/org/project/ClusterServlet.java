package org.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import project.utils.AlgoDBSTREAM;
import project.utils.Cluster;
import project.utils.DoubleArray;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class ClusterServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response){
		String query = request.getParameter("query");
		if("home".equals(query)){
			try{
			RequestDispatcher view = request.getRequestDispatcher("/jsp/home.jsp");
    	    view.forward(request, response);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("loadData".equals(query)){
			try{
				RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
	    	    view.forward(request, response);
			}
			catch(Exception ex){
				
			}
		}
		else if("viewData".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
					List<XYData> dataList = new ArrayList<>();
					File file = (File) session.getAttribute("File");
					System.out.println(file.toString());
					//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file.toString());
					BufferedReader br = new BufferedReader(new FileReader(file));
					CSVReader reader = new CSVReader(br);
					String[] row = null;
					while((row=reader.readNext())!=null){
					XYData data = new XYData();
					data.setxData(row[0]);
					data.setyData(row[1]);
					dataList.add(data);
					}
					System.out.println(dataList);
					request.setAttribute("dataList", dataList);
					RequestDispatcher view = request.getRequestDispatcher("/jsp/viewData.jsp");
		    	    view.forward(request, response);
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("viewDataGraph".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
					OutputStream out = response.getOutputStream();
					List<XYData> dataList = new ArrayList<>();
					File file = (File) session.getAttribute("File");
					System.out.println(file.toString());
					//InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file.toString());
					BufferedReader br = new BufferedReader(new FileReader(file));
					CSVReader reader = new CSVReader(br);
					String[] row = null;
					XYSeries xySeries = new XYSeries("Data");
					while((row=reader.readNext())!=null){
					double xData = Double.parseDouble(row[0]);
					double yData = Double.parseDouble(row[1]);
					xySeries.add(xData,yData);
					}
					XYSeriesCollection xyCollection = new XYSeriesCollection();
					xyCollection.addSeries(xySeries);
					JFreeChart xylineChart = ChartFactory.createScatterPlot("Scatter Plot", "X Attribute", "Y Attribute", xyCollection, PlotOrientation.VERTICAL, true, true, false);
					response.setContentType("image/png");
					ChartUtilities.writeChartAsPNG(out, xylineChart, 300, 300);
					
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("viewReclusterDataGraph".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
					OutputStream out = response.getOutputStream();
					List<XYData> dataList = new ArrayList<>();
					File file = new File("D:/MicroCluster/Files/"+"recluster.csv");
					BufferedReader br = new BufferedReader(new FileReader(file));
					CSVReader reader = new CSVReader(br);
					String[] row = null;
					XYSeries xySeries = new XYSeries("Data");
					while((row=reader.readNext())!=null){
					double xData = Double.parseDouble(row[0].replace("\"", ""));
					double yData = Double.parseDouble(row[1].replace("\"", ""));
					xySeries.add(xData,yData);
					}
					XYSeriesCollection xyCollection = new XYSeriesCollection();
					xyCollection.addSeries(xySeries);
					JFreeChart xylineChart = ChartFactory.createScatterPlot("Scatter Plot", "X Attribute", "Y Attribute", xyCollection, PlotOrientation.VERTICAL, true, true, false);
					response.setContentType("image/png");
					ChartUtilities.writeChartAsPNG(out, xylineChart, 300, 300);
					
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("viewClusterDataGraph".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
					OutputStream out = response.getOutputStream();
					List<XYData> dataList = new ArrayList<>();
					int clusetrSize = (int) session.getAttribute("clusterListSize");
					String path="D:/MicroCluster/Files/";
					List<List<XYData>> clusterList = new ArrayList<>();
					
					for(int i=0;i<clusetrSize;i++){
						List<XYData> list = new ArrayList<>();
						String csvFile = path+"cluster"+i+".csv";
				        BufferedReader br = null;
				        String line = "";
				        String cvsSplitBy = ",";
				        
				        try {
				        	
				            br = new BufferedReader(new FileReader(csvFile));
				            while ((line = br.readLine()) != null) {
				            	XYData xyData = new XYData();	
				                // use comma as separator
				                String[] data = line.split(cvsSplitBy);
				                xyData.setxData(data[0]);
				                xyData.setyData(data[1]);
				                list.add(xyData);

				            }
				        clusterList.add(list);

				        } catch (FileNotFoundException e) {
				            e.printStackTrace();
				        } catch (IOException e) {
				            e.printStackTrace();
				        } finally {
				            if (br != null) {
				                try {
				                    br.close();
				                } catch (IOException e) {
				                    e.printStackTrace();
				                }
				            }
				        }
					}
					List<XYData> list2 = new ArrayList<>();
					String csvFile = path+"Noise"+".csv";
			        BufferedReader br = null;
			        String line = "";
			        String cvsSplitBy = ",";
			        
			        try {
			        	
			            br = new BufferedReader(new FileReader(csvFile));
			            while ((line = br.readLine()) != null) {
			            	XYData xyData = new XYData();	
			                // use comma as separator
			                String[] data = line.split(cvsSplitBy);
			                xyData.setxData(data[0]);
			                xyData.setyData(data[1]);
			                list2.add(xyData);

			            }
			       

			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally {
			            if (br != null) {
			                try {
			                    br.close();
			                } catch (IOException e) {
			                    e.printStackTrace();
			                }
			            }
			        }
			        
					
					XYSeriesCollection xyCollection = new XYSeriesCollection();
					
					for(int i=0;i<clusterList.size();i++){
					XYSeries xySeries = new XYSeries("Cluster"+(i+1));
					List<XYData> list3 = new ArrayList<>();
					list3=clusterList.get(i);
					for(int j=0;j<list3.size();j++){
						list3.get(j).getxData().replace("\"", "");
						double xData = Double.parseDouble(list3.get(j).getxData().replace("\"", ""));
						double yData = Double.parseDouble(list3.get(j).getyData().replace("\"", ""));
						xySeries.add(xData,yData);
						}
					xyCollection.addSeries(xySeries);
					}
					XYSeries xySeries = new XYSeries("Noise");
					for(int i=0;i<list2.size();i++){
						double xData = Double.parseDouble(list2.get(i).getxData().replace("\"", ""));
						double yData = Double.parseDouble(list2.get(i).getyData().replace("\"", ""));
						xySeries.add(xData,yData);
					}
					xyCollection.addSeries(xySeries);
					JFreeChart xylineChart = ChartFactory.createScatterPlot("Scatter Plot", "X Attribute", "Y Attribute", xyCollection, PlotOrientation.VERTICAL, true, true, false);
					response.setContentType("image/png");
					ChartUtilities.writeChartAsPNG(out, xylineChart, 300, 300);
					
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("viewCluster".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
				RequestDispatcher view = request.getRequestDispatcher("/jsp/cluster.jsp");
	    	    view.forward(request, response);
				}
			}
			catch(Exception ex){
				
			}
		}
		else if("viewClusters".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
				int clusetrSize = (int) session.getAttribute("clusterListSize");
				String path="D:/MicroCluster/Files/";
				List<List<XYData>> clusterList = new ArrayList<>();
				
				for(int i=0;i<clusetrSize;i++){
					List<XYData> list = new ArrayList<>();
					String csvFile = path+"cluster"+i+".csv";
			        BufferedReader br = null;
			        String line = "";
			        String cvsSplitBy = ",";
			        
			        try {
			        	
			            br = new BufferedReader(new FileReader(csvFile));
			            while ((line = br.readLine()) != null) {
			            	XYData xyData = new XYData();	
			                // use comma as separator
			                String[] data = line.split(cvsSplitBy);
			                xyData.setxData(data[0]);
			                xyData.setyData(data[1]);
			                list.add(xyData);

			            }
			        clusterList.add(list);

			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally {
			            if (br != null) {
			                try {
			                    br.close();
			                } catch (IOException e) {
			                    e.printStackTrace();
			                }
			            }
			        }
				}
				List<XYData> list2 = new ArrayList<>();
				String csvFile = path+"Noise"+".csv";
		        BufferedReader br = null;
		        String line = "";
		        String cvsSplitBy = ",";
		        
		        try {
		        	
		            br = new BufferedReader(new FileReader(csvFile));
		            while ((line = br.readLine()) != null) {
		            	XYData xyData = new XYData();	
		                // use comma as separator
		                String[] data = line.split(cvsSplitBy);
		                xyData.setxData(data[0]);
		                xyData.setyData(data[1]);
		                list2.add(xyData);

		            }
		       

		        } catch (FileNotFoundException e) {
		            e.printStackTrace();
		        } catch (IOException e) {
		            e.printStackTrace();
		        } finally {
		            if (br != null) {
		                try {
		                    br.close();
		                } catch (IOException e) {
		                    e.printStackTrace();
		                }
		            }
		        }
				request.setAttribute("clusterList", clusterList);
				request.setAttribute("noiseList", list2);
				RequestDispatcher view = request.getRequestDispatcher("/jsp/microcluster.jsp");
	    	    view.forward(request, response);
				}
			}
			catch(Exception ex){
				
			}
		}
		else if("viewReCluster".equals(query)){
			try{
				HttpSession session = request.getSession(false);
				if(session.getAttribute("File")==null){
					request.setAttribute("msg", "No Dataset Found. Please load dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
			int clusetrSize = (int) session.getAttribute("clusterListSize");
			String path="D:/MicroCluster/Files/";
			List<List<XYData>> clusterList = new ArrayList<>();
			
			for(int i=0;i<clusetrSize;i++){
				List<XYData> list = new ArrayList<>();
				String csvFile = path+"cluster"+i+".csv";
		        BufferedReader br = null;
		        String line = "";
		        String cvsSplitBy = ",";
		        
			        try {
			        	
			            br = new BufferedReader(new FileReader(csvFile));
			            while ((line = br.readLine()) != null) {
			            	XYData xyData = new XYData();	
			                // use comma as separator
			                String[] data = line.split(cvsSplitBy);
			                xyData.setxData(data[0]);
			                xyData.setyData(data[1]);
			                list.add(xyData);
	
			            }
			        clusterList.add(list);
	
			        } catch (FileNotFoundException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } finally {
			            if (br != null) {
			                try {
			                    br.close();
			                } catch (IOException e) {
			                    e.printStackTrace();
			                }
			            }
			        }
			    }
			//
			File file = new File("D:/MicroCluster/Files/"+"recluster.csv");
			List<XYData> reclusterList = new ArrayList<>();
			CSVWriter csvWriter = new CSVWriter(new FileWriter(file));
			for(int i=0;i<clusterList.size();i++){
				List<XYData> list = clusterList.get(i);
				String[] values;
				for(int j=0;j<list.size();j++){
					values=((list.get(j).getxData().replace("\"", ""))+","+(list.get(j).getyData().replace("\"", ""))).split(",");
					csvWriter.writeNext(values);
					XYData xyData = new XYData();
					xyData.setxData(list.get(j).getxData().replace("\"", ""));
					xyData.setyData(list.get(j).getyData().replace("\"", ""));
					reclusterList.add(xyData);
				}
			}
			csvWriter.close();
			request.setAttribute("reclusterList", reclusterList);
			RequestDispatcher view = request.getRequestDispatcher("/jsp/recluster.jsp");
    	    view.forward(request, response);
			
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
		else if("downloadRecluster".equals(query)){
			try{
			response.setContentType("text/html");  
			PrintWriter out = response.getWriter(); 
			response.setContentType("APPLICATION/OCTET-STREAM");   
			response.setHeader("Content-Disposition","attachment; filename=\"" + "recluster.csv" + "\"");
			FileInputStream fileInputStream = new FileInputStream(new File("D:/MicroCluster/Files/"+"recluster.csv"));  
            
			int i;   
			while ((i=fileInputStream.read()) != -1) {  
			out.write(i);   
			}   
			fileInputStream.close();   
			out.close();
		}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		String source = request.getParameter("source");
		if("uploadFile".equals(source)){
			try{
			String UPLOAD_DIRECTORY = "d:/MicroCluster/Files";
			new File(UPLOAD_DIRECTORY).mkdirs();
			String fname = null;
			String fsize = null;
			String ftype = null;
			List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : multiparts) {
				if (!item.isFormField()) {
					fname = new File(item.getName()).getName();
					fsize = new Long(item.getSize()).toString();
					ftype = item.getContentType();
					item.write(new File(UPLOAD_DIRECTORY + File.separator
							+ fname));
				}
			}
			File file = new File(UPLOAD_DIRECTORY+"/"+fname);
			HttpSession session = request.getSession(true);
			session.setAttribute("File", file);
			request.setAttribute("msg", "File Uploaded Successfully");
			RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
    	    view.forward(request, response);
		}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
		else if("minepsilon".equals(source)){
			try{
				HttpSession session = request.getSession(false);
				
				if(session.getAttribute("File")==null){
					request.setAttribute("msg1", "Session Expired. Please upload a dataset.");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/index.jsp");
		    	    view.forward(request, response);
				}
				else{
					
					String minPts = request.getParameter("minPts");
					String epsl = request.getParameter("eps");
					File file = (File) session.getAttribute("File");
					int minpts = Integer.parseInt(minPts);
					double eps = Double.parseDouble(epsl);
					String separator = ",";
					AlgoDBSTREAM algo = new AlgoDBSTREAM();
					List<Cluster> clusterList = new ArrayList<>();
					clusterList=algo.runAlgorithm(file.toString(), minpts, eps, separator);
					session.setAttribute("clusterListSize", clusterList.size());
					String output = "D:/MicroCluster/Files/output.txt";
					algo.saveToFile(output);
					algo.printStatistics();
					request.setAttribute("msg", "Data is converted to Micro Clusters");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/cluster.jsp");
		    	    view.forward(request, response);
				}
			}
			catch(NumberFormatException ex){
				request.setAttribute("msg1", "Please enter EPS and MinPoints in Numerics only");
				RequestDispatcher view = request.getRequestDispatcher("/jsp/cluster.jsp");
	    	    view.forward(request, response);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
}
