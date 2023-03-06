package com.techelevator.controller;


import com.techelevator.dao.CollectionsDao;
import com.techelevator.dao.RecordDao;
import com.techelevator.dao.UserDao;
import com.techelevator.model.Collection;
import com.techelevator.model.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin
@RestController
public class CollectionsController {

    @Autowired
    private CollectionsDao collectionsDao;
    @Autowired
    private RecordDao recordsDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(path="/userCollections", method = RequestMethod.GET)
    public String showUserCollections(ModelMap model) {
        List<Collection> collections = collectionsDao.findAllUserCollections();
        model.addAttribute("collections", collections);
        return "userCollections";
    }

    @RequestMapping(path="/collections", method = RequestMethod.GET)
    public List<Collection> showCollections(ModelMap model) {
        List<Collection> collections = collectionsDao.getAllCollections();
        model.addAttribute("collections", collections);
        return collections;
    }

    @RequestMapping(path="/findRecordsInCollection/{collectionId}", method = RequestMethod.GET)
    public List<Record> findRecordsInCollection(@PathVariable Integer collectionId) {
        List<Record> records = recordsDao.getRecordsByCollection(collectionId);
        return records;
    }

    @CrossOrigin
    @RequestMapping(path="/currentUser", method = RequestMethod.GET)
    public String getLoggedInUsername(Principal principal) {
        if(principal != null) {

            System.out.print("principal");
            System.out.print(principal.getName());
            String name = principal.getName();

            return name;
        }
        else
        {
            System.out.print("Someone");
            return "";
        }
    }

    @RequestMapping(path="/findCollection", method=RequestMethod.GET)
    public String findCollectionById(int collectionId, ModelMap model) {
        Collection collection = collectionsDao.findCollectionById(collectionId);
        model.addAttribute("collection", collection);
        return "collectionDetails";
    }

    @RequestMapping(path="/deleteCollection", method=RequestMethod.POST)
    public String deleteCollection(int collectionId) throws SQLException {
        collectionsDao.deleteCollection(collectionId);
        return "redirect:/collections";
    }

    @RequestMapping(path="addRecordToCollection", method=RequestMethod.POST)
    public String addRecordToCollection(int collectionId, int recordId) {
        collectionsDao.addRecordToCollection(collectionId, recordId);
        return "redirect:/collections";
    }

    @RequestMapping(path="deleteRecordFromCollection", method=RequestMethod.POST)
    public String deleteRecordFromCollection(int collectionId, int recordId) {
        collectionsDao.deleteRecordFromCollection(collectionId, recordId);
        return "redirect:/collections";
    }

    @RequestMapping(path="/transferCollection", method=RequestMethod.POST)
    public String transferCollection(int collectionId, int altUserId, int newUserId) {
        collectionsDao.transferCollection(collectionId, altUserId, newUserId);
        return "redirect:/collections";
    }


    @RequestMapping(path="/library", method=RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")
    public List<Record> getUserLibrary(Principal principal){
        String username = principal.getName();
        System.out.println(username);
        List<Record> records;
        return recordsDao.getRecordsForUser(username);


    }

}

