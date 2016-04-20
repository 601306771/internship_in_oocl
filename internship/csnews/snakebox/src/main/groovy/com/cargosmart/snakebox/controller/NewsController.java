package com.cargosmart.snakebox.controller;

import java.util.Date;
import java.util.List;

import com.cargosmart.snakebox.entity.News;
import com.cargosmart.snakebox.entity.News.Status;
import com.cargosmart.snakebox.repository.NewsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/news")
class NewsController {

    @Autowired
    NewsRepository newsRepository;
    
 

    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v1", method = RequestMethod.POST)
    ResponseEntity<String> post(@RequestBody News news) {
        newsRepository.save(news);
        return ResponseEntity.ok("received");
    }
    
    /**
     * delete tags
     * @param news
     * @return
     */
    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v1/delete/{newsID}/{tagsID}", method = RequestMethod.GET)
    ResponseEntity<String> postToUpdate(@PathVariable String newsID,@PathVariable String tagsID) {
        newsRepository.delete(newsID, tagsID);
        return ResponseEntity.ok("delete:" + newsID + " 's  Tag = " + tagsID);
    }
    
    
    /**
     * Insert tags
     * @param news
     * @return
     */
    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v1/insert/{newsID}", method = RequestMethod.GET)
    ResponseEntity<String> postToInsert(@PathVariable String newsID) {
    	System.out.print(newsID);
    	News news = new News();
    	news.setHeading(newsID);
    	news.setContent(newsID);
    	news.setUrl(newsID);
    	Date date = new Date();
    	news.setPublishDt(date);
    	news.setTimezone(newsID);
    	news.setSender(newsID);
    	news.setCaptureDt(date);
    	news.setStatus(Status.received);
    	news.setKey(news.key());
    	System.out.println(news);
        newsRepository.save(news);
        return ResponseEntity.ok("insert");
    }
    
    /**
     * update tags
     * @param news
     * @return
     */
    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v1/update/{newsID}", method = RequestMethod.GET)
    ResponseEntity<String> postToUpdate(@PathVariable String newsID) {
    	News news = new News();
    	news.setHeading("lxy11");
    	news.setKey(newsID);
    	news.setContent("lxy11");
    	news.setUrl("lxy11");
    	Date date = new Date();
    	news.setPublishDt(date);
    	news.setTimezone("lxy11");
    	news.setSender("lxy11");
    	news.setCaptureDt(date);
    	news.setStatus(Status.received);
    	System.out.print(newsID + "----" +news);
        newsRepository.update(news);
        return ResponseEntity.ok("update:" + news.getKey());
    }

    /**
     * find news by status
     * @param status
     * @return
     */
    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v1/{status}", method = RequestMethod.GET)
    ResponseEntity<List<News>> getPostsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(newsRepository.findByStatus(status));
    }
    
    /**
     * find news by key
     * @param key
     * @return
     */
    @CrossOrigin(maxAge = 3600L)
    @RequestMapping(value = "/v2/{key}", method = RequestMethod.GET)
    ResponseEntity<List<News>> getPostsByKey(@PathVariable String key) {
        return ResponseEntity.ok(newsRepository.findByKey(key));
    }
    
    

}
