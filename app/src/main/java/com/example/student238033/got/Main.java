package com.example.student238033.got;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * The type Main.
 */
public class Main extends AppCompatActivity {

    /**
     * The Zaplanuj trase.
     */
    Button zaplanujTrase;
    /**
     * The Zarzadzaj trasami.
     */
    Button zarzadzajTrasami;

    /**
     * On create.
     *
     * @param savedInstanceState     the savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stworzBazeDanych();

        zaplanujTrase = (Button)findViewById(R.id.przyciskZaplanujTrase);
        zaplanujTrase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, ZaplanujTrase.class));
            }
        });
        zarzadzajTrasami = (Button)findViewById(R.id.przyciskZarzadzajTrasamiPunktowanymi);
        zarzadzajTrasami.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this, ZarzadanieTrasami.class));
            }
        });
    }

    /**
     * Stworz baze danych.
     */
    private void stworzBazeDanych() {
        TrasyPunktowaneDB db = new TrasyPunktowaneDB();
//        if(db.dajBazeTras().isEmpty()){
//            db.budujTrasy();
//            db.zapiszTrasyDoPliku(this);
//        }

        if(db.dajBazeTras().isEmpty()){
            db.pobierzTrasyZPliku(this);
        }
        if(db.dajGrupyGorskie().isEmpty()){
            db.budujGrupyGorskie();
        }
        if(db.dajPodgrupyGorskie().isEmpty()) {
            db.budujPodrupyGorskie();
        }
        if(db.dajStopnieTrudnosci().isEmpty()){
            db.budujStopnieTrudnosci();
        }
        if(db.dajZaleznosciGrupIPodgrup().isEmpty()){
            db.budujZaleznosciGrupIPodgrup();
        }
    }
}
