package com.simulation.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
* Description:字符串处理工具类 
* @ClassName: StringUtil 
* @author Jalf
* @since 2016年6月2日 下午3:24:29 
* Copyright  foxtail All right reserved.
 */
public class StringUtil{
	
	/**
	* Description:时间字符串格式化,适应于两个timestamp相减返回值处理   
	* @Title: timeStringToFormat  
	* @author Jalf
	* @since 2016年6月2日 下午3:24:55
	* @param timeString
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String  timeStringToFormat(String timeString){
		String formatString = "";
		if(null!=timeString&&!"".equals(timeString)){
			timeString = timeString.substring(0,timeString.indexOf(".")); //去掉毫秒
			String dateString = timeString.substring(0,timeString.indexOf(" "));
			String[] subTime = timeString.substring(timeString.indexOf(" ")).split(":");
			formatString = dateString+"天"+subTime[0]+"时"+subTime[1]+"分"+subTime[2]+"秒";
		}
		return formatString;
	}

	/**
	* Description:字符串进行编码    
	* @Title: escape  
	* @author Jalf
	* @since 2016年6月2日 下午3:27:03
	* @param src
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String escape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for(int i=0; i<src.length(); i++){
			char j=src.charAt(i);
			if ((Character.isDigit(j))||(Character.isLowerCase(j))||(Character.isUpperCase(j))) {
				tmp.append(j);
			}else if (j < 'Ā') {
				tmp.append("%");
				if(j < '\020'){
					tmp.append("0");
				}
				tmp.append(Integer.toString(j, 16));
			}else{
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	public static String replaceVariable(String template,Map<String, String> map) throws Exception {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		while (regexMatcher.find()) {
			String key = regexMatcher.group(1);
			String toReplace = regexMatcher.group(0);
			String value = (String) map.get(key);
			if (value != null)
				template = template.replace(toReplace, value);
			else {
				throw new Exception(new StringBuilder().append("没有找到[").append(key).append("]对应的变量值，请检查表变量配置!").toString());
			}
		}
		return template;
	}

	/**
	* Description:字符串转对象    
	* @Title: stringToObject  
	* @author Jalf
	* @since 2016年6月2日 下午3:28:30
	* @param valueString
	* @param fieldType
	* @param format
	* @return
	* @throws Exception
	* Copyright  foxtail All right reserved.
	 */
	public static Object stringToObject(String valueString, String fieldType,String format) throws Exception {
		if(fieldType.equals("varchar")){
			return valueString;
		}else if(fieldType.equals("number")){
			return Double.parseDouble(valueString);
		}else if(fieldType.equals("date")){
			if(null != format && "".equals(format)) {
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				return sdf.parse(valueString);
			}else{
				return valueString;
			}
		}else{
			return valueString;
		}
	}

	/**
	* Description:字符全替换    
	* @Title: replaceAll  
	* @author Jalf
	* @since 2016年6月2日 下午3:29:34
	* @param toReplace
	* @param replace
	* @param replaceBy
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String replaceAll(String toReplace, String replace,String replaceBy) {
		replaceBy = replaceBy.replaceAll("\\$", "\\\\\\$");
		return toReplace.replaceAll(replace, replaceBy);
	}

	/**
	* Description:数字格式化    
	* @Title: comdify  
	* @author Jalf
	* @since 2016年6月2日 下午3:29:59
	* @param value
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String comdify(String value) {
		DecimalFormat df = null;
		if (value.indexOf(".") > 0) {
			int i = value.length() - value.indexOf(".") - 1;
			switch (i) {
			case 0:
				df = new DecimalFormat("###,##0");
				break;
			case 1:
				df = new DecimalFormat("###,##0.0");
				break;
			case 2:
				df = new DecimalFormat("###,##0.00");
				break;
			case 3:
				df = new DecimalFormat("###,##0.000");
				break;
			case 4:
				df = new DecimalFormat("###,##0.0000");
				break;
			default:
				df = new DecimalFormat("###,##0.00000");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0D;
		try {
			number = Double.parseDouble(value);
		} catch (Exception e) {
			number = 0.0D;
		}
		return df.format(number);
	}

	/**
	* Description:字符串进行解码    
	* @Title: unescape  
	* @author Jalf
	* @since 2016年6月2日 下午3:30:21
	* @param src
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		int pos = 0;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					char ch=(char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos=pos + 6;
					continue;
				}
				char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
				tmp.append(ch);
				lastPos = pos + 3;
				continue;
			}
			if (pos == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
				continue;
			}
			tmp.append(src.substring(lastPos, pos));
			lastPos = pos;
		}
		return tmp.toString();
	}

	/**
	* Description:判断先后顺序    
	* @Title: isExist  
	* @author Jalf
	* @since 2016年6月2日 下午3:31:44
	* @param content
	* @param begin
	* @param end
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isExist(String content, String begin, String end) {
		String tmp = content.toLowerCase();
		begin = begin.toLowerCase();
		end = end.toLowerCase();
		int beginIndex = tmp.indexOf(begin);
		int endIndex = tmp.indexOf(end);
		return (beginIndex != -1) && (endIndex != -1)&&(beginIndex < endIndex);
	}

	/**
	* Description:去除前端空格    
	* @Title: trimPrefix  
	* @author Jalf
	* @since 2016年6月2日 下午3:32:27
	* @param toTrim
	* @param trimStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String trimPrefix(String toTrim, String trimStr) {
		while (toTrim.startsWith(trimStr)) {
			toTrim = toTrim.substring(trimStr.length());
		}
		return toTrim;
	}

	/**
	* Description:去除后端空格    
	* @Title: trimSufffix  
	* @author Jalf
	* @since 2016年6月2日 下午3:32:46
	* @param toTrim
	* @param trimStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String trimSufffix(String toTrim, String trimStr) {
		while (toTrim.endsWith(trimStr)) {
			toTrim = toTrim.substring(0, toTrim.length() - trimStr.length());
		}
		return toTrim;
	}

	/**
	* Description:去除前后端空格    
	* @Title: trim  
	* @author Jalf
	* @since 2016年6月2日 下午3:33:06
	* @param toTrim
	* @param trimStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String trim(String toTrim, String trimStr) {
		return trimSufffix(trimPrefix(toTrim, trimStr), trimStr);
	}

	/**
	* Description:替换字符串中正则表达式转义斜杠丢失问题   
	* @Title: repRegularLostSlash  
	* @author Jalf
	* @since 2016年6月2日 下午3:33:34
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String repRegularLostSlash(String str) {
		if (str.indexOf("(d{") > 0) {
			str = str.replace("(d{", "(\\d{");
		}
		if (str.indexOf("]d)") > 0) {
			str = str.replace("]d)", "]\\d)");
		}
		if (str.indexOf(")s(") > 0) {
			str = str.replace(")s(", ")\\s(");
		}
		return str;
	}

	public static String escapeHtml(String content) {
		return StringEscapeUtils.escapeHtml(content);
	}

	public static String unescapeHtml(String content) {
		return StringEscapeUtils.unescapeHtml(content);
	}

	/**
	* Description:判断字符串是否为空   
	* @Title: isEmpty  
	* @author Jalf
	* @since 2016年6月2日 下午3:34:58
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str.trim()) || "null".equals(str)) {
			return true;
		}
		return false;
	}

	/**
	* Description:判断字符串是否不为空    
	* @Title: isNotEmpty  
	* @author Jalf
	* @since 2016年6月2日 下午3:35:33
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	* Description: 替换字符模板    
	* @Title: replaceVariable  
	* @author Jalf
	* @since 2016年6月2日 下午3:36:14
	* @param template
	* @param repaceStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String replaceVariable(String template, String repaceStr) {
		Pattern regex = Pattern.compile("\\{(.*?)\\}");
		Matcher regexMatcher = regex.matcher(template);
		if (regexMatcher.find()) {
			String toReplace = regexMatcher.group(0);
			template = template.replace(toReplace, repaceStr);
		}
		return template;
	}

	/**
	* Description:截取字符串    
	* @Title: subString  
	* @author Jalf
	* @since 2016年6月2日 下午3:36:41
	* @param str
	* @param len
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String subString(String str, int len) {
		int strLen = str.length();
		if (strLen < len){
			return str;
		}
		char[] chars = str.toCharArray();
		int cnLen = len * 2;
		String tmp = "";
		int iLen = 0;
		for (int i = 0; i < chars.length; i++) {
			int iChar = chars[i];
			if (iChar <= 128){
				iLen += 1;
			}
			else{
				iLen += 2;
			}
			if (iLen >= cnLen){
				break;
			}
			tmp = new StringBuilder().append(tmp).append(String.valueOf(chars[i])).toString();
		}
		return tmp;
	}

	public static boolean validByRegex(String regex, String input) {
		Pattern p = Pattern.compile(regex, 2);
		Matcher regexMatcher = p.matcher(input);
		return regexMatcher.find();
	}

	/**
	* Description:判断是否是数字类型    
	* @Title: isNumeric  
	* @author Jalf
	* @since 2016年6月2日 下午3:38:55
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isNumeric(String str) {
		int i = str.length();
		while (true) {
			i--;
			if (i < 0)
				break;
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	* Description:首字母大写    
	* @Title: makeFirstLetterUpperCase  
	* @author Jalf
	* @since 2016年6月2日 下午3:39:30
	* @param newStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String makeFirstLetterUpperCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		}
		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return new StringBuilder().append(firstChar.toUpperCase()).append(newStr.substring(1)).toString();
	}

	/**
	* Description:首字母小写    
	* @Title: makeFirstLetterLowerCase  
	* @author Jalf
	* @since 2016年6月2日 下午3:39:51
	* @param newStr
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String makeFirstLetterLowerCase(String newStr) {
		if (newStr.length() == 0) {
			return newStr;
		}
		char[] oneChar = new char[1];
		oneChar[0] = newStr.charAt(0);
		String firstChar = new String(oneChar);
		return new StringBuilder().append(firstChar.toLowerCase()).append(newStr.substring(1)).toString();
	}

	/**
	* Description:格式化    
	* @Title: formatParamMsg  
	* @author Jalf
	* @since 2016年6月2日 下午3:40:25
	* @param message
	* @param args
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String formatParamMsg(String message, Object[] args) {
		for (int i = 0; i < args.length; i++) {
			message = message.replace(new StringBuilder().append("{").append(i).append("}").toString(), args[i].toString());
		}
		return message;
	}

	/**
	* Description:格式化参数形式消息    
	* @Title: formatParamMsg  
	* @author Jalf
	* @since 2016年6月2日 下午3:40:52
	* @param message
	* @param params
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String formatParamMsg(String message, Map params) {
		if (params == null)
			return message;
		Iterator keyIts = params.keySet().iterator();
		while (keyIts.hasNext()) {
			String key = (String) keyIts.next();
			Object val = params.get(key);
			if (val != null) {
				message = message.replace(new StringBuilder().append("${").append(key).append("}").toString(), val.toString());
			}
		}
		return message;
	}

	public static StringBuilder formatMsg(CharSequence msgWithFormat,boolean autoQuote, Object[] args) {
		int argsLen = args.length;
		boolean markFound = false;
		StringBuilder sb = new StringBuilder(msgWithFormat);
		if (argsLen > 0) {
			for (int i = 0; i < argsLen; i++) {
				String flag = new StringBuilder().append("%").append(i + 1).toString();
				int idx = sb.indexOf(flag);
				while (idx >= 0) {
					markFound = true;
					sb.replace(idx, idx + 2, toString(args[i], autoQuote));
					idx = sb.indexOf(flag);
				}
			}
			if ((args[(argsLen - 1)] instanceof Throwable)) {
				StringWriter sw = new StringWriter();
				((Throwable) args[(argsLen - 1)]).printStackTrace(new PrintWriter(sw));
				sb.append("\n").append(sw.toString());
			} else if ((argsLen == 1) && (!markFound)) {
				sb.append(args[(argsLen - 1)].toString());
			}
		}
		return sb;
	}

	public static StringBuilder formatMsg(String msgWithFormat, Object[] args) {
		return formatMsg(new StringBuilder(msgWithFormat), true, args);
	}

	public static String toString(Object obj, boolean autoQuote) {
		StringBuilder sb = new StringBuilder();
		if (obj == null) {
			sb.append("NULL");
		} else if ((obj instanceof Object[])) {
			for (int i = 0; i < ((Object[]) (Object[]) obj).length; i++) {
				sb.append(((Object[]) (Object[]) obj)[i]).append(", ");
			}
			if (sb.length() > 0)
				sb.delete(sb.length() - 2, sb.length());
		} else {
			sb.append(obj.toString());
		}
		if ((autoQuote)&& (sb.length() > 0)&& ((sb.charAt(0) != '[') || (sb.charAt(sb.length() - 1) != ']'))
				&& ((sb.charAt(0) != '{') || (sb.charAt(sb.length() - 1) != '}'))) {
			sb.insert(0, "[").append("]");
		}
		return sb.toString();
	}

	public static String returnSpace(String str) {
		String space = "";
		if (!str.isEmpty()) {
			String[] path = str.split("\\.");
			for (int i = 0; i < path.length - 1; i++) {
				space = new StringBuilder().append(space).append("&nbsp;&emsp;").toString();
			}
		}
		return space;
	}

	public static synchronized String encryptSha256(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] digest = md.digest(inputStr.getBytes("UTF-8"));
			return new String(Base64.encodeBase64(digest));
		} catch (Exception e) {
		}
		return null;
	}

	public static synchronized String encryptMd5(String inputStr) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputStr.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString(b & 0xFF));
			}
			return sb.toString();
		} catch (Exception e) {
		}
		return null;
	}

	public static String getArrayAsString(List<String> arr) {
		if ((arr == null) || (arr.size() == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.size(); i++) {
			if (i > 0)
				sb.append(",");
			sb.append((String) arr.get(i));
		}
		return sb.toString();
	}

	public static String getArrayAsString(String[] arr) {
		if ((arr == null) || (arr.length == 0))
			return "";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0)
				sb.append(",");
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	public static String getSetAsString(Set set) {
		if ((set == null) || (set.size() == 0)){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		int i = 0;
		Iterator it = set.iterator();
		while (it.hasNext()) {
			if (i++ > 0){
				sb.append(",");
			}
			sb.append(it.next().toString());
		}
		return sb.toString();
	}

	/**
	* Description:数值转换成大写    
	* @Title: hangeToBig  
	* @author Jalf
	* @since 2016年6月2日 下午3:44:17
	* @param value
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String hangeToBig(double value) {
		char[] hunit = { '拾', '佰', '仟' };
		char[] vunit = { '万', '亿' };
		char[] digit = { 38646, '壹', 36144, '叁', 32902, '伍', 38470, '柒', '捌','玖' };
		long midVal = (long) (value * 100.0D);
		String valStr = String.valueOf(midVal);
		String head = valStr.substring(0, valStr.length() - 2);
		String rail = valStr.substring(valStr.length() - 2);
		String prefix = "";
		String suffix = "";
		if (rail.equals("00")) {
			suffix = "整";
		} else {
			suffix = new StringBuilder().append(digit[(rail.charAt(0) - '0')])
					.append("角").append(digit[(rail.charAt(1) - '0')])
					.append("分").toString();
		}
		char[] chDig = head.toCharArray();
		char zero = '0';
		byte zeroSerNum = 0;
		for (int i = 0; i < chDig.length; i++) {
			int idx = (chDig.length - i - 1) % 4;
			int vidx = (chDig.length - i - 1) / 4;
			if (chDig[i] == '0') {
				zeroSerNum = (byte) (zeroSerNum + 1);
				if (zero == '0') {
					zero = digit[0];
				}
				if ((idx != 0) || (vidx <= 0) || (zeroSerNum >= 4))
					continue;
				prefix = new StringBuilder().append(prefix).append(vunit[(vidx - 1)]).toString();
				zero = '0';
			} else {
				zeroSerNum = 0;
				if (zero != '0') {
					prefix = new StringBuilder().append(prefix).append(zero).toString();
					zero = '0';
				}
				prefix = new StringBuilder().append(prefix).append(digit[(chDig[i] - '0')]).toString();
				if (idx > 0){
					prefix = new StringBuilder().append(prefix).append(hunit[(idx - 1)]).toString();
				}
				if ((idx != 0) || (vidx <= 0)){
					continue;
				}
				prefix = new StringBuilder().append(prefix).append(vunit[(vidx - 1)]).toString();
			}
		}
		if (prefix.length() > 0){
			prefix = new StringBuilder().append(prefix).append('圆').toString();
		}
		return new StringBuilder().append(prefix).append(suffix).toString();
	}

	public static String jsonUnescape(String str) {
		return str.replace("&quot;", "\"").replace("&nuot;", "\n");
	}

	public static String htmlEntityToString(String dataStr) {
		dataStr = dataStr.replace("&apos;", "'").replace("&quot;", "\"")
				.replace("&gt;", ">").replace("&lt;", "<")
				.replace("&amp;", "&");

		int start = 0;
		int end = 0;
		StringBuffer buffer = new StringBuffer();
		while (start > -1) {
			int system = 10;
			if (start == 0) {
				int t = dataStr.indexOf("&#");
				if (start != t) {
					start = t;
				}
				if (start > 0) {
					buffer.append(dataStr.substring(0, start));
				}
			}
			end = dataStr.indexOf(";", start + 2);
			String charStr = "";
			if (end != -1) {
				charStr = dataStr.substring(start + 2, end);
				char s = charStr.charAt(0);
				if ((s == 'x') || (s == 'X')) {
					system = 16;
					charStr = charStr.substring(1);
				}
			}
			try {
				char letter = (char) Integer.parseInt(charStr, system);
				buffer.append(new Character(letter).toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			start = dataStr.indexOf("&#", end);
			if (start - end > 1) {
				buffer.append(dataStr.substring(end + 1, start));
			}
			if (start == -1) {
				int length = dataStr.length();
				if (end + 1 != length) {
					buffer.append(dataStr.substring(end + 1, length));
				}
			}
		}
		return buffer.toString();
	}

	/**
	* Description:字符串转html转义    
	* @Title: stringToHtmlEntity  
	* @author Jalf
	* @since 2016年6月2日 下午3:47:10
	* @param str
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String stringToHtmlEntity(String str) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			switch (c) {
			case '\n':
				sb.append(c);
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '&':
				sb.append("&amp;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			default:
				if ((c < ' ') || (c > '~')) {
					sb.append("&#x");
					sb.append(Integer.toString(c, 16));
					sb.append(';');
				} else {
					sb.append(c);
				}
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		int a = 30019;
		System.out.println(number2uper(a));
	}

	public static String encodingString(String str, String from, String to) {
		String result = str;
		try {
			result = new String(str.getBytes(from), to);
		} catch (Exception e) {
			result = str;
		}
		return result;
	}

	public static String convertScriptLine(String arg, Boolean flag) {
		if (StringUtils.isEmpty(arg))
			return arg;
		String origStr = "\n";
		String targStr = "/n";
		if (!flag.booleanValue()) {
			origStr = "/n";
			targStr = "\n";
		}
		String[] args = arg.split(origStr);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(args[i]);
			if (args.length != i + 1)
				sb.append(targStr);
		}
		return sb.toString();
	}

	public static String convertLine(String arg, Boolean flag) {
		if (StringUtils.isEmpty(arg))
			return arg;
		String origStr = "\n";
		String targStr = "/n";
		if (!flag.booleanValue()) {
			origStr = "/n";
			targStr = "\n";
		}
		String[] args = arg.split(origStr);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			sb.append(StringUtils.deleteWhitespace(args[i]));
			if (args.length != i + 1)
				sb.append(targStr);
		}
		return sb.toString();
	}

	/**
	* Description:数字前面补0    
	* @Title: frontCompWithZore  
	* @author Jalf
	* @since 2016年6月2日 下午3:48:29
	* @param sourceNumber
	* @param formatLength
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String frontCompWithZore(int sourceNumber, int formatLength) {
		String newString = String.format("%0" + formatLength + "d",
				sourceNumber);
		return newString;
	}

	/**
	* Description:数字转中文    
	* @Title: number2uper  
	* @author Jalf
	* @since 2016年6月2日 下午3:48:45
	* @param number
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String number2uper(int number) {
		Double n = Double.parseDouble(number + "");
		String fraction[] = { "角", "分" };
		String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String unit[][] = { { "", "万", "亿" }, { "", "拾", "佰", "仟" } };

		String head = n < 0 ? "负" : "";
		n = Math.abs(n);

		String s = "";
		for (int i = 0; i < fraction.length; i++) {
			s += (digit[(int) (Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i])
					.replaceAll("(零.)+", "");
		}
		if (s.length() < 1) {
			// s = "整";
		}
		int integerPart = (int) Math.floor(n);

		for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
			String p = "";
			for (int j = 0; j < unit[1].length && n > 0; j++) {
				p = digit[integerPart % 10] + unit[1][j] + p;
				integerPart = integerPart / 10;
			}
			s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i]
					+ s;
		}
		// return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+",
		// "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
		return head
				+ s.replaceAll("(零.)*零元", "").replaceFirst("(零.)+", "")
						.replaceAll("(零.)+", "零").replaceAll("^整$", "");
	}

	// 阿拉伯数字转中文小写？
	public static String transitionAlb(int num) {
		String str = String.valueOf(num);
		StringBuffer result = new StringBuffer();
		String[] bb = { "O", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		// 处理两位数的
		if (str.length() == 2) {
			String temp0 = String.valueOf(str.charAt(0));
			String temp1 = String.valueOf(str.charAt(1));
			if (!temp0.equals("1")) {
				result.append(bb[Integer.parseInt(temp0)]);
			}
			result.append("十");
			if (!temp1.equals("0")) {
				result.append(bb[Integer.parseInt(temp1)]);
			}
		} else {
			for (int i = 0; i < str.length(); i++) {
				String index = str.charAt(i) + "";
				result.append(bb[Integer.parseInt(index)]);

			}
		}

		return result.toString();
	}

}