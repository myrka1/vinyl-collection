package com.techelevator.controller;


import com.techelevator.dao.RecordDao;
import com.techelevator.model.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.SQLException;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping
public class RecordsController {

    private final RecordDao recordDao;

    @Autowired
    public RecordsController(RecordDao recordDao) {
        this.recordDao = recordDao;
    }


    @GetMapping("/list")
    public @ResponseBody
    List<Records> listRecords() {
        return recordDao.findAllRecords();
    }

    @GetMapping("/{recordId}")
    public @ResponseBody Records findRecordById(@PathVariable int recordId) {
        return recordDao.findByRecordId(recordId);
    }

    @GetMapping("/title/{recordTitle}")
    public @ResponseBody String findByRecordTitle(@PathVariable String recordTitle) {
        return recordDao.findRecordByTitle(recordTitle);
    }

    @PostMapping("/add")
    public @ResponseBody void addRecord(@RequestBody Records record, Principal principal) throws SQLException {
        recordDao.addRecord(record, principal);
    }

    @GetMapping("/records/{userId}")
    @ResponseBody
    public List<Integer> getRecordsForUser(@PathVariable int userId) {
        return recordDao.getRecordIdsForUser(userId);
    }

    @RequestMapping(path="/libraryStatistics", method=RequestMethod.GET)
    public String showLibraryStatistics(@RequestParam("username")String username, ModelMap model) {
        List<Records> records = recordDao.getRecordsForUser(username);
        int recordCount = records.size();
        model.addAttribute("recordCount", recordCount);
        return "libraryStatistics";
    }

}
