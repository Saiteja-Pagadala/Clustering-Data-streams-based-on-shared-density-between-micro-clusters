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
                           <li><a href="${pageContext.request.contextPath}/process?query=loadData" class="menu-top-active">Load Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewData" >View Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewCluster" >Convert Data</a></li>
                           <li><a href="${pageContext.request.contextPath}/process?query=viewClusters" >View Micro Clusters</a></li>
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
                <h4 class="header-line">Load Data</h4>
                
                            </div>

        </div>
             <div class="row">
            <div class="col-md-12">
            <c:if test="${ empty msg }">
             <div class="panel panel-info">
                        <div class="panel-heading">
                           UPLOAD DATASET
                        </div>
                        <div class="panel-body">
                            <form action="${pageContext.request.contextPath}/process?source=uploadFile" method="post" enctype="multipart/form-data">
                                        <div class="form-group">
                                            <label>Upload File</label>
                                            <input class="form-control" type="file" name="dataFile"/>
                                            <p class="help-block">Please upload a csv file of size less than 1MB</p>
                                           
                                        </div>
                                 <button type="submit" class="btn btn-info">Upload</button>

                                    </form>
                            </div>
                        </div>
            	</c:if>
            	<c:if test="${not empty msg }">
            		<h3>${msg }</h3>
            	</c:if>
               
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
      <!-- CUSTOM SCRIPTS  -->
    <script src="assets/js/custom.js"></script>
</body>
</html>