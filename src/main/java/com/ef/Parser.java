package com.ef;

import java.util.List;

import com.ef.parser.dto.LogDTO;
import com.ef.parser.exception.ParserException;
import com.ef.parser.service.DataLoader;
import com.ef.parser.service.LogService;
import com.ef.parser.util.DurationEnum;
import com.ef.parser.util.SharedUtils;

/**
 * Entry point of application
 * 
 * @author azza_hamdy
 * */
public class Parser {

	public static void main(String[] args) {
		System.out
				.println(" ************** Application Started *************** ");
		try {
			String filePath = null;
			String startDate = null;
			String duration = null;
			String threshold = null;
			String ip = null;
			for (String s : args) {
				if (s.contains("accesslog")) {
					filePath = s.substring(s.lastIndexOf("=") + 1, s.length());
				} else if (s.contains("startDate")) {
					startDate = s.substring(s.lastIndexOf("=") + 1, s.length());
				} else if (s.contains("duration")) {
					duration = s.substring(s.lastIndexOf("=") + 1, s.length());
				} else if (s.contains("threshold")) {
					threshold = s.substring(s.lastIndexOf("=") + 1, s.length());
				} else if (s.contains("ip") || s.contains("IP")
						|| s.contains("Ip")) {
					ip = s.substring(s.lastIndexOf("=") + 1, s.length());
				}

			}

			saveLogs(filePath);			

			

			if (ip != null && !ip.isEmpty()) {
				if (SharedUtils.isValideIp(ip)) {
					List<LogDTO> res = logservice.findByIp(ip);
					if (res != null && res.size() > 0) {
						for (LogDTO log : res)
							System.out.println(log);
					}
				} else
					throw new ParserException("not valid Ip");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveLogs(String filePath) throws ParserException {
		LogService logservice = new LogService();
		if (filePath != null && !filePath.isEmpty()) {
			System.out.println("start loading data :");
			long start = System.currentTimeMillis();
			List<LogDTO> dataList = new DataLoader().loadData(filePath);
			logservice.saveLogData(dataList);
			System.out.format("Data loaded sucessfully In :: %d%n",
					System.currentTimeMillis() - start);
		}else {
			throw new ParserException("Invalid file path");
		}
	}
	
	private static void loadDataByStartDate(String threshold,String duration,String startDate) {
		try {
			Integer validthrehold = SharedUtils.checkthrehold(threshold);
			List<LogDTO> res = null;
			if (SharedUtils.isValieDate(startDate)
					&& SharedUtils.isValideDuration(duration)
					&& validthrehold != null) {
				if (duration.equalsIgnoreCase(DurationEnum.hourly.toString())) {
					res = logservice.loadHourlyLogs(startDate, validthrehold);
				} else if (duration.equalsIgnoreCase(DurationEnum.daily.toString())) {
					res = logservice.loadDailyLogs(startDate, validthrehold);
				}
				if (res != null && res.size() > 0) {
					res.forEach(log ->{
						System.out.println(log.getIp());
					});
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
