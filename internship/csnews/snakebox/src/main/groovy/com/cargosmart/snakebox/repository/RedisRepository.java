package com.cargosmart.snakebox.repository;

import com.cargosmart.snakebox.entity.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by CHANRO3 on 3/7/2016.
 */
abstract class RedisRepository{

    @Autowired
    protected RedisTemplate<String, Object> template;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public void saveHash(String key, Map<String, Object> properties){
        template.opsForHash().putAll(key, properties);
    }
    
    public void deleteHash(String key, String tagsID){
        template.opsForHash().delete(key, tagsID);;
    }
    
   

    public String convertGMTDateToString(Date date){
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(date);
    }

    public Date convertToGMTDate(String dateStr)throws Exception{
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.parse(dateStr);
    }

    public String hvalue(String key, String hashkey){
        return (String) template.opsForHash().get(key, hashkey);
    }

}
