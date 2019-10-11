package com.suntak.eightdisciplines.ExcelUtil;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class StringUtils {

	
	
	
	public static String getUseTime(String recordDate ,String finishDate){
		String usetime = "";
		if(!"".equals(finishDate.trim()) && finishDate != null){ 
			String recorddate1 = recordDate.substring(11,16);
			String finishdate1 = finishDate.substring(11,16); 
			int hours = Integer.parseInt(recorddate1.substring(0, 2));
			int houre = Integer.parseInt(finishdate1.substring(0, 2));
			if (hours > houre) {
				Calendar cal1 = Calendar.getInstance();
				cal1.add(Calendar.DATE, -1);
				cal1.set(Calendar.HOUR,
						Integer.parseInt(recorddate1.substring(0, 2)));
				cal1.set(Calendar.MINUTE,
						Integer.parseInt(recorddate1.substring(3, 5)));
				cal1.set(Calendar.SECOND,0);
				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.HOUR,
						Integer.parseInt(finishdate1.substring(0, 2)));
				cal2.set(Calendar.MINUTE,
						Integer.parseInt(finishdate1.substring(3, 5)));
				cal2.set(Calendar.SECOND,0);
				long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();
				usetime = new Long(l / (1000 * 60)).toString();
			} else {
				Calendar cal1 = Calendar.getInstance();
				cal1.set(Calendar.HOUR,
						Integer.parseInt(recorddate1.substring(0, 2)));
				cal1.set(Calendar.MINUTE,
						Integer.parseInt(recorddate1.substring(3, 5)));
				cal1.set(Calendar.SECOND,0);
				Calendar cal2 = Calendar.getInstance();
				cal2.set(Calendar.HOUR,
						Integer.parseInt(finishdate1.substring(0, 2)));
				cal2.set(Calendar.MINUTE,
						Integer.parseInt(finishdate1.substring(3, 5)));
				cal2.set(Calendar.SECOND,0);
				long l = cal2.getTimeInMillis() - cal1.getTimeInMillis();
				usetime = new Long(l / (1000 * 60)).toString();
			} 
		}  
		return usetime;
	}
	
	public static final String getIpAddr(final HttpServletRequest request)
			throws Exception {
		if (request == null) {
			throw (new Exception(
					"getIpAddr method HttpServletRequest Object is null"));
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;

	}

	public static final String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec("nbtstat -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public static int searchIndex(String[] data, String string) {
		if (data == null || data.length == 0)
			return -1;
		for (int i = 0; i < data.length; i++) {
			if ((data[i] != null && data[i].equals(string))
					|| (data[i] == null && string == null)) {
				return i;
			}
		}
		return -1;
	}

	public static boolean isNull(String string) {
		if (string == null || string.equals(""))
			return true;
		return false;
	}

	public static String arrayToString(List<String> list, String split) {
		if (list == null || list.size() == 0)
			return null;

		String result = "";
		if (isNull(split))
			split = ",";

		for (String str : list) {
			result += str + split;
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public static String arrayToString(String[] list, String split) {
		if (list == null || list.length == 0)
			return null;

		String result = "";
		if (isNull(split))
			split = ",";

		for (String str : list) {
			result += str + split;
		}
		result = result.substring(0, result.length() - 1);
		return result;
	}

	public static String getFileName(String name) {
		if (isNull(name))
			return null;

		if (name.lastIndexOf("\\") != -1) {
			name = name.substring(name.lastIndexOf("\\") + 1, name.length());
		} else if (name.lastIndexOf("/") != -1) {
			name = name.substring(name.lastIndexOf("/") + 1, name.length());
		}

		return name;
	}

	public static String getExtensionName(String name) {
		if (isNull(name))
			return null;
		if (name.lastIndexOf(".") != -1) {
			return name.substring(name.lastIndexOf(".") + 1);
		}
		return "";
	}

	public static void main(String[] arg0) {
		String a = "${bean.aid} sdfad ${xx.yy}sfdd";

		String regex = "";

		search(regex, a);
	}

	public static String getUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static List<String> search(String regex, String string) {
		List<String> list = new ArrayList<String>();
		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(string);
		while (m.find()) {
			String g = m.group();
			list.add(g);
		}
		return list;
	}
}
