package com.nodelab.accademiaVillaDeiRomani.service;

import com.nodelab.accademiaVillaDeiRomani.report.bean.ReportStudenteBean;

import net.sf.jasperreports.engine.JasperPrint;

public interface ReportService {

	public JasperPrint generateStudentiReport(ReportStudenteBean reportStudenteBean) throws Exception;
}
