package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;

import com.rays.pro4.Model.OrderModel;
import com.rays.pro4.Model.UserModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet (name = "OrderCtl", urlPatterns = { "/ctl/OrderCtl"})
public class OrderCtl extends BaseCtl {
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		OrderBean bean = new OrderBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setOrderName(DataUtility.getString(request.getParameter("orderName")));
		bean.setOrderType(DataUtility.getString(request.getParameter("type")));
		bean.setOrderAddress(DataUtility.getString(request.getParameter("orderAddress")));
		
		return bean;
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		 boolean pass= true;
		 
		 if(DataValidator.isNull(request.getParameter("orderName"))) {
			 request.setAttribute("orderName",PropertyReader.getValue("error.require", "orderName"));
			 pass= false;
		 }else if(!DataValidator.isName(request.getParameter("orderName"))) {
		 		request.setAttribute("orderName","orderName contain only alphabet ");
			
		 }
		 if(DataValidator.isNull(request.getParameter("type"))) {
			 request.setAttribute("type",PropertyReader.getValue("error.require", "type"));
			 pass= false;
		 }else if(!DataValidator.isName(request.getParameter("type"))) {
		 		request.setAttribute("type","orderType contain only alphabet ");
			pass= false;

		 
		 }
		 if(DataValidator.isNull(request.getParameter("orderAddress"))) {
			 request.setAttribute("orderAddress",PropertyReader.getValue("error.require", "orderAddress"));
			 pass= false;
		 }else if(!DataValidator.isName(request.getParameter("orderAddress"))) {
		 		request.setAttribute("orderAddress","orderAddress contain only alphabet ");
			pass= false;

		 
		 }
		return pass;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		OrderModel model = new OrderModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (id > 0 || op != null) {
			  OrderBean bean = (OrderBean)populateBean(request);
			  
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		ServletUtility.forward(getView(), request, response);
	}
			


  @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
	   
	  String op = DataUtility.getString(request.getParameter("operation"));
	  System.out.println(op);
		long id = DataUtility.getLong(request.getParameter("id"));


		OrderModel model = new OrderModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			OrderBean bean = (OrderBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Order successfully Updated", request);

				} else {
					System.out.println("update chali..");
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("Order is successfully Added", request);
					ServletUtility.forward(getView(), request, response);
					bean.setId(pk);
				}
				

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Order id already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			OrderBean bean = (OrderBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {

			ServletUtility.redirect(ORSView.ORDER_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

	}


	  
	   

		
	@Override
	protected String getView() {
		
		return ORSView.ORDER_VIEW;
	}
	
}
