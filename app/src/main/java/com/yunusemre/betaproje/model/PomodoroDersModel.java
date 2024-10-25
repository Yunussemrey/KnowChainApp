package com.yunusemre.betaproje.model;

public class PomodoroDersModel {
    String dersAdi;
    String tarih;
    String calismaDurum;
    String email;

    public PomodoroDersModel() {
    }

    public PomodoroDersModel(String dersAdi, String tarih, String calismaDurum,String email) {
        this.dersAdi = dersAdi;
        this.tarih = tarih;
        this.calismaDurum = calismaDurum;
        this.email = email;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getCalismaDurum() {
        return calismaDurum;
    }

    public void setCalismaDurum(String calismaDurum) {
        this.calismaDurum = calismaDurum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
