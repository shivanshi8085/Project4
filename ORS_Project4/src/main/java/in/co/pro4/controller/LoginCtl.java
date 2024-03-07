package in.co.pro4.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.pro4.bean.BaseBean;
import in.co.pro4.bean.RoleBean;
import in.co.pro4.bean.UserBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.model.RoleModel;
import in.co.pro4.model.UserModel;
import in.co.pro4.utility.DataUtility;
import in.co.pro4.utility.DataValidator;
import in.co.pro4.utility.PropertyReader;
import in.co.pro4.utility.ServletUtility;

/**
 * Login functionality Controller. Performs operation for Login
 * 
 *
 */



/**
 * Servlet implementation class LoginCtl
 * @author Shivanshi Gupta
 * 
 *
 */
@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {

	private static final long serialVersionUID = 1L;
	public static final String OP_REGISTER = "Register";
	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "logout";

	private static Logger log = Logger.getLogger(LoginCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * in.co.pro4.controller.BaseCtl#validate(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected boolean validate(HttpServletRequest request) {
		 System.out.println("loginctl validate");
		log.debug("LoginCtl Method validate Started");
        System.out.println("..call..chiled-LoginCtl-Validate");
		boolean pass = true;

		String op = request.getParameter("operation");
		System.out.println("LoginCtl-operation="+op);
		if (OP_SIGN_UP.equals(op) || OP_LOG_OUT.equals(op)) {
			return pass;
		}

		String login = request.getParameter("login");

		if (DataValidator.isNull(login)) {
			 System.out.println("loginctl Email null");
			request.setAttribute("login", PropertyReader.getValue("error.require", "Login Id "));
			pass = false;
		} else if (!DataValidator.isEmail(login)) {
			 System.out.println("loginctl Email not email");
			request.setAttribute("login", PropertyReader.getValue("error.email", "Login Id "));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("password"))) {
			// System.out.println("loginctl password");
			request.setAttribute("password", PropertyReader.getValue("error.require", "Password "));
			pass = false;
		} 
		
	   //else if (!DataValidator.isPassword(request.getParameter("password"))) {
		//	request.setAttribute("password", "Password contain 8 letters with alpha-numeric & special Character");
		//	pass = false;
		//}

		log.debug("LoginCtl Method validate Ended");

		return pass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#populatebean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("LoginCtl-PopulateBean");

		log.debug("LoginCtl Method populatebean Started");
		// System.out.println("BaseBean Populatebean-loginCtl");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));// get kiya loginctl
//		String s=(String)request.getParameter("id");
//		System.out.println("id="+s);
		bean.setLogIn(DataUtility.getString(request.getParameter("login")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		

		log.debug("LoginCtl Method populatebean Ended");

		return bean;
	}

	/**
	 * Contains display logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.info("LoginCtl Doget");
	//	System.out.println("LoginCtl-DoGet");
		// System.out.println("Lctl Do post");
		HttpSession session = request.getSession(false);

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("LoginCtl-DoGet-Operation=="+op);

		if (OP_LOG_OUT.equals(op) && !OP_SIGN_IN.equals(op)) {
			// System.out.println("Do log out chal rha h");
			Cookie c=new Cookie("SessionId", session.getId());
			response.addCookie(c);
			
			
			session.invalidate();
			ServletUtility.setSuccessMessage("User Logout Succesfully", request);
			ServletUtility.forward(getView(), request, response);
			return;
		}
		//RequestDispatcher rd = request.getRequestDispatcher(page);
		//rd.forward(request, response);
		
		// System.out.println("Do get chalega");
		

		
		
		ServletUtility.forward(getView(), request, response);
		

	}

	/**
	 * Contains submit logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		log.debug(" Method doPost Started");
		System.out.println("LoginCtl-doPost");
		

		String op = DataUtility.getString(request.getParameter("operation"));
		System.out.println("LoginCtl-DoPost-Operation=="+op);

		UserModel model = new UserModel(); 
		RoleModel role = new RoleModel();

		long id = DataUtility.getLong(request.getParameter("id"));
		System.out.println("LoginCtl-DoPost-ID=="+id);

	if (OP_SIGN_IN.equalsIgnoreCase(op)) {
			// System.out.println(" Lctl Do post 1354353");
			System.out.println("LoginCtl-dopost-Opertaion-sinIn mila=="+op);
			UserBean bean = (UserBean) populateBean(request);
			
			
			

			try {

				bean = model.authenticate(bean.getLogIn(), bean.getPassword());
				String str = request.getParameter("URI");
				
				

			if (bean != null) {
					session.setAttribute("user", bean);
				  
					// System.out.println("name " +bean.getFirstName());

					long rollId = bean.getRoleId();

					RoleBean rolebean = role.findByPK(rollId);

					if (rolebean != null) {
						session.setAttribute("role", rolebean.getName());
					}
					if ("null".equalsIgnoreCase(str)) {
						
						ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}
					
			} else {
					// System.out.println(" Lgnctl Do post hat be");
					bean = (UserBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Invalid LoginId And Password", request);
					
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}

	} else if (OP_SIGN_UP.equalsIgnoreCase(op)) {
			// System.out.println("Lctl Do post kya karega re tu");
			System.out.println("LoginCtl-doPost-operition(signUp)");

			ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
			return;

		}

		ServletUtility.forward(getView(), request, response);

		log.debug("UserCtl Method doPost Ended");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#getView()
	 */
	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}
}
