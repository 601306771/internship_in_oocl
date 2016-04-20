package com.cargosmart.snakebox.repository;

import java.util.HashMap;
import java.util.Map;

import com.cargosmart.snakebox.entity.News;
import com.cargosmart.snakebox.entity.Tags;
import com.google.gson.Gson;

public class TagsRepository extends RedisRepository{
	
	  public Map<String, Object> toProperties(Tags tags){
	        final Map<String, Object> properties = new HashMap<String, Object>();
	        properties.put("status", tags.getKey());

	        return properties;
	    }
	  
	  public void save(Tags tags){
	        saveHash(tags.getKey(),toProperties(tags));
	    }

}
