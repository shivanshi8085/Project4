<%@page import="com.rays.pro4.controller.HotelCtl"%>
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

	<jsp:useBean id="bean" class="com.rays.pro4.Bean.HotelBean" scope="request"></jsp:useBean>
	<%@ include file="Header.jsp"%>

	<center>

		<form action="<%=ORSView.HOTEL_CTL%>" method="post">

			<div align="center">
				<h1>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<tr>
						<th><font size="5px"> Update Hotel </font></th>
					</tr>
					<%
						} else {
					%>
					<tr>
						<th><font size="5px"> Add Hotel </font></th>
					</tr>
					<%
						}
					%>
				</h1>

				<h3>
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%></font>
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font>
				</h3>

			</div>
			<input type="hidden" name="id" value="<%=bean.getId()%>">
			<table>
				<tr>
					<th align="left">Hotel Name <span style="color: red">*</span> :
					</th>
					<td><input type="text" name="hotelname" placeholder="Enter Hotel Name"
						size="25" value="<%=DataUtility.getStringData(bean.getHotelName())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("hotelname", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				<tr>
					<th align="left">RoomNo <span style="color: red">*</span>:
					</th>
					<td><input type="text" name="roomno" placeholder="Enter No"
						size="25"
						value="<%=DataUtility.getStringData(bean.getRoomNo()).equals("0") ? "" : DataUtility.getStringData(bean.getRoomNo())%>"></td>
					<td style="position: fixed"><font color="red"><%=ServletUtility.getErrorMessage("roomno", request)%></font></td>

				</tr>
				<tr>
					<th style="padding: 3px"></th>
				</tr>

				

				<tr>
					<th style="padding: 3px"></th>
				</tr>
				<tr>
					<%
						if (bean.getId() > 0) {
					%>
					<td colspan="2">&nbsp; &emsp; <input type="submit"
						name="operation" value="<%=HotelCtl.OP_UPDATE%>"> &nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=HotelCtl.OP_CANCEL%>"></td>

					<%
						} else {
					%>

                       

					  <td colspan="2">&nbsp; &emsp;  
					  <input type="submit" name="operation" value="<%=HotelCtl.OP_SAVE%>">&nbsp;
						&nbsp; <input type="submit" name="operation"
						value="<%=HotelCtl.OP_RESET%>"></td>
					</center>
					<%
						}
					%>
				</tr>

			</table>
	</center>

	<%@ include file="Footer.jsp"%>






</body>
</html>