package com.ef.parser.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.ef.parser.dto.LogDTO;
import com.ef.parser.util.IParserConstant;

/**
 * This service used to read and load data from log file.
 * @author azza hamdy
 * */
public class DataLoader {
	
	/**
	 * read log data from file in given file path, parse it and compose list of {@link LogDTO} that represent data object.
	 * @param filePath
	 * @return list of {@link LogDTO}.
	 * */
	public List<LogDTO> loadData(String filePath) {
		BufferedReader reader = null;
		List<LogDTO> logList = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
			String line = null;
			LogDTO logObject = null;
			logList = new ArrayList<LogDTO>();
			while ((line = reader.readLine()) != null) {
				String[] lineValues = line.split(IParserConstant.FILE_DELIMITER);
				logObject = new LogDTO(lineValues[0].substring(0,lineValues[0].lastIndexOf(".")), lineValues[1],lineValues[2],lineValues[3],lineValues[4]);
				logList.add(logObject);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				if(reader!=null)
					reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return logList;
	}
	
}
