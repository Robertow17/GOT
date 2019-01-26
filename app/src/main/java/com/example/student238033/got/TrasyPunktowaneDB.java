package com.example.student238033.got;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The type Trasy punktowane db.
 */
public class TrasyPunktowaneDB {
    /**
     * The Trasy punktowane.
     */
    static ArrayList<TrasaPunktowana> trasyPunktowane = new ArrayList<TrasaPunktowana>();
    /**
     * The Grupy gorskie.
     */
    static HashMap<String, GrupaGorska> grupyGorskie = new HashMap<String, GrupaGorska>();
    /**
     * The Podgrupy gorskie.
     */
    static HashMap<String, PodgrupaGorska> podgrupyGorskie = new HashMap<String, PodgrupaGorska>();
    /**
     * The Stopnie trudnosci.
     */
    static HashMap<String, StopienTrudnosci> stopnieTrudnosci = new HashMap<String, StopienTrudnosci>();
    /**
     * The Zaleznosci grup i podgrup.
     */
    static HashMap<String, ArrayList<String>> zaleznosciGrupIPodgrup = new HashMap<String, ArrayList<String>>();
    /**
     * The Output stream.
     */
    ObjectOutputStream outputStream;
    /**
     * The File output stream.
     */
    FileOutputStream fileOutputStream;
    /**
     * The Input stream.
     */
    ObjectInputStream inputStream;
    /**
     * The File input stream.
     */
    FileInputStream fileInputStream;

    /**
     * Instantiates a new Trasy punktowane db.
     */
    public TrasyPunktowaneDB(){


    }

    /**
     * Dodaj trase.
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
    public void dodajTrase(String start, String koniec, GrupaGorska gr, PodgrupaGorska pd, StopienTrudnosci st, boolean niepelnosprawny, int pktwej, int pktzej){
        trasyPunktowane.add(new TrasaPunktowana(start, koniec, gr, pd, st, niepelnosprawny, pktwej, pktzej));
    }

    /**
     * Modyfikuj trase.
     *
     * @param index           the index
     * @param start           the start
     * @param koniec          the koniec
     * @param gr              the gr
     * @param pd              the pd
     * @param st              the st
     * @param niepelnosprawny the niepelnosprawny
     * @param pktwej          the pktwej
     * @param pktzej          the pktzej
     */
    public void modyfikujTrase(int index, String start, String koniec, GrupaGorska gr, PodgrupaGorska pd, StopienTrudnosci st, boolean niepelnosprawny, int pktwej, int pktzej){
        TrasaPunktowana aktualna = trasyPunktowane.get(index);
        aktualna.setMiejscePoczatkowe(start);
        aktualna.setMiejsceKoncowe(koniec);
        aktualna.setGrupaGorska(gr);
        aktualna.setPodgrupaGorska(pd);
        aktualna.setStopienTrudnosci(st);
        aktualna.setCzyDlaNiepelnosprawnych(niepelnosprawny);
        aktualna.setPunktyZaWejscie(pktwej);
        aktualna.setPunktyZaZejscie(pktzej);
    }

    /**
     * Dodaj baze tras.
     *
     * @param baza the baza
     */
    public void dodajBazeTras(ArrayList<TrasaPunktowana> baza){
        trasyPunktowane = baza;
    }

    /**
     * Usun trase.
     *
     * @param index the index
     */
    public void usunTrase(int index){
        trasyPunktowane.remove(index);
    }

    /**
     * Daj trase trasa punktowana.
     *
     * @param index the index
     * @return the trasa punktowana
     */
    public TrasaPunktowana dajTrase(int index){
        return trasyPunktowane.get(index);
    }

    /**
     * Daj baze tras array list.
     *
     * @return the array list
     */
    public ArrayList<TrasaPunktowana> dajBazeTras()
    {
        return trasyPunktowane;
    }

    /**
     * Daj grupy gorskie hash map.
     *
     * @return the hash map
     */
    public HashMap<String, GrupaGorska> dajGrupyGorskie() {
        return grupyGorskie;
    }

    /**
     * Daj podgrupy gorskie hash map.
     *
     * @return the hash map
     */
    public HashMap<String, PodgrupaGorska> dajPodgrupyGorskie() {
        return podgrupyGorskie;
    }

    /**
     * Daj stopnie trudnosci hash map.
     *
     * @return the hash map
     */
    public HashMap<String, StopienTrudnosci> dajStopnieTrudnosci() {
        return stopnieTrudnosci;
    }

    /**
     * Daj zaleznosci grup i podgrup hash map.
     *
     * @return the hash map
     */
    public HashMap<String, ArrayList<String>> dajZaleznosciGrupIPodgrup() {
        return zaleznosciGrupIPodgrup;
    }

    /**
     * Zapisz trasy do pliku.
     *
     * @param context the context
     */
    public void zapiszTrasyDoPliku(Context context){
        try {
            fileOutputStream = context.openFileOutput("trasy.tmp", Context.MODE_PRIVATE );
            outputStream = new ObjectOutputStream(fileOutputStream);
            for (TrasaPunktowana t: trasyPunktowane) {
                outputStream.writeObject(t);
            }
            outputStream.flush();
            outputStream.close();
            fileOutputStream.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Pobierz trasy z pliku.
     *
     * @param context the context
     * @param nazwaPliku the nazwaPliku
     */
    public void pobierzTrasyZPliku(Context context) {
        try {
            fileInputStream = context.openFileInput("trasy.tmp");
            inputStream = new ObjectInputStream(fileInputStream);
            while (true) {
                TrasaPunktowana trasa = ((TrasaPunktowana) inputStream.readObject());
                if (trasa != null) {
                    trasyPunktowane.add(trasa);
                } else {
                    break;
                }
            }
            inputStream.close();
            fileInputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Buduj trasy.
     */
    public void budujTrasy(){
        trasyPunktowane.add(new TrasaPunktowana("Ptasznik", "Jaszkowa Dolna", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, true, 7, 9));
        trasyPunktowane.add(new TrasaPunktowana("Przełęcz Łaszczowa", "Kłodzko", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, false, 7, 8));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzka Góra", "Kłodzko", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 10,14));
        trasyPunktowane.add(new TrasaPunktowana("Ołdrzychowice Kłodzkie", "Sarnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 5,4));
        trasyPunktowane.add(new TrasaPunktowana("Ołdrzychowice Kłodzkie", "Rogówka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący,true, 4,3));
        trasyPunktowane.add(new TrasaPunktowana("Ołdrzychowice Kłodzkie", "Kłodzko", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, false, 6,8));
        trasyPunktowane.add(new TrasaPunktowana("Rogówka", "Sarnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, true, 4,6));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Różanka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 6,7));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Leśniczówka Kolonia Lesica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 4,6));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Kamieńczyk", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, false, 5,8));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Przełęcz Międzyleska", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 8,9));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Opacz", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 8,11));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Rozdroża pod Kamiennym Garbem", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 10,14));
        trasyPunktowane.add(new TrasaPunktowana("Kłodzko", "Rogówka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, true, 4,5));
        trasyPunktowane.add(new TrasaPunktowana("Długopole Zdrój", "Kłodzko", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, true, 14,12));
        trasyPunktowane.add(new TrasaPunktowana("Jawornica", "Kłodzko", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 3,3));
        trasyPunktowane.add(new TrasaPunktowana("Jawornik Wielki", "Złoty Stok", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, false, 4, 5));
        trasyPunktowane.add(new TrasaPunktowana("Jawornik Wielki", "Lądek Zdrój", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 10, 14));
        trasyPunktowane.add(new TrasaPunktowana("Jawornik Wielki", "Opacz", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, true, 6,8));
        trasyPunktowane.add(new TrasaPunktowana("Rozdroże pod Kamiennym Garbem", "Międzylesie", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 3,3));
        trasyPunktowane.add(new TrasaPunktowana("Leśniczówka Kolonia Lesica", "Międzylesie", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 8,9));
        trasyPunktowane.add(new TrasaPunktowana("Kamieńczyk", "Międzylesie", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 12,14));
        trasyPunktowane.add(new TrasaPunktowana("Solna Jama", "Marianówka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, true, 4,3));
        trasyPunktowane.add(new TrasaPunktowana("Długopole Zdrój", "Marianówka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, false, 6,8));
        trasyPunktowane.add(new TrasaPunktowana("Marianówka", "Bystrzyca Kłodzka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 8,9));
        trasyPunktowane.add(new TrasaPunktowana("Krzyżowa", "Bystrzyca Kłodzka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 10,5));
        trasyPunktowane.add(new TrasaPunktowana("Huta", "Stara Łomnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, true, 8,6));
        trasyPunktowane.add(new TrasaPunktowana("Gorzanów", "Stara Łomnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, false, 3,4));
        trasyPunktowane.add(new TrasaPunktowana("Rogówka", "Stara Łomnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 5,3));
        trasyPunktowane.add(new TrasaPunktowana("Solna Jama", "Jawornica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, false, 4,6));
        trasyPunktowane.add(new TrasaPunktowana("Solna Jama", "Różanka", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 8,10));
        trasyPunktowane.add(new TrasaPunktowana("Łomnicka Równia", "Stara Łomnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, true, 6,6));
        trasyPunktowane.add(new TrasaPunktowana("Borówkowa Góra", "Złoty Stok", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Nieznany, true, 3, 3));
        trasyPunktowane.add(new TrasaPunktowana("Borówkowa Góra", "Jawornik Wielki", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Średniozaawansowany, false, 11, 8));
        trasyPunktowane.add(new TrasaPunktowana("Borówkowa Góra", "Lądek Zdrój", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 4,5));
        trasyPunktowane.add(new TrasaPunktowana("Opacz", "Domaszków", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, true, 8,6));
        trasyPunktowane.add(new TrasaPunktowana("Opacz", "Sarnica", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Zaawansowany, false, 11,14));
        trasyPunktowane.add(new TrasaPunktowana("Międzylesie", "Domaszków", GrupaGorska.Sudety, PodgrupaGorska.Kotlina_Kłodzka, StopienTrudnosci.Początkujący, true, 5,4));
    }

    /**
     * Buduj grupy gorskie.
     */
    public void budujGrupyGorskie() {
        grupyGorskie.put("Tatry i Podtatrze", GrupaGorska.Tatry_i_podtatrze);
        grupyGorskie.put("Sudety", GrupaGorska.Sudety);
        grupyGorskie.put("Beskidy Zachodnie", GrupaGorska.Beskidy_Zachodnie);
        grupyGorskie.put("Góry Świętokrzyskie", GrupaGorska.Góry_Świętokrzyskie);
        grupyGorskie.put("Beskidy Wschodnie", GrupaGorska.Beskidy_Wschodnie);
        grupyGorskie.put("Słowacja", GrupaGorska.Słowacja);
        grupyGorskie.put("Tatry Słowackie", GrupaGorska.Tatry_Słowakie);
    }

    /**
     * Buduj podrupy gorskie.
     */
    public void budujPodrupyGorskie() {
        podgrupyGorskie.put("Tatry Wysokie", PodgrupaGorska.Tatry_Wysokie);
        podgrupyGorskie.put("Tatry Zachodnie", PodgrupaGorska.Tatry_Zachodnie);
        podgrupyGorskie.put("Podtatrze", PodgrupaGorska.Podtatrze);
        podgrupyGorskie.put("Zapadne Tatry", PodgrupaGorska.Zapadne_Tatry);
        podgrupyGorskie.put("Vysoke Tatry", PodgrupaGorska.Vysoke_Tatry);
        podgrupyGorskie.put("Belanske Tatry", PodgrupaGorska.Belanske_Tatry);
        podgrupyGorskie.put("Tatry Słowackie", PodgrupaGorska.Tatry_Słowackie);
        podgrupyGorskie.put("Niske Tatry", PodgrupaGorska.Niske_Tatry);
        podgrupyGorskie.put("Beskid Śląski", PodgrupaGorska.Beskid_Śląski);
        podgrupyGorskie.put("Beskid Żywiecki", PodgrupaGorska.Beskid_Żywiecki);
        podgrupyGorskie.put("Gorce", PodgrupaGorska.Gorce);
        podgrupyGorskie.put("Beskid Wyspowy", PodgrupaGorska.Beskid_Wyspowy);
        podgrupyGorskie.put("Orawa", PodgrupaGorska.Orawa);
        podgrupyGorskie.put("Spisz i Pienieny", PodgrupaGorska.Spisz_i_Pieniny);
        podgrupyGorskie.put("Beskid Sądecki", PodgrupaGorska.Beskid_Sądecki);
        podgrupyGorskie.put("Pogórze Wielickie", PodgrupaGorska.Pogórze_Wielickie);
        podgrupyGorskie.put("Pogórze Wiśnickie", PodgrupaGorska.Pogórze_Wiśnickie);
        podgrupyGorskie.put("Pogórze Rożnowskie", PodgrupaGorska.Pogórze_Rożnowskie);
        podgrupyGorskie.put("Pogórze Ciężkowickie", PodgrupaGorska.Pogórze_Ciężkowickie);
        podgrupyGorskie.put("Beskid Niski część zachodnia", PodgrupaGorska.Beskid_Niski_zach);
        podgrupyGorskie.put("Beskid Niski część wschodnia", PodgrupaGorska.Beskid_Niski_wsch);
        podgrupyGorskie.put("Bieszczady", PodgrupaGorska.Bieszczady);
        podgrupyGorskie.put("Pogórze Strzyżowsko-Dynowskie", PodgrupaGorska.Pogórze_Strzyżowsko_Dynowskie);
        podgrupyGorskie.put("Pogórze Przemyskie", PodgrupaGorska.Pogórze_Przemyskie);
        podgrupyGorskie.put("Góry Świętokrzyskie", PodgrupaGorska.Gór_Świętokrzystkie);
        podgrupyGorskie.put("Góry Izerskie", PodgrupaGorska.Góry_Izerskie);
        podgrupyGorskie.put("Pogórze Izerskie", PodgrupaGorska.Pogórze_Izerskie);
        podgrupyGorskie.put("Karkonosze", PodgrupaGorska.Karkonosze);
        podgrupyGorskie.put("Kotlina Jeleniogórska", PodgrupaGorska.Kotlina_Jeleniogórska);
        podgrupyGorskie.put("Rudawy Janowickie", PodgrupaGorska.Rudawy_Janowickie);
        podgrupyGorskie.put("Pogórze Kaczawskie", PodgrupaGorska.Pogórze_Kaczawskie);
        podgrupyGorskie.put("Góry Kazawskie", PodgrupaGorska.Góry_Kazawskie);
        podgrupyGorskie.put("Góry Kamienne", PodgrupaGorska.Góry_Kamienne);
        podgrupyGorskie.put("Kotlina Kamiennogórska", PodgrupaGorska.Kotlina_Kamiennogórska);
        podgrupyGorskie.put("Góry Wałbrzyskie", PodgrupaGorska.Góry_Wałbrzyskie);
        podgrupyGorskie.put("Góry Sowie", PodgrupaGorska.Góry_Sowie);
        podgrupyGorskie.put("Góry Bardzkie", PodgrupaGorska.Góry_Bardzkie);
        podgrupyGorskie.put("Góry Stołowe", PodgrupaGorska.Góry_Stołowe);
        podgrupyGorskie.put("Góry Orlickie", PodgrupaGorska.Góry_Orlickie);
        podgrupyGorskie.put("Góry Bystrzyckie", PodgrupaGorska.Góry_Bystrzyckie);
        podgrupyGorskie.put("Kotlina Kłodzka", PodgrupaGorska.Kotlina_Kłodzka);
        podgrupyGorskie.put("Masyw Śnieżka", PodgrupaGorska.Masyw_Śnieżka);
        podgrupyGorskie.put("Góry Złote", PodgrupaGorska.Góry_Złote);
        podgrupyGorskie.put("Góry Opawskie", PodgrupaGorska.Góry_Opawskie);
        podgrupyGorskie.put("Wzgórza Strzegomskie", PodgrupaGorska.Wzgórza_Strzegomskie);
        podgrupyGorskie.put("Masyw Ślęży", PodgrupaGorska.Masyw_Ślęży);
        podgrupyGorskie.put("Mała Fatry", PodgrupaGorska.Mała_Fatry);
        podgrupyGorskie.put("Grzbiet Graniczny", PodgrupaGorska.Grzbiet_Graniczny);
        podgrupyGorskie.put("Pieniny", PodgrupaGorska.Pienieny);
    }

    /**
     * Buduj stopnie trudnosci.
     */
    public void budujStopnieTrudnosci() {
        stopnieTrudnosci.put("Początkujący", StopienTrudnosci.Początkujący);
        stopnieTrudnosci.put("Zaawansowany", StopienTrudnosci.Zaawansowany);
        stopnieTrudnosci.put("Średniozaawansowany", StopienTrudnosci.Średniozaawansowany);
        stopnieTrudnosci.put("Nieznany", StopienTrudnosci.Nieznany);
    }

    /**
     * Buduj zaleznosci grup i podgrup.
     */
    public void budujZaleznosciGrupIPodgrup() {
        ArrayList<String> tatryIPodtatrze = new ArrayList<String>();
        tatryIPodtatrze.add("Tatry Wysokie");
        tatryIPodtatrze.add("Tatry Zachodnie");
        tatryIPodtatrze.add("Podtatrze");
        zaleznosciGrupIPodgrup.put("Tatry i Podtatrze", tatryIPodtatrze);
        ArrayList<String> tatrySlowacke = new ArrayList<String>();
        tatrySlowacke.add("Zapadne Tatry");
        tatrySlowacke.add("Vysoke Tatry");
        tatrySlowacke.add("Belanskie Tatry");
        tatrySlowacke.add("Niske Tatry");
        tatrySlowacke.add("Tatry Słowackie");
        zaleznosciGrupIPodgrup.put("Tatry Słowackie", tatrySlowacke);
        ArrayList<String> beskidyZachodnie = new ArrayList<String>();
        beskidyZachodnie.add("Beskid Śląski");
        beskidyZachodnie.add("Beskid Żywiecki");
        beskidyZachodnie.add("Beskid Mały");
        beskidyZachodnie.add("Beskid Średni");
        beskidyZachodnie.add("Gorce");
        beskidyZachodnie.add("Beskid Wyspowy");
        beskidyZachodnie.add("Orawa");
        beskidyZachodnie.add("Spisz i Pieniny");
        beskidyZachodnie.add("Beskid Sądecki");
        beskidyZachodnie.add("Pogórze Wielickie");
        beskidyZachodnie.add("Pogórze Wiśnickie");
        beskidyZachodnie.add("Pogórze Różnowskie");
        zaleznosciGrupIPodgrup.put("Beskidy Zachodnie", beskidyZachodnie);
        ArrayList<String> beskidyWschodnie = new ArrayList<String>();
        beskidyWschodnie.add("Pogórze Ciężkowickie");
        beskidyWschodnie.add("Beskid Niski część zachodnia");
        beskidyWschodnie.add("Beskid Niski część wschodnia");
        beskidyWschodnie.add("Bieszczady");
        beskidyWschodnie.add("Pogórze Strzyżowsko-Dynowskie");
        beskidyWschodnie.add("Pogórze Przemyskie");
        zaleznosciGrupIPodgrup.put("Beskidy Wschodnie", beskidyWschodnie);
        ArrayList<String> gorySwietokrzyskie = new ArrayList<String>();
        gorySwietokrzyskie.add("Góry Świętokrzyskie");
        zaleznosciGrupIPodgrup.put("Góry Świętokrzyskie", gorySwietokrzyskie);
        ArrayList<String> sudety = new ArrayList<String>();
        sudety.add("Góry Izerskie");
        sudety.add("Pogórze Izerskie");
        sudety.add("Karkonosze");
        sudety.add("Kotlina Jeleniogórska");
        sudety.add("Rudawy Janowickie");
        sudety.add("Góry Kaczawskie");
        sudety.add("Podórze Kaczawskie");
        sudety.add("Kotlina Kamiennogórska");
        sudety.add("Góry Kamienne");
        sudety.add("Góry Wałbrzyskie");
        sudety.add("Góry Sowie");
        sudety.add("Góry Bardzkie");
        sudety.add("Góry Stołowe");
        sudety.add("Góry Orlickie");
        sudety.add("Kotlina Kłodzka");
        sudety.add("Masyw Śnieżnika");
        sudety.add("Góry Złote");
        sudety.add("Góry Opawskie");
        sudety.add("Wzgórza Strzegomskie");
        sudety.add("Masyw Ślęży");
        zaleznosciGrupIPodgrup.put("Sudety", sudety);
        ArrayList<String> slowacja = new ArrayList<String>();
        slowacja.add("Mała Fatra");
        slowacja.add("Grzbiet Graniczny");
        slowacja.add("Pieniny");
        zaleznosciGrupIPodgrup.put("Słowacja", slowacja);
    }
}
