package com.cargosmart.snakebox.controller;

import com.cargosmart.snakebox.Application;
import com.cargosmart.snakebox.entity.News;
import com.cargosmart.snakebox.repository.NewsRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsControllerTest {

    static class API {

        static MediaType applicationJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8"));

        static MvcResult postNews(MockMvc mockMvc, News news) throws Exception {
            return mockMvc.perform(post("/news/v1").contentType(applicationJson)
                    .content(GSON.toJson(news))).andReturn();
        }
        
        
    }

    private boolean init;
    private static MockMvc mockMvc;
    List<News> newss;
    final static Gson GSON = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Autowired
    private WebApplicationContext webApplicationContext;
//    @Autowired
//    private FilterChainProxy springSecurityFilterChain;
    @Autowired
    private NewsRepository newsRepository;


    @Before
    public void setUp() throws Exception {
        if (!init) {
            mockMvc = webAppContextSetup(webApplicationContext).build();
            Type listType = new TypeToken<ArrayList<News>>() {}.getType();
            newss = GSON.fromJson(ReadTxtFile("testcase/news/news.json"), listType);
            init = true;
        }
    }
    
    private static String ReadTxtFile(String FileName) throws Exception {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(FileName));
        ByteArrayOutputStream memStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = bufferedInputStream.read(buffer)) != -1){
            memStream.write(buffer, 0, len);
        }
        byte[] data = memStream.toByteArray();        
        bufferedInputStream.close();
        memStream.close();
        bufferedInputStream.close();
        return new String(data);
    }
    
    
    @After
    public void tearDown() throws Exception {

    }

    @Test
    /**
     * Post news to Snakebox
     */
    public void $1_postNews() throws Exception {
    	int i = 0;
        for (News news : newss) {
        	news.setKey(String.valueOf(i));
        	i++;
            MvcResult result = API.postNews(mockMvc, news);
            Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.OK.value());
            Assert.assertEquals(result.getResponse().getContentAsString(), "received");
        }
    }


//    @Test
    /**
     * Fail post news to Snakebox
     */
    public void $2_postNewsFailed() throws Exception {
        News news = new News();
        news.setHeading( "Hello world");
        MvcResult result = API.postNews(mockMvc, news);
        Assert.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
        Thread.sleep(10000);
    }
}