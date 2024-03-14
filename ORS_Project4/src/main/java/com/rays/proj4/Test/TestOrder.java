package com.rays.proj4.Test;

import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.OrderBean;
import com.rays.pro4.Model.OrderModel;

public class TestOrder {
	public static void main(String[] args) throws Exception {
		
		//testAdd();
		//testUpdate();
		//testDelete();
		//testSearch();
		
		
		
	}

	private static void testSearch() throws Exception {
		
		OrderBean bean = new OrderBean();
		OrderModel model = new OrderModel();
		//bean.setOrderName("h");
		
		List list = model.search(bean,0,0);
		Iterator it = list.iterator();
		System.out.println("Printed");
		
		while(it.hasNext()) {
			bean = (OrderBean)it.next();
		System.out.println(bean.getId());
		System.out.println(bean.getOrderName());
		System.out.println(bean.getOrderType());
		System.out.println(bean.getOrderAddress());
		
		
		}	
	}

	private static void testDelete() throws Exception {
		OrderBean bean = new OrderBean();
		
		bean.setId(5);
		OrderModel model = new OrderModel();
		model.delete(bean);
		
		System.out.println("order delete");
		
		
	}

	private static void testUpdate() throws Exception {
      OrderBean bean = new OrderBean();
		
		bean.setId(1);
	
		bean.setOrderName("Pranshueee");
		bean.setOrderType("Cash");
		bean.setOrderAddress("Indore");
		
		OrderModel model = new OrderModel();
		model.update(bean);
		
		System.out.println("order update");
		
		
		
	}

	private static void testAdd() throws Exception {
		
		OrderBean bean = new OrderBean();
		
		//bean.setId(4);
	
		bean.setOrderName("wwwwa");
		bean.setOrderType("Cash");
		bean.setOrderAddress("Indore");
		
		OrderModel model = new OrderModel();
		model.add(bean);
		
		System.out.println("Order add");
		
	}
}