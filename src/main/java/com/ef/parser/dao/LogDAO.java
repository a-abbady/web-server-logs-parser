package com.ef.parser.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ef.parser.dto.LogDTO;
import com.ef.parser.util.db.DBUtil;

/**
 * Log Data Access Object.
 * @author azza hamdy
 * */
public class LogDAO {

	private boolean dataSaved;

	/**
	 * save list of {@link LogDTO} into database.
	 * @param logList
	 * */
	public void saveData(List<LogDTO> logList) {

		if (logList != null && logList.size() > 0) {
			String sql = "insert into log (request_date, request_ip, request_method, request_status, request_body) values (str_to_date(?,'%Y-%m-%d %T'),?,?,?,?)";
			Connection connection = DBUtil.getDBConnection();
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(sql);

				final int batchSize = 10000;
				int count = 0;

				for (LogDTO log : logList) {

					ps.setString(1, log.getStartDate());
					ps.setString(2, log.getIp());
					ps.setString(3, log.getRequestMethod());
					ps.setString(4, log.getRequestStatus());
					ps.setString(5, log.getRequestBody());
					ps.addBatch();

					if (++count % batchSize == 0) {
						ps.executeBatch();
					}
				}
				ps.executeBatch(); // insert remaining records
				dataSaved = true;
			} catch (Exception e) {
				dataSaved = false;
				e.printStackTrace();
			} finally {
				try {
					if (ps != null)
						ps.close();
					DBUtil.closeDBConn(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isDataSaved() {
		return dataSaved;
	}

	public void setDataSaved(boolean dataSaved) {
		this.dataSaved = dataSaved;
	}
	/**
	 * find logs within specific range.
	 * @param fromDate start date
	 * @param endDate end date
	 * @param threshold number of requests used to get ips that made requets more than this number.
	 * @return List of {@link LogDTO}.  
	 * */
	public List<LogDTO> getLogInRang(String fromDate, String endDate,Integer threshold) {
		List<LogDTO> results = null;

		if (fromDate != null && !fromDate.isEmpty()) {
			String sql = "select * from log"
					+ " where request_date >= str_to_date(?,'%Y-%m-%d %T')"
					+ " and request_date <=  str_to_date(?,'%Y-%m-%d %T')"
					+ " having count(request_ip)>?;";
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				connection = DBUtil.getDBConnection();
				ps = connection.prepareStatement(sql);
				ps.setString(1, fromDate);
				ps.setString(2, endDate);
				ps.setInt(3, threshold);
				rs = ps.executeQuery();
				if (!rs.isBeforeFirst()) {
					System.out.println("no data found");
				} else {
					results = new ArrayList<LogDTO>();
					while (rs.next()) {
						LogDTO log = new LogDTO(rs.getDate("request_date")
								.toString(), rs.getString("request_ip"),
								rs.getString("request_method"),
								rs.getString("request_status"),
								rs.getString("request_body"));
						results.add(log);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(ps!=null)
						ps.close();
					if(rs!=null)
						rs.close();
					DBUtil.closeDBConn(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return results;
	}
	/**
	 * find all requests by specific IP.
	 * @param ip
	 * @return {@link List} of {@link LogDTO}.
	 * */
	public List<LogDTO> findByIp(String ip) {
		List<LogDTO> resluts = null;
		if (ip != null && !ip.isEmpty()) {
			String sql = "select * from log where request_ip = ?;";
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				connection = DBUtil.getDBConnection();
				ps = connection.prepareStatement(sql);
				ps.setString(1, ip);
				rs = ps.executeQuery();
				if (!rs.isBeforeFirst()) {
					System.out.println("no data found");
				} else {
					resluts = new ArrayList<LogDTO>();
					while (rs.next()) {
						LogDTO log = new LogDTO(rs.getDate("request_date").toString(),
								rs.getString("request_ip"),
								rs.getString("request_method"),
								rs.getString("request_status"),
								rs.getString("request_body"));
						resluts.add(log);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(ps!=null)
						ps.close();
					if(rs!=null)
						rs.close();
					DBUtil.closeDBConn(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return resluts;
	}

	/**
	 * save list of {@link LogDTO} daily.
	 * @param logList
	 * @return true if data saved in database successfully.
	 * */
	public boolean saveDailyLog(List<LogDTO> logList) {
		boolean dataSaved = false;
		if (logList != null && logList.size() > 0) {
			String sql = "insert into daily_log (request_ip,request_date,request, request_status, user_agent) values (?,str_to_date(?,'%Y-%m-%d %T'),?,?,?)";
			Connection connection = DBUtil.getDBConnection();
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(sql);

				for (LogDTO log : logList) {

					ps.setString(1, log.getIp());
					ps.setString(2, log.getStartDate());
					ps.setString(3, log.getRequestMethod());
					ps.setString(4, log.getRequestStatus());
					ps.setString(5, log.getRequestBody());
					ps.addBatch();
				}
				ps.executeBatch();
				dataSaved = true;
			} catch (Exception e) {
				dataSaved = false;
				e.printStackTrace();
			} finally {
				try {
					if(ps!=null)
						ps.close();
					DBUtil.closeDBConn(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dataSaved;
	}
	/**
	 * save list of {@link LogDTO} hourly.
	 * @param logList
	 * @return true if data saved in database successfully.
	 * */
	public boolean saveHourlyLog(List<LogDTO> logList) {
		boolean dataSaved = false;
		if (logList != null && logList.size() > 0) {
			String sql = "insert into hourly_log (request_ip,request_date,request, request_status, user_agent) values (?,str_to_date(?,'%Y-%m-%d %T'),?,?,?)";
			Connection connection = DBUtil.getDBConnection();
			PreparedStatement ps = null;
			try {
				ps = connection.prepareStatement(sql);

				for (LogDTO log : logList) {
					ps.setString(1, log.getIp());
					ps.setString(2, log.getStartDate());
					ps.setString(3, log.getRequestMethod());
					ps.setString(4, log.getRequestStatus());
					ps.setString(5, log.getRequestBody());
					ps.addBatch();
				}
				ps.executeBatch();
				dataSaved = true;
			} catch (Exception e) {
				dataSaved = false;
				e.printStackTrace();
			} finally {
				try {
					if(ps!=null)
						ps.close();
					DBUtil.closeDBConn(connection);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return dataSaved;
	}
}
