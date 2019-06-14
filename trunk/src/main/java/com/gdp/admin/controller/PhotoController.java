package com.gdp.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdp.entity.Config;
import com.gdp.entity.FaceInfo;
import com.gdp.entity.UserInfo;
import com.gdp.pojo.admin.WeChatInfoVO;
import com.gdp.service.ConfigService;
import com.gdp.service.FaceInfoService;
import com.gdp.service.UserInfoService;
import com.gdp.util.ParaUtil;


/**
 * 后台照片管理系统控制器
 * 
 * @author Jashon
 * @since 2018-09-22
 */
@RestController
@RequestMapping("/admin")
public class PhotoController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FaceInfoService faceInfoService;
	@Autowired
	private ConfigService configService;
	@Autowired	
	private UserInfoService userInfoService;
	
	/**
	 * 根据 openid 获取该用户存放的所有文件
	 *
	 * @param openid	唯一 openid
	 * @param index		页码
	 * @param limit		每页的页数
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("/listAllPhotosByOpenid")
	@ResponseBody
	public Map<String, Object> listAllPhotosByOpenid(String openid, int index, int limit, HttpServletRequest httpServletRequest) {
		Map<String, Object> modelMap = new HashMap<>();
		JSONArray data = new JSONArray();
		
		// 获取 uploads 文件夹本地路径
		String pathname = ParaUtil.UPLOAD_PATH;

//		if("".equals(ParaUtil.domain_name)) {
		// 获取 key 为 domain_name 的配置项(域名地址)
		Config config = configService.selectByPrimaryKey("domain_name");
		ParaUtil.domain_name = config.getValuees();
//		}

		data.clear();
        List<FaceInfo> list = faceInfoService.listByOpenIdForAdmin(openid);

		// 判断剩余数据项是否足够一页
		if((index*limit) < list.size()) {
			for(int i = (index-1)*limit; i < (index*limit); i++) {
				JSONObject obj = new JSONObject();
				StringBuilder sb = new StringBuilder(ParaUtil.domain_name);
				sb.append("uploads/abbr/").append(list.get(i).getPhotoPath());

                obj.put("id", list.get(i).getId());
                obj.put("url", sb.toString());
				obj.put("name", list.get(i).getPhotoPath().split("/")[1]);
				File f = new File(pathname + list.get(i).getPhotoPath());
                double d = getTotalSizeOfFilesInDir(f);
                obj.put("size", String.format("%.2f", (d/1024)) + " KB");    // 图片的大小

                data.add(obj);

				logger.info("1. " + data.toJSONString());
			}
		} else {
			for(int i = (index-1)*limit; i < list.size(); i++) {
				JSONObject obj = new JSONObject();
                StringBuilder sb = new StringBuilder(ParaUtil.domain_name);
                sb.append("uploads/abbr/").append(list.get(i).getPhotoPath());

                obj.put("id", list.get(i).getId());
                obj.put("url", sb.toString());
                obj.put("name", list.get(i).getPhotoPath().split("/")[1]);
                File f = new File(pathname + list.get(i).getPhotoPath());
                double d = getTotalSizeOfFilesInDir(f);
                obj.put("size", String.format("%.2f", (d/1024)) + " KB");    // 图片的大小

                data.add(obj);

                logger.info("2. " + data.toJSONString());
			}
		}

        modelMap.put("code", 0);
        modelMap.put("msg", "");
        modelMap.put("count", list.size());
		modelMap.put("data", data);
		return modelMap;
	}
	

	/**
	public Map<String, Object> listAllPhotosByOpenid(String openid, int index, int limit, HttpServletRequest httpServletRequest) {
		Map<String, Object> res = new HashMap<>();
		JSONArray urls = new JSONArray();
		JSONArray names = new JSONArray();

		// 获取 uploads 文件夹本地路径
		String pathname = RealPathUtils.uploadsPath;
		File file = new File(pathname + openid);
		File[] files = file.listFiles();

//		if("".equals(ParaUtil.domain_name)) {
			// 获取 key 为 domain_name 的配置项(域名地址)
			Config config = configService.getConfig("domain_name");
			ParaUtil.domain_name = config.getValuees();
//		}

		urls.clear();
        System.out.println("-> index:" + index + "\n limit: " + limit);

		// 判断剩余数据项是否足够一页
		if((index*limit) < files.length) {
			for(int i = (index-1)*limit; i < (index*limit); i++) {
				StringBuilder sb = new StringBuilder(ParaUtil.domain_name);
				sb.append("uploads/" + openid + "/");
				sb.append(files[i].getName());
				urls.add(sb.toString());
                System.out.println(urls.toString());
				names.add(files[i].getName());
			}
		} else {
			for(int i = (index-1)*limit; i < files.length; i++) {
				StringBuilder sb = new StringBuilder(ParaUtil.domain_name);
				sb.append("uploads/" + openid + "/");
				sb.append(files[i].getName());
				urls.add(sb.toString());
				names.add(files[i].getName());
			}
		}

		res.put("urls", urls);
		res.put("names", names);
		res.put("totals", files.length);
		return res;
	}
	 */
	
	/**
	 * 列出所有用户的 wxid && nick_name;
	 * 
	 * @return
	 */
	@RequestMapping("/listUsers")
	@ResponseBody
	public Map<String, Object> listAllUser(int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		
		String foldpath = ParaUtil.UPLOAD_PATH;
		
		List<WeChatInfoVO> list = faceInfoService.listAllWxid();
		List<WeChatInfoVO> res = new ArrayList<>();

		if(list.size() == 0){
            map.put("code", -1);
            map.put("msg", "暂无用户数据");
            map.put("count", list.size());
            map.put("data", "");
            return map;
        }

        // 判断剩余数据项是否足够一页
        if((page*limit) < list.size()) {
            for (int i = (page - 1) * limit; i < (page * limit); i++) {
                WeChatInfoVO vo = list.get(i);
                // 查询并设置用户微信昵称
                UserInfo info = userInfoService.selectByPrimaryKey(vo.getWxid());
                vo.setNickName(info.getNickName());
                
                double l = getTotalSizeOfFilesInDir(new File(foldpath + vo.getWxid()));
                // 转换为 小数点后两位的数值
                l = l/1024/1024;
                vo.setSize(String.format("%.2f",l) + " MB");
                res.add(vo);
            }
        } else {
            for (int i = (page - 1) * limit; i < list.size(); i++) {
                WeChatInfoVO vo = list.get(i);
                // 查询并设置用户微信昵称
                UserInfo info = userInfoService.selectByPrimaryKey(vo.getWxid());
                vo.setNickName(info.getNickName());
                
                double l = getTotalSizeOfFilesInDir(new File(foldpath + vo.getWxid()));
                // 转换为 小数点后两位的数值
                l = l/1024/1024;
                vo.setSize(String.format("%.2f",l) + " MB");
                res.add(vo);
            }
        }

//		for(WeChatInfoVO vo : list) {
//			// 计算文件夹所占内存大小
//			double l = getTotalSizeOfFilesInDir(new File(foldpath + vo.getWxid()));
//			// 转换为 小数点后两位的数值
//			l = l/1024/1024;
//			vo.setSize(String.format("%.2f",l) + " MB");
//			res.add(vo);
//		}
		
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(res);

		logger.info("-> 所有用户的 微信id 和昵称 : " + jsonArray);
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", list.size());
		map.put("data", jsonArray);
		return map;
	}

	
	/**
	 * 递归方式 计算文件的大小
	 * 
	 * @param file
	 * @return
	 */
    private long getTotalSizeOfFilesInDir(final File file) {
        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        long total = 0;
        if (children != null)
            for (final File child : children)
                total += getTotalSizeOfFilesInDir(child);
        return total;
    }
	
}
