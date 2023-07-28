package com.example.mywebsite.services;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
@Service
public class DownloadPriceService {
    @Scheduled(cron = "0 00 10 * * ?")
    public void downlandPrice() throws IOException {
        String url = "http://www.alliance.web.kg/price.xls";
        String filePath = "/price.xls";

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("lock", "key1"));

        boolean downloaded = false;
        while (!downloaded){
            try (CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build();
                 CloseableHttpResponse response = httpClient.execute(new HttpGet(url));
                 OutputStream outputStream = new FileOutputStream(filePath)) {

                    if (response.getStatusLine().getStatusCode() == 200) {
                        response.getEntity().writeTo(outputStream);
                        System.out.println("Dowland");
                        downloaded = true;
                    } else {
                        System.out.println("Sleep one hour");
                        Thread.sleep(3600000);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
