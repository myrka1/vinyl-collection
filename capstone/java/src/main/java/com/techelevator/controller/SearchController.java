package com.techelevator.controller;

import com.techelevator.model.Record;
import com.techelevator.services.CoverArtArchiveService;
import com.techelevator.services.MusicBrainzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class SearchController {
    @Autowired
    private MusicBrainzService service;
    @Autowired
    private CoverArtArchiveService service2;

    @RequestMapping(path="/search/{artist}/{album}", method = RequestMethod.GET)
    public List<Record> search(@PathVariable String artist, @PathVariable String album) {
        return service.doASearch(artist, album);
    }
}
