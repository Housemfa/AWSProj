package com.chae.book.springboot.web;

import com.chae.book.springboot.service.jsoup.JSoupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JSoupControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private JSoupService jSoupService;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Crawl_Test() throws Exception {
        String url = "http://localhost:"+port+"/api/v1/crawl";
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, "20211226",String.class);
        responseEntity = restTemplate.postForEntity(url, "20211227",String.class);
        responseEntity = restTemplate.postForEntity(url, "20211228",String.class);



    }
    @Test
    public void Search_Test() throws Exception {
        List<String> testList = jSoupService.search("20211211");
        System.out.println(testList);

    }
    @Test
    public void Path_Test() throws Exception{
        List<String> testList = new ArrayList<>();
        testList.add("https://sukebei.nyaa.si/download/3535242.torrent");
        jSoupService.download(testList);

    }
}
