package com.techelevator.controller;

import com.techelevator.model.Records;
import com.techelevator.services.CoverArtArchiveService;
import com.techelevator.services.MusicBrainzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TestController {

    @Autowired
    private MusicBrainzService service;
    @Autowired
    private CoverArtArchiveService service2;

    @RequestMapping(path="/test/{artist}/{album}", method = RequestMethod.GET)
    public List<Records> test(@PathVariable String artist, @PathVariable String album) {
        return service.doASearch(artist, album);
    }


}
