<%@page import="com.rays.pro4.controller.OrderCtl"%>
<%@page import="com.rays.pro4.Util.DataUtility"%>
<%@page import="com.rays.pro4.Util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
   <jsp:useBean id="bean" class="com.rays.pro4.Bean.OrderBean" scope ="request"></jsp:useBean>
   <%@ include file="Header.jsp"%>
   
   <center>

    <form action="OrderCtl" method="post">
    
     <div align="center">    
            <h1>
 				
           		<% if(bean != null && bean.getId() > 0) { %>
            <tr><th><font size="5px"> Update Order </font>  </th></tr>
            	<%}else{%>
			<tr><th><font size="5px"> Add Order </font>  </th></tr>            
            	<%}%>
            </h1>
            
               <h3><font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
            <font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
            </h3>
	       
            
            
            
    <table>
    <th> <input type="hidden" name="id" value="<%=bean.getId()%>"></th>
        <tr>
          <th align="left">Order Name <span style="color: red">*</span> :</th>
          <td><input type="text" name="orderName" placeholder="Enter Order Name" size="26"  value="<%=DataUtility.getStringData(bean.getOrderName())%>"></td>
           <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("orderAddress", request)%></font></td> 
                    
                </tr>
                 </tr>
                
           <th align="left">Order type <span style="color: red">*</span> :</th>
          <td><input type="text" name="type" placeholder="Enter Order Address" size="26"  value="<%=DataUtility.getStringData(bean.getOrderAddress())%>"></td>
           <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("type", request)%></font></td> 
                    
                <tr>
            
                <tr>
                
           <th align="left">Order Address <span style="color: red">*</span> :</th>
          <td><input type="text" name="orderAddress" placeholder="Enter Order Address" size="26"  value="<%=DataUtility.getStringData(bean.getOrderAddress())%>"></td>
           <td style="position: fixed "><font color="red"><%=ServletUtility.getErrorMessage("orderAddress", request)%></font></td> 
                    
                <tr>
            
          
        <tr ><th></th>
                <%
                if(bean.getId()>0){
                %>
                <td colspan="2" >
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_UPDATE%>">
                
                <% }else{%>
                
                <td colspan="2" > 
                &nbsp;  &emsp;
                    <input type="submit" name="operation" value="<%=OrderCtl.OP_SAVE%>">
                
                <% } %>
                </tr>
      
       </form>
                
     </table>          
            
   
   

</body>
</html>