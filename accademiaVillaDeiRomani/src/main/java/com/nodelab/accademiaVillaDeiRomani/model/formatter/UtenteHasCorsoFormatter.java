package com.nodelab.accademiaVillaDeiRomani.model.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import com.nodelab.accademiaVillaDeiRomani.model.UtenteHasCorso;
import com.nodelab.accademiaVillaDeiRomani.repository.UtenteHasCorsoRepository;
import com.nodelab.accademiaVillaDeiRomani.service.CorsoService;
import com.nodelab.accademiaVillaDeiRomani.service.UtenteService;

@Component
public class UtenteHasCorsoFormatter implements Formatter<UtenteHasCorso> {

	@Autowired
	CorsoService corsoService;

	@Autowired
	UtenteService utenteService;

	@Autowired
	UtenteHasCorsoRepository utenteHasCorsoRepository;

	@Override
	public String print(UtenteHasCorso arg0, Locale arg1) {
		return arg0.getCorso().getNome();
	}

	@Override
	public UtenteHasCorso parse(String arg0, Locale arg1) throws ParseException {
		/*try {*/
			if (arg0.equals("0")) {
				return new UtenteHasCorso();
			}

			if (arg0.indexOf("#")==-1) {
				return utenteHasCorsoRepository.findById(Integer.parseInt(arg0)).get();
			}
			//arg0 è id_corso#id_utente

			UtenteHasCorso utenteHasCorso = new UtenteHasCorso();
			String[] array=arg0.split("#");
			utenteHasCorso.setCorso(corsoService.getCorsoById(Integer.parseInt(array[0])));
			utenteHasCorso.setUtente(utenteService.findUtenteById(Integer.parseInt(array[1])));
			return utenteHasCorso;
		/*} catch (Exception e) {
			return new UtenteHasCorso();
		}*/
	}

}
