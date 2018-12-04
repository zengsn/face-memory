package com.gdp.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdp.entity.Config;
import com.gdp.pojo.admin.WeChatInfoVO;
import com.gdp.service.ConfigService;
import com.gdp.service.FaceInfoService;
import com.gdp.util.LogUtils;
import com.gdp.util.ParaUtil;
import com.gdp.util.RealPathUtils;

import net.sf.json.JSONArray;

/**
 * 后台照片管理系统控制器
 * 
 * @author Jashon
 * @since 2018-09-22
 */
@Controller
@RequestMapping("/admin")
public class PhotoController {

//	private Logger logger = Logger.getLogger(PhotoController.class);
	
	@Autowired
	private FaceInfoService faceInfoService;
	@Autowired
	private ConfigService configService;

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
        System.out.println("---> index:" + index + "\n limit: " + limit);

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
	
	/**
	 * 列出所有用户的 wxid && nick_name;
	 * 
	 * @return
	 */
	@RequestMapping("/listUsers")
	@ResponseBody
	public Map<String, Object> listAllUser(HttpServletRequest httpServletRequest) {
		Map<String, Object> map = new HashMap<>();
		
		String foldpath = RealPathUtils.uploadsPath;
		
		List<WeChatInfoVO> list = faceInfoService.listAllWxid();
		List<WeChatInfoVO> res = new ArrayList<>();
		for(WeChatInfoVO vo : list) {
			// 计算文件夹所占内存大小
			double l = getTotalSizeOfFilesInDir(new File(foldpath + vo.getWxid()));
			// 转换为 小数点后两位的数值
			l = l/1024/1024;
			vo.setSize(String.format("%.2f",l) + " MB");
			res.add(vo);
		}
		
		JSONArray jsonArray = JSONArray.fromObject(res);

		LogUtils.logger.info("所有用户的 微信id 和昵称 : " + jsonArray);
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
