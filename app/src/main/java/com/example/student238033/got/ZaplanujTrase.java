package com.example.student238033.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * The type Zaplanuj trase.
 */
public class ZaplanujTrase extends AppCompatActivity implements ZaplanujTraseAdapter.ItemClickListener{

    /**
     * The Baza danych.
     */
    TrasyPunktowaneDB bazaDanych;
    /**
     * The Baza tras.
     */
    ArrayList<TrasaPunktowana> bazaTras;
    /**
     * The Podgrupy.
     */
    HashMap<String, PodgrupaGorska> podgrupy;
    /**
     * The Punkty na trasie.
     */
    ArrayList<ZaplanujTraseRzad> punktyNaTrasie;
    /**
     * The Trasy w podgrupie.
     */
    ArrayList<TrasaPunktowana> trasyWPodgrupie;
    /**
     * The Suma punktow.
     */
    int sumaPunktow;
    /**
     * The Zaplanowana trasa.
     */
    ArrayList<TrasaPunktowana> zaplanowanaTrasa;

    /**
     * The Przegladaj trasy button.
     */
    Button przegladajTrasyButton;
    /**
     * The Dodaj punkt button.
     */
    Button dodajPunktButton;
    /**
     * The Anuluj.
     */
    Button anuluj;
    /**
     * The Zapisz.
     */
    Button zapisz;
    /**
     * The Punkty tekst.
     */
    TextView punktyTekst;
    /**
     * The Punkty.
     */
    TextView punkty;
    /**
     * The Lista podgrup gorskich.
     */
    Spinner listaPodgrupGorskich;
    /**
     * The Widok trasy.
     */
    RecyclerView widokTrasy;
    /**
     * The Adapter widoku.
     */
    ZaplanujTraseAdapter adapterWidoku;


    /**
     * On create.
     *
     * @param savedInstanceState     the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaplanuj_trase);

        zainicjujAtrybuty();
        
        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZaplanujTrase.this, Main.class));
            }
        });

        zapisz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                budujOknoAlertuZapisz();

            }
        });

        dodajPunktButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dodajPunkt();
            }
        });

        ArrayAdapter<String> PodgrupyAdapter = stworzAdapterDlaListyPodgrupGorskich();
        listaPodgrupGorskich.setAdapter(PodgrupyAdapter);
        listaPodgrupGorskich.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ZaplanujTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ZaplanujTrase.this, android.R.color.black));
                    wybierzTrasyZPodgrupy();
                    listaPodgrupGorskich.setEnabled(false);
                    ZaplanujTraseRzad pierwszyPunkt =new ZaplanujTraseRzad(trasyWPodgrupie);
                    punktyNaTrasie.add(pierwszyPunkt);
                    stworzWidokTrasy(punktyNaTrasie);
                    zmienWidok();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


    }

    /**
     * Stworz adapter dla listy podgrup gorskich ArrayAdapter<String>.
     */
    @NonNull
    private ArrayAdapter<String> stworzAdapterDlaListyPodgrupGorskich() {
        String[] podgrupyGorskie = podgrupy.keySet().toArray(new String[podgrupy.size()]);
        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        helper.addAll(Arrays.asList(podgrupyGorskie));
        return new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper);
    }

    /**
     * Zmien widok.
     */
    private void zmienWidok() {
        przegladajTrasyButton.setVisibility(View.INVISIBLE);
        punktyTekst.setVisibility(View.VISIBLE);
        punkty.setVisibility(View.VISIBLE);
        dodajPunktButton.setVisibility(View.VISIBLE);
    }

    /**
     * Wybierz trasy z podgrupy.
     */
    private void wybierzTrasyZPodgrupy() {
        String podgrupa = listaPodgrupGorskich.getSelectedItem().toString();
        for (TrasaPunktowana t: bazaTras ) {
            if(t.getPodgrupaGorska() == podgrupy.get(podgrupa)){
                trasyWPodgrupie.add(t);
            }
        }
    }

    /**
     * Stworz widok trasy.
     *
     * @param rzedyWidoku the rzedyWidoku
     */
    private void stworzWidokTrasy(ArrayList<ZaplanujTraseRzad> rzedyWidoku) {
        widokTrasy.setLayoutManager(new LinearLayoutManager(ZaplanujTrase.this));
        adapterWidoku = new ZaplanujTraseAdapter(ZaplanujTrase.this, rzedyWidoku);
        adapterWidoku.setClickListener(ZaplanujTrase.this);
        widokTrasy.setAdapter(adapterWidoku);
    }

    /**
     * Buduj okno alertu zapisz.
     */
    private void budujOknoAlertuZapisz() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ZaplanujTrase.this);
        builder.setMessage("Twoja trasa została zapisana.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ZaplanujTrase.this);
                        builder.setMessage("Czy chcesz zaplanować kolejną trasę?")
                                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ZaplanujTrase.this, ZaplanujTrase.class));
                                    }
                                })
                                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ZaplanujTrase.this, Main.class));
                                    }
                                })
                                .show();
                    }
                })
                .show();
    }

    /**
     * Zainicjuj atrybuty.
     */
    private void zainicjujAtrybuty() {
        bazaDanych = new TrasyPunktowaneDB();
        bazaTras = bazaDanych.dajBazeTras();
        podgrupy = bazaDanych.dajPodgrupyGorskie();
        punktyNaTrasie = new ArrayList<ZaplanujTraseRzad>();
        zaplanowanaTrasa = new ArrayList<TrasaPunktowana>();
        trasyWPodgrupie = new ArrayList<TrasaPunktowana>();
        sumaPunktow =0;

        przegladajTrasyButton = findViewById(R.id.przyciskZaplanujTrase);
        dodajPunktButton = findViewById(R.id.PrzyciskDodajPunkt);
        punktyTekst = findViewById(R.id.punktyTekst);
        punkty = findViewById(R.id.punkty);
        listaPodgrupGorskich = findViewById(R.id.listaPodgrupGorskich);
        anuluj = findViewById(R.id.przyciskZarzadzajTrasamiPunktowanymi);
        zapisz = findViewById((R.id.PrzyciskZarzadzajPowiadomieniami));
        widokTrasy = findViewById(R.id.punktyNaTrasie);
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * Dodaj punkt.
     */
    public void dodajPunkt(){
        ZaplanujTraseRzad ostatnia = punktyNaTrasie.get(punktyNaTrasie.size()-1);
        String ostatniPunkt = ostatnia.getWybranyPunkt();
        if(!Objects.equals(ostatniPunkt,"")){
            ArrayList<TrasaPunktowana> mozliweTrasy = new ArrayList<TrasaPunktowana>();
            for (TrasaPunktowana t : trasyWPodgrupie) {
                if(Objects.equals(ostatniPunkt, t.getMiejscePoczatkowe()) || Objects.equals(ostatniPunkt, t.getMiejsceKoncowe())) {
                    mozliweTrasy.add(t);
                }
            }
            ZaplanujTraseRzad nowaLista = new ZaplanujTraseRzad(mozliweTrasy);
            punktyNaTrasie.add(nowaLista);
            adapterWidoku.notifyDataSetChanged();
        }
        else {
            budujOknoAlertuBlad();
        }
    }

    /**
     * Buduj okno alertu blad.
     */
    private void budujOknoAlertuBlad() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ZaplanujTrase.this);
        builder.setTitle("Błąd!")
                .setMessage("Wybierz najpierw poprzedni punkt pośredni!")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {}
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Aktualizuj sume punktow.
     *
     * @param pkt the pkt
     */
    public void aktualizujSumePunktow(int pkt){
        punkty.setText(Integer.toString(pkt));
    }

    /**
     * Dodaj trase.
     *
     * @param t the t
     */
    public void dodajTrase(TrasaPunktowana t){
        zaplanowanaTrasa.add(t);
        if(zaplanowanaTrasa.size()==1){
            sumaPunktow = t.getPunktyZaWejscie();
        }
        else
        {
            String punkt = punktyNaTrasie.get(punktyNaTrasie.size()-1).getWybranyPunkt();
            if(Objects.equals(t.getMiejscePoczatkowe(), punkt)){
                sumaPunktow = sumaPunktow + t.getPunktyZaZejscie();
            }
            else{
                sumaPunktow = sumaPunktow + t.getPunktyZaWejscie();
            }
        }
        aktualizujSumePunktow(sumaPunktow);
    }

    /**
     * Usun trase.
     */
    public void usunTrase(){
        TrasaPunktowana usuwana = zaplanowanaTrasa.get(zaplanowanaTrasa.size()-1);
        String nieaktualnyPunkt = punktyNaTrasie.get(punktyNaTrasie.size()-1).getWybranyPunkt();
        if(Objects.equals(usuwana.getMiejscePoczatkowe(), nieaktualnyPunkt)){
            sumaPunktow = sumaPunktow - usuwana.getPunktyZaZejscie();
        }
        else{
            sumaPunktow = sumaPunktow - usuwana.getPunktyZaWejscie();
        }
        aktualizujSumePunktow(sumaPunktow);
        zaplanowanaTrasa.remove(zaplanowanaTrasa.size()-1);
    }
}
