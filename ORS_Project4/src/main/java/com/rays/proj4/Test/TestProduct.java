package com.rays.proj4.Test;

import java.util.ArrayList;
import java.util.List;

import com.rays.pro4.Bean.ProductBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Model.ProductModel;

public class TestProduct {
	public static void main(String[] args) throws Exception {
		
		//testDelete();
		testSearch();
		
	}

	private static void testSearch() {
		
		ProductBean bean = new ProductBean();
		ProductModel model = new ProductModel();
		
		
		
	}

	private static void testDelete() throws ApplicationException {

		ProductBean bean = new ProductBean();
		ProductModel model = new ProductModel();
		
		bean.setId(3);
		model.delete(bean);
		
		System.out.println("delete");
	}

}

