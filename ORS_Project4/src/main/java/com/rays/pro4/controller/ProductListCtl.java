package com.rays.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.LessionBean;
import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.LessionModel;
import com.rays.pro4.Model.ProductModel;
import com.rays.pro4.Model.TopicModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "ProductListCtl", urlPatterns = { "/ctl/ProductListCtl"})
public class ProductListCtl extends BaseCtl {
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		ProductBean bean = new ProductBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		
		bean.setType(DataUtility.getString(request.getParameter("type")));
		return bean;
		
		
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		List list= null;
		 
		int pageNo = 1;
		int pageSize = 10;

        ProductBean bean = (ProductBean)populateBean(request);
        String op= DataUtility.getString(request.getParameter("operation"));
        
        ProductModel model = new ProductModel();
        
        try {
			list = model.search(bean, pageNo, pageSize);
		}catch (Exception e) {
			ServletUtility.handleException(e, request, response);
		}
		
		
		
		if (list == null || list.size() == 0) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.forward(getView(), request, response);





	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		 List list = null;
			
			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
			
			String op = DataUtility.getString(request.getParameter("operation"));
			ProductBean bean = (ProductBean)populateBean(request);
			String[] ids = request.getParameterValues("ids");
			
			ProductModel model = new ProductModel();
			
			if (OP_SEARCH.equalsIgnoreCase(op)) {
				pageNo = 1;
			} else if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			} else if (OP_NEW.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.LESSION_CTL, request, response);
				return;
			} else if (OP_RESET.equalsIgnoreCase(op)) {
				ServletUtility.redirect(ORSView.LESSION_LIST_CTL, request, response);
				return;
			} else if (OP_DELETE.equalsIgnoreCase(op)) {
				pageNo = 1;
				if (ids != null && ids.length > 0) {
					ProductBean deletebean = new ProductBean();
					for (String id : ids) {
						deletebean.setId(DataUtility.getInt(id));
						try {
							model.delete(deletebean);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ServletUtility.setSuccessMessage("prodect is Deleted Successfully", request);
					}
				} else {
					ServletUtility.setErrorMessage("Select at least one record", request);
				}
			}
			try {

				list = model.search(bean, pageNo, pageSize);
				
			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
				ServletUtility.setList(list, request);
				ServletUtility.setBean(bean, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);
			

		

	}
		
	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.PRODUCT_LIST_VIEW;
	}

}
