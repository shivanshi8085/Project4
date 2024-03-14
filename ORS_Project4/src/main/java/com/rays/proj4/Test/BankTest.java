package com.rays.proj4.Test;

import java.util.Iterator;
import java.util.List;

import com.rays.pro4.Bean.BankBean;

import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.BankModel;




public class BankTest {
	public static void main(String[] args) throws Exception {
		
		//testAdd();
		//testSearch();
		//testFindByPK();
		testupdate();
		
	}

	private static void testupdate() throws Exception {

		BankModel model = new BankModel();
		BankBean bean = new BankBean();

		bean.setName("Shubham");
		bean.setAccountNo(11456789);
		bean.setBankName("RBI");
		bean.setId(2);

		model.update(bean);
		System.out.println("updateee");
	}

	private static void testFindByPK() throws Exception {
		
		BankBean bean = new BankBean();
		long pk = 1;
		BankModel model = new BankModel();

		bean = model.findByPK(pk);

		System.out.println(bean.getId());
		System.out.println(bean.getName());
		System.out.println(bean.getAccountNo());
         System.out.println(bean.getBankName());

		
	}

	private static void testSearch() throws ApplicationException {
		
		BankBean bean = new BankBean();
		BankModel model = new BankModel();

		List list = model.search(bean, 0, 0);
		Iterator it = list.iterator();

		while (it.hasNext()) {
			bean = (BankBean) it.next();
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAccountNo());
			System.out.println(bean.getBankName());

		}
		
	}

	private static void testAdd() throws Exception {
		
		BankBean bean= new BankBean();
		BankModel model = new BankModel();
		
		bean.setId(3);
		bean.setName("hritika");
		bean.setAccountNo(652567);
		bean.setBankName("PNB");
		
		model.add(bean);
		System.out.println("adddedddd");
		
	}

}
