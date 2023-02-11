package com.techelevator.dao;

import com.techelevator.model.Collections;

import java.sql.SQLException;
import java.util.List;

public interface CollectionsDao {

    Integer createCollection(int collectionId, int recordId);

    List<Collections> getAllCollections();

    List<Collections> findAllUserCollections();

    Collections findCollectionById(int collectionId);

    void transferCollection(int collectionId, int oldUserId, int newUserId);

    void addNotes(String notes, int recordId);

    boolean deleteCollection(int collectionId) throws SQLException;

    boolean addRecordToCollection(int collectionId, int recordId);

    boolean deleteRecordFromCollection(int collectionId, int recordId);


}
