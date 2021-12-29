package com.chae.book.springboot.web;

import com.chae.book.springboot.service.jsoup.JSoupService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class JSoupController {

    private final JSoupService jSoupService;
    @PostMapping("/api/v1/crawl")
    public String searchAndDownload(@RequestBody String date) throws Exception{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate localDate = LocalDate.now();

        if(date.equals("")){
            date = dtf.format(localDate);
        }
        List<String> downloadUrlList = jSoupService.search(date);

        jSoupService.download(downloadUrlList);

        return "";
    }
}
