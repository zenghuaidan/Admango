package com.edeas.admango.dao;

import java.util.Date;

import javax.inject.Inject;

import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//need use hibernate4's sessionholder
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.edeas.admango.model.Category;
import com.edeas.basic.test.AbstractDBUnitTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
//@ContextConfiguration(locations = {"classpath:beans.xml"})  
public class TestCategoryDao extends AbstractDBUnitTestCase {	
	
	@Inject
	private ICategoryDao categoryDao;
	
	@Inject
	private SessionFactory sessionFactory;

	
	@Before
	public void setUp() throws Exception {
		Session session = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		backupAllTables();	
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();		
		DatabaseOperation.CLEAN_INSERT.execute(connection, builder.build(TestCategoryDao.class.getClassLoader().getResourceAsStream("t_category.xml")));
	}	
	
	@Test
	public void test() {
		//fail("Not yet implemented");		
		Category category = new Category();		
		category.setName("test4");
		category.setCorder(1);
		category.setCreateDate(new Date());
		category.setUpdateDate(new Date());
		categoryDao.add(category);		
	}
	
	@After
	public void tearDown() throws Exception {		
//		resumeDatabase();
		SessionHolder sessionHolder = (SessionHolder)TransactionSynchronizationManager.getResource(sessionFactory);
		Session session = sessionHolder.getSession();
		session.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);		
	}
}
