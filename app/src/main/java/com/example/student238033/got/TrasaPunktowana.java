package com.example.student238033.got;

import java.io.Serializable;

/**
 * Created by Student238033 on 04.01.2019.
 */
public class TrasaPunktowana implements Serializable {

    private String miejscePoczatkowe;
    private String miejsceKoncowe;
    private GrupaGorska grupaGorska;
    private PodgrupaGorska podgrupaGorska;
    private StopienTrudnosci stopienTrudnosci;
    private boolean czyDlaNiepelnosprawnych;
    private int punktyZaWejscie;
    private int punktyZaZejscie;

    /**
     * Instantiates a new Trasa punktowana.
     *
     * @param start           the start
     * @param koniec          the koniec
     * @param gr              the gr
     * @param pd              the pd
     * @param st              the st
     * @param niepelnosprawny the niepelnosprawny
     * @param pktwej          the pktwej
     * @param pktzej          the pktzej
     */
    public TrasaPunktowana(String start, String koniec, GrupaGorska gr, PodgrupaGorska pd, StopienTrudnosci st, boolean niepelnosprawny, int pktwej, int pktzej){
        miejscePoczatkowe = start;
        miejsceKoncowe = koniec;
        grupaGorska = gr;
        podgrupaGorska = pd;
        stopienTrudnosci = st;
        czyDlaNiepelnosprawnych = niepelnosprawny;
        punktyZaWejscie = pktwej;
        punktyZaZejscie = pktzej;
    }


    /**
     * Gets miejsce poczatkowe.
     *
     * @return the miejsce poczatkowe
     */
    public String getMiejscePoczatkowe() {
        return miejscePoczatkowe;
    }

    /**
     * Sets miejsce poczatkowe.
     *
     * @param miejscePoczatkowe the miejsce poczatkowe
     */
    public void setMiejscePoczatkowe(String miejscePoczatkowe) {
        this.miejscePoczatkowe = miejscePoczatkowe;
    }

    /**
     * Gets miejsce koncowe.
     *
     * @return the miejsce koncowe
     */
    public String getMiejsceKoncowe() {
        return miejsceKoncowe;
    }

    /**
     * Sets miejsce koncowe.
     *
     * @param miejsceKoncowe the miejsce koncowe
     */
    public void setMiejsceKoncowe(String miejsceKoncowe) {
        this.miejsceKoncowe = miejsceKoncowe;
    }

    /**
     * Gets grupa gorska.
     *
     * @return the grupa gorska
     */
    public GrupaGorska getGrupaGorska() {
        return grupaGorska;
    }

    /**
     * Sets grupa gorska.
     *
     * @param grupaGorska the grupa gorska
     */
    public void setGrupaGorska(GrupaGorska grupaGorska) {
        this.grupaGorska = grupaGorska;
    }

    /**
     * Gets podgrupa gorska.
     *
     * @return the podgrupa gorska
     */
    public PodgrupaGorska getPodgrupaGorska() {
        return podgrupaGorska;
    }

    /**
     * Sets podgrupa gorska.
     *
     * @param podgrupaGorska the podgrupa gorska
     */
    public void setPodgrupaGorska(PodgrupaGorska podgrupaGorska) {
        this.podgrupaGorska = podgrupaGorska;
    }

    /**
     * Gets stopien trudnosci.
     *
     * @return the stopien trudnosci
     */
    public StopienTrudnosci getStopienTrudnosci() {
        return stopienTrudnosci;
    }

    /**
     * Sets stopien trudnosci.
     *
     * @param stopienTrudnosci the stopien trudnosci
     */
    public void setStopienTrudnosci(StopienTrudnosci stopienTrudnosci) {
        this.stopienTrudnosci = stopienTrudnosci;
    }

    /**
     * Is czy dla niepelnosprawnych boolean.
     *
     * @return the boolean
     */
    public boolean isCzyDlaNiepelnosprawnych() {
        return czyDlaNiepelnosprawnych;
    }

    /**
     * Sets czy dla niepelnosprawnych.
     *
     * @param czyDlaNiepelnosprawnych the czy dla niepelnosprawnych
     */
    public void setCzyDlaNiepelnosprawnych(boolean czyDlaNiepelnosprawnych) {
        this.czyDlaNiepelnosprawnych = czyDlaNiepelnosprawnych;
    }

    /**
     * Gets punkty za wejscie.
     *
     * @return the punkty za wejscie
     */
    public int getPunktyZaWejscie() {
        return punktyZaWejscie;
    }

    /**
     * Sets punkty za wejscie.
     *
     * @param punkty the punkty
     */
    public void setPunktyZaWejscie(int punkty) {
        this.punktyZaWejscie = punkty;
    }

    /**
     * Gets punkty za zejscie.
     *
     * @return the punkty za zejscie
     */
    public int getPunktyZaZejscie() {
        return punktyZaZejscie;
    }

    /**
     * Sets punkty za zejscie.
     *
     * @param punkty the punkty
     */
    public void setPunktyZaZejscie(int punkty) {
        this.punktyZaZejscie = punkty;
    }
}
