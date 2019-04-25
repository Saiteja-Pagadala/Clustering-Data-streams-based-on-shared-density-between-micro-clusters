package org.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import au.com.bytecode.opencsv.CSVReader;

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
					RequestDispatcher view = request.getRequestDispatcher("/jsp/viewData.jsp");
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
	}
	
	public void doPost(HttpServletRequest request,HttpServletResponse response){
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
					int minpts = Integer.parseInt(minPts);
					double eps = Double.parseDouble(epsl);
					String separator = ",";
					AlgoDBSTREAM algo = new AlgoDBSTREAM();
					
					request.setAttribute("msg", "eps and minpts submitted");
					RequestDispatcher view = request.getRequestDispatcher("/jsp/cluster.jsp");
		    	    view.forward(request, response);
				}
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
}
