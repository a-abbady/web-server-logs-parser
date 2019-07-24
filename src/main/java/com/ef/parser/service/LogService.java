package com.ef.parser.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ef.parser.dao.LogDAO;
import com.ef.parser.dto.LogDTO;
import com.ef.parser.util.SharedUtils;

/**
 * Log Service
 * 
 * @author azza hamdy
 * */
public class LogService {
	private LogDAO logDao;

	public LogService() {
		logDao = new LogDAO();
	}

	public void saveLogData(List<LogDTO> logs) {
		logDao.saveData(logs);
	}

	public boolean isDataSaved() {
		return logDao.isDataSaved();
	}

	public List<LogDTO> loadHourlyLogs(String startDate, Integer threhold) {
		List<LogDTO> results = null;
		if (startDate != null && !startDate.isEmpty() && threhold != null) {
			String from = startDate.replace(".", " ");
			Date fromDate = SharedUtils.stringToDate(from);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			cal.add(Calendar.HOUR, 1);
			String toDate = SharedUtils.formateDate(cal.getTime());
			results = logDao.getLogInRang(from, toDate, threhold);
			logDao.saveHourlyLog(results);
		}
		return results;
	}
	
	public List<LogDTO> loadDailyLogs(String startDate, Integer threhold) {
		List<LogDTO> results = null;
		if (startDate != null && !startDate.isEmpty() && threhold != null) {
			String from = startDate.replace(".", " ");
			Date fromDate = SharedUtils.stringToDate(from);
			Calendar cal = Calendar.getInstance();
			cal.setTime(fromDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			String toDate = SharedUtils.formateDate(cal.getTime());
			results = logDao.getLogInRang(from, toDate, threhold);
			logDao.saveDailyLog(results);
		}
		return results;
	}

	public List<LogDTO> findByIp(String ip) {
		List<LogDTO> res = null;
		if (ip != null && !ip.isEmpty())
			res = logDao.findByIp(ip);

		return res;
	}
}
