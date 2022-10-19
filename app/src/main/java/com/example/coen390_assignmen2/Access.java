package com.example.coen390_assignmen2;

public class Access {
    private int accessid;
    private String id;
    private String accessType;
    private String timeStamp;

    public Access(int accessid, String id, String accessType, String timeStamp)
    {
        this.accessid=accessid;
        this.id=id;
        this.accessType=accessType;
        this.timeStamp=timeStamp;
    }

    public int getaccessid() {
        return accessid;
    }

    public void setaccessid(int accessid) {
        this.accessid = accessid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getaccessType() {
        return accessType;
    }

    public void setaccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
