package com.chae.book.springboot.service.jsoup;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class JSoupService {
    //private final String downloadPath = "C:"+File.separator+"Users"+File.separator+"bubsa"+File.separator+"Documents"+File.separator+"Torrents";
    private final String downloadPath = "D:"+File.separator+"Torrents";
    private final String url = "https://sukebei.nyaa.si";
    private final String query = "/?f=0&c=0_0&q=fc2&p=";
    //private final String query = "/?f=0&c=0_0&q=nhdtb&p=";

    public List<String> search(String date)throws Exception{
        List<String> downloadUrlList = new ArrayList<>();
        int page = 1;
        boolean whileFlag = true;
        do{
            Document doc = null;
            System.out.println("page : "+page);
            try {
                if(page>14) whileFlag = false;
                doc = Jsoup.connect(url + query+page++).get();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Element table = doc.select("table").get(0);
            Elements rows = table.select("tr.success");
            if(query.contains("nhdtb")){
                rows.addAll(table.select("tr.default"));
            }
            for (int i = 0; i < rows.size(); i++) {
                Element row = rows.get(i);


                Element td = row.select("td.text-center > a").first();
                Element dateTd = row.select("td").get(4);

                String downloadUrl = url + td.attr("href");

                int dateParsedInt = Integer.valueOf(dateTd.html().split(" ")[0].replace("-", ""));
                int dateParamInt = Integer.valueOf(date);

                if(dateParsedInt>dateParamInt){
                    continue;
                }else if(dateParsedInt<dateParamInt){
                    System.out.println("SEARCH FINISHED!");
                    whileFlag = false;
                }else {//if(dateParsedInt == dateParamInt)
                    downloadUrlList.add(downloadUrl);
                }

            }
        }while(whileFlag);
        return downloadUrlList;
    }
    public void download(List<String> downloadUrlList){
        //content-disposition: inline; filename="FC2-PPV-2504284.torrent"; filename*=UTF-8''FC2-PPV-2504284.torrent
        try {
            for (String downloadUrl : downloadUrlList) {
                URL url = new URL(downloadUrl);
                // open the connection
                URLConnection con = url.openConnection();
                // get and verify the header field
                String fieldValue = con.getHeaderField("content-disposition");

                if (fieldValue == null || !fieldValue.contains("filename=\"")) {
                    // no file name there -> throw exception ...
                }
                // parse the file name from the header field
                String[] split = fieldValue.split(";");
                String filename = split[1].replace("filename=\"","").replace("\"","").replace(" ","");
                // create file in systems temporary directory
                File download = new File(downloadPath, filename);

                // open the stream and download
                ReadableByteChannel rbc = Channels.newChannel(con.getInputStream());
                FileOutputStream fos = new FileOutputStream(download);
                try {

                    System.out.println("PREPARING "+filename+". . .");
                    Thread.sleep(10 * 1000);

                    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                } finally {
                    fos.close();

                }
            }
            System.out.println("FINISHED "+downloadUrlList.size()+" downloads!");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
