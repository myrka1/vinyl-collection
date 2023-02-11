package com.techelevator.dao;

import com.techelevator.model.Records;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RecordDao {


    void addRecord(Records records, Principal principal) throws SQLException;

    List<Records> getRecordsByCollection(Integer collectionID);

    List<Records> findAllRecords();

    List<Records> findAllRecordsByUser(String username);

    Records findByRecordId(int recordId);

    String findRecordByTitle(String recordTitle);

    void addRecords(Records records);

    List<Integer> getRecordIdsForUser(int userId);

    List<Records> getRecordsForUser(String username);
}
