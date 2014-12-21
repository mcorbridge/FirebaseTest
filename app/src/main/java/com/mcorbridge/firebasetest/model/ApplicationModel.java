package com.mcorbridge.firebasetest.model;

/**
 * Created by Mike on 12/21/2014.
 * copyright Michael D. Corbridge
 */
public final class ApplicationModel {
    private static final ApplicationModel instance = new ApplicationModel();
    public static ApplicationModel getInstance() { return instance; }

    private ApplicationModel() {}

    //-------------------------------------------
    public String getApplicationTeam() {
        return applicationTeam;
    }

    public void setApplicationTeam(String applicationTeam) {
        this.applicationTeam = applicationTeam;
    }

    public String applicationTeam;
    //-------------------------------------------
}