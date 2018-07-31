package com.nodelab.accademiaVillaDeiRomani.report.bean;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ReportStudenteBeanDataSourceRowMapper implements RowMapper<ReportStudenteBeanDataSource> {

	@Override
	public ReportStudenteBeanDataSource mapRow(ResultSet rs, int arg1) throws SQLException {
		ReportStudenteBeanDataSource reportStudenteBeanDataSource = new ReportStudenteBeanDataSource();
		reportStudenteBeanDataSource.setNome(rs.getString("nome"));
		reportStudenteBeanDataSource.setCognome(rs.getString("cognome"));
		reportStudenteBeanDataSource.setMatricola(rs.getString("matricola"));
		reportStudenteBeanDataSource.setTelefono(rs.getString("telefono"));
		reportStudenteBeanDataSource.setEmail(rs.getString("email"));


		return reportStudenteBeanDataSource;
	}

}
