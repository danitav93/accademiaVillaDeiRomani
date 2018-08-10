package com.nodelab.accademiaVillaDeiRomani.service;

import java.io.File;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smattme.MysqlExportService;

@Service("backupService")
public class BackupServiceImpl implements BackupService {

	@Autowired
	ApplicationInfoService applicationInfoService;
	
	@Autowired
	OAuthMailService oAuthMailServic;
	
	@Autowired
	MailService mailService;
	
	@Override
	public void generateBackUp() throws Exception {
		
		//required properties for exporting of db
		Properties properties = new Properties();
		
		
		properties.setProperty(MysqlExportService.JDBC_CONNECTION_STRING, applicationInfoService.getDbUrl().substring(0, applicationInfoService.getDbUrl().indexOf("?")));
		properties.setProperty(MysqlExportService.DB_USERNAME,applicationInfoService.getDbUsername());
		properties.setProperty(MysqlExportService.DB_PASSWORD, applicationInfoService.getDbPassword());
		properties.setProperty(MysqlExportService.PRESERVE_GENERATED_ZIP, "true");
		
		
		//set the outputs temp dir
		properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		mysqlExportService.export();
		File file = mysqlExportService.getGeneratedZipFile();
		
		mailService.sendBackup(file);
		mysqlExportService.clearTempFiles(false);
		
	}

	
	
	
	
	
	
	
}
