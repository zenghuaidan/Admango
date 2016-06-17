package com.edeas.basic.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.BeforeClass;


public class AbstractDBUnitTestCase1 {

	protected static IDatabaseConnection connection;
	protected static File tableBackupFile;
	protected static File tableDTDBackupFile;
	
	@BeforeClass
	public static void init() throws Exception {
		Properties properties = new Properties();
		properties.load(AbstractDBUnitTestCase1.class.getClassLoader().getResourceAsStream("jdbc.properties"));
		Class.forName(properties.getProperty("jdbc.driverClassName"));
		Connection conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
		connection = new DatabaseConnection(conn);		
	}
	
	protected void backupAllTables() throws Exception {
		IDataSet dataSet = connection.createDataSet();
		tableBackupFile = File.createTempFile("tables", ".xml");
		tableDTDBackupFile = File.createTempFile("tables_dtd", ".xsd");
		FlatXmlDataSet.write(dataSet, new FileOutputStream(tableBackupFile));
		FlatDtdDataSet.write(dataSet, new FileOutputStream(tableDTDBackupFile));
	}
	
	protected void backupTables(String[] tables) throws Exception {
		QueryDataSet backupDataSet = new QueryDataSet(connection);
		for (String table : tables) {
			backupDataSet.addTable(table);
		}
		tableBackupFile = File.createTempFile("tables", ".xml");
		FlatXmlDataSet.write(backupDataSet, new FileOutputStream(tableBackupFile));
	}
	
	protected void backupTable(String table) throws Exception {
		backupTables(new String[] { table });
	}		
		
	protected void resumeDatabase() throws Exception {
		FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
		builder.setMetaDataSetFromDtd(new FileInputStream(tableDTDBackupFile));		
		DatabaseOperation.CLEAN_INSERT.execute(connection, builder.build(tableBackupFile));
	}
	
	@AfterClass
	public static void destroy() throws Exception {		
		if (connection != null) {
			connection.close();
			connection = null;
		}
	}
}
