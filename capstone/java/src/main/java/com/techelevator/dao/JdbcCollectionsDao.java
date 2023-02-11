package com.techelevator.dao;

import com.techelevator.model.Collections;
import com.techelevator.model.UserDto;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcCollectionsDao implements CollectionsDao {

    private final JdbcTemplate jdbcTemplate;
    public JdbcCollectionsDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Integer createCollection(int collectionId, int recordId) {
        String sql = "INSERT INTO users_collections (collectionId, recordId) VALUES (? , ?) RETURNING collection_id;";
        Integer newCollectionId;
        try {
            newCollectionId = jdbcTemplate.queryForObject(sql, Integer.class, collectionId, recordId);
        } catch (DataAccessException e) {
            return null;
        }
        return newCollectionId;
    }

    ;

    @Override
    public List<Collections> getAllCollections() {
        List<Collections> collectionsList = new ArrayList<>();
        String sql = "SELECT * FROM users_collections;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Collections collections = mapToRowUsersCollections(results);
            collectionsList.add(collections);
        }
        return collectionsList;
    }

    @Override
    public List<Collections> findAllUserCollections() {
        List<Collections> collectionsList = new ArrayList<>();
        String sql = "SELECT * FROM collections WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Collections collections = mapToRowCollections(results);
            collectionsList.add(collections);
        }
        return collectionsList;
    }

    @Override
    public Collections findCollectionById(int collectionId) {
        String sql = "SELECT collections_id FROM collections WHERE collection_id = ?;";
        Collections id = jdbcTemplate.queryForObject(sql, Collections.class, collectionId);
        return id;

    }

    public void addNotes(String notes, int collectionId) {
        String sql = "UPDATE collections SET notes = ? WHERE collection_id = ?;";
        jdbcTemplate.update(sql, notes, collectionId);
    }

    @Override
    public boolean deleteCollection(int collectionId) {
        String sql = "DELETE FROM collections WHERE collections_id = ?";
        try (Connection connection = jdbcTemplate.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, collectionId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean addRecordToCollection(int collectionId, int recordId) {
        String sql = "INSERT INTO collections (collection_id, record_id) VALUES (?, ?)";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            {
                statement.setInt(1, collectionId);
                statement.setInt(2, recordId);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteRecordFromCollection(int collectionId, int recordId) {
        String sql = "DELETE FROM collections WHERE collection_id = ? AND record_id = ?";
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            {
                statement.setInt(1, collectionId);
                statement.setInt(2, recordId);
                int rowsAffected = statement.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Collections mapToRowCollections(SqlRowSet rowSet) {
        Collections collection = new Collections();
        collection.setCollectionId(rowSet.getInt("collection_id"));
        collection.setRecordId(rowSet.getInt("record_id"));
        collection.setNotes(rowSet.getString("notes"));
        return collection;
    }

    private Collections mapToRowUsersCollections(SqlRowSet rowSet) {
        Collections collection = new Collections();
        collection.setCollectionId(rowSet.getInt("collection_id"));
        collection.setCollectionTitle(rowSet.getString("collection_title"));
        collection.setNotes(rowSet.getString("collection_notes"));
        return collection;
    }


        public void transferCollection ( int collectionId, int oldUserId, int newUserId){

            String updateQuery = "UPDATE collections SET user_id = ? AND user_id = ?";
            try {
                Connection connection = jdbcTemplate.getDataSource().getConnection();
                PreparedStatement statement = connection.prepareStatement(updateQuery);
                {
                    statement.setInt(1, newUserId);
                    statement.setInt(2, collectionId);
                    statement.setInt(3, oldUserId);

                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected == 0) {
                        throw new RuntimeException("Failed to transfer Collection");
                    }
                }
                throw new RuntimeException("Failed to transfer Collection");
            } catch (RuntimeException | SQLException exception) {
                exception.printStackTrace();
            }
        }


        private UserDto mapRowToUserDTO (SqlRowSet rowSet){
            UserDto user = new UserDto();
            user.setUserId(rowSet.getInt("user_id"));
            user.setName(rowSet.getString("username"));
            return user;
        }

    }

