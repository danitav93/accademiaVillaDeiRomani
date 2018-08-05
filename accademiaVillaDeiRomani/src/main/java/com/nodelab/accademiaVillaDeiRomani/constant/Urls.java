package com.nodelab.accademiaVillaDeiRomani.constant;

public class Urls {
	
	//----------
	//global
	//----------
	
	public static final String pathSeparator="/";
	public static final String baseAuthenticatedUserPath=pathSeparator+"VillaDeiRomaniAccademy";
	public static final String adminSeparator="admin";
	public static final String studentSeparator="student";
	public static final String redirect="redirect:";
	public static final String dominio="http://localhost:8080";

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
	public static final String abilitaUtentePath = pathSeparator+"abilita";
	public static final String registrazioneAvvenutaConSuccessoPath = pathSeparator+"registrationSucceded";
	public static final String openResetPasswordView = pathSeparator+"openResetPasswordView";
	public static final String openUpdatePasswordView=pathSeparator+"openUpdatePasswordView";
	public static final String resetPasswordMailConfirmationPath =pathSeparator+"resetPasswordMailConfirmation";


	

	//submit
	public static final String submitRegistrationPath = pathSeparator+"submitRegistration";
	public static final String resetPassword = pathSeparator+"resetPassword";
	public static final String updatePassword = pathSeparator+"updatePassword";

	//----------
	//Admin controller
	//----------
		
	//view
	public static final String openNewUserPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openCreaUtentePanel";
	public static final String openReportStudentiPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openReportStudentiPanel";
	public static final String openCreateCoursePanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openCreateCoursePanel";
	public static final String openDeleteCoursePanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openDeleteCoursePanel";
	public static final String openEditCoursePanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openEditCoursePanel";
	public static final String openVisualizzaCoursePanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openVisualizzaCoursePanel";
	public static final String openCreateAttivitaDidatticaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openCreateAttivitaDidatticaPanel";
	public static final String openDeleteAttivitaDidatticaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openDeleteAttivitaDidatticaPanel";
	public static final String openEditAttivitaDidatticaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openEditAttivitaDidatticaPanel";
	public static final String openVisualizzaAttivitaDidatticaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openVisualizzaAttivitaDidatticaPanel";
	public static final String openAddRelAttivitaCorsoPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openAddRelAttivitaCorsoPanel";
	public static final String openDeleteRelAttivitaCorsoPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openDeleteRelAttivitaCorsoPanel";
	public static final String openEditRelAttivitaCorsoPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openEditRelAttivitaCorsoPanel";
	public static final String openVisualizeRelAttivitaCorsoPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openVisualizeRelAttivitaCorsoPanel";
	public static final String openCreateTassaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openCreateTassaPanel";
	public static final String openDeleteTassaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openDeleteTassaPanel";
	public static final String openEditTassaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openEditTassaPanel";
	public static final String openVisualizaTassaPanelPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"openVisualizaTassaPanel";
	public static final String userSelectedPath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"userSelected";
	public static final String adminPath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"admin";

	//submit
	public static final String submitSearchPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"userSearch";
	public static final String submitNewUserPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitNewUser";
	public static final String submitStudentiReportPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitStudentiReport";
	public static final String submitCreateCoursePath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitCreateCourse";
	public static final String submitDeleteCoursePath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitDeleteCorso";
	public static final String submitEditCoursePath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitEditCourse";
	public static final String submitCreateAttivitaDidatticaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitCreateAttivitaDidattica";
	public static final String submitDeleteAttivitaDidatticaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitDeleteAttivitaDidattica";
	public static final String submitEditAttivitaDidatticaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitEditAttivitaDidattica";
	public static final String submitAddRelAttivitaCorsoPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitAddRelAttivitaCorso";
	public static final String submitDeleteRelAttivitaCorsoPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitDeleteRelAttivitaCorso";
	public static final String submitEditRelAttivitaCorsoPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitEditRelAttivitaCorso";
	public static final String submitCreateTassaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitCreateTassa";
	public static final String submitDeleteTassaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitDeleteTassa";
	public static final String submitEditTassaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitEditTassa";
	
	
	
	//----------
	//Studente Controller
	//----------
				
	//view
	public static final String editPersonalDataStudentPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"editStudentPersonalData";
	public static final String homeAdminPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"homeAdmin";
	public static final String openPanelAggiungiPercorsoFormativoPath= baseAuthenticatedUserPath+pathSeparator+studentSeparator+pathSeparator+"openPanelAggiungiPercorsoFormativo";
	public static final String studentPath = baseAuthenticatedUserPath+pathSeparator+studentSeparator+pathSeparator+"student";
	public static final String insertTaxPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"insertTax";
	public static final String deleteTaxPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"deleteTax";
	public static final String insertExamPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"insertExam";
	public static final String abilitazioneStudenteDirettaPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"abilitazioneStudenteDiretta";

	
	//submit
	public static final String submitEditStudentePath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitStudentEditPersonalData";
	public static final String submitNewPercorsoFormativoPath = baseAuthenticatedUserPath+pathSeparator+studentSeparator+pathSeparator+"submitNewPercorsoFormativo";
	public static final String submitNewTaxPath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitNewTax";
	public static final String submitDeleteTaxPath= baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitDeleteTax";
	public static final String submitNewExamPath = baseAuthenticatedUserPath+pathSeparator+adminSeparator+pathSeparator+"submitNewExam";

	
	//----------
	//errors
	//----------
	public static final String accessDenied= pathSeparator+"accessDenied";
	public static final String errorPath = "/error";
	
	
	
	
	//mails
	public static final String abilita = dominio+pathSeparator+"abilita";
	public static final String resetPasswordMailConfirmation =dominio+pathSeparator+"resetPasswordMailConfirmation";

	
	
}
