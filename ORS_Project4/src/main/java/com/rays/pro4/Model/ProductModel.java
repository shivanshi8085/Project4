package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Util.JDBCDataSource;

public class ProductModel {
	
	public int nextPK() throws Exception {


		String sql = "SELECT MAX(ID) FROM ST_PRODUCT";
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

	

	public long add(ProductBean bean) throws Exception {

		String sql = "INSERT INTO ST_PRODUCT VALUES(?,?,?)";

		Connection conn = null;
		int pk = 0;


			conn = JDBCDataSource.getConnection();
			pk = nextPK();

			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3,bean.getType());;
			
			int a = pstmt.executeUpdate();
			conn.commit();
			pstmt.close();

		

		
		return pk;

	}

		public void delete(ProductBean bean) throws ApplicationException {
		String sql = "DELETE FROM ST_PRODUCT WHERE ID=?";
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

	

	
	

	public ProductBean findByPK(long pk) throws Exception {
		String sql = "SELECT * FROM ST_PRODUCT WHERE ID=?";
		ProductBean bean = null;
		Connection conn = null;
		
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setType(rs.getString(3));
											
				
				
			/* rs.close(); */
			
		
			}
		return bean;
	

	
	}
	public void update(ProductBean bean) throws Exception {
		String sql = "UPDATE ST_PRODUCT SET NAME =?, TYPE =?  WHERE ID =?";
		Connection conn = null;
		
	
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bean.getName());
			
			pstmt.setString(2, bean.getType());
			pstmt.setLong(3,bean.getId());
			
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
	
			
				conn.rollback();
			
		
			
	
		}
	

	

	
	public List search(ProductBean bean, int pageNo, int pageSize) throws ApplicationException {
		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PRODUCT WHERE 1=1");
		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND NAME like '" + bean.getName() + "%'");
			}
			
		   if (bean.getType() != null && bean.getType().length() > 0) {
				sql.append(" AND TYPE like '" + bean.getType() + "%'");
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
				bean = new ProductBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				
				bean.setType(rs.getString(3));
			
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
