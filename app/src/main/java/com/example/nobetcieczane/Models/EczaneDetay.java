package com.example.nobetcieczane.Models;

public class EczaneDetay {

    private String eczaneIsmi;
    private String adres;
    private String telefon;
    private String fax;
    private String tarif;

    public String getEczaneIsmi() {
        return eczaneIsmi;
    }

    public void setEczaneIsmi(String eczaneIsmi) {
        this.eczaneIsmi = eczaneIsmi;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    @Override
    public String toString() {
        return "EczaneDetay{" +
                "eczaneIsmi='" + eczaneIsmi + '\'' +
                ", adres='" + adres + '\'' +
                ", telefon='" + telefon + '\'' +
                ", fax='" + fax + '\'' +
                ", tarif='" + tarif + '\'' +
                '}';
    }
}
