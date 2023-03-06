package com.techelevator.dao;

import com.techelevator.model.Record;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.security.Principal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRecordsDao implements RecordDao {

    private final DataSource dataSource;

    private final JdbcTemplate jdbcTemplate;
    private final Record records = new Record();

    public JdbcRecordsDao(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Record> getRecords() throws SQLException {
        List<Record> records = new ArrayList<>();
        String sql = "SELECT * FROM records";
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            {
                while (resultSet.next()) {
                    Record recordsLibrary = new Record();
                    recordsLibrary.setRecordId(resultSet.getInt("record_id"));
                    recordsLibrary.setRecordTitle(resultSet.getString("record_title"));
                    recordsLibrary.setRecordArtist(resultSet.getString("record_artist"));
                    recordsLibrary.add(records);
                }
                return records;
            }
        }
    }

    public List<Record> getRecordsByCollection(Integer collectionId) {
        List<Record> records = new ArrayList<>();
        System.out.println(collectionId);
        String sql = "SELECT records.record_id, record_artist, record_title, record_front " +
                "From records " +
                "Join collections_records ON collections_records.record_id = records.record_id " +
                "Join collections ON collections.collection_id = collections_records.collection_id " +
                "Where collections.collection_id = ?";
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, collectionId);
            {
                while (results.next()) {
                    Record record = mapRowToRecords(results);
                    records.add(record);
                }
                return records;
            }
        }


    @Override
    public void addRecord(Record record, Principal principal) throws SQLException {
        String sql = "INSERT INTO records (record_title, record_artist, record_back, record_front, musicbrainz_id) VALUES (?, ?, ?, ?, ?) RETURNING record_id";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, record.getRecordTitle());
            statement.setString(2, record.getRecordArtist());
            statement.setString(3, record.getBackLink());
            statement.setString(4, record.getFrontLink());
            statement.setString(5, record.getMusicBrainzId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                int recordId = resultSet.getInt(1);
                String username = principal.getName();
                String insertIntoUsersRecords = "INSERT INTO users_records (record_id, user_id) VALUES (?, (SELECT user_id FROM users WHERE username = ? ));";
                PreparedStatement statement2 = connection.prepareStatement(insertIntoUsersRecords);
                statement2.setInt(1, recordId);
                statement2.setString(2, username);
                statement2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Record> findAllRecords() {
        List<Record> recordsList = new ArrayList<>();
        String sql = "SELECT * FROM records;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Record record = mapRowToRecords(results);
            recordsList.add(record);
        }
        return recordsList;
    }

    @Override
    public List<Record> findAllRecordsByUser(String username) {
        List<Record> recordsList = new ArrayList<>();
        String sql = "SELECT * FROM records AS r JOIN users_records AS ur ON ur.record_id = r.record_id JOIN users AS u ON u.user_id = ur.user_id WHERE u.username = '?';";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, String.class, username);
        while (results.next()) {
            Record record = mapRowToRecords(results);
            recordsList.add(record);
        }
        return recordsList;
    }

    @Override
    public Record findByRecordId(int recordId) {
        String sql = "SELECT record_id FROM records WHERE record_id = ?;";
        Record id = jdbcTemplate.queryForObject(sql, Record.class, recordId);
        return id;
    }

    @Override
    public String findRecordByTitle(String recordTitle) {
        String sql = "SELECT record_title FROM records WHERE record_title = ?;";
        String title = jdbcTemplate.queryForObject(sql, String.class, recordTitle);
        return title;
    }

    @Override
    public void addRecords(Record records) {
        String sql = "INSERT INTO records (record_title, record_artist) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            {
                statement.setString(1, records.getRecordTitle());
                statement.setString(2, records.getRecordArtist());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Integer> getRecordIdsForUser(int userId) {
        List<Integer> recordsIds = new ArrayList<>();
        String sql = "SELECT record_id FROM users_records WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    recordsIds.add(resultSet.getInt("record_id"));
                }
            }
        } catch (SQLException e) {

        }
        return recordsIds;
    }

    @Override
    public List<Record> getRecordsForUser(String username) {
        List<Record> recordsForUser = new ArrayList<>();
        String sql = "SELECT r.record_id, r.record_title, r.record_artist, r.record_front, r.record_back, r.musicbrainz_id FROM records AS r JOIN users_records AS ur ON ur.record_id = r.record_id JOIN users AS u ON u.user_id = ur.user_id WHERE u.username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            while (results.next()) {
                int recordId = results.getInt("record_id");
                String recordTitle = results.getString("record_title");
                String recordArtist = results.getString("record_artist");
                String frontLink = results.getString("record_front");
                String backLink = results.getString("record_back");
                String musicBrainzId = results.getString("musicbrainz_id");
                Record record = new Record(recordId, recordArtist, recordTitle);
                record.setFrontLink(frontLink);
                record.setBackLink(backLink);
                record.setMusicBrainzId(musicBrainzId);
                recordsForUser.add(record);
            }
        return recordsForUser;
    }


    private Record mapRowToRecords(SqlRowSet rowSet) {
        Record record = new Record();
        record.setRecordId(rowSet.getInt("record_id"));
        record.setRecordArtist(rowSet.getString("record_artist"));
        record.setRecordTitle(rowSet.getString("record_title"));
        record.setFrontLink(rowSet.getString("record_front"));
        return record;

    }


}
