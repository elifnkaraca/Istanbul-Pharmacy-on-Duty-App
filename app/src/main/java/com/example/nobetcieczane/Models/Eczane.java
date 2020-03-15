package com.example.nobetcieczane.Models;

import java.util.List;

public class Eczane {

    private String tarih;
    private String ilceIsmi;
    private List<EczaneDetay> eczaneDetay;

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getIlceIsmi() {
        return ilceIsmi;
    }

    public void setIlceIsmi(String ilceIsmi) {
        this.ilceIsmi = ilceIsmi;
    }

    public List<EczaneDetay> getEczaneDetay() {
        return eczaneDetay;
    }

    public void setEczaneDetay(List<EczaneDetay> eczaneDetay) {
        this.eczaneDetay = eczaneDetay;
    }

    @Override
    public String toString() {
        return "Eczane{" +
                "tarih='" + tarih + '\'' +
                ", ilceIsmi='" + ilceIsmi + '\'' +
                ", eczaneDetay=" + eczaneDetay +
                '}';
    }
}
