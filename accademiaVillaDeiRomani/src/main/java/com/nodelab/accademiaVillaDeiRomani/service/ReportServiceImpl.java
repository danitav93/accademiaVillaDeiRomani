package com.nodelab.accademiaVillaDeiRomani.service;

import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Application;
import com.nodelab.accademiaVillaDeiRomani.formBean.ReportStudenteBean;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBeanDataSource;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Value(value = "classpath:static/img/logo_villa.png")
	private Resource logoImageResource;
	
	@Value(value = "classpath:static/report/studenti.jasper")
	private Resource studentReportResource;
	
	@Autowired
	UtenteService utenteService;
	
	private static SimpleDateFormat format =
	        new SimpleDateFormat(" dd/MM/yyyy");
	private static SimpleDateFormat simpleFormat =
	        new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public JasperPrint generateStudentiReport(ReportStudenteBean reportStudenteBean) throws Exception {
		
		
		
		
	        Map<String, Object> params = new HashMap<String, Object>();
	        
	        //mi prendo il logo e lo metto come parametro del report
	        BufferedImage classpathImage = ImageIO.read(logoImageResource.getInputStream());
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
	        
	        
	        
	        return JasperFillManager.fillReport(studentReportResource.getInputStream(), params, getStudentiDataSource(reportStudenteBean));
	}

	/**
	 * Genero la lista degli studenti, costruendo una raw query da eseguire sul db a seconda dei filtri messi in reportStudentiBean
	 * @param reportStudenteBean
	 * @return
	 */
	private JRBeanCollectionDataSource getStudentiDataSource(ReportStudenteBean reportStudenteBean) {
		
		List<ReportStudenteBeanDataSource> studenti= new ArrayList<>();
		
		//costruisco la query
		String query="select u.nome,u.cognome,u.matricola,u.telefono,u.email from utente u ";
		String whereClauses=" where u.id_role=2";
		String and=" AND ";
		
		if (reportStudenteBean.getCorso()!=null) {
			query=query+" join utente_has_corso uc on u.id_utente=uc.id_utente join corso c on c.id_corso=uc.id_corso";
			whereClauses=whereClauses+and+"c.id_corso="+reportStudenteBean.getCorso().getIdCorso();
        }
        if (reportStudenteBean.getAttivitaDidattica()!=null && !reportStudenteBean.isEsameSostenuto()) {
        	query=query+" join utente_has_attivita_didattica ua on u.id_utente=ua.id_utente join attivita_didattica a on a.id_attivita_didattica=ua.id_attivita_didattica";
			whereClauses=whereClauses+and+"a.id_attivita_didattica="+reportStudenteBean.getAttivitaDidattica().getIdAttivitaDidattica();
        }
        if (reportStudenteBean.getAttivitaDidattica()!=null && reportStudenteBean.isEsameSostenuto()) {
        	query=query+" join utente_has_attivita_didattica ua on u.id_utente=ua.id_utente join attivita_didattica a on a.id_attivita_didattica=ua.id_attivita_didattica";
			whereClauses=whereClauses+and+"a.id_attivita_didattica="+reportStudenteBean.getAttivitaDidattica().getIdAttivitaDidattica()+and+"not ua.voto_esame is null";
        }
        if (reportStudenteBean.getContributo()!=null) {
			whereClauses=whereClauses+and+"u.id_utente not in(select p.id_utente from utente p join  utente_has_contributo ut on ut.id_utente=p.id_utente join contributo t on t.id_contributo=ut.id_contributo where t.id_contributo="+reportStudenteBean.getContributo().getIdContributo()+")";
        }
        if (reportStudenteBean.getaImmatricolazioneDate()!=null && reportStudenteBean.getDaImmatricolazioneDate()!=null && reportStudenteBean.getaImmatricolazioneDate().after(reportStudenteBean.getDaImmatricolazioneDate())) {
			whereClauses=whereClauses+and+"u.data_iscrizione between \""+simpleFormat.format(reportStudenteBean.getDaImmatricolazioneDate())+"\""+and+"\""+simpleFormat.format(reportStudenteBean.getaImmatricolazioneDate())+"\"";
        }
        if (reportStudenteBean.getDaAnni()!=null && reportStudenteBean.getaAnni()!=null && reportStudenteBean.getDaAnni()<=reportStudenteBean.getaAnni()) {
			whereClauses=whereClauses+and+"timestampdiff(year,"+" u.data_nascita"+", now()) between "+reportStudenteBean.getDaAnni()+and+reportStudenteBean.getaAnni();
        }
        
        if (reportStudenteBean.getSex()!=null) {
			whereClauses=whereClauses+and+"u.sex="+reportStudenteBean.getSex();
        }
		
		query=query+whereClauses+";";
		
		
		studenti= utenteService.executeStudentReportRowQuery(query);
		
		return new JRBeanCollectionDataSource(studenti);
	}
	
	
	
	

}
