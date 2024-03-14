package com.rays.pro4.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.HotelBean;

import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Util.JDBCDataSource;

  public class HotelModel {

	public static Integer nextPK() throws ApplicationException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_HOTEL");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {

			throw new ApplicationException("Exception : in getting next pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public static long add(HotelBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPK();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_HOTEL VALUES(?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getHotelName());
			pstmt.setInt(3, bean.getRoomNo());
			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Exception : add rollback=" + e1.getMessage());
			}

			throw new ApplicationException("Exception : in adding user=" + e.getMessage());

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(HotelBean bean) throws Exception {

		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		conn.setAutoCommit(false);
		PreparedStatement pstmt = conn.prepareStatement("UPDATE ST_HOTEL SET HOTEL_NAME= ? , ROOM_NO =  ? WHERE ID=?");
		pstmt.setString(1, bean.getHotelName());
		pstmt.setInt(2, bean.getRoomNo());
		pstmt.setLong(3, bean.getId());

		pstmt.executeUpdate();

		conn.commit();
		pstmt.close();

	}

	public static void delete(HotelBean delete) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_HOTEL WHERE id=?");
			pstmt.setLong(1, delete.getId());

			pstmt.executeUpdate();

			conn.commit();
			pstmt.close();
		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception e1) {
				throw new ApplicationException("Exception : delete rollback" + e1.getMessage());
			}
			throw new ApplicationException("Exception : in deleting user");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public HotelBean findByPK(long pk) throws Exception {

		HotelBean bean = null;

		Connection conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM ST_HOTEL WHERE ID=?");
		pstmt.setLong(1, pk);
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new HotelBean();

			bean.setId(rs.getLong(1));
			bean.setHotelName(rs.getString(2));
			bean.setRoomNo(rs.getInt(3));
		}

		return bean;
	}

	public List search(HotelBean bean, int pageNo, int pageSize) throws Exception {
		System.out.println("List search(TopicBean bean, int pageNo, int pageSize)");

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_HOTEL WHERE 1=1");

		if (bean != null) {

			if (bean.getHotelName() != null && bean.getHotelName().length() > 0) {

				sql.append(" AND  Hotel_Name like '" + bean.getHotelName() + "%'");
			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;

			sql.append(" LIMIT " + pageNo + "," + pageSize);
		}

		System.out.println(sql.toString());

		ArrayList list = new ArrayList();
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			bean = new HotelBean();

			bean.setId(rs.getLong(1));
			bean.setHotelName(rs.getString(2));
			bean.setRoomNo(rs.getInt(3));

			list.add(bean);
		}
		rs.close();

		return list;

	}
}