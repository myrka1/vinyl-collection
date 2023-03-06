package com.techelevator.dao;

import com.techelevator.model.Record;

import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface RecordDao {


    void addRecord(Record records, Principal principal) throws SQLException;

    List<Record> getRecordsByCollection(Integer collectionID);

    List<Record> findAllRecords();

    List<Record> findAllRecordsByUser(String username);

    Record findByRecordId(int recordId);

    String findRecordByTitle(String recordTitle);

    void addRecords(Record records);

    List<Integer> getRecordIdsForUser(int userId);

    List<Record> getRecordsForUser(String username);
}
