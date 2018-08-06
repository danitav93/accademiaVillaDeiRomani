package com.nodelab.accademiaVillaDeiRomani.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smattme.MysqlExportService;

@Service("backupService")
public class BackupServiceImpl implements BackupService {

	@Autowired
	ApplicationInfoService applicationInfoService;
	
	@Override
	public void generateBackUp() throws ClassNotFoundException, IOException, SQLException {
		
		//required properties for exporting of db
		Properties properties = new Properties();
		
		
		properties.setProperty(MysqlExportService.DB_NAME, applicationInfoService.getDbName());
		properties.setProperty(MysqlExportService.DB_USERNAME,applicationInfoService.getDbUsername());
		properties.setProperty(MysqlExportService.DB_PASSWORD, applicationInfoService.getDbPassword());
			
		
		//properties relating to email config
		properties.setProperty(MysqlExportService.EMAIL_HOST, applicationInfoService.getEmailHost());
		properties.setProperty(MysqlExportService.EMAIL_PORT, applicationInfoService.getEmailPort());
		properties.setProperty(MysqlExportService.EMAIL_USERNAME, applicationInfoService.getEmailUsername());
		properties.setProperty(MysqlExportService.EMAIL_PASSWORD, applicationInfoService.getEmailPassword());
		properties.setProperty(MysqlExportService.EMAIL_FROM, "tavernelli.daniele@gmail.com");
		properties.setProperty(MysqlExportService.EMAIL_TO, "tavernelli.daniele@gmail.com");

		//set the outputs temp dir
		properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		mysqlExportService.export();
		
		
		
	}

	
	
	
	
	
	
	
}
