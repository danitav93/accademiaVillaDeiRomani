package com.nodelab.accademiaVillaDeiRomani.utility;

import java.time.LocalDateTime;
import java.util.Date;

public class TimeAndDate {

	public static Date  getCurrentDate() {
		
		return java.sql.Timestamp.valueOf(LocalDateTime.now());
		
    }
	
    
}
