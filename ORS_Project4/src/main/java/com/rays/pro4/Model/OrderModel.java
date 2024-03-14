package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class OrderModel {
	
	private static Logger log = Logger.getLogger(OrderModel.class);
	
	
	private static final List list = null;

	public int nextPK()throws Exception {
		
		String sql = "Select Max(ID)from st_order";
		Connection conn= null;
		int pk= 0;
		
		conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs= ps.executeQuery();
		while(rs.next()) {
			pk=rs.getInt(1);
		}
		
		return pk + 1;
		
	}
	
	public long add(OrderBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model add Started");

		String sql = "INSERT INTO st_order VALUES(?,?,?,?)";

		Connection conn = null;
		int pk = 0;

		

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, pk);
			pstmt.setString(2, bean.getOrderName());
			pstmt.setString(3, bean.getOrderType());
			pstmt.setString(4, bean.getOrderAddress());
			
			int a = pstmt.executeUpdate();
			System.out.println(a);
			conn.commit();
			pstmt.close();

		} catch (Exception e) {
			log.error("Database Exception ...", e);
			try {
				e.printStackTrace();
				conn.rollback();

			} catch (Exception e2) {
				e2.printStackTrace();
				// application exception
				throw new ApplicationException("Exception : add rollback exceptionn" + e2.getMessage());
			}
		}

		finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Add End");
		return pk;

	}

	/**
	 * Delete a User
	 *
	 * @param bean
	 * @throws DatabaseException
	 */
	public void delete(OrderBean bean) throws ApplicationException {
		log.debug("Model delete start");
		String sql = "DELETE FROM st_order WHERE ID=?";
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Delete End");
	}

	/**
	 * Find User by Login
	 *
	 * @param login : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public OrderBean findByLogin(String login) throws ApplicationException {
		log.debug("Model findByLohin Started");
		System.out.println("find by login start");
		String sql = "SELECT * FROM st_order WHERE login=?";
		OrderBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, login);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setOrderAddress(rs.getString(4));
				
			}
			rs.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception .", e);
			throw new ApplicationException("Exception: Exception in getting user by Login");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findby login end");
		System.out.println("find by login end");
		return bean;
	}

	/**
	 * Find User by PK
	 *
	 * @param pk : get parameter
	 * @return bean
	 * @throws DatabaseException
	 */

	public OrderBean findByPK(long pk) throws ApplicationException {
		log.debug("Model findBy PK start");
		String sql = "SELECT * FROM st_order WHERE ID=?";
		OrderBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setOrderAddress(rs.getString(4));
				
			

			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception ", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Method Find By PK end");
		return bean;
	}

	/**
	 * Update a user
	 *
	 * @param bean
	 * @throws DatabaseException
	 */

	public void update(OrderBean bean) throws ApplicationException, DuplicateRecordException {
		log.debug("Model Update Start");
		String sql = "UPDATE st_order SET name=?,type=?,address=? WHERE id=?";
		Connection conn = null;
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getOrderName());
			pstmt.setString(2, bean.getOrderType());
			pstmt.setString(3, bean.getOrderAddress());
			pstmt.setLong(4, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("DataBase Exception", e);
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
				throw new ApplicationException("Exception : Update Rollback Exception " + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Update End ");
	}

	

	public List search(OrderBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model Search Start");
		StringBuffer sql = new StringBuffer("SELECT * FROM st_order WHERE 1=1");
		
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new OrderBean();
				bean.setId(rs.getLong(1));
				bean.setOrderName(rs.getString(2));
				bean.setOrderType(rs.getString(3));
				bean.setOrderAddress(rs.getString(4));
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception", e);
			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model Search end");
		return list;

	}



}


	
	