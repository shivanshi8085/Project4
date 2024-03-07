package in.co.pro4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.pro4.bean.BaseBean;
import in.co.pro4.bean.CollegeBean;
import in.co.pro4.bean.UserBean;
import in.co.pro4.exception.ApplicationException;
import in.co.pro4.model.RoleModel;
import in.co.pro4.model.UserModel;
import in.co.pro4.utility.DataUtility;
import in.co.pro4.utility.PropertyReader;
import in.co.pro4.utility.ServletUtility;

/**
 * 
 * User List functionality Controller. 
 * Performs operation for list, search and delete operations of User
 * 
 *
 */



/**
 * Servlet implementation class UserListCtl
 * @author Shivanshi Gupta
 *
 */
@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(UserListCtl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * in.co.pro4.controller.BaseCtl#preload(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preload(HttpServletRequest request) {
		System.out.println("!..userListCtl preload->Chiled..!");
     
		RoleModel rmodel = new RoleModel();
		UserModel umodel = new UserModel();

		try {
			List rlist = rmodel.list(0, 0);
			List ulist = umodel.list(0, 0);
			System.out.println("UserList-Preload-rlist="+rlist);
			

			request.setAttribute("RoleList", rlist);
			request.setAttribute("LoginId", ulist);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#populatebean(javax.servlet.http.
	 * HttpServletRequest)
	 */
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		System.out.println("userList populatebean->chiled");
		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleid")));
		bean.setLogIn(DataUtility.getString(request.getParameter("loginid")));
		bean.setGender(DataUtility.getString(request.getParameter("Gender")));
		bean.setDob(DataUtility.getDate(request.getParameter("dob")));
		System.out.println("firstName="+bean.getFirstName()+""+"roleid="+bean.getRoleId()+""+"loginid="+bean.getLogIn());
		

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
		log.debug("UserListCtl doGet");
		System.out.println("doGeat=run");
		List list = null;
		List nextList = null;

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		UserBean bean = (UserBean) populateBean(request);
		System.out.println("UsirList-doget-populatebean=="+bean+"==UserBean");
		
		String op = DataUtility.getString(request.getParameter("operation"));
          System.out.println("UserListCtl-doGet-Operation="+op);
//	        get the selected checkbox ids array for delete list

		UserModel model = new UserModel();

		try {
			list = model.search(bean, pageNo, pageSize);
			System.out.println("list===" + list);
			
            //first time list load hoti he uske bad NEXT button ko inadel karne ke liye.NextList size chack kra
			nextList = model.search(bean, pageNo + 1, pageSize);
			System.out.println("pageNo=="+pageNo+"==DoGet");

			request.setAttribute("nextlist", nextList.size());
			System.out.println("nextListSize=="+nextList.size()+"==DoGetSetATTribute");
			

			ServletUtility.setList(list, request);
			if (list == null || list.size() == 0) {
				ServletUtility.setErrorMessage("No record found ", request);
			}
		//	ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			System.out.println("DoGet==Forword veiw page pr le gyi");
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		log.debug("UserListCtl doGet End");
	}

	/**
	 * Contains submit logic.
	 *
	 * @param request  the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException      Signals that an I/O exception has occurred.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		log.debug("UserListCtl doPost Start");
		System.out.println("Dopost-UserList=start");

		List list;
		List nextList = null;

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		System.out.println("pageNo="+pageNo);
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		System.out.println("pagesize="+pageSize);
		System.out.println(pageNo+"==0)?1:"+pageNo);
		
		pageNo = (pageNo == 0) ? 1 : pageNo;
		
		System.out.println("pazeNo="+pageNo);
		
		pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
		System.out.println("pazesize="+pageSize);

		String op = DataUtility.getString(request.getParameter("operation"));
		UserBean bean = (UserBean) populateBean(request);
		// get the selected checkbox ids array for delete list
		String[] ids = request.getParameterValues("ids");
		System.out.println("ids=="+ids);
		UserModel model = new UserModel();

		if (OP_SEARCH.equalsIgnoreCase(op)) {
			pageNo = 1;
		} else if (OP_NEXT.equalsIgnoreCase(op)) {
			
			pageNo++;
			System.out.println("pageNo++="+pageNo);
		} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
			pageNo--;
		} else if (OP_NEW.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		} else if (OP_DELETE.equalsIgnoreCase(op)) {
			pageNo = 1;
			if (ids != null && ids.length > 0) {
				UserBean beanIdDelete = new UserBean();
				for (String id : ids) {
					beanIdDelete.setId(DataUtility.getInt(id));
					try {
						model.delete(beanIdDelete);
					} catch (ApplicationException e) {
						log.error(e);
						ServletUtility.handleException(e, request, response);
						return;
					}

					ServletUtility.setSuccessMessage("User is Deleted Successfully", request);
				}
			} else {
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
		}
		try {
			System.out.println("dopost-ListRunBefore-pageNo="+pageNo+"pagesize="+ pageSize);

			list = model.search(bean, pageNo, pageSize);
			System.out.println("dopost-ListRun-pageNo,Pagesize="+pageNo+" "+bean+" "+ pageSize);
			
			

			nextList = model.search(bean, pageNo + 1, pageSize);
			System.out.println("nextList="+bean+" "+pageNo+" "+pageSize);

			request.setAttribute("nextlist", nextList.size());
			System.out.println("next list size="+nextList.size());

		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		if (list == null || list.size() == 0 && !OP_DELETE.equalsIgnoreCase(op)) {
			ServletUtility.setErrorMessage("No record found ", request);
		}
		ServletUtility.setList(list, request);
		ServletUtility.setBean(bean, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		System.out.println("ForwordRun");
		ServletUtility.forward(getView(), request, response);
		log.debug("UserListCtl dopost End");

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see in.co.pro4.controller.BaseCtl#getview()
	 */
	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
