package com.edeas.basic.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.edeas.basic.dao.BasicDao;
import com.edeas.basic.model.Pager;
import com.edeas.basic.model.User;

@Repository
public class UserDao extends BasicDao<User> implements IUserDao {

	public Pager<User> findByHQL(String hql, Object[] args, Map<String, Object> alias) {
		return super.findByHQL(hql, args, alias);
	}

	public Pager<User> findByHQL(String hql, Object[] args) {
		return super.findByHQL(hql, args);
	}

	public Pager<User> findByHQL(String hql, Map<String, Object> alias) {
		return super.findByHQL(hql, alias);
	}

	public List<User> listByHQL(String hql, Object[] args, Map<String, Object> alias) {
		return super.listByHQL(hql, args, alias);
	}

	public List<User> listByHQL(String hql, Object[] args) {
		return super.listByHQL(hql, args);
	}

	public List<User> listByHQL(String hql, Map<String, Object> alias) {
		return super.listByHQL(hql, alias);
	}

	public <N> Pager<N> findBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz,
			boolean hasEntity) {
		return super.findBySQL(sql, args, alias, clz, hasEntity);
	}
	
	public <N>Pager<N> findBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
		return super.findBySQL(sql, args, clz, hasEntity);
	}
	
	public <N>Pager<N> findBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return super.findBySQL(sql, alias, clz, hasEntity);
	}

	public <N> List<N> listBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz,
			boolean hasEntity) {
		return super.listBySQL(sql, args, alias, clz, hasEntity);
	}

	public <N> List<N> listBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
		return super.listBySQL(sql, args, clz, hasEntity);
	}

	public <N> List<N> listBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return super.listBySQL(sql, alias, clz, hasEntity);
	}

	public void updateByHQL(String hql, Object[] args, Map<String, Object> alias) {
		super.updateByHQL(hql, args, alias);
	}

	public void updateByHQL(String hql, Object[] args) {
		super.updateByHQL(hql, args);
	}

	public void updateByHQL(String hql, Map<String, Object> alias) {
		super.updateByHQL(hql, alias);
	}

	public void updateBySQL(String sql, Object[] args, Map<String, Object> alias) {
		super.updateBySQL(sql, args, alias);
	}

	public void updateBySQL(String sql, Object[] args) {
		super.updateBySQL(sql, args);
	}

	public void updateBySQL(String sql, Map<String, Object> alias) {
		super.updateBySQL(sql, alias);
	}

}
