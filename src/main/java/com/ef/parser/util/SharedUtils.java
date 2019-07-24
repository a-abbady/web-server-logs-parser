package com.ef.parser.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ef.parser.exception.ParserException;

public class SharedUtils {
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	private static Pattern pattern;
	private static Matcher matcher;

	public static Date stringToDate(String date) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			cal.setTime(sdf.parse(date));
			System.out.println(sdf.format(cal.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal.getTime();
	}

	public static String formateDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static boolean isValideIp(final String ip) {
		pattern = Pattern.compile(IPADDRESS_PATTERN);
		matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	public static boolean isValieDate(String startDate) throws ParserException {
		boolean valideDate = false;
		if (startDate == null)
			throw new ParserException("Start date argument is empty");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
		try {
			sdf.parse(startDate);
			valideDate = true;

		} catch (Exception e) {
			valideDate = false;
			throw new ParserException("not valide date formate, it must be in this formate 'yyyy-MM-dd.HH:mm:ss'");
		}

		return valideDate;
	}

	public static boolean isValideDuration(String duration) throws ParserException {
		boolean validDur = false;
		if (duration == null)
			throw new ParserException("Duration argument is Empty");

		if (DurationEnum.daily.toString().equalsIgnoreCase(duration)
				|| DurationEnum.hourly.toString().equalsIgnoreCase(duration)) {
			validDur = true;
		} else {
			throw new ParserException("invalid duration");
		}
		return validDur;
	}

	public static Integer checkthrehold(String threhold) throws ParserException {
		Integer validThrehold = null;
		if (threhold == null)
			throw new ParserException("threshold argument is empty");

		try {
			validThrehold = Integer.parseInt(threhold);
		} catch (NumberFormatException e) {
			throw new ParserException("threshold not valid");
		}

		return validThrehold;
	}

}
