package com.example.student238033.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Wyswietl trase.
 */
public class WyswietlTrase extends AppCompatActivity {
    /**
     * The Niepelnosprawni.
     */
    TextView niepelnosprawni;
    /**
     * The Miejsce poczatkowe.
     */
    TextView miejscePoczatkowe;
    /**
     * The Miejsce koncowe.
     */
    TextView miejsceKoncowe;
    /**
     * The Punkty wejscie.
     */
    TextView punktyWejscie;
    /**
     * The Punkty zejscie.
     */
    TextView punktyZejscie;
    /**
     * The Grupa.
     */
    TextView grupa;
    /**
     * The Podgrupa.
     */
    TextView podgrupa;
    /**
     * The Stopien.
     */
    TextView stopien;
    /**
     * The Anuluj.
     */
    Button anuluj;
    /**
     * The Ok.
     */
    Button ok;

    /**
     * The Grupy.
     */
    HashMap<String, GrupaGorska> grupy;
    /**
     * The Podgrupy.
     */
    HashMap<String, PodgrupaGorska> podgrupy;
    /**
     * The Stopnie.
     */
    HashMap<String, StopienTrudnosci> stopnie;
    /**
     * The Aktualna.
     */
    TrasaPunktowana aktualna;
    /**
     * The Baza danych.
     */
    TrasyPunktowaneDB bazaDanych;
    /**
     * The Trasy.
     */
    ArrayList<TrasaPunktowana> trasy;

    /**
     * On create.
     *
     * @param savedInstanceState     the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wyswietl_trase);

        Intent mIntent = getIntent();
        final int index = mIntent.getIntExtra("index", -1);
        zainicjujAtrybuty(index);

        if (aktualna != null) {
            ustawDaneTrasy(aktualna);
        }

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anuluj();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budujOknoAlertuKontynuacja();
            }
            });
    }


    private void anuluj()
    {
        bazaDanych.zapiszTrasyDoPliku(WyswietlTrase.this);
        startActivity(new Intent(WyswietlTrase.this, Main.class));
    }

    /**
     * ustaw dane trasy.
     *
     * @param aktualna     the aktualna
     */
    private void ustawDaneTrasy(TrasaPunktowana aktualna) {
        miejscePoczatkowe.setText(aktualna.getMiejscePoczatkowe());
        miejsceKoncowe.setText(aktualna.getMiejsceKoncowe());
        punktyWejscie.setText(Integer.toString(aktualna.getPunktyZaWejscie()));
        punktyZejscie.setText(Integer.toString(aktualna.getPunktyZaZejscie()));
        GrupaGorska gr = aktualna.getGrupaGorska();
        for (Map.Entry<String, GrupaGorska> entry : grupy.entrySet()) {
            if (entry.getValue().equals(gr)) {
                String grKey = entry.getKey();
                grupa.setText(grKey);
            }
        }
        PodgrupaGorska pd = aktualna.getPodgrupaGorska();
        for (Map.Entry<String, PodgrupaGorska> entry2 : podgrupy.entrySet()) {
            if (entry2.getValue().equals(pd)) {
                String pdKey = entry2.getKey();
                podgrupa.setText(pdKey);
            }
        }
        StopienTrudnosci st = aktualna.getStopienTrudnosci();
        for (Map.Entry<String, StopienTrudnosci> entry3 : stopnie.entrySet()) {
            if (entry3.getValue().equals(st)) {
                String stKey = entry3.getKey();
                stopien.setText(stKey);
            }
        }
        if (aktualna.isCzyDlaNiepelnosprawnych()) {
            niepelnosprawni.setText("TAK");
        } else {
            niepelnosprawni.setText("NIE");
        }
    }

    /**
     * Buduj okno alertu kontynuacja.
     */
    private void budujOknoAlertuKontynuacja() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(WyswietlTrase.this);
        builder.setMessage("Czy chcesz nadal zarządzać trasami?")
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(WyswietlTrase.this, ZarzadanieTrasami.class));
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        bazaDanych.zapiszTrasyDoPliku(WyswietlTrase.this);
                        startActivity(new Intent(WyswietlTrase.this, Main.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Zainicjuj atrybuty.
     *
     * @param index     the index
     */
    private void zainicjujAtrybuty(int index) {
        aktualna = null;
        bazaDanych = new TrasyPunktowaneDB();
        trasy = bazaDanych.dajBazeTras();
        if (index != -1) {
            aktualna = trasy.get(index);
        }
        grupy = bazaDanych.dajGrupyGorskie();
        podgrupy = bazaDanych.dajPodgrupyGorskie();
        stopnie = bazaDanych.dajStopnieTrudnosci();

        niepelnosprawni = (TextView) findViewById(R.id.niepelnosprawni1);
        miejscePoczatkowe = (TextView) findViewById(R.id.miejscePoczatkowe);
        miejsceKoncowe = (TextView) findViewById(R.id.miejsceKoncowe);
        punktyWejscie = (TextView) findViewById(R.id.punktyWejscie);
        punktyZejscie = (TextView) findViewById(R.id.punktyZejscie);
        grupa = (TextView) findViewById(R.id.grupaGorska);
        podgrupa = (TextView) findViewById(R.id.podgrupaGorska);
        stopien = (TextView) findViewById(R.id.stopienTrudnosci);
        anuluj = (Button) findViewById(R.id.anuluj);
        ok = (Button) findViewById((R.id.przyciskZaplanujTrase));

    }
}
