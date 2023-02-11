package com.techelevator.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.model.Records;
import com.techelevator.model.Releases;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class MusicBrainzService {

    @Autowired
    CoverArtArchiveService service;

    public List<Records> doASearch(String artistSearch, String albumSearch) {
        RestTemplate template = new RestTemplate();
        String musicBrainzId = "";
        String author = "";
        String title = "";
        String frontLink = "";
        String backLink = "";
        Records record;

        System.out.println("artist: " + artistSearch + "   " + "album: " + albumSearch); //debugging line

        String url = "http://musicbrainz.org/ws/2/release/?query=artistname:" + "\"" + artistSearch + "\"" + "%20AND%20release:" + "\"" + albumSearch + "\" + &fmt=json";
                //+ "%20AND%20status:" + "\"" + "official" + "\"" + "&fmt=json";
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
        List<Records> recordsList = new ArrayList<>();

        try {
            ObjectMapper om = new ObjectMapper();
            JsonNode jsonNode = om.readTree(response.getBody());
            JsonNode releases = jsonNode.path("releases");

            System.out.println("release size: " + releases.size()); //always 25
            System.out.println();

            for(JsonNode current : releases) {
                boolean vinyl = current.path("media").path(0).path("format").asText().contains("Vinyl");
                boolean twelve = current.path("media").path(0).path("format").asText().contains("12");
                //System.out.println("isVinyl: " + current.path("media").path(0).path("format").asText());
                if(vinyl && twelve) {
                    musicBrainzId = current.path("id").asText();
                    title = current.path("title").asText();
                    author = current.path("artist-credit").path(0).path("artist").path("name").asText();
                    frontLink = service.doASearch(musicBrainzId, true);
                    backLink = service.doASearch(musicBrainzId, false);
                    if(!frontLink.equalsIgnoreCase("BLANK ID") || !backLink.equalsIgnoreCase("BLANK ID")) {
                        record = new Records(musicBrainzId, author, title, frontLink, backLink);
                        recordsList.add(record);
                    }
                }
            }

            System.out.println("Testing list:"); //debugging lines
            System.out.println("recordslistlength: " + recordsList.size());
            for(Records currentRecord : recordsList) {
                System.out.println(currentRecord.getMusicBrainzId());
                System.out.println(currentRecord.getRecordTitle());
                System.out.println(currentRecord.getRecordArtist());
                System.out.println(currentRecord.getFrontLink());
                System.out.println(currentRecord.getBackLink());
                System.out.println();
            }
            System.out.println("recordslist: " + recordsList); //sus functioning

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return recordsList;
    }
}
