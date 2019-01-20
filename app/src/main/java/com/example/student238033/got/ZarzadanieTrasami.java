package com.example.student238033.got;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

public class ZarzadanieTrasami extends AppCompatActivity implements TrasyAdapter.ItemClickListener{

    TrasyAdapter adapter;
    Button dodaj;
    Button anuluj;
    TrasyPunktowaneDB db;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zarzadanie_trasami);

        zainicjujAtrybuty();

        if(db.dajBazeTras().isEmpty()){
            budujOknoAlertuPustaBaza();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TrasyAdapter(this, db.dajBazeTras());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);


        dodaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZarzadanieTrasami.this, DodajTrase.class));
            }
        });

        anuluj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ZarzadanieTrasami.this, Main.class));
            }
        });
    }

    private void zainicjujAtrybuty() {
        anuluj = (Button)findViewById(R.id.przyciskZarzadzajTrasamiPunktowanymi);
        recyclerView = findViewById(R.id.ListaTras);
        dodaj = (Button)findViewById(R.id.przyciskZaplanujTrase);

        db = new TrasyPunktowaneDB();
    }

    private void budujOknoAlertuPustaBaza() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(ZarzadanieTrasami.this);
        builder.setMessage("Baza tras punktowanych jest pusta. Dodaj trasę.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ZarzadanieTrasami.this, DodajTrase.class));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    @Override
    public void onItemClick(View view, int position) {

    }
}