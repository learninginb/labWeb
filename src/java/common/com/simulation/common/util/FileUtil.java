package com.simulation.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;


/**
* Description:文件操作工具类 
* @ClassName: FileUtil 
* @author Jalf
* @since 2016年6月2日 下午2:58:11 
* Copyright  foxtail All right reserved.
 */
public class FileUtil {

	/**
	* Description:将字符串写入文件    
	* @Title: writeFile  
	* @author Jalf
	* @since 2016年6月2日 下午2:58:38
	* @param fileName
	* @param content
	* Copyright  foxtail All right reserved.
	 */
	public static void writeFile(String fileName, String content) {
		try {
			fileName = URLDecoder.decode(fileName,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writeFile(fileName, content, "UTF-8");
	}

	/**
	* Description:将字符串写文件，指定编码    
	* @Title: writeFile  
	* @author Jalf
	* @since 2016年6月2日 下午2:58:54
	* @param fileName
	* @param content
	* @param charset
	* Copyright  foxtail All right reserved.
	 */
	public static void writeFile(String fileName, String content, String charset) {
		try {
			createFolder(fileName, true);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), charset));
			out.write(content);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Description:将文件流写入文件    
	* @Title: writeFile  
	* @author Jalf
	* @since 2016年6月2日 下午2:59:14
	* @param fileName
	* @param is
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static void writeFile(String fileName, InputStream is)throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		byte[] bs = new byte[512];
		int n = 0;
		while ((n = is.read(bs)) != -1) {
			fos.write(bs, 0, n);
		}
		
		is.close();
		fos.close();
	}

	/**
	* Description:将文件读取成字符串输出    
	* @Title: readFile  
	* @author Jalf
	* @since 2016年6月2日 下午2:59:27
	* @param fileName
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String readFile(String fileName) {
		try {
			File file = new File(fileName);
			String charset = getCharset(file);
			StringBuffer sb = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), charset));
			String str;
			while ((str = in.readLine()) != null) {
				sb.append(str + "\r\n");
			}
			in.close();
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	* Description:判断是否存在文件    
	* @Title: isExistFile  
	* @author Jalf
	* @since 2016年6月2日 下午2:59:48
	* @param dir
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean isExistFile(String dir) {
		boolean isExist = false;
		File fileDir = new File(dir);
		if (fileDir.isDirectory()) {
			File[] files = fileDir.listFiles();
			if ((files != null) && (files.length != 0)) {
				isExist = true;
			}
		}
		return isExist;
	}

	/**
	* Description:获取文件字符编码    
	* @Title: getCharset  
	* @author Jalf
	* @since 2016年6月2日 下午3:00:07
	* @param file
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getCharset(File file) {
		String charset = "GBK";
		BufferedInputStream bis = null;
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if ((first3Bytes[0] == -1) && (first3Bytes[1] == -2)) {
				charset = "UTF-16LE";
				checked = true;
			} else if ((first3Bytes[0] == -2) && (first3Bytes[1] == -1)) {
				charset = "UTF-16BE";
				checked = true;
			} else if ((first3Bytes[0] == -17) && (first3Bytes[1] == -69) && (first3Bytes[2] == -65)) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();
			if (!checked) {
				while ((read = bis.read()) != -1) {
					if (read >= 240) {
						break;
					}
					if ((128 <= read) && (read <= 191))
						break;
					if ((192 <= read) && (read <= 223)) {
						read = bis.read();
						if ((128 > read) || (read > 191)) {
							break;
						}
						continue;
					} else if ((224 <= read) && (read <= 239)) {
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						read = bis.read();
						if ((128 > read) || (read > 191))
							break;
						charset = "UTF-8";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return charset;
	}

	/**
	* Description:将文件流读取成字节    
	* @Title: readByte  
	* @author Jalf
	* @since 2016年6月2日 下午3:00:33
	* @param is
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static byte[] readByte(InputStream is) {
		try {
			byte[] r = new byte[is.available()];
			is.read(r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* Description:将文件读取成字节    
	* @Title: readByte  
	* @author Jalf
	* @since 2016年6月2日 下午3:00:58
	* @param fileName
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static byte[] readByte(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] r = new byte[fis.available()];
			fis.read(r);
			fis.close();
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* Description:将字节写入文件    
	* @Title: writeByte  
	* @author Jalf
	* @since 2016年6月2日 下午3:01:18
	* @param fileName
	* @param b
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean writeByte(String fileName, byte[] b) {
		try {
			BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(fileName));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	* Description:删除文件及所在子目录    
	* @Title: deleteDir  
	* @author Jalf
	* @since 2016年6月2日 下午3:01:55
	* @param dir
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}

	/**
	* Description:序列化文件为对象    
	* @Title: serializeToFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:02:10
	* @param obj
	* @param fileName
	* Copyright  foxtail All right reserved.
	 */
	public static void serializeToFile(Object obj, String fileName) {
		try {
			ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName));
			out.writeObject(obj);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* Description:反序列化文件    
	* @Title: deserializeFromFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:02:24
	* @param fileName
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static Object deserializeFromFile(String fileName) {
		try {
			File file = new File(fileName);
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			Object obj = in.readObject();
			in.close();
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	* Description:将字节写入文件    
	* @Title: writeByte  
	* @author Jalf
	* @since 2016年6月2日 下午3:02:38
	* @param filePath
	* @param fileName
	* @param b
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean writeByte(String filePath, String fileName, byte[] b) {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				f.mkdirs();
			}
			// fileName表示你创建的文件名；为txt类型；
			File file = new File(f, fileName);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			BufferedOutputStream fos = new BufferedOutputStream(
					new FileOutputStream(file));
			fos.write(b);
			fos.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	* Description:输入流转成字符串    
	* @Title: inputStream2String  
	* @author Jalf
	* @since 2016年6月2日 下午3:02:54
	* @param input
	* @param charset
	* @return
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static String inputStream2String(InputStream input, String charset) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();
	}

	/**
	* Description:输入流转换成字符串 默认utf-8编码    
	* @Title: inputStream2String  
	* @author Jalf
	* @since 2016年6月2日 下午3:03:42
	* @param input
	* @return
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static String inputStream2String(InputStream input)
			throws IOException {
		return inputStream2String(input, "utf-8");
	}

	/**
	* Description:获取文件列表    
	* @Title: getFiles  
	* @author Jalf
	* @since 2016年6月2日 下午3:03:59
	* @param path
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static File[] getFiles(String path) {
		File file = new File(path);
		return file.listFiles();
	}

	/**
	* Description:创建目录    
	* @Title: createFolderFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:04:11
	* @param path
	* Copyright  foxtail All right reserved.
	 */
	public static void createFolderFile(String path) {
		createFolder(path, true);
	}

	/**
	* Description:创建目录,创建前先检查path是否为路径还是文件    
	* @Title: createFolder  
	* @author Jalf
	* @since 2016年6月2日 下午3:04:26
	* @param path
	* @param isFile
	* Copyright  foxtail All right reserved.
	 */
	public static void createFolder(String path, boolean isFile) {
		if (isFile) {
			if(path.lastIndexOf(File.separator)>0){
				path = path.substring(0, path.lastIndexOf(File.separator));
			}else if(path.lastIndexOf("/")>0){
				path = path.substring(0, path.lastIndexOf("/"));
			}
		}
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
	}

	/**
	* Description:创建文件及目录    
	* @Title: createFolder  
	* @author Jalf
	* @since 2016年6月2日 下午3:04:41
	* @param dirstr
	* @param name
	* Copyright  foxtail All right reserved.
	 */
	public static void createFolder(String dirstr, String name) {
		dirstr = StringUtil.trimSufffix(dirstr, File.separator)
				+ File.separator + name;
		File dir = new File(dirstr);
		dir.mkdir();
	}

	/**
	* Description:重命名目录    
	* @Title: renameFolder  
	* @author Jalf
	* @since 2016年6月2日 下午3:04:57
	* @param path
	* @param newName
	* Copyright  foxtail All right reserved.
	 */
	public static void renameFolder(String path, String newName) {
		File file = new File(path);
		if (file.exists())
			file.renameTo(new File(newName));
	}

	/**
	* Description: 获取文件目录   
	* @Title: getDiretoryOnly  
	* @author Jalf
	* @since 2016年6月2日 下午3:06:03
	* @param dir
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static ArrayList<File> getDiretoryOnly(File dir) {
		ArrayList<File> dirs = new ArrayList<File>();
		if ((dir != null) && (dir.exists()) && (dir.isDirectory())) {
			File[] files = dir.listFiles(new FileFilter() {
				public boolean accept(File file) {
					return file.isDirectory();
				}
			});
			for (int i = 0; i < files.length; i++) {
				dirs.add(files[i]);
			}
		}
		return dirs;
	}

	/**
	* Description: 获取文件目录    
	* @Title: getFileOnly  
	* @author Jalf
	* @since 2016年6月2日 下午3:06:41
	* @param dir
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public ArrayList<File> getFileOnly(File dir) {
		ArrayList<File> dirs = new ArrayList<File>();
		File[] files = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		for (int i = 0; i < files.length; i++) {
			dirs.add(files[i]);
		}
		return dirs;
	}

	/**
	* Description:删除文件   
	* @Title: deleteFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:06:55
	* @param path
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean deleteFile(String path) {
		File file = new File(path);
		return file.delete();
	}

	/**
	* Description:复制文件      
	* @Title: copyFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:21:32
	* @param from
	* @param to
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean copyFile(String from, String to) {
		File fromFile = new File(from);
		File toFile = new File(to);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(fromFile);
			fos = new FileOutputStream(toFile);

			byte[] buf = new byte[4096];
			int bytesRead;
			while ((bytesRead = fis.read(buf)) != -1) {
				fos.write(buf, 0, bytesRead);
			}
			fos.flush();
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	* Description:备份文件    
	* @Title: backupFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:21:09
	* @param filePath
	* Copyright  foxtail All right reserved.
	 */
	public static void backupFile(String filePath) {
		String backupName = filePath + ".bak";
		File file = new File(backupName);
		if (file.exists()) {
			file.delete();
		}
		copyFile(filePath, backupName);
	}

	/**
	* Description:获取文件的扩展名 如txt    
	* @Title: getFileExt  
	* @author Jalf
	* @since 2016年6月2日 下午3:20:48
	* @param file
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getFileExt(File file) {
		if (file.isFile()) {
			return getFileExt(file.getName());
		}
		return "";
	}

	/**
	* Description:获取文件的扩展名 如txt    
	* @Title: getFileExt  
	* @author Jalf
	* @since 2016年6月2日 下午3:20:13
	* @param fileName
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getFileExt(String fileName) {
		int pos = fileName.lastIndexOf(".");
		if (pos > -1) {
			return fileName.substring(pos + 1).toLowerCase();
		}
		return "";
	}

	/**
	* Description:复制目录    
	* @Title: copyDir  
	* @author Jalf
	* @since 2016年6月2日 下午3:19:57
	* @param fromDir
	* @param toDir
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static void copyDir(String fromDir, String toDir) throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromFile = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();

				copyFile(fromFile, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/"
						+ file[i].getName());
		}
	}

	/**
	* Description:复制目录    
	* @Title: copyDirectiory  
	* @author Jalf
	* @since 2016年6月2日 下午3:19:40
	* @param fromDir
	* @param toDir
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	private static void copyDirectiory(String fromDir, String toDir)
			throws IOException {
		new File(toDir).mkdirs();
		File[] file = new File(fromDir).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				String fromName = file[i].getAbsolutePath();
				String toFile = toDir + "/" + file[i].getName();
				copyFile(fromName, toFile);
			}
			if (file[i].isDirectory())
				copyDirectiory(fromDir + "/" + file[i].getName(), toDir + "/"+ file[i].getName());
		}
	}

	/**
	* Description:获取文件大小    
	* @Title: getFileSize  
	* @author Jalf
	* @since 2016年6月2日 下午3:19:06
	* @param file
	* @return
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static String getFileSize(File file) throws IOException {
		if (file.isFile()) {
			FileInputStream fis = new FileInputStream(file);
			int size = fis.available();
			fis.close();
			return getSize(size);
		}
		return "";
	}

	/**
	* Description:计算文件的大小，返回M或者KB或者bytes    
	* @Title: getSize  
	* @author Jalf
	* @since 2016年6月2日 下午3:18:53
	* @param size
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getSize(double size) {
		DecimalFormat df = new DecimalFormat("0.00");
		if (size > 1048576.0D) {
			double ss = size / 1048576.0D;
			return df.format(ss) + " M";
		}
		if (size > 1024.0D) {
			double ss = size / 1024.0D;
			return df.format(ss) + " KB";
		}
		return size + " bytes";
	}

	/**
	* Description:下载文件    
	* @Title: downLoadFile  
	* @author Jalf
	* @since 2016年6月2日 下午3:14:31
	* @param response
	* @param fullPath
	* @param fileName
	* @throws IOException
	* Copyright  foxtail All right reserved.
	 */
	public static void downLoadFile(HttpServletResponse response,String fullPath, String fileName) throws IOException {
		OutputStream outp = response.getOutputStream();
		File file = new File(fullPath);
		if (file.exists()) {
			response.setContentType("APPLICATION/OCTET-STREAM");
			String filedisplay = URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="+ filedisplay);
			FileInputStream in = null;
			try {
				outp = response.getOutputStream();
				in = new FileInputStream(fullPath);
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = in.read(b)) > 0) {
					outp.write(b, 0, i);
				}
				outp.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					in.close();
					in = null;
				}
				if (outp != null) {
					outp.close();
					outp = null;
					response.flushBuffer();
				}
			}
		} else {
			outp.write("文件不存在!".getBytes("utf-8"));
		}
	}

	/**
	* Description:获取父目录    
	* @Title: getParentDir  
	* @author Jalf
	* @since 2016年6月2日 下午3:13:19
	* @param baseDir
	* @param currentFile
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String getParentDir(String baseDir, String currentFile) {
		File f = new File(currentFile);
		String parentPath = f.getParent();
		String path = parentPath.replace(baseDir, "");
		return path.replace(File.separator, "/");
	}

	/**
	* Description:读取propeties文件中key值    
	* @Title: readFromProperties  
	* @author Jalf
	* @since 2016年6月2日 下午3:12:40
	* @param fileName
	* @param key
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static String readFromProperties(String fileName, String key) {
		String value = "";
		InputStream stream = null;
		try {
			stream = new BufferedInputStream(new FileInputStream(fileName));
			Properties prop = new Properties();
			prop.load(stream);
			value = prop.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	* Description:保存 propeties文件    
	* @Title: saveProperties  
	* @author Jalf
	* @since 2016年6月2日 下午3:10:41
	* @param fileName
	* @param key
	* @param value
	* @return
	* Copyright  foxtail All right reserved.
	 */
	public static boolean saveProperties(String fileName, String key,String value) {
		StringBuffer sb = new StringBuffer();
		boolean isFound = false;
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
			String str;
			while ((str = in.readLine()) != null) {
				if (str.startsWith(key)) {
					sb.append(key + "=" + value + "\r\n");
					isFound = true;
					continue;
				}
				sb.append(str + "\r\n");
			}
			if (!isFound) {
				sb.append(key + "=" + value + "\r\n");
			}
			writeFile(fileName, sb.toString(), "utf-8");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
			if (in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		FileUtil.writeFile("D:\\test\\diagrams\\dd.txt", "dddddddddddddddddddddddddddd");
	}
}
