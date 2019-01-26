package com.example.student238033.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import java.util.Map;
import java.util.Objects;

/**
 * The type Modyfikuj trase.
 */
public class ModyfikujTrase extends AppCompatActivity {

    /**
     * The Aktualna.
     */
    TrasaPunktowana aktualna;
    /**
     * The Db.
     */
    TrasyPunktowaneDB db;
    /**
     * The Grupy.
     */
    HashMap<String, GrupaGorska> grupy ;
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
    EditText punktyWejscie ;
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
     * The Flag.
     */
    static int flag;

    /**
     * On create.
     *
     * @param savedInstanceState     the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modyfikuj_trase);

        Intent mIntent = getIntent();
        final int index = mIntent.getIntExtra("index", -1);

        zainicjujAtrybuty(index);

        ArrayList<String> helper = stworzListeGrup();
        budujListeRozwijanaGrupy(helper);

        ArrayList<String> helper2 = stworzPustaListePodgrup();
        budujListeRozwijanaPodgrupy(helper2);

        ArrayList<String> helper3 = stworzListeStopni();
        budujListeRozwijanaStopnie(helper3);

        if (aktualna != null) {
            wstawDaneTrasy(helper, helper3);
        }


        dodaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(sprawdzWpisaneDane()){
                        db.modyfikujTrase(index ,miejscePoczatkowe.getText().toString(),miejsceKoncowe.getText().toString(),grupy.get(listaRozwijanaGrupy.getSelectedItem().toString()),podgrupy.get(listaRozwijanaPodgrupy.getSelectedItem().toString()),stopnie.get(listaRozwijanaStopnie.getSelectedItem().toString()),niepelnosprawni.isChecked(),wpisanyPunkt(punktyWejscie),wpisanyPunkt(punktyZejscie));
                        budujOknoAlertuPomyslnaModyfikacja();
                    }
                }
            });

            anuluj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.zapiszTrasyDoPliku(ModyfikujTrase.this);
                    startActivity(new Intent(ModyfikujTrase.this, Main.class));
                }
            });
        }

    /**
     * Sprawdz wpisane dane.
     */
    private boolean sprawdzWpisaneDane() {
        String start = miejscePoczatkowe.getText().toString();
        String meta = miejsceKoncowe.getText().toString();
        String grupa = listaRozwijanaGrupy.getSelectedItem().toString();
        String podgrupa = listaRozwijanaPodgrupy.getSelectedItem().toString();
        String stopien = listaRozwijanaStopnie.getSelectedItem().toString();
        Integer pktwej = wpisanyPunkt(punktyWejscie);
        Integer pktzej = wpisanyPunkt(punktyZejscie);

        if(pktwej!=null && pktzej!=null) {
            if (sprawdzPunkty(pktwej) && sprawdzPunkty(pktzej)) {
                if (!Objects.equals(start, " ") && !Objects.equals(meta, " ") && !Objects.equals(grupa, "Wybierz...") && !Objects.equals(podgrupa, "Wybierz...") && !Objects.equals(stopien, "Wybierz...")) {
                    return true;
                }
                else {
                    budujOknoAlertuBlad();
                    return false;
                }
            }
            else {
                budujOknoAlertuZlyPunkt();
                return false;
            }
        }
        else{
            budujOknoAlertuBlad();
            return false;
        }
    }

    /**
     * Wpisany punkt Integer.
     *
     * @param pkt the pkt
     */
    private Integer wpisanyPunkt(EditText pkt){
        try {
            Integer p = Integer.parseInt(pkt.getText().toString());
            return p;
        }
        catch (Exception e){
            return null;
        }
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
     * Buduj okno alertu zly punkt.
     */
    private void budujOknoAlertuZlyPunkt() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ModyfikujTrase.this);
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
        builder = new AlertDialog.Builder(ModyfikujTrase.this);
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
     * Buduj okno alertu pomyslna modyfikacja.
     */
    private void budujOknoAlertuPomyslnaModyfikacja() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ModyfikujTrase.this);
        builder.setMessage("Trasa została pomyślnie zmodyfikowana.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder builder;
                        builder = new AlertDialog.Builder(ModyfikujTrase.this);
                        builder.setMessage("Czy chcesz nadal zarządzać trasami?")
                                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(new Intent(ModyfikujTrase.this, ZarzadanieTrasami.class));
                                    }
                                })
                                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        db.zapiszTrasyDoPliku(ModyfikujTrase.this);
                                        startActivity(new Intent(ModyfikujTrase.this, Main.class));
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * Wstaw dane trasy.
     *
     * @param helper the helper
     * @param helper3 the helper3
     */
    private void wstawDaneTrasy(ArrayList<String> helper, ArrayList<String> helper3) {
        miejscePoczatkowe.setText(aktualna.getMiejscePoczatkowe());
        miejsceKoncowe.setText(aktualna.getMiejsceKoncowe());
        punktyWejscie.setText(Integer.toString(aktualna.getPunktyZaWejscie()));
        punktyZejscie.setText(Integer.toString(aktualna.getPunktyZaZejscie()));
        ArrayList<String> mozliwePodgrupy = null;
        GrupaGorska gr = aktualna.getGrupaGorska();
        for (Map.Entry<String, GrupaGorska> entry : grupy.entrySet()) {
            if (entry.getValue().equals(gr)) {
                String grKey = entry.getKey();
                int selection = helper.indexOf(grKey);
                listaRozwijanaGrupy.setSelection(selection);
                mozliwePodgrupy = zaleznosciGrupIPodgrup.get(grKey);
                ArrayList<String> mozliwe = stworzListePodgrup(mozliwePodgrupy);
                budujListeRozwijanaPodgrupy(mozliwe);
                PodgrupaGorska pd = aktualna.getPodgrupaGorska();
                for (Map.Entry<String, PodgrupaGorska> entry2 : podgrupy.entrySet()) {
                    if (entry2.getValue().equals(pd)) {
                        String pdKey = entry2.getKey();
                        int sel = mozliwe.indexOf(pdKey);
                        listaRozwijanaPodgrupy.setSelection(sel);
                    }
                }
            }
        }
        StopienTrudnosci st = aktualna.getStopienTrudnosci();
        for (Map.Entry<String, StopienTrudnosci> entry3 : stopnie.entrySet()) {
            if (entry3.getValue().equals(st)) {
                String stKey = entry3.getKey();
                int selection = helper3.indexOf(stKey);
                listaRozwijanaStopnie.setSelection(selection);
            }
        }
        niepelnosprawni.setChecked(aktualna.isCzyDlaNiepelnosprawnych());
    }

    /**
     * Buduj liste rozwijana stopnie.
     *
     * @param helper3 the helper3
     */
    private void budujListeRozwijanaStopnie(ArrayList<String> helper3) {
        ArrayAdapter<String> StopienAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper3);
        listaRozwijanaStopnie.setAdapter(StopienAdapter);
        listaRozwijanaStopnie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.black));
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
     * @param helper2 the helper2
     */
    private void budujListeRozwijanaPodgrupy(ArrayList<String> helper2) {
        ArrayAdapter<String> PodgrupyAdapter = new ArrayAdapter<>(ModyfikujTrase.this, android.R.layout.simple_spinner_dropdown_item, helper2);
        listaRozwijanaPodgrupy.setAdapter(PodgrupyAdapter);
        listaRozwijanaPodgrupy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Buduj liste rozwijana grupy.
     *
     * @param helper the helper
     */
    private void budujListeRozwijanaGrupy(ArrayList<String> helper) {
        ArrayAdapter<String> GrupyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, helper);
        listaRozwijanaGrupy.setAdapter(GrupyAdapter);
        listaRozwijanaGrupy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.darker_gray));
                } else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(ModyfikujTrase.this, android.R.color.black));
                    ArrayList<String> mozliwe = zaleznosciGrupIPodgrup.get(listaRozwijanaGrupy.getSelectedItem().toString());
                    ArrayList<String> help = stworzListePodgrup(mozliwe);
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) listaRozwijanaPodgrupy.getAdapter();
                    adapter.clear();
                    adapter.addAll(help);
                    adapter.notifyDataSetChanged();
                    if(++flag >1){
                        listaRozwijanaPodgrupy.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /**
     * Stworz liste stopni.
     */
    @NonNull
    private ArrayList<String> stworzListeStopni() {
        String[] stopien = stopnie.keySet().toArray(new String[stopnie.size()]);
        ArrayList<String> helper3 = new ArrayList<String>();
        helper3.add("Wybierz...");
        helper3.addAll(Arrays.asList(stopien));
        return helper3;
    }

    /**
     * Stworz liste podgrup.
     */
    @NonNull
    private ArrayList<String> stworzListePodgrup(ArrayList<String> mozliwePodgrupy) {
        ArrayList<String> helper2 = new ArrayList<String>();
        helper2.add("Wybierz...");
        helper2.addAll(mozliwePodgrupy);
        return helper2;
    }

    /**
     * Stworz pusta liste podgrup.
     */
    @NonNull
    private ArrayList<String> stworzPustaListePodgrup() {
        ArrayList<String> helper2 = new ArrayList<String>();
        helper2.add("Wybierz...");
        return helper2;
    }

    /**
     * Stworz liste grup.
     */
    @NonNull
    private ArrayList<String> stworzListeGrup() {
        String[] grupyGorskie = grupy.keySet().toArray(new String[grupy.size()]);
        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        helper.addAll(Arrays.asList(grupyGorskie));
        return helper;
    }

    /**
     * Zainicjuj atrybuty.
     */
    private void zainicjujAtrybuty(int index) {
        flag=0;
        aktualna = null;
        db = new TrasyPunktowaneDB();
        if (index != -1) {
            aktualna = db.dajBazeTras().get(index);
        }
        grupy = db.dajGrupyGorskie();
        podgrupy = db.dajPodgrupyGorskie();
        stopnie = db.dajStopnieTrudnosci();
        zaleznosciGrupIPodgrup = db.dajZaleznosciGrupIPodgrup();

        niepelnosprawni = (CheckBox) findViewById(R.id.checkBox);

        miejscePoczatkowe = (EditText) findViewById(R.id.miejscePoczatkowe);
        miejsceKoncowe = (EditText) findViewById(R.id.miejsceKoncowe);
        punktyWejscie = (EditText) findViewById(R.id.pktZaWej);
        punktyZejscie = (EditText) findViewById(R.id.pktZaZej);

        listaRozwijanaGrupy = findViewById(R.id.grupaGorska);
        listaRozwijanaPodgrupy = findViewById(R.id.podgrupaGorska);
        listaRozwijanaStopnie = findViewById(R.id.stopienTrudnosci);
        dodaj = (Button) findViewById(R.id.przyciskZaplanujTrase);
        anuluj = (Button) findViewById(R.id.przyciskZarzadzajTrasamiPunktowanymi);
    }

}


