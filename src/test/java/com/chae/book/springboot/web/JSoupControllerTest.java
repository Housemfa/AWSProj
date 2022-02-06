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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        ResponseEntity<String> responseEntity;
        //ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, "20220111",String.class);
        //responseEntity = restTemplate.postForEntity(url, "20220110",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220109",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220108",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220107",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220106",String.class);
//
//        responseEntity = restTemplate.postForEntity(url, "20220105",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220104",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220103",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220102",String.class);
//        responseEntity = restTemplate.postForEntity(url, "20220101",String.class);

        //2021-11-11 15:48	~2021-09-09 16:38	(596~574)

        LocalDate startDt = LocalDate.of(2022,1,30);

        LocalDate endDt = LocalDate.of(2022,1,24);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDtFormat;
        String endDtFormat;
        do{
            startDtFormat = startDt.format(formatter2);
            endDtFormat = endDt.format(formatter2);
            System.out.println(startDtFormat+" / "+endDtFormat);
            responseEntity = restTemplate.postForEntity(url, startDtFormat,String.class);
            startDt = startDt.minusDays(1);
        }while(Integer.valueOf(startDtFormat)>=Integer.valueOf(endDtFormat));

    }
    @Test
    public void Search_Test() throws Exception {
        //List<String> testList = jSoupService.search("20211211");
        //System.out.println(testList);
        LocalDate startDt = LocalDate.of(2021,11,11);
        LocalDate endDt = LocalDate.of(2021,9,9);
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd");
        String startDtFormat;
        String endDtFormat;
        do{
            startDtFormat = startDt.format(formatter2);
            endDtFormat = endDt.format(formatter2);
            System.out.println(startDtFormat+" / "+endDtFormat);
            startDt = startDt.minusDays(1);
        }while(Integer.valueOf(startDtFormat)>=Integer.valueOf(endDtFormat));


    }
    @Test
    public void Path_Test() throws Exception{
        List<String> testList = new ArrayList<>();
        testList.add("https://sukebei.nyaa.si/download/3535242.torrent");
        jSoupService.download(testList);

    }
}
