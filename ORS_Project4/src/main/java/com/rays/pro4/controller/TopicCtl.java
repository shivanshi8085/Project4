package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.TopicModel;
import com.rays.pro4.Model.TopicModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "TopicCtl", urlPatterns = { "/ctl/TopicCtl" })
public class TopicCtl extends BaseCtl {
	
	
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

		if (DataValidator.isNull(request.getParameter("no"))) {
			request.setAttribute("no", PropertyReader.getValue("error.require", "no"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("no"))) {
			request.setAttribute("no", "no contains digit only");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("discription"))) {
			request.setAttribute("discription", PropertyReader.getValue("error.require", "discription"));
			pass = false;
		} /*
			 * else if (DataValidator.isName(request.getParameter("discription"))){
			 * 
			 * request.setAttribute("discription", "discription contain alphabet only");
			 * pass = false; }
			 */
		return pass;

	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		TopicBean bean = new TopicBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setName(DataUtility.getString(request.getParameter("name")));
        bean.setNo(DataUtility.getInt(request.getParameter("no")));
        bean.setDiscription(DataUtility.getString(request.getParameter("discription")));
		return bean;

		
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		TopicModel model = new TopicModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			TopicBean bean;
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


	TopicModel model = new TopicModel();
	if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
		TopicBean bean = (TopicBean) populateBean(request);

		try {
			if (id > 0) {

				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Topic is successfully Updated", request);

			} else {
				long pk = model.add(bean);

				ServletUtility.setSuccessMessage("Topic is successfully Added", request);
				ServletUtility.forward(getView(), request, response);
				
			}
			

		} catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
		} catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Topic already exists", request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} else if (OP_DELETE.equalsIgnoreCase(op)) {

		TopicBean bean = (TopicBean) populateBean(request);
		try {
			model.delete(bean);
			ServletUtility.redirect(ORSView.TOPIC_CTL, request, response);
			return;
		} catch (ApplicationException e) {
			return;
		}

	} else if (OP_CANCEL.equalsIgnoreCase(op)) {
		System.out.println(" U  ctl Do post 77777");

		ServletUtility.redirect(ORSView.TOPIC_CTL, request, response);
		return;
	}
	ServletUtility.forward(getView(), request, response);

}

	
	
	@Override
	protected String getView() {
		return ORSView.TOPIC_VIEW;
	}

}
