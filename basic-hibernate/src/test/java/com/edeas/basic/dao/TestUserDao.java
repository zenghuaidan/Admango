package com.edeas.basic.dao;

import java.util.List;

import javax.inject.Inject;

import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.edeas.basic.model.User;
import com.edeas.basic.test.AbstractDBUnitTestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
public class TestUserDao extends AbstractDBUnitTestCase {

	@Inject
	private SessionFactory sessionFactory;
	
	@Inject
	private IUserDao userDao;
	
	@Before
	public void setUp() throws Exception {
		// fix lazy load issue
		Session session = sessionFactory.openSession();
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));		
		backupAllTables();		
		FlatXmlDataSet dataSet = new FlatXmlDataSetBuilder().build(TestUserDao.class.getClassLoader().getResourceAsStream("t_user.xml"));
		DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
	}
	
	@Test
	public void testFindAll() throws Exception {	
		List<User> users = userDao.findAll();
		Assert.assertEquals(18, users.size());
	}
	
	@Test
	public void testGetByIds() throws Exception {			
		List<User> users = userDao.getByIds(new Long[] {1l, 3l, 5l});
		Assert.assertEquals(3, users.size());
		users = userDao.getByIds(new Long[] {98l, 99l, 100l});
		Assert.assertEquals(0, users.size());
	}
	
	@After
	public void tearDown() throws Exception {
		resumeDatabase();
		SessionHolder holder = (SessionHolder) TransactionSynchronizationManager.getResource(sessionFactory);
		Session session = holder.getSession();
		session.flush();
		TransactionSynchronizationManager.unbindResource(sessionFactory);
	}
}
