package com.techelevator.model;

public class Collections {

    private int collectionId;
    private int recordId;
    private String notes;
    private String collectionTitle;

    public Collections () {

    }


    public Collections (int collectionId, int recordId, String notes, String collectionTitle) {
        this.collectionId = collectionId;
        this.recordId = recordId;
        this.notes = notes;
        this.collectionTitle = collectionTitle;
        }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public String getCollectionTitle() {
        return collectionTitle;
    }

    public void setCollectionTitle(String collectionTitle) {
        this.collectionTitle = collectionTitle;
    }

    @Override
    public String toString() {
        return "Collections{" +
                "collectionId=" + collectionId +
                ", recordId=" + recordId +
                ", notes='" + notes + '\'' +
                '}';
    }
}


