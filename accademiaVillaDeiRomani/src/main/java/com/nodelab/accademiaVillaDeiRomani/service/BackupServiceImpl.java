package com.nodelab.accademiaVillaDeiRomani.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.stereotype.Service;

import com.smattme.MysqlExportService;

@Service("backupService")
public class BackupServiceImpl implements BackupService {

	@Override
	public void generateBackUp() throws ClassNotFoundException, IOException, SQLException {
		
		//required properties for exporting of db
		Properties properties = new Properties();
		properties.setProperty(MysqlExportService.DB_NAME, "villa_dei_romani_accademy");
		properties.setProperty(MysqlExportService.DB_USERNAME, "root");
		properties.setProperty(MysqlExportService.DB_PASSWORD, "");


		//properties relating to email config
		properties.setProperty(MysqlExportService.EMAIL_HOST, "smtp.gmail.com");
		properties.setProperty(MysqlExportService.EMAIL_PORT, "587");
		properties.setProperty(MysqlExportService.EMAIL_USERNAME, "tavernelli.daniele@gmail.com");
		properties.setProperty(MysqlExportService.EMAIL_PASSWORD, "Guit.1993!");
		properties.setProperty(MysqlExportService.EMAIL_FROM, "tavernelli.daniele@gmail.com");
		properties.setProperty(MysqlExportService.EMAIL_TO, "tavernelli.daniele@gmail.com");

		//set the outputs temp dir
		properties.setProperty(MysqlExportService.TEMP_DIR, new File("external").getPath());
		MysqlExportService mysqlExportService = new MysqlExportService(properties);
		mysqlExportService.export();
		
		
		
	}

	
	
	
	
	
	
	
}
