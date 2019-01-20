package com.example.student238033.got;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Main extends AppCompatActivity {

    Button zaplanujTrase;
    Button zarzadzajTrasami;
    Button bt3;
    Button bt4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TrasyPunktowaneDB db = new TrasyPunktowaneDB();
//        if(db.dajBazeTras().isEmpty()){
//            db.budujTrasy();
//        }
//
//        TrasyPunktowanePlik helper = new TrasyPunktowanePlik(db.dajBazeTras());
//        helper.zapiszTrasyDoPliku(this);

        if(db.dajBazeTras().isEmpty()){
            TrasyPunktowanePlik helper = new TrasyPunktowanePlik();
            helper.pobierzTrasyZPliku(this);
            db.dodajBazeTras(helper.getTrasyZPliku());
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
        //bt3 = (Button)findViewById(R.id.PrzyciskZarzadzajPowiadomieniami);
       // bt4 = (Button)findViewById(R.id.button4);
    }



}
