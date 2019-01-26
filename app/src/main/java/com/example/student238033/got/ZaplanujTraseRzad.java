package com.example.student238033.got;

import java.util.ArrayList;

/**
 * Created by Student238033 on 19.01.2019.
 */
public class ZaplanujTraseRzad {

    private ArrayList<TrasaPunktowana> trasy;
    private int wybrana;
    private String wybranyPunkt;


    /**
     * Instantiates a new Zaplanuj trase rzad.
     *
     * @param trasy the trasy
     */
    public ZaplanujTraseRzad(ArrayList<TrasaPunktowana> trasy){
        this.trasy = trasy;
        this.wybrana = 0;
        this.wybranyPunkt = "";
    }

    /**
     * Gets wybrany punkt.
     *
     * @return the wybrany punkt
     */
    public String getWybranyPunkt() {
        return wybranyPunkt;
    }

    /**
     * Sets wybrany punkt.
     *
     * @param wybranyPunkt the wybrany punkt
     */
    public void setWybranyPunkt(String wybranyPunkt) {
        this.wybranyPunkt = wybranyPunkt;
    }

    /**
     * Gets wybrana.
     *
     * @return the wybrana
     */
    public int getWybrana() {
        return wybrana;
    }

    /**
     * Sets wybrana.
     *
     * @param wybrana the wybrana
     */
    public void setWybrana(int wybrana) {
        this.wybrana = wybrana;
    }

    /**
     * Gets trasy.
     *
     * @return the trasy
     */
    public ArrayList<TrasaPunktowana> getTrasy() {
        return trasy;
    }

    /**
     * Sets trasy.
     *
     * @param trasy the trasy
     */
    public void setTrasy(ArrayList<TrasaPunktowana> trasy) {
        this.trasy = trasy;
    }


}
