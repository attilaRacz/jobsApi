package com.example.jobsapi.util;

public class PositionSearchResult {
    private String jobTitle;
    private String location;
    private String URL;

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "PositionSearchResult{" +
                "jobTitle='" + jobTitle + '\'' +
                ", location='" + location + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}
