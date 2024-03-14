package com.rays.proj4.Test;

import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Bean.TopicBean;
import com.rays.pro4.Exception.ApplicationException;
import com.rays.pro4.Exception.DuplicateRecordException;
import com.rays.pro4.Model.TopicModel;

public class TopicTest {
	 
	 public static void main(String[] args) throws Exception {
		
		 
		// testadd();
		 //testfindByPk();
		  testUpdate();
	}

	private static void testUpdate() throws Exception {
		
		 TopicBean bean = new TopicBean();
	      bean.setId(1);
		 bean.setName("shiv");
		 bean.setNo(123);
		 bean.setDiscription("qwerty");
		 
		 TopicModel model = new TopicModel();
          model.update(bean);
          
          System.out.println("updateeee");
		
	}

	private static void testfindByPk() throws Exception {
		
		TopicBean bean = new TopicBean();
		TopicModel model = new TopicModel();
		long pk= 1;
	 bean  =   model.findByPK(1);
		
		if (bean == null) {
	}
	      System.out.println(bean.getId());
	      System.out.println(bean.getName());
	      System.out.println(bean.getNo());
	      System.out.println(bean.getDiscription());

		
		
	}

	private static void testadd() throws Exception {
		
		 TopicBean bean = new TopicBean();
	      bean.setId(1);
		 bean.setName("shiv");
		 bean.setNo(123);
		 bean.setDiscription("qwerty");
		 
		 TopicModel model = new TopicModel();
		 model.add(bean);
		 
		 System.out.println("adddeddd");
	}

}
