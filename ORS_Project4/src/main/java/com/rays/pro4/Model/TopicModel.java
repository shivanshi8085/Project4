package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DatabaseException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Exception.RecordNotFoundException;
import com.rays.pro4.Util.EmailBuilder;
import com.rays.pro4.Util.EmailMessage;
import com.rays.pro4.Util.EmailUtility;
import com.rays.pro4.Util.JDBCDataSource;

public class TopicModel {
	
	public int nextPK() throws Exception {


		String sql = "SELECT MAX(ID) FROM ST_TOPIC";
		Connection conn = null;
		int pk = 0;
	
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		   return pk + 1;
	}

	

	public long add(TopicBean bean) throws Exception {

		String sql = "INSERT INTO ST_TOPIC VALUES(?,?,?,?)";

		Connection conn = null;
		int pk = 0;


			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setInt(3,bean.getNo());;
			pstmt.setString(4,bean.getDiscription());
		

			int a = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		

		
		return pk;

	}

		public void delete(TopicBean bean) throws ApplicationException {
		String sql = "DELETE FROM ST_TOPIC WHERE ID=?";
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
			try {
				conn.rollback();
			} catch (Exception e2) {
				throw new ApplicationException("Exception: Delete rollback Exception" + e2.getMessage());
			}
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	

	
	

	public TopicBean findByPK(long pk) throws Exception {
		String sql = "SELECT * FROM ST_TOPIC WHERE ID=?";
		TopicBean bean = null;
		Connection conn = null;
		
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TopicBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setNo(rs.getInt(3));
				bean.setDiscription(rs.getString(4));
				
							
				
				
			/* rs.close(); */
			
		
			}
		return bean;
	

	
	}
	public void update(TopicBean bean) throws Exception {
		String sql = "UPDATE ST_TOPIC SET NAME=?, NO=?, DISCRIPTION=?  WHERE ID=?";
		Connection conn = null;
		
	
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			pstmt.setInt(2, bean.getNo());
			pstmt.setString(3, bean.getDiscription());
			pstmt.setLong(4,bean.getId());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
	
			
				conn.rollback();
			
		
			
	
		}
	

	

	
	public List search(TopicBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_TOPIC WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			if (bean.getNo()  > 0) {
				sql.append(" AND NO  = '" + bean.getNo());
			}
					   
			if (bean.getDiscription() != null && bean.getDiscription().length() > 0) {
				sql.append(" AND DISCRIPTION like '" + bean.getDiscription() + "%'");
			}
		   }
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + ", " + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new TopicBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setNo(rs.getInt(3));
				bean.setDiscription(rs.getString(4));
			
				list.add(bean);

			}
			rs.close();
		} catch (Exception e) {
			throw new ApplicationException("Exception: Exception in Search User");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	}
