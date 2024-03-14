package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.rays.pro4.Bean.LessionBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class LessionModel {
	
	public int nextPK()throws Exception{
		
		
		String sql = "SELECT MAX(ID) FROM ST_LESSION";
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
	
	public long add(LessionBean bean)throws Exception{
		
		Connection conn= JDBCDataSource.getConnection();
		int pk=0;
		pk=nextPK();
		PreparedStatement ps= conn.prepareStatement("INSERT INTO ST_LESSION VALUES(?,?,?)");
		
		ps.setLong(1,pk);
		ps.setString(2,bean.getName());
		ps.setString(3,bean.getSubject());
		
		ps.executeUpdate();

		return pk;
		
		
	}
	
	public void update(LessionBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement("UPDATE ST_LESSION SET NAME = ?, SUBJECT = ?  WHERE ID=?");
		
		ps.setString(1,bean.getName());
		
		ps.setString(2,bean.getSubject());
		ps.setLong(3,bean.getId());
		
		ps.executeUpdate();
		
	}
	
	public void delete(LessionBean bean)throws Exception {
		
		Connection conn= JDBCDataSource.getConnection();
		PreparedStatement ps= conn.prepareStatement("DELETE FROM ST_LESSION WHERE ID=?");
		
		ps.setLong(1,bean.getId());
		ps.executeUpdate();
	}
	public LessionBean findByPK(long pk) throws Exception {
	
	String sql = "SELECT * FROM ST_LESSION WHERE ID=?";
	LessionBean bean = null;
	Connection conn = null;
	
		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new LessionBean();
			bean.setId(rs.getLong(1));
			bean.setName(rs.getString(2));
			bean.setSubject(rs.getString(3));
									
			
			
		/* rs.close(); */
		}
      return bean;
}
	public List search(LessionBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_LESSION WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			/*
			 * if (bean.getAccountNo() > 0) { sql.append(" AND ACCOUNT_NO = '" +
			 * bean.getAccountNo()); }
			 */
					   
			if (bean.getSubject() != null && bean.getSubject().length() > 0) {
				sql.append(" AND SUBJECT like '" + bean.getSubject() + "%'");
			}
		   }
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" Limit " + pageNo + "," + pageSize);
		}
		List list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new LessionBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				
				bean.setSubject(rs.getString(3));
			
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
