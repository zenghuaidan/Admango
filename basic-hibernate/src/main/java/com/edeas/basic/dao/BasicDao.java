package com.edeas.basic.dao;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.util.CollectionUtils;

import com.edeas.basic.model.Pager;
import com.edeas.basic.model.SystemContext;

@SuppressWarnings({"unchecked", "rawtypes"})
public class BasicDao<T> implements IBasicDao<T> {
	
	private SessionFactory sessionFactory;
	private Class<?> clz;	
	
	private Class<?> getClz() {
		if (clz == null) {
			clz = (Class<?>)(((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
		}
		return clz;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Inject
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public T add(T t) {
		getSession().save(t);
		return null;
	}
	
	public T getById(Long id) {
		return (T)getSession().get(getClz(), id);
	}
	
	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0)
			return Collections.EMPTY_LIST;
		return getSession()//
				.createQuery("from " + getClz().getName() + " where id in (:ids)")//
				.setParameterList("ids", CollectionUtils.arrayToList(ids))//
				.list();
	}

	public void update(T t) {
		getSession().update(t);
	}

	public void delete(Long id) {
		T t = getById(id);
		getSession().delete(t);
	}

	public List<T> findAll() {
		return getSession().createQuery("from " + getClz().getName()).list();
	}
			
	private void setAliasArgs(Map<String, Object> alias, Query query) {
		if (alias == null || alias.isEmpty())
			return;
		for (String key : alias.keySet()) {
			Object value = alias.get(key);
			if (value instanceof Collection) {
				query.setParameterList(key, (Collection)value);
			} else {
				query.setParameter(key, alias.get(key));
			}
		}
	}
	
	private void setArgs(Object[] args, Query query) {
		if (args == null || args.length == 0)
			return;
		int index = 0;
		for (Object object : args) {
			query.setParameter(index++, object);			
		}
	}
	
	private String constructCountSql(String sql, boolean isHql) {
		int beginIndex = sql.toLowerCase().indexOf("from");
		String countSql = "select count(*) " + sql.substring(beginIndex);
		countSql = isHql ? countSql.replaceAll("(?i)fetch", "") : countSql;				  	  	    
		return countSql;
	}
	
	private Long getTotalCount(String sql, Object[] args, Map<String, Object> alias, boolean isHql) {
		String countSql = constructCountSql(sql, isHql);
		Query countQuery = isHql ? getSession().createQuery(countSql) : getSession().createSQLQuery(countSql);
		setArgs(args, countQuery);
		setAliasArgs(alias, countQuery);
		Long total = (Long)countQuery.uniqueResult();
		return total;
	}
	
	public Pager<T> findByHQL(String hql, Object[] args, Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		query.setFirstResult(SystemContext.getPageOffset());
		query.setMaxResults(SystemContext.getPageSize());
		
		
		Pager<T> pager = new Pager<T>();
		pager.setOffset(SystemContext.getPageOffset());
		pager.setSize(SystemContext.getPageSize());
		pager.setTotal(getTotalCount(hql, args, alias, true));
		pager.setDatas(query.list());
		return pager;
	}
	
	public Pager<T> findByHQL(String hql, Object[] args) {
		return findByHQL(hql, args, null);
	}
	
	public Pager<T> findByHQL(String hql, Map<String, Object> alias) {
		return findByHQL(hql, null, alias);
	}
	
	public List<T> listByHQL(String hql, Object[] args, Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		return query.list();
	}
	
	public List<T> listByHQL(String hql, Object[] args) {		
		return listByHQL(hql, args, null);
	}
	
	public List<T> listByHQL(String hql, Map<String, Object> alias) {
		return listByHQL(hql, null, alias);
	}
	
	public <N>Pager<N> findBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		SQLQuery query = getSession().createSQLQuery(sql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		query.setFirstResult(SystemContext.getPageOffset());
		query.setMaxResults(SystemContext.getPageSize());
		if (hasEntity) {
			query.addEntity(clz);
		} else {
			query.setResultTransformer(Transformers.aliasToBean(clz));
		}
		
		Pager<N> pager = new Pager<N>();
		pager.setOffset(SystemContext.getPageOffset());
		pager.setSize(SystemContext.getPageSize());
		pager.setTotal(getTotalCount(sql, args, alias, false));
		pager.setDatas(query.list());
		return pager;
	}
	
	public <N>Pager<N> findBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
		return findBySQL(sql, args, null, clz, hasEntity);
	}
	
	public <N>Pager<N> findBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return findBySQL(sql, null, alias, clz, hasEntity);
	}

	public <N>List<N> listBySQL(String sql, Object[] args, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		SQLQuery query = getSession().createSQLQuery(sql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		if (hasEntity) {
			query.addEntity(clz);
		} else {
			query.setResultTransformer(Transformers.aliasToBean(clz));
		}
		return query.list();
	}
	
	public <N>List<N> listBySQL(String sql, Object[] args, Class<?> clz, boolean hasEntity) {
		return listBySQL(sql, args, null, clz, hasEntity);
	}
	
	public <N>List<N> listBySQL(String sql, Map<String, Object> alias, Class<?> clz, boolean hasEntity) {
		return listBySQL(sql, null, alias, clz, hasEntity);
	}
	
	public void updateByHQL(String hql, Object[] args, Map<String, Object> alias) {
		Query query = getSession().createQuery(hql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		query.executeUpdate();
	}
	
	public void updateByHQL(String hql, Object[] args) {
		updateByHQL(hql, args, null);
	}
	
	public void updateByHQL(String hql, Map<String, Object> alias) {
		updateByHQL(hql, null, alias);
	}
	
	public void updateBySQL(String sql, Object[] args, Map<String, Object> alias) {
		Query query = getSession().createSQLQuery(sql);
		setArgs(args, query);
		setAliasArgs(alias, query);
		query.executeUpdate();
	}
	
	public void updateBySQL(String sql, Object[] args) {
		updateBySQL(sql, args, null);
	}
	
	public void updateBySQL(String sql, Map<String, Object> alias) {
		updateBySQL(sql, null, alias);
	}
}
