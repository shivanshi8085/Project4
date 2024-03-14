<%@page import="com.rays.pro4.controller.HotelListCtl"%>
<%@page import="com.rays.pro4.Bean.HotelBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
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
  <jsp:useBean id="bean" class="com.rays.pro4.Bean.HotelBean" scope="request"></jsp:useBean>
	<%@include file="Header.jsp"%>
	<form action="<%=ORSView.HOTEL_LIST_CTL%>" method="post">

<center>

     <div align="centre">
				<h1>Hotel List</h1>
				<h3>
					<font colour="red"><%=ServletUtility.getErrorMessage(request)%></font>
					<font colour="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

<%
		         			int pageNo = ServletUtility.getPageNo(request);
					int pageSize = ServletUtility.getPageSize(request);
					int index = ((pageNo - 1) * pageSize) + 1;

                    List list = ServletUtility.getList(request);
					Iterator <HotelBean>it = list.iterator();

                      if (list.size() != 0) {
				%>
				<table width="100%" align="center">
				<tr>
					<th></th>
					<td align="center"><label>HotelName</font> :
					</label> <input type="text" name="hotelname" placeholder="Enter Hotel Name"
						value="<%=ServletUtility.getParameter("hotelname", request)%>">
						
						<input type="submit" name="operation"
						value="<%=HotelListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=HotelListCtl.OP_RESET%>">
</td>
</tr>
				
				<table border="1" width="100%" align="centre" cellpadding=6px
					cellspacing=".2">
					<tr style="background: orange">
						<th><input type="checkbox" id="select_all" name="select">Select
							All</th>

                        <th>S.No.</th>
						<th>HotelName</th>
						<th>RoomNo</th>
						<th>Edit</th>
					</tr>
					<%
						while (it.hasNext()) {
								bean = it.next();
					%>
					<tr align="centre">

     <td><input type="checkbox" class="checkbox" name="ids" value="<%=bean.getId()%>"></td>

                         <td><%=index++%></td>
						<td><%=bean.getHotelName()%></td>
						<td><%=bean.getRoomNo()%></td>
					    <td><a href="HotelCtl?id=<%=bean.getId()%>">Edit</a></td>
					</tr>
					<%
						}
					%>
					<tr>
						<table>
							<td><input type="submit" name="operation"
								value="<%=HotelListCtl.OP_DELETE%>"></td>
							</center>
						</table>
					</tr>

<%
						}
						if (list.size() == 0) {
					%>
					<td align="centre"><input type="submit" name="operation"
						value="<%=HotelListCtl.OP_BACK%>"> &nbsp; &nbsp;</td>
					<%
						}
					%>
					</tr>
				</table>
				<br>

</table>

</table>
	</form>
	</br>
	</br>
	</br>
	</br>

</center>

<%@include file="Footer.jsp"%>


  


</body>
</html>