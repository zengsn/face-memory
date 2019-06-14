package com.gdp.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件上传工具类
 *
 * @author Jashon
 * @since 2018-07-26
 */
public class FileUploadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);

	/**
	 * 上传照片到服务器
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static Map<String, Object> fileUpload(HttpServletRequest request) throws UnsupportedEncodingException{
    	
    	String openid = AESUtil.decrypt(request.getHeader("token"), "openid");
    	
    	request.setCharacterEncoding("utf-8");  // 设置编码
    	HashMap<String, Object> map = new HashMap<String, Object>();
    	String value = "";	// 存放 wxid
    	
    	// 获取文件需要上传到的本地路径
        String path = RealPathUtils.uploadsPath;
        logger.info("path: " + path);
        
        // 获得磁盘文件条目工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        // 高水平的API文件上传处理
        ServletFileUpload upload = new ServletFileUpload(factory);

        List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e1) {
			e1.printStackTrace();
        	map.put("isSuccess", false);
		}
        FileItem picture = null;
        for (FileItem item : list) {
            //获取表单的属性名字
            String name = item.getFieldName();
            //如果获取的表单信息是普通的 文本 信息
            if (item.isFormField()) {
                //获取用户具体输入的字符串
                value = item.getString();
                request.setAttribute(name, value);
                logger.info("\n - name 和 value: " + name + "-" + value);
//                path = path + value + "/";
            } else {
                picture = item;
            }
        }
        
        // 从请求头中取出 openid 
        path = path + openid + "/";
    	File dir = new File(path);
        // 判断目标文件夹是否存在，若不存在则新建
        if (!dir.exists()) {
            dir.mkdir();
        }

        // 如果没以下两行设置的话,上传大的文件会占用很多内存，
        // 设置暂时存放的存储室,这个存储室可以和最终存储文件的目录不同
        /**
         * 原理: 它是先存到暂时存储室，然后再真正写到对应目录的硬盘上，
         * 按理来说当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的
         * 然后再将其真正写到对应目录的硬盘上
         */
        factory.setRepository(dir);
        // 设置缓存的大小，当上传文件的容量超过该缓存时，直接放到暂时存储室
        factory.setSizeThreshold(1024 * 1024);


        // 自定义上传图片的名字为 年月日时分秒
        String originalFileName = picture.getName();
        String pix = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = FileUploadUtil.getNewFileName(pix);
        
        String destPath = path + fileName;

        // 写到的本地磁盘的位置
//        logger.info("[FileUploadUtil.fileUpload]\n ---> destpath: " + destPath);
//	            logger.debug("destPath=" + destPath);

        // 真正写到磁盘上
        File file = new File(destPath);
        try {
	        OutputStream out = new FileOutputStream(file);
	        InputStream in = picture.getInputStream();
	        int length = 0;
	        byte[] buf = new byte[1024];
	
	        // in.read(buf) 每次读到的数据存放在buf 数组中
	        while ((length = in.read(buf)) != -1) {
	            // 在buf数组中取出数据写到（输出流）磁盘上
	            out.write(buf, 0, length);
	        }
	        in.close();
	        out.close();
	        map.put("isSuccess", true);
	        map.put("picPath", openid + "/" + fileName);
	        map.put("openid", openid);
        } catch (Exception e) {
        	map.put("isSuccess", false);
        	e.printStackTrace();
        }
		return map;
        
    }

    /**
     * 获取新的文件名<br/>
     * 格式: 年月日时分.后缀
     * 
     * @param pix
     * @return
     */
	public static String getNewFileName(String pix) {
		Calendar date = Calendar.getInstance();
		
        StringBuilder newFileName = new StringBuilder();
        newFileName.append(date.get(Calendar.YEAR));
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR_OF_DAY);
        int minute = date.get(Calendar.MINUTE);
        if(month < 10) {
        	newFileName.append(0);
        }
        newFileName.append(month);
        
        if(day < 10) {
        	newFileName.append(0);
        }
        newFileName.append(day);
        
        if(hour < 10) {
        	newFileName.append(0);
        }
        newFileName.append(hour);
        
        if(minute < 10) {
        	newFileName.append(0);
        }
        newFileName.append(minute);
        newFileName.append(pix);
        
		return newFileName.toString();
	}

	/**
	 * 删除指定路径的文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		boolean isDeleted = file.delete();

		return isDeleted;
	}
}
