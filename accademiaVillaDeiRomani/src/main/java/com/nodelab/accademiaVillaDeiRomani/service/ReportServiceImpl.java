package com.nodelab.accademiaVillaDeiRomani.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Application;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBean;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBeanDataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportServiceImpl implements ReportService {
	
	SimpleDateFormat format =
	        new SimpleDateFormat(" dd/MM/yyyy");

	@Override
	public JasperPrint generateStudentiReport(ReportStudenteBean reportStudenteBean) throws Exception {
		
		
	        Map<String, Object> params = new HashMap<String, Object>();
	        
	        //mi prendo il logo e lo metto come parametro del report
	        ClassLoader classLoader = getClass().getClassLoader();
	    	File imagePath = new File(classLoader.getResource("static/img/logoBig.jpeg").getFile());
	        BufferedImage classpathImage = ImageIO.read(imagePath);
	        params.put("logo", classpathImage);
	        
	        //mi prendo la data corrente e la metto come parametro del report
	        Calendar cal = Calendar.getInstance();
	        Date date=cal.getTime();
	        params.put("data", date);
	        
	        //setto il parametro filtri
	        String filtri="";
	        if (reportStudenteBean.getCorso()!=null) {
	        	filtri=filtri+"Corso: "+reportStudenteBean.getCorso().getNome()+"  ";
	        }
	        if (reportStudenteBean.getAttivitaDidattica()!=null && !reportStudenteBean.isEsameSostenuto()) {
	        	filtri=filtri+"Insegnamento: "+reportStudenteBean.getAttivitaDidattica().getNome()+"  ";
	        }
	        if (reportStudenteBean.getAttivitaDidattica()!=null && reportStudenteBean.isEsameSostenuto()) {
	        	filtri=filtri+"Esame conseguito: "+reportStudenteBean.getAttivitaDidattica().getNome()+"  ";
	        }
	        if (reportStudenteBean.getContributo()!=null) {
	        	filtri=filtri+"Contributo non pagato: "+reportStudenteBean.getContributo().getNome()+"  ";
	        }
	        if (reportStudenteBean.getaImmatricolazioneDate()!=null && reportStudenteBean.getDaImmatricolazioneDate()!=null && reportStudenteBean.getaImmatricolazioneDate().after(reportStudenteBean.getDaImmatricolazioneDate())) {
	        	filtri=filtri+"Immatricolazione: "+format.format(reportStudenteBean.getDaImmatricolazioneDate())+" - "+format.format(reportStudenteBean.getaImmatricolazioneDate())+"  ";
	        }
	        if (reportStudenteBean.getDaAnni()!=null && reportStudenteBean.getaAnni()!=null && reportStudenteBean.getDaAnni()<=reportStudenteBean.getaAnni()) {
	        	filtri=filtri+"Età: "+reportStudenteBean.getDaAnni()+" - "+reportStudenteBean.getaAnni()+"  ";
	        }
	        if (reportStudenteBean.getSex()!=null) {
	        	String sesso=Application.sessoFemminile;
	        	if (reportStudenteBean.getSex()==1) {
	        		sesso=Application.sessoMaschile;
	        	}
	        	filtri=filtri+"Sesso: "+sesso+"  ";
	        }
	        params.put("filtri", filtri);
	        
	        
	        File reportPath = new File(classLoader.getResource("static/report/studenti.jasper").getFile());
	        return JasperFillManager.fillReport(reportPath.getAbsolutePath(), params, getStudentiDataSource(reportStudenteBean));
	}

	/**
	 * Genero la lista degli studenti, costruendo una raw query da eseguire sul db a seconda dei filtri messi in reportStudentiBean
	 * @param reportStudenteBean
	 * @return
	 */
	private JRBeanCollectionDataSource getStudentiDataSource(ReportStudenteBean reportStudenteBean) {
		
		List<ReportStudenteBeanDataSource> studenti= new ArrayList<>();
		
		//costruisco la query
		String query="SELECT U.NOME,U.COGNOME,U.MATRICOLA,U.TELEFONO,U.EMAIL FROM UTENTE U ";
		String whereClauses=" WHERE U.ID_ROLE=2";
		String and=" AND ";
		
		if (reportStudenteBean.getCorso()!=null) {
			query=query+" JOIN UTENTE_HAS_CORSO UC ON U.ID_UTENTE=UC.ID_UTENTE JOIN CORSO C ON C.ID_CORSO=UC.ID_CORSO";
			whereClauses=whereClauses+and+"ID_CORSO="+reportStudenteBean.getCorso().getIdCorso();
        }
        if (reportStudenteBean.getAttivitaDidattica()!=null && !reportStudenteBean.isEsameSostenuto()) {
        	query=query+" JOIN UTENTE_HAS_ATTIVITA_DIDATTICA UA ON U.ID_UTENTE=UA.ID_UTENTE JOIN ATTIVITA_DIDATTICA A ON A.ID_ATTIVITA_DIDATTICA=UA.ID_ATTIVITA_DIDATTICA";
			whereClauses=whereClauses+and+"ID_ATTIVITA_DIDATTICA="+reportStudenteBean.getAttivitaDidattica().getIdAttivitaDidattica();
        }
        if (reportStudenteBean.getAttivitaDidattica()!=null && reportStudenteBean.isEsameSostenuto()) {
        	query=query+" JOIN UTENTE_HAS_ATTIVITA_DIDATTICA UA ON U.ID_UTENTE=UA.ID_UTENTE JOIN ATTIVITA_DIDATTICA A ON A.ID_ATTIVITA_DIDATTICA=UA.ID_ATTIVITA_DIDATTICA";
			whereClauses=whereClauses+and+"ID_ATTIVITA_DIDATTICA="+reportStudenteBean.getAttivitaDidattica().getIdAttivitaDidattica()+and+"NOT UA.VOTO_ESAME=NULL";
        }
        if (reportStudenteBean.getContributo()!=null) {
			whereClauses=whereClauses+and+"NOT EXISTS(SELECT UT.ID_UTENTE=U.ID_UTENTE FROM UTENTE_HAS_CONTRIBUTO UT JOIN CONTRIBUTO T ON T.ID_CONTRIBUTO=UT.ID_CONTRIBUTO )"+and+"T.ID_CONTRIBUTO="+reportStudenteBean.getContributo().getIdContributo();
        }
        if (reportStudenteBean.getaImmatricolazioneDate()!=null && reportStudenteBean.getDaImmatricolazioneDate()!=null && reportStudenteBean.getaImmatricolazioneDate().after(reportStudenteBean.getDaImmatricolazioneDate())) {
        }
        if (reportStudenteBean.getDaAnni()!=null && reportStudenteBean.getaAnni()!=null && reportStudenteBean.getDaAnni()<=reportStudenteBean.getaAnni()) {
        }
        if (reportStudenteBean.getSex()!=null) {
        	String sesso=Application.sessoFemminile;
        	if (reportStudenteBean.getSex()==1) {
        		sesso=Application.sessoMaschile;
        	}
        }
		
		query=query+whereClauses+";";
		
		
		ReportStudenteBeanDataSource utente=new ReportStudenteBeanDataSource();
		utente.setNome("Daniele");
		utente.setCognome("Tavernelli");
		utente.setMatricola("1");
		utente.setTelefono("telefono");
		utente.setEmail("danitav93@hotmail.it");
		
		studenti.add(utente);
		
		return new JRBeanCollectionDataSource(studenti);
	}
	
	
	
	

}
