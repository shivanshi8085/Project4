package com.rays.proj4.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.HotelBean;

import com.rays.pro4.Bean.UserBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.HotelModel;
import com.rays.pro4.Model.UserModel;

public class TestHotel {
	public static void main(String[] args) throws Exception {

		// testAdd();
		// testUpdate();
		// testDelete();
		// testSearch();
		testpk();

	}

	private static void testpk() throws Exception {

		HotelBean bean = new HotelBean();
		long pk = 3;
		HotelModel model = new HotelModel();

		bean = model.findByPK(pk);

		System.out.println(bean.getId());
		System.out.println(bean.getHotelName());
		System.out.println(bean.getRoomNo());

	}

	private static void testSearch() throws Exception {

		HotelBean bean = new HotelBean();
		HotelModel model = new HotelModel();

		List list = model.search(bean, 0, 0);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (HotelBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getHotelName());
			System.out.println(bean.getRoomNo());

		}

	}

	private static void testDelete() throws ApplicationException {

		HotelModel model = new HotelModel();
		HotelBean bean = new HotelBean();

		bean.setId(1);
		model.delete(bean);
		System.out.println("deleted");

	}

	private static void testUpdate() throws Exception {

		HotelModel model = new HotelModel();
		HotelBean bean = new HotelBean();

		bean.setHotelName("Gyani");
		bean.setRoomNo(11);
		bean.setId(5);

		model.update(bean);
		System.out.println("updated");

	}

	private static void testAdd() throws Exception {

		HotelModel model = new HotelModel();
		HotelBean bean = new HotelBean();
		bean.setId(1);
		bean.setHotelName("Gurukripa");
		bean.setRoomNo(15);

		model.add(bean);
		System.out.println("Added");

	}
}
