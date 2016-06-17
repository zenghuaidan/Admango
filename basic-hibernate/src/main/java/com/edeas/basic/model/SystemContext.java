package com.edeas.basic.model;

public class SystemContext {
	private int pageSize = 15;
	private int pageOffset;
//	private String sort;
//	private String order;
	private String realPath;
	
	private SystemContext() {}
	private static ThreadLocal<SystemContext> threadLocal = new ThreadLocal<SystemContext>();
	
	public static SystemContext getInstance() {
		SystemContext instance = threadLocal.get();
		if (instance == null) {
			threadLocal.set(instance = new SystemContext());
		}
		return instance;
	}

	public static int getPageSize() {
		return getInstance().pageSize;
	}

	public static void setPageSize(int pageSize) {
		getInstance().pageSize = pageSize;
	}

	public static int getPageOffset() {
		return getInstance().pageOffset;
	}

	public static void setPageOffset(int pageOffset) {
		getInstance().pageOffset = pageOffset;
	}

//	public static String getSort() {
//		return getInstance().sort;
//	}
//
//	public static void setSort(String sort) {
//		getInstance().sort = sort;
//	}
//
//	public static String getOrder() {
//		return getInstance().order;
//	}
//
//	public static void setOrder(String order) {
//		getInstance().order = order;
//	}

	public static String getRealPath() {
		return getInstance().realPath;
	}

	public static void setRealPath(String realPath) {
		getInstance().realPath = realPath;
	}
	
	public static void remove() {
		threadLocal.remove();
	}
	
}
