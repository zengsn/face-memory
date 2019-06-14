package com.gdp.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

public class ImageZipUtil {

	private static Logger logger = LoggerFactory.getLogger(ImageZipUtil.class);
	
	/**
	 * <p>Title: thumbnailImage</p>
	 * <p>Description: 根据图片路径生成缩略图 </p>
	 * @param imgPath	原图片路径
	 * @param outImg	输出照片路径
	 * @param format	
	 * @param w			缩略图宽
	 * @param h			缩略图高
	 * @param force		是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
	 * @return
	 */
	public static void thumbnailImage(String imgPath, String outImg, String format, int w, int h, boolean force) {
		
		logger.info("压缩照片： imgPath:" + imgPath);
		logger.info("压缩照片： outImg:" + outImg);
		
		File imgFile = new File(imgPath); 	// 原始照片路径
		File file = null;					// 输出照片路径
		try {
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			// 获取图片后缀
			BufferedImage img = ImageIO.read(imgFile);
			double dw = w;
			dw /= img.getWidth();
			dw *= img.getHeight();
			h = (int) dw;
			System.out.println("照片原始宽高：" + img.getWidth() + "、" + img.getHeight());
			System.out.println("照片压缩后的宽高：" + dw  + "、" + h);
			
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.getGraphics();
			g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
			g.dispose();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bi,format,os);
			byte [] bytes = os.toByteArray();
			BufferedOutputStream bos = null;  
			FileOutputStream fos = null;  
			
			int  i = outImg.lastIndexOf("/");
			file = new File(outImg.substring(0, i));
			// 判断目标文件夹是否存在，若不存在则新建
	        if (!file.exists()) {
	        	file.mkdir();
	        }
			file = new File(outImg);  	// 输出压缩后的照片
	        
			fos = new FileOutputStream(file);  
			bos = new BufferedOutputStream(fos);  
			bos.write(bytes);  
			bos.close();
			fos.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
/*	
	public static  InputStream thumbnailImageByUrl(String url,String format,int w, int h, boolean force){
		try {
			// ImageIO 支持的图片类型 : [BMP, bmp, jpg, JPG, wbmp, jpeg, png, PNG, JPEG, WBMP, GIF, gif]
			// 获取图片后缀
			Image img = ImageIO.read(new URL(url));
			BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.getGraphics();
			g.drawImage(img, 0, 0, w, h, Color.LIGHT_GRAY, null);
			g.dispose();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bi, format, os);
			InputStream input = new ByteArrayInputStream(os.toByteArray());
			os.close();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/
	public static void main(String[] args) {
//		File imgFile = new File("C:\\Users\\73028\\Desktop\\201809281751.gif");
//		thumbnailImage("C:\\Users\\73028\\Desktop\\201809281751.gif", "C:\\Users\\73028\\Desktop\\dog.jpg", "JPG",72, 72, false);

//				String imgPath = "C:\\Users\\73028\\Desktop\\201905090851.jpg";
//		String outImg = "C:\\Users\\73028\\Desktop\\201905090851_abbr_07.jpg";
//		System.out.println(new File(imgPath).length());
//		
//		googleImageZip(imgPath, outImg);
	}
	
	/**
	 * 使用谷歌开源工具进行图片压缩
	 * 
	 * @param imgPath
	 * @param outImg
	 */
	public static void googleImageZip(String imgPath, String outImg) {
		/**
		 * scale是可以指定图片的大小，值在0到1之间，1f就是原图大小，0.5就是原图的一半大小，这里的大小是指图片的长宽。
		 * 而outputQuality是图片的质量，值也是在0到1，越接近于1质量越好，越接近于0质量越差。
		 */
		try {
			if (new File(imgPath).length() > 1100000) {
				Thumbnails.of(imgPath) 
				.scale(0.7f) 
				.toFile(outImg);
			} else {
				Thumbnails.of(imgPath) 
				.scale(1f) 
				.toFile(outImg);
			}
			
		} catch (IOException e) {
			logger.error("{} 图片压缩异常: {}", imgPath, e.getMessage());
			e.printStackTrace();
		}

	}
	
	
	
}
