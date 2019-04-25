<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <title>Clustering Data Streams Based on Shared Density</title>
    <!-- BOOTSTRAP CORE STYLE  -->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
    <!-- FONT AWESOME STYLE  -->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
    <!-- CUSTOM STYLE  -->
    <link href="assets/css/style.css" rel="stylesheet" />
    <!-- GOOGLE FONT -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />

</head>
<body>
    <div class="navbar navbar-inverse set-radius-zero" >
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <h3>
					Clustering Data Streams Based on Shared Density Between Micro Clusters
                   
                </h3>

            </div>

            <!-- <div class="right-div">
                <a href="#" class="btn btn-info pull-right">LOG ME OUT</a>
            </div> -->
        </div>
    </div>
    <!-- LOGO HEADER END-->
    <section class="menu-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <div class="navbar-collapse collapse ">
                        <ul id="menu-top" class="nav navbar-nav navbar-right">
                           <li><a href="${pageContext.request.contextPath}/process?query=home" >Home</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=loadData" >Load Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewData" >View Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewCluster" >Convert Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewClusters" class="menu-top-active">View Micro Clusters</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewReCluster">Re-Cluster</a></li>
                        </ul>
                    </div>
                </div>

            </div>
        </div>
    </section>
     <!-- MENU SECTION END-->
    <div class="content-wrapper">
         <div class="container">
        <div class="row pad-botm">
            <div class="col-md-12">
                <h4 class="header-line">View Micro Clusters</h4>
                
                            </div>

        </div>
             <div class="row">
            <div class="col-md-12">
            <c:if test="${not empty msg }">
            	<h3><a href="${pageContext.request.contextPath}/process?query=viewData" class="btn btn-primary">Click Here</a> to View Uploaded Dataset</h3>
            </c:if>
            <c:if test="${empty msg }">
            <div class="col-md-6">
        		 <div class="panel panel-info">
                        <div class="panel-heading">
                            Micro Clusters
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
                                    <thead>
                                        <tr>
                                            <th>Cluster No</th>
                                            <th>Data</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${clusterList }" var="data" varStatus="count">
                                        <tr class="odd gradeX">
                                            <td>${count.index+1}</td>
                                            <td><button class="btn btn-primary btn-lg" style="padding: 5px 5px;font-size: 15px;" data-toggle="modal" data-target="#myModal${count.index}">
				                             View Data
				                            </button></td>
                                        </tr>
                                    </c:forEach>
                                       </tbody>
                                </table>
                            </div>
                            
                        </div>
                    </div>
                    <!--End Advanced Tables -->    
            </div>
            <div class="col-md-6">
             <div class="panel panel-info">
             
            			<div class="panel-heading">
                            Noise Cluster
                        </div>
                        <div class="panel-body">
                            <button class="btn btn-primary btn-lg" style="padding: 5px 5px;font-size: 15px;" data-toggle="modal" data-target="#myModalNoise">
                             View Data
                            </button>
                            
                        </div>
                        </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Scatter Plot
                        </div>
                        <div class="panel-body">
                             <img src="${pageContext.request.contextPath}/process?query=viewClusterDataGraph" width="520px" height="520" border="0" usemap="#chart" />
                        </div>
                        <div class="panel-footer">
                            
                        </div>
            
                </div>
                </div>
            </c:if>
        </div>
        <c:forEach items="${clusterList }" var="data" varStatus="count">
        <div class="modal fade" id="myModal${count.index }" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">Cluster ${count.index +1}</h4>
                                        </div>
                                        <div class="modal-body">
                                        
                                            <div class="table-responsive">
				                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
				                                    <thead>
				                                        <tr>
				                                            <th>X Attribute</th>
				                                            <th>Y Attribute</th>
				                                        </tr>
				                                    </thead>
				                                    <tbody>
				                                    <c:forEach items="${data }" var="d" varStatus="">
				                                        <tr class="odd gradeX">
				                                            <td>${d.xData}</td>
				                                            <td>${d.yData}</td>
				                                        </tr>
				                                    </c:forEach>
				                                       </tbody>
				                                </table>
				                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                      
                                        </div>
                                    </div>
                                </div>
                            </div>
                            </c:forEach>
                            <div class="modal fade" id="myModalNoise" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                            <h4 class="modal-title" id="myModalLabel">Noise Cluster</h4>
                                        </div>
                                        <div class="modal-body">
                                        
                                            <div class="table-responsive">
				                                <table class="table table-striped table-bordered table-hover" id="dataTables-example">
				                                    <thead>
				                                        <tr>
				                                            <th>X Attribute</th>
				                                            <th>Y Attribute</th>
				                                        </tr>
				                                    </thead>
				                                    <tbody>
				                                    <c:forEach items="${noiseList }" var="d" varStatus="">
				                                        <tr class="odd gradeX">
				                                            <td>${d.xData}</td>
				                                            <td>${d.yData}</td>
				                                        </tr>
				                                    </c:forEach>
				                                       </tbody>
				                                </table>
				                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                      
                                        </div>
                                    </div>
                                </div>
                            </div>
        </div>
    </div>
    </div>
     <!-- CONTENT-WRAPPER SECTION END-->
    <section class="footer-section">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                   &copy; 2017 | Clustering Data Streams Based on Shared Density
                </div>

            </div>
        </div>
    </section>
      <!-- FOOTER SECTION END-->
    <!-- JAVASCRIPT FILES PLACED AT THE BOTTOM TO REDUCE THE LOADING TIME  -->
    <!-- CORE JQUERY  -->
    <script src="assets/js/jquery-1.10.2.js"></script>
    <!-- BOOTSTRAP SCRIPTS  -->
    <script src="assets/js/bootstrap.js"></script>
    <!-- DATATABLE SCRIPTS  -->
    <script src="assets/js/dataTables/jquery.dataTables.js"></script>
    <script src="assets/js/dataTables/dataTables.bootstrap.js"></script>
      <!-- CUSTOM SCRIPTS  -->
    <script src="assets/js/custom.js"></script>
</body>
</html>