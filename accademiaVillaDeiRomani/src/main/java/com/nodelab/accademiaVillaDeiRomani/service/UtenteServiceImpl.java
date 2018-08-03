package com.nodelab.accademiaVillaDeiRomani.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nodelab.accademiaVillaDeiRomani.constant.Ruoli;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiEsameBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.AggiungiTasseBean;
import com.nodelab.accademiaVillaDeiRomani.formBean.PercorsoFormativoBean;
import com.nodelab.accademiaVillaDeiRomani.model.AttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Contributo;
import com.nodelab.accademiaVillaDeiRomani.model.CorsoHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.Matricola;
import com.nodelab.accademiaVillaDeiRomani.model.Utente;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasAttivitaDidattica;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasContributo;
import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBeanDataSource;
import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBeanDataSourceRowMapper;
import com.nodelab.accademiaVillaDeiRomani.repository.MatricolaRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.RoleRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteHasAttivitaDidatticaRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteHasContributoRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteHasCorsoRepository;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteRepository;
import com.nodelab.accademiaVillaDeiRomani.utility.TimeAndDate;

@Service("utenteService")
public class UtenteServiceImpl implements UtenteService  {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UtenteHasCorsoRepository utenteHasCorsoRepository;

	@Autowired
	private MatricolaRepository matricolaRepository;

	@Autowired
	private UtenteHasAttivitaDidatticaRepository utenteHasAttivitaDidatticaRepository;

	@Autowired
	private UtenteHasContributoRepository utenteHasContributoRepository;



	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Utente findUtenteByEmail(String email) {
		return utenteRepository.findByEmail(email);
	}

	@Override
	public Utente findUtenteById(int id) {
		return utenteRepository.findById(id).get();
	}

	@Override
	public void saveUser(Utente utente) {
		utenteRepository.save(utente);
	}

	/**
	 * prendo il valore dal record che sarà la matricola dell utente che si registra. Poi aumento il valore
	 */
	@Override
	public String getNewMatricola() {
		Matricola matricola= matricolaRepository.findById(1).get();
		Integer matricolaToReturn=matricola.getValue();
		matricola.setValue(matricola.getValue()+1);
		return matricolaToReturn.toString();
	}



	@Override
	public Utente saveNewRegisteredUser(Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setMatricola(getNewMatricola());
		utente.setDataIscrizione(TimeAndDate.getCurrentDate());
		utente.setRole(roleRepository.findByName(Ruoli.ruolo_studente));
		utente.setActive(0);
		return utenteRepository.save(utente);
	}

	@Override
	public void update(@Valid Utente utente) {

		Utente oldUtente = findUtenteById(utente.getIdUtente());
		utente.setPassword(oldUtente.getPassword());
		utente.setDataIscrizione(oldUtente.getDataIscrizione());
		utente.setMatricola(oldUtente.getMatricola());
		utente.setActive(oldUtente.getActive());


		//cancello le relazioni vecchie
		if (oldUtente.getUtenteHasCorso()!=null) {
			utenteHasCorsoRepository.delete(oldUtente.getUtenteHasCorso());
		}

		//salvo quelle nuove
		utenteHasCorsoRepository.save(utente.getUtenteHasCorso());

		//salvo l'utente 
		utenteRepository.save(utente);

	}

	@Override
	public Utente saveNewUserFromAdmin(@Valid Utente utente) {
		utente.setPassword(bCryptPasswordEncoder.encode(utente.getPassword()));
		utente.setMatricola(getNewMatricola());
		utente.setDataIscrizione(TimeAndDate.getCurrentDate());
		utente.setActive(0);
		return utenteRepository.save(utente);

	}

	@Override
	public Utente findUtenteByMatricola(String matricola) {
		return utenteRepository.findByMatricola(matricola);
	}

	@Override
	public void abilitaUtenteByMatricola(String matricola) {
		Utente utente=utenteRepository.findByMatricola(matricola);

		utente.setActive(1);

		utenteRepository.save(utente);

		return;
	}

	@Override
	public List<ReportStudenteBeanDataSource> executeStudentReportRowQuery(String query) {
		List<ReportStudenteBeanDataSource> list=jdbcTemplate.query(query, new ReportStudenteBeanDataSourceRowMapper());
		if (list==null || list.isEmpty()) {
			return new ArrayList<>();
		}
		return list;
	}

	@Override
	public void updatePercorsoFormativo(PercorsoFormativoBean percorsoFormativoBean) {

		//cancello tutti gli esami che l'utente aveva prima
		List<UtenteHasAttivitaDidattica> listToDelete= new ArrayList<>();
		for (UtenteHasAttivitaDidattica utenteHasAttivitaDidattica: percorsoFormativoBean.getUtente().getUtenteHasAttivitaDidatticaSet()) {
			listToDelete.add(utenteHasAttivitaDidattica);
		}
		
		utenteHasAttivitaDidatticaRepository.deleteAll(listToDelete);
		utenteHasAttivitaDidatticaRepository.flush();

		//cancello il corso che avevo prima
		if (percorsoFormativoBean.getUtente().getUtenteHasCorso()!=null) {
			utenteHasCorsoRepository.delete(percorsoFormativoBean.getUtente().getUtenteHasCorso());
			utenteHasCorsoRepository.flush();
		}
		//salvo il nuovo corso
		UtenteHasCorso utenteHasCorso= new UtenteHasCorso();
		utenteHasCorso.setCorso(percorsoFormativoBean.getCorso());
		utenteHasCorso.setUtente(percorsoFormativoBean.getUtente());
		percorsoFormativoBean.getUtente().setUtenteHasCorso(utenteHasCorso);
		utenteHasCorsoRepository.save(utenteHasCorso);
		//salvo le nuove attivita didattiche
		List<UtenteHasAttivitaDidattica> listToSave= new ArrayList<>();
		//quelle obbligatorie
		for (CorsoHasAttivitaDidattica corsoHasAttivitaDidattica: percorsoFormativoBean.getCorso().getCorsoHasAttivitaDidatticaSet()) {
			if (corsoHasAttivitaDidattica.isObbligatorio()) {
				UtenteHasAttivitaDidattica utenteHasAttivitaDidattica = new UtenteHasAttivitaDidattica();
				utenteHasAttivitaDidattica.setAttivitaDidattica(corsoHasAttivitaDidattica.getAttivitaDidattica());
				utenteHasAttivitaDidattica.setUtente(percorsoFormativoBean.getUtente());
				listToSave.add(utenteHasAttivitaDidattica);
			}
		}
		//quellio facoltativi
		for (AttivitaDidattica attivitaDidattica:percorsoFormativoBean.getAttivitaDidatticaFacoltativaList()) {
			UtenteHasAttivitaDidattica utenteHasAttivitaDidattica = new UtenteHasAttivitaDidattica();
			utenteHasAttivitaDidattica.setAttivitaDidattica(attivitaDidattica);
			utenteHasAttivitaDidattica.setUtente(percorsoFormativoBean.getUtente());
			listToSave.add(utenteHasAttivitaDidattica);
		}

		percorsoFormativoBean.getUtente().setUtenteHasAttivitaDidatticaSet(new HashSet<UtenteHasAttivitaDidattica>(listToSave));
		utenteHasAttivitaDidatticaRepository.saveAll(listToSave);

		percorsoFormativoBean.getUtente().setHasPercorsoFormativo(true);
		utenteRepository.save(percorsoFormativoBean.getUtente());

	}

	@Override
	public void addTax(@Valid AggiungiTasseBean aggiungiTasseBean) {


		UtenteHasContributo utenteHasContributo = new UtenteHasContributo();
		utenteHasContributo.setContributo(aggiungiTasseBean.getContributo());
		utenteHasContributo.setData(aggiungiTasseBean.getData());
		utenteHasContributo.setImporto(aggiungiTasseBean.getImporto());
		utenteHasContributo.setUtente(aggiungiTasseBean.getUtente());
		utenteHasContributoRepository.save(utenteHasContributo);



	}

	@Override
	public void addExam(@Valid AggiungiEsameBean aggiungiEsameBean) {

		for (UtenteHasAttivitaDidattica utenteHasAttivitaDidattica: aggiungiEsameBean.getUtente().getUtenteHasAttivitaDidatticaSet()) {
			//cerco l'esame che ho inserito tra quelli associati a'utente
			if (aggiungiEsameBean.getAttivitaDidattica().getIdAttivitaDidattica()==utenteHasAttivitaDidattica.getAttivitaDidattica().getIdAttivitaDidattica()) {
				utenteHasAttivitaDidattica.setDataEsame(aggiungiEsameBean.getDataEsame());
				utenteHasAttivitaDidattica.setVotoEsame(aggiungiEsameBean.getVotoEsame());
				utenteHasAttivitaDidatticaRepository.save(utenteHasAttivitaDidattica);
				return;
			}
		}

	}

	@Override
	public @Valid Utente saveUpdatedUser(Utente oldUtente, @Valid Utente newUtente) {

		oldUtente.setNome(newUtente.getNome());
		oldUtente.setCognome(newUtente.getCognome());
		oldUtente.setDataNascita(newUtente.getDataNascita());
		oldUtente.setNazione(newUtente.getNazione());
		oldUtente.setIndirizzo(newUtente.getIndirizzo());
		oldUtente.setSex(newUtente.getSex());
		oldUtente.setCodiceFiscale(newUtente.getCodiceFiscale());
		oldUtente.setTelefono(newUtente.getTelefono());
		oldUtente.setEmail(newUtente.getEmail());


		return utenteRepository.save(oldUtente);
	}

	@Override
	public void removeTax(Utente utente, Contributo contributo) {
		
		for (UtenteHasContributo utenteHasContributo:utente.getUtenteHasContributiSet()) {
			if (utenteHasContributo.getContributo().getIdContributo()==contributo.getIdContributo()) {
				utenteHasContributoRepository.delete(utenteHasContributo);
			}
		}
		
	}







}
