package com.example.student238033.got;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;


/**
 * Created by Student238033 on 20.01.2019.
 */

public class TrasyPunktowanePlik {

    ArrayList<TrasaPunktowana> trasyZPliku;
    ObjectOutputStream outputStream;
    FileOutputStream fos;
    ObjectInputStream inputStream;
    FileInputStream fis;


    public TrasyPunktowanePlik() {
        this.trasyZPliku = new ArrayList<>();
    }

    public TrasyPunktowanePlik(ArrayList<TrasaPunktowana> trasy){
        this.trasyZPliku = trasy;
    }

    public ArrayList<TrasaPunktowana> getTrasyZPliku() {
        return trasyZPliku;
    }

    public void setTrasyZPliku(ArrayList<TrasaPunktowana> trasyZPliku) {
        this.trasyZPliku = trasyZPliku;
    }

    public void zapiszTrasyDoPliku(Context context){
        try {
            fos = context.openFileOutput("t.tmp", Context.MODE_APPEND );
            outputStream = new ObjectOutputStream(fos);
            for (TrasaPunktowana t: trasyZPliku) {
                outputStream.writeObject(t);
            }
            outputStream.flush();
            outputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void pobierzTrasyZPliku(Context context) {
        try {
            fis = context.openFileInput("t.tmp");
            inputStream = new ObjectInputStream(fis);
            while (true) {
                TrasaPunktowana trasa = ((TrasaPunktowana) inputStream.readObject());
                if (trasa != null) {
                    trasyZPliku.add(trasa);
                } else {
                    break;
                }
            }
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
