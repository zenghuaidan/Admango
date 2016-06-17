package com.edeas.admango.dao;

import org.springframework.stereotype.Repository;

import com.edeas.admango.model.Category;
import com.edeas.basic.dao.BasicDao;

@Repository(value="categoryDao")
public class CategoryDao extends BasicDao<Category> implements ICategoryDao {	

}
