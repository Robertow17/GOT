package com.example.student238033.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * The type Dodaj trase.
 */
public class DodajTrase extends AppCompatActivity {
    /**
     * The Baza danych.
     */
    TrasyPunktowaneDB bazaDanych = new TrasyPunktowaneDB();
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
     * The Zaleznosci grup i podgrup.
     */
    HashMap<String, ArrayList<String>> zaleznosciGrupIPodgrup;

    /**
     * The Niepelnosprawni.
     */
    CheckBox niepelnosprawni;
    /**
     * The Miejsce poczatkowe.
     */
    EditText miejscePoczatkowe;
    /**
     * The Miejsce koncowe.
     */
    EditText miejsceKoncowe;
    /**
     * The Punkty wejscie.
     */
    EditText punktyWejscie;
    /**
     * The Punkty zejscie.
     */
    EditText punktyZejscie;
    /**
     * The Dodaj.
     */
    Button dodaj;
    /**
     * The Anuluj.
     */
    Button anuluj;

    /**
     * The Lista rozwijana grupy.
     */
    Spinner listaRozwijanaGrupy;
    /**
     * The Lista rozwijana podgrupy.
     */
    Spinner listaRozwijanaPodgrupy;
    /**
     * The Lista rozwijana stopnie.
     */
    Spinner listaRozwijanaStopnie;


    /**
     * On create.
     *
     * @param savedInstanceState     the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodaj_trase);

        zainicjujAtrybuty();

        budujListeRozwijanaGrupy();
        budujNieaktywnaListaRozwijanaPodgrupy();
        budujListeRozwijanaStopnie();

        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = miejscePoczatkowe.getText().toString();
                String meta = miejsceKoncowe.getText().toString();
                String grupa = listaRozwijanaGrupy.getSelectedItem().toString();
                String podgrupa = listaRozwijanaPodgrupy.getSelectedItem().toString();
                String stopien = listaRozwijanaStopnie.getSelectedItem().toString();
                Integer pktwej = wpisanyPunkt(punktyWejscie);
                Integer pktzej = wpisanyPunkt(punktyZejscie);
                try{
                    sprawdzWpisaneDane(start,meta,grupa,podgrupa,stopien,pktwej,pktzej);
                    bazaDanych.dodajTrase(miejscePoczatkowe.getText().toString(),miejsceKoncowe.getText().toString(),grupy.get(listaRozwijanaGrupy.getSelectedItem().toString()),podgrupy.get(listaRozwijanaPodgrupy.getSelectedItem().toString()),stopnie.get(listaRozwijanaStopnie.getSelectedItem().toString()),niepelnosprawni.isChecked(),wpisanyPunkt(punktyWejscie),wpisanyPunkt(punktyZejscie));
                    budujOknoAlertuTrasaDodana();
                }
                catch(NullPointerException e){
                    budujOknoAlertuBlad();
                }
                catch (IllegalArgumentException e) {
                    budujOknoAlertuZlyPunkt();
                }
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bazaDanych.zapiszTrasyDoPliku(DodajTrase.this);
                startActivity(new Intent(DodajTrase.this, Main.class));
            }
        });
    }


    /**
     * Sprawdz wpisane dane boolean.
     *
     * @param start    the start
     * @param meta     the meta
     * @param grupa    the grupa
     * @param podgrupa the podgrupa
     * @param stopien  the stopien
     * @param pktwej   the pktwej
     * @param pktzej   the pktzej
     * @return the boolean
     */
    public boolean sprawdzWpisaneDane(String start, String meta, String grupa, String podgrupa, String stopien, Integer pktwej, Integer pktzej) {

        if(pktwej!=null && pktzej!=null) {
            if (sprawdzPunkty(pktwej) && sprawdzPunkty(pktzej)) {
                if (!Objects.equals(start, " ") && !Objects.equals(meta, " ") && !Objects.equals(grupa, "Wybierz...") && !Objects.equals(podgrupa, "Wybierz...") && !Objects.equals(stopien, "Wybierz...")) {
                    return true;
                }
                else {
                    throw new NullPointerException();
                }
            }
            else {
                throw new IllegalArgumentException();
            }
        }
        else{
            throw new NullPointerException();
        }
    }
//    /**
//     * Sprawdz wpisane dane boolean.
//     *
//     * @return the boolean
//     */
//    public boolean sprawdzWpisaneDane() {
//        String start = miejscePoczatkowe.getText().toString();
//        String meta = miejsceKoncowe.getText().toString();
//        String grupa = listaRozwijanaGrupy.getSelectedItem().toString();
//        String podgrupa = listaRozwijanaPodgrupy.getSelectedItem().toString();
//        String stopien = listaRozwijanaStopnie.getSelectedItem().toString();
//        Integer pktwej = wpisanyPunkt(punktyWejscie);
//        Integer pktzej = wpisanyPunkt(punktyZejscie);
//
//        if(pktwej!=null && pktzej!=null) {
//            if (sprawdzPunkty(pktwej) && sprawdzPunkty(pktzej)) {
//                if (!Objects.equals(start, " ") && !Objects.equals(meta, " ") && !Objects.equals(grupa, "Wybierz...") && !Objects.equals(podgrupa, "Wybierz...") && !Objects.equals(stopien, "Wybierz...")) {
//                    return true;
//                }
//                else {
//                    budujOknoAlertuBlad();
//                    return false;
//                }
//            }
//            else {
//                budujOknoAlertuZlyPunkt();
//                return false;
//            }
//        }
//        else{
//            budujOknoAlertuBlad();
//            return false;
//        }
//    }

    /**
     * Wpisany punkt Integer.
     *
     * @param pkt the pkt
     * @return the Integer
     */
    public Integer wpisanyPunkt(EditText pkt){
        try {
            Integer p = Integer.parseInt(pkt.getText().toString());
            return p;
        }
        catch (Exception e){
            return null;
        }
    }

    /**
     * Buduj okno alertu zly punkt.
     */
    private void budujOknoAlertuZlyPunkt() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(DodajTrase.this);
        builder.setTitle("Błąd!")
                .setMessage("Punkty muszą mieścić się w zakresie od 1 do 20.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Buduj okno alertu blad.
     */
    private void budujOknoAlertuBlad() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(DodajTrase.this);
        builder.setTitle("Błąd!")
                .setMessage("Uzupełnij wszystkie pola.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Buduj okno alertu trasa dodana.
     */
    private void budujOknoAlertuTrasaDodana() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(DodajTrase.this);
        builder.setMessage("Trasa została pomyślnie dodana do bazy.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(DodajTrase.this);
                        builder.setMessage("Czy chcesz nadal zarządzać trasami?")
                                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(DodajTrase.this, ZarzadanieTrasami.class));
                                    }
                                })
                                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(DodajTrase.this, Main.class));
                                        bazaDanych.zapiszTrasyDoPliku(DodajTrase.this);
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
        grupy = bazaDanych.dajGrupyGorskie();
        podgrupy = bazaDanych.dajPodgrupyGorskie();
        stopnie = bazaDanych.dajStopnieTrudnosci();
        zaleznosciGrupIPodgrup = bazaDanych.dajZaleznosciGrupIPodgrup();

        niepelnosprawni = (CheckBox) findViewById(R.id.checkBoxNiepelnosprawni);
        miejscePoczatkowe = (EditText) findViewById(R.id.miejscePoczatkowe);
        miejsceKoncowe = (EditText) findViewById(R.id.miejsceKoncowe);
        punktyWejscie = (EditText) findViewById(R.id.pktZaWej);
        punktyZejscie = (EditText) findViewById(R.id.pktZaZej);
        dodaj = (Button)findViewById(R.id.przyciskZaplanujTrase);
        anuluj = (Button)findViewById(R.id.anuluj);

        listaRozwijanaGrupy = findViewById(R.id.grupaGorska);
        listaRozwijanaPodgrupy = findViewById(R.id.podgrupaGorska);
        listaRozwijanaStopnie = findViewById(R.id.stopienTrudnosci);
    }

    /**
     * Buduj liste rozwijana grupy.
     */
    private void budujListeRozwijanaGrupy() {
        String[] grupyGorskie = grupy.keySet().toArray(new String[grupy.size()]);
        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        helper.addAll(Arrays.asList(grupyGorskie));
        ArrayAdapter<String> GrupyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper);
        listaRozwijanaGrupy.setAdapter(GrupyAdapter);
        listaRozwijanaGrupy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.black));
                    listaRozwijanaPodgrupy.setEnabled(true);
                    budujListeRozwijanaPodgrupy(zaleznosciGrupIPodgrup.get(listaRozwijanaGrupy.getSelectedItem().toString()));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Buduj liste rozwijana podgrupy.
     *
     * @param mozliwePodgrupy the mozliwePodgrupy
     */
    private void budujListeRozwijanaPodgrupy(ArrayList<String> mozliwePodgrupy) {
        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        helper.addAll(mozliwePodgrupy);
        ArrayAdapter<String> PodgrupyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper);
        listaRozwijanaPodgrupy.setAdapter(PodgrupyAdapter);
        listaRozwijanaPodgrupy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Sprawdz punkty boolean.
     *
     * @param punkty the punkty
     * @return the boolean
     */
    public boolean sprawdzPunkty(int punkty){
        if(punkty > 0 && punkty <=20) {
            return true;
        }
        return false;
    }

    /**
     * Buduj nieaktywna liste rozwijana podgrupy.
     */
    private void budujNieaktywnaListaRozwijanaPodgrupy() {
        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        ArrayAdapter<String> PodgrupyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper);
        listaRozwijanaPodgrupy.setAdapter(PodgrupyAdapter);
        listaRozwijanaPodgrupy.setEnabled(false);
    }

    /**
     * Buduj liste rozwijana stopnie.
     */
    private void budujListeRozwijanaStopnie() {
        String[] stopien = stopnie.keySet().toArray(new String[stopnie.size()]);
        ArrayList<String> helper3 = new ArrayList<String>();
        helper3.add("Wybierz...");
        helper3.addAll(Arrays.asList(stopien));
        ArrayAdapter<String> StopienAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper3);
        listaRozwijanaStopnie.setAdapter(StopienAdapter);
        listaRozwijanaStopnie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(DodajTrase.this, android.R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}
