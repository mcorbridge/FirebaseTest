package com.mcorbridge.firebasetest.vo;

import java.io.Serializable;

/**
 * Created by Mike on 12/20/2014.
 * copyright Michael D. Corbridge
 */
public class Player implements Serializable{

    private static final long serialVersionUID = 7526472295622776147L;

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String lname;

    //-----------------------------------------

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String UUID;

    //-----------------------------------------

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String fname;

    //-----------------------------------------

}
