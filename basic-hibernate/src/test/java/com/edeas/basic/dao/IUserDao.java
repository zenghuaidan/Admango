package com.edeas.basic.dao;

import java.util.List;
import java.util.Map;

import com.edeas.basic.dao.IBasicDao;
import com.edeas.basic.model.Pager;
import com.edeas.basic.model.User;

interface IUserDao extends IBasicDao<User>{
	public Pager<User> findByHQL(String hql, Object[] args, Map<String, Object> alias);
	
	public Pager<User> findByHQL(String hql, Object[] args);
	
	public Pager<User> findByHQL(String hql, Map<String, Object> alias);
	
	public List<User> listByHQL(String hql, Object[] args, Map<String, Object> alias);
	
	public List<User> listByHQL(String hql, Object[] args);
	
	public List<User> listByHQL(String hql, Map<String, Object> alias);
	
	public <N>Pager<N> findBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity);
	
	public <N>Pager<N> findBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity);
	
	public <N>Pager<N> findBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);

	public <N>List<N> listBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity);
	
	public <N>List<N> listBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity);
	
	public <N>List<N> listBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity);
	
	public void updateByHQL(String hql, Object[] args, Map<String, Object> alias);
	
	public void updateByHQL(String hql, Object[] args);
	
	public void updateByHQL(String hql, Map<String, Object> alias);
	
	public void updateBySQL(String sql, Object[] args, Map<String, Object> alias);
	
	public void updateBySQL(String sql, Object[] args);
	
	public void updateBySQL(String sql, Map<String, Object> alias);
}
