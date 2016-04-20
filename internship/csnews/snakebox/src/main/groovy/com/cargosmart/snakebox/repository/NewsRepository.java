package com.cargosmart.snakebox.repository;

import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cargosmart.snakebox.entity.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.stereotype.Repository;

/**
 * Created by CHANRO3 on 3/4/2016.
 */
@Repository
public class NewsRepository extends RedisRepository{

    public Map<String, Object> toProperties(News news){
        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("status", news.getStatus().name());
        properties.put("heading", news.getHeading());
        properties.put("content", news.getContent());
        properties.put("url", news.getUrl());
        properties.put("sender", news.getSender());
        properties.put("timezone", news.getTimezone());
        properties.put("publishDt", convertGMTDateToString(news.getPublishDt()));
        properties.put("captureDt", convertGMTDateToString(news.getCaptureDt()));
        properties.put("images", new Gson().toJson(news.getImages()));
        return properties;
    }

    News deserialize(String key){
        News news = new News();
        news.setStatus( News.Status.valueOf(hvalue(key, "status")));
        news.setHeading(hvalue(key, "heading"));
        news.setContent(hvalue(key, "content"));
        news.setUrl(hvalue(key, "url"));
        try {
			news.setPublishDt(convertToGMTDate(hvalue(key, "publishDt")));
			  news.setCaptureDt(convertToGMTDate(hvalue(key, "captureDt")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        news.setTimezone(hvalue(key, "timezone"));
        news.setSender(hvalue(key, "sender"));
        List<String> images = new Gson().fromJson(hvalue(key,"images"), new TypeToken<List<String>>(){}.getType());
        news.setImages(images);
        news.key();
        return news;
    }

    
   
    /** tag/v1/delete/{ news ID }/{tags ID}
     * delete new tags
     * @param news
     */
    public void delete(String key,String tag){
    	deleteHash(key,tag);
    }
    
    
    /**
     * add new tags
     * @param news
     */
    public void save(News news){
        saveHash(news.key(),toProperties(news));
    }
    
    /**
     * update
     * @param news
     */
    public void update(News news){
        saveHash(news.getKey(),toProperties(news));
    }

    /**
     * find By Status
     * @param status
     * @return
     */
    public List<News> findByStatus(String status){
        List<News> newss = new LinkedList<News>();
        List<String> keys = new ArrayList<String>( template.keys( "news:"+status+":*"));
        Collections.sort(keys, Collections.reverseOrder());
        System.out.println(keys);

        for(int i = 0; i < keys.size(); i++){
        	 newss.add(deserialize(keys.get(i)));
        }
        System.out.println(newss);
        return newss;
    }
    
    /**
     * find By Key
     * @param status
     * @return
     */
    public List<News> findByKey(String key){
        List<News> newss = new LinkedList<News>();
        List<String> keys = new ArrayList<String>( template.keys( "news:received:"+key+":*"));
        Collections.sort(keys, Collections.reverseOrder());
        System.out.println(keys);

        for(int i = 0; i < keys.size(); i++){
        	 newss.add(deserialize(keys.get(i)));
        }
        System.out.println(newss);
        return newss;
    }

}
