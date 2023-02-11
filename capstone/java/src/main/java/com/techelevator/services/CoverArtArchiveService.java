package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
public class CoverArtArchiveService {

    public String doASearch(String id, boolean side) {
        RestTemplate template = new RestTemplate();
        String url = "https://coverartarchive.org/release/" + id;
        String coverArt = "https://git.techelevator.com/campuses/nlr/oct-2022/java-green/student-pairs/module3-capstone/m3-record-collection-capstone/-/raw/main/capstone/vue/src/assets/default.png";

        HttpHeaders headers = new HttpHeaders();
        try {
            ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(response.getBody());
            JsonNode images = jsonNode.path("images");

            for(JsonNode image : images) {
                //if side = true (returns front cover)
                //if side = false (returns back cover)
                if(side && image.path("front").asBoolean()) {
                    coverArt = image.path("image").asText();
                    return coverArt;
                }
                else if(!side && image.path("back").asBoolean()) {
                    coverArt = image.path("image").asText();
                    return coverArt;
                }
              }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException e) {
            System.out.println("logging: no cover art found");
        }

        return coverArt;
    }

}
