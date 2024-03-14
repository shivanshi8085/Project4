package com.rays.pro4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.pro4.Bean.BaseBean;
import com.rays.pro4.Bean.HotelBean;
import com.rays.pro4.Bean.HotelBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Bean.HotelBean;
import com.rays.pro4.Model.HotelModel;

import com.rays.pro4.Model.HotelModel;
import com.rays.pro4.Util.DataUtility;
import com.rays.pro4.Util.DataValidator;
import com.rays.pro4.Util.PropertyReader;
import com.rays.pro4.Util.ServletUtility;

@WebServlet(name = "HotelCtl", urlPatterns = { "/ctl/HotelCtl" })
public class HotelCtl extends BaseCtl {
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		HotelBean bean = new HotelBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setHotelName(DataUtility.getString(request.getParameter("hotelname")));
		bean.setRoomNo(DataUtility.getInt(request.getParameter("roomno")));
		
		
		
		return bean;
		
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		boolean pass = true;

		if (DataValidator.isNull(request.getParameter("hotelname"))) {
			request.setAttribute("hotelname", PropertyReader.getValue("error.require", "hotelname"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("hotelname"))) {
			request.setAttribute("hotelname", "hotelname contains alphabet only");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("roomno"))) {
			request.setAttribute("roomno", PropertyReader.getValue("error.require", "roomno"));
			pass = false;
		} else if (!DataValidator.isInteger(request.getParameter("roomno"))) {
			request.setAttribute("roomno", "roomno contains digit only");
			pass = false;
		}
		
		
		return pass;
		
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String op = DataUtility.getString(request.getParameter("operation"));

		HotelModel model = new HotelModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		if (id > 0 || op != null) {
			
			HotelBean bean;
			try {
		
					bean = model.findByPK(id);
					System.out.println(" find kiya");
					System.out.println(bean);
					ServletUtility.setBean(bean, request);
								
				
					ServletUtility.forward(getView(), request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			return;
		
		}

	ServletUtility.forward(getView(), request,response);	
		}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

		String op = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));


		HotelModel model = new HotelModel();
		if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {
			HotelBean bean = (HotelBean) populateBean(request);

			try {
				if (id > 0) {

					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("hotel is successfully Updated", request);

				} else {
					long pk = model.add(bean);

					ServletUtility.setSuccessMessage("hotel is successfully Added", request);
					ServletUtility.forward(getView(), request, response);
					
				}
				

			} catch (ApplicationException e) {
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Hotel already exists", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (OP_DELETE.equalsIgnoreCase(op)) {

			HotelBean bean = (HotelBean) populateBean(request);
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
		// TODO Auto-generated method stub
		return ORSView.HOTEL_VIEW;
	}

}
