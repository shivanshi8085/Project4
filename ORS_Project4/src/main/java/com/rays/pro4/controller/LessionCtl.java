package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.LessionBean;

import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;

import com.rays.pro4.Model.LessionModel;
import com.rays.pro4.Model.TopicModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "LessionCtl", urlPatterns = { "/ctl/LessionCtl"})
public class LessionCtl extends BaseCtl {

	@Override
	protected boolean validate(HttpServletRequest request) {

		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name", PropertyReader.getValue("error.require", "name"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name", "name contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("subject"))) {
			request.setAttribute("subject", PropertyReader.getValue("error.require", "subject"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("subject"))) {
			request.setAttribute("subject", "subject contains alphebet only");
			pass = false;
		}
		
		return pass;
		

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		LessionBean bean = new LessionBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setSubject(DataUtility.getString(request.getParameter("subject")));

		return bean;

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		LessionModel model = new LessionModel() ;
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			LessionBean bean;
			try {
		
					 
				    bean = model.findByPK(id);
					System.out.println(" FIND kIA");
					System.out.println(bean);
					ServletUtility.setBean(bean, request);
								
				
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return;
			}
		

		ServletUtility.forward(getView(), request, response);


			}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));


		LessionModel model = new LessionModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			LessionBean bean = (LessionBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("lession is successfully Updated", request);

				} else {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("lession is successfully Added", request);
					ServletUtility.forward(getView(), request, response);
					
				}
				

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("lession already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			LessionBean bean = (LessionBean) populateBean(request);
			try {
				model.delete(bean);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ServletUtility.redirect(ORSView.LESSION_CTL, request, response);
			return;

		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			System.out.println(" U  ctl Do post 77777");

			ServletUtility.redirect(ORSView.LESSION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);


		
	}

	@Override
	protected String getView() {
		// TODO Auto-generated method stub
		return ORSView.LESSION_VIEW;
	}

}
