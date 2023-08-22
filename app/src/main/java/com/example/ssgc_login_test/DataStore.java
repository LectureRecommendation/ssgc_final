package com.example.ssgc_login_test;

import java.util.List;

public class DataStore {
    private static final DataStore ourInstance = new DataStore();

    public static DataStore getInstance() {
        return ourInstance;
    }

    private List<Lecture> lectures;

    private DataStore() {}

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }
}
