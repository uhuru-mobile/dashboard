package com.uhuru.dashboard;

/**
 * Created by dorian on 14/04/15.
 */
public class SaveData {

    private int id;
    private String timestamp;
    private String module;
    private int verbose;
    private String incident;
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getVerbose() {
        return verbose;
    }

    public void setVerbose(int verbose) {
        this.verbose = verbose;
    }

    public String getIncident() {
        return incident;
    }

    public void setIncident(String incident) {
        this.incident = incident;
    }

    @Override
    public String toString() {
        return "SaveData [id=" + id + ", module=" + module + ", verbose=" + verbose
                + ", incident=" + incident + ", comment=" + comment + "]";
    }

    public SaveData(String timestamp, String module, int verbose, String incident) {
        super();
        //this.id = id;
        this.timestamp = timestamp;
        this.module = module;
        this.verbose = verbose;
        this.incident = incident;
        this.comment = null;
    }

    public SaveData(String timestamp, String module, int verbose, String incident, String comment) {
        super();
        //this.id = id;
        this.timestamp = timestamp;
        this.module = module;
        this.verbose = verbose;
        this.incident = incident;
        this.comment = comment;
    }

    public SaveData() {
        super();
        // TODO Auto-generated constructor stub
    }
}
