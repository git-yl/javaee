package cn.edu.bjtu.weibo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.edu.bjtu.weibo.model.Picture;
import cn.edu.bjtu.weibo.service.UserAvatarsService;
import redis.clients.jedis.Jedis;

public class UserAvatarsServiceImpl implements UserAvatarsService {

	@Override
	public List<Picture> getUserAvatarList(String userId, int pageIndex, int numberPerPage) {
		List<String> values = null;
		List<Picture> pics = new ArrayList<Picture>();
		Jedis jedis = null;
		try{
			jedis = new Jedis("127.0.0.1", 6379);
			Long len = jedis.llen("PicurlOr");
			if(len < pageIndex*numberPerPage){
			}else if(len < (pageIndex+1)*numberPerPage){
				values = jedis.lrange("PicurlOr", pageIndex*numberPerPage, len-1);
			}else{
				values = jedis.lrange("PicurlOr", pageIndex*numberPerPage, (pageIndex+1)*numberPerPage-1);
			}
			for(int i=0;i<values.size();i++){
				Picture ap = new Picture();
				ap.setPicurl(values.get(i));
				pics.add(ap);
			}
		}catch (Exception e){
			return null;
		}finally{
			if(jedis.isConnected()){
				jedis.close();
			}
		}
		return pics;
	}

	@Override
	public boolean uploadUserAvatar(String userId, MultipartFile multipartFile) {
		// TODO 自动生成的方法存根
		return false;
	}

}
