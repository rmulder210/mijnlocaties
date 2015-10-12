package com.goldengateway.apps.mijnlocaties.model;

/**
 * Created by Rob on 04-10-15.

 */
public class Locatie {

    private int locatieid;
    private String omschrijving;
    private String naamkort;
    private String adres;
    private String postcode;
    private double lgraad;
    private double bgraad;


    public Locatie() {
    }

    public Locatie( int locatieid, String omschrijving, double lgraad, double bgraad) {
        this.locatieid = locatieid;
        this.omschrijving = omschrijving;
        this.lgraad = lgraad;
        this.bgraad = bgraad;
    }


    public int getLocatieid() {
        return locatieid;
    }

    public void setLocatieid(int locatieid) {
        this.locatieid = locatieid;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getNaamkort() {
        return naamkort;
    }

    public void setNaamkort(String naamkort) {
        this.naamkort = naamkort;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public double getLgraad() {
        return lgraad;
    }

    public void setLgraad(double lgraad) {
        this.lgraad = lgraad;
    }

    public double getBgraad() {
        return bgraad;
    }

    public void setBgraad(double bgraad) {
        this.bgraad = bgraad;
    }
}