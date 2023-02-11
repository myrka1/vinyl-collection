package com.techelevator.model;

import java.sql.Connection;
import java.util.List;

public class Records {


    private Integer recordId;
    private String recordArtist;
    private String recordTitle;
    private String frontLink;
    private String backLink;
    private String musicBrainzId;

    public Records () {

    }

    public Records (Integer recordId, String recordArtist, String recordTitle) {
        this.recordId = recordId;
        this.recordArtist = recordArtist;
        this.recordTitle = recordTitle;
    }

    public Records (String musicBrainzId, String recordArtist, String recordTitle, String frontLink, String backLink) {
        this.recordArtist = recordArtist;
        this.recordTitle = recordTitle;
        this.frontLink = frontLink;
        this.backLink = backLink;
        this.musicBrainzId = musicBrainzId;
    }

    public void add(List<Records> records) {
        records.add(this);
    }

    public String getFrontLink() {
        return frontLink;
    }

    public void setFrontLink(String frontLink) {
        this.frontLink = frontLink;
    }

    public String getBackLink() {
        return backLink;
    }

    public void setBackLink(String backLink) {
        this.backLink = backLink;
    }

    public String getMusicBrainzId() {
        return musicBrainzId;
    }

    public void setMusicBrainzId(String musicBrainzId) {
        this.musicBrainzId = musicBrainzId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getRecordArtist() {
        return recordArtist;
    }

    public void setRecordArtist(String recordArtist) {
        this.recordArtist = recordArtist;
    }

    public String getRecordTitle() {
        return recordTitle;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    @Override
    public String toString() {
        return "Records{" +
                "recordId=" + recordId +
                ", recordArtist='" + recordArtist + '\'' +
                ", recordTitle='" + recordTitle + '\'' +
                '}';
    }

}
