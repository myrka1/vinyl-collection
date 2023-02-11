package com.techelevator.model;

import java.util.List;

public class Releases {
    private List<Records> recordsList;

    public Releases(List<Records> recordsList) {
        this.recordsList = recordsList;
    }

    public List<Records> getRecordsList() {
        return recordsList;
    }
    public void setRecordsList(List<Records> recordsList) {
        this.recordsList = recordsList;
    }
}
