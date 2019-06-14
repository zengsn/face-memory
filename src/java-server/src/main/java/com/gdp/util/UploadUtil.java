package com.gdp.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
	
	private static Logger logger = LoggerFactory.getLogger(UploadUtil.class);

	/**
	 * 上传图片
	 * 
	 * @param picture	图片文件
	 * @param pathname	保存的文件路径
	 * @return
	 */
	public static boolean upload(MultipartFile picture, String pathname) {
		boolean res = false;
		
		File distFile = new File(pathname);
		// 判断目标文件所在目录是否存在
        if( !distFile.getParentFile().exists()) {
            // 如果目标文件所在的目录不存在，则创建父目录
        	distFile.getParentFile().mkdirs();
        }
        logger.info(">>> 保存的文件路径为: " + pathname);
        // 将内存中的数据写入磁盘
        try {
			picture.transferTo(distFile);
			res = true;
		} catch (IllegalStateException e) {
			logger.error(">>> 写入文件状态异常: {}", e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(">>> IO异常: {}", e.getMessage());
			e.printStackTrace();
		}
        
		return res;	
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
	
}
