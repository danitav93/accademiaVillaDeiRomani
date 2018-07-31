package com.nodelab.accademiaVillaDeiRomani.constant;

public class Urls {
	
	//----------
	//global
	//----------
	
	public static final String pathSeparator="/";
	public static final String baseAuthenticatedUserPath=pathSeparator+"VillaDeiRomaniAccademy";
	public static final String adminSeparator="admin";
	
	//----------
	//login controller
	//----------
	
	//view
	public static final String loginPath= pathSeparator+"login";
	//submit
	public static final String submitLoginPath= baseAuthenticatedUserPath+pathSeparator+"submitLogin";
	
	//----------
	//Registration controller
	//----------
	
	//view
	public static final String registrationPath=pathSeparator+"registration";
	public static final String downloadAutoDichiarazionePath=pathSeparator+"downloadAutoDichiarazione";
	public static final String abilitaUtentePath = pathSeparator+"abilita";;

	//submit
	public static final String submitRegistrationPath = pathSeparator+"submitRegistration";

	//----------
	//Admin controller
	//----------
		
	//view
	public static final String openNewUserPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openCreaUtentePanel";
	public static final String openReportStudentiPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openReportStudentiPanel";
	public static final String userSelectedPath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"userSelected";
	//submit
	public static final String submitSearchPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"userSearch";
	public static final String submitNewUserPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitNewUser";
	public static final String submitStudentiReportPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitStudentiReport";


	
	
	
	
	//----------
	//Studente Controller
	//----------
				
	//view
	public static final String editStudentPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"studentEdit";
	public static final String homeAdminPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"homeAdmin";;
	
	//----------
	//Update Studente Controller
	//----------
	
	//submit
	public static final String submitEditStudentePath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitStudentEdit";

	
	//----------
	//errors
	//----------
	public static final String accessDenied= pathSeparator+"accessDenied";
	public static final String errorPath = "/error";

	
	
}
