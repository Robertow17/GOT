package com.example.student238033.got;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Student238033 on 18.01.2019.
 */
public class ZaplanujTraseAdapter extends RecyclerView.Adapter<ZaplanujTraseAdapter.ViewHolder> {

    private Context context;
    private ArrayList<ZaplanujTraseRzad> mData;
    private LayoutInflater mInflater;
    private ZaplanujTraseAdapter.ItemClickListener mClickListener;
    private String[] alfabet;

    /**
     * Instantiates a new Zaplanuj trase adapter.
     *
     * @param context the context
     * @param data    the data
     */
    ZaplanujTraseAdapter(Context context, ArrayList<ZaplanujTraseRzad> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.alfabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "W", "X", "Y", "Z"};
    }

    /**
     * On create view holder ZaplanujTraseAdapter.ViewHolder.
     *
     * @param parent     the parent
     * @param viewType  the viewType
     */
    @Override
    public ZaplanujTraseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.zaplanuj_trase_row, parent, false);
        return new ZaplanujTraseAdapter.ViewHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder     the holder
     * @param position   the position
     */
    @Override
    public void onBindViewHolder(final ZaplanujTraseAdapter.ViewHolder holder, final int position) {
        final ZaplanujTraseRzad trasy= mData.get(position);
        String pkt = alfabet[position];
        holder.punkt.setText(pkt);

        ArrayList<String> helper = new ArrayList<String>();
        helper.add("Wybierz...");
        uzupelnijListeMozliwychPunktow(position, trasy, helper);

        ArrayAdapter<String> PunktyTrasyAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, helper);
        holder.lista.setAdapter(PunktyTrasyAdapter);
        if(trasy.getWybrana()!=0){
            holder.lista.setSelection(trasy.getWybrana());
            holder.lista.setEnabled(false);
        }
        holder.lista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pozycja, long id) {
                if (pozycja == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
                }
                else {
                    ((TextView) view).setTextColor(ContextCompat.getColor(context, android.R.color.black));
                    if (!Objects.equals(trasy.getWybranyPunkt(), "")) {
                        if (position != 0) {
                            ((ZaplanujTrase) context).usunTrase();
                        }
                    }
                    ustawWybrane(pozycja);
                    if (position == 0) {
                        if (mData.size() < 2) {
                            ((ZaplanujTrase) context).dodajPunkt();
                        }
                    }
                    else {
                        znajdzTrase();
                    }

                }
            }

            /**
             * Znajdz trase.
             */
            private void znajdzTrase() {
                String punktStartu = mData.get(position - 1).getWybranyPunkt();
                String punktMety = holder.lista.getSelectedItem().toString();
                for (TrasaPunktowana t : trasy.getTrasy()) {
                    if ((Objects.equals(t.getMiejscePoczatkowe(), punktStartu) && Objects.equals(t.getMiejsceKoncowe(), punktMety)) || ((Objects.equals(t.getMiejsceKoncowe(), punktStartu) && Objects.equals(t.getMiejscePoczatkowe(), punktMety)))) {
                        ((ZaplanujTrase) context).dodajTrase(t);
                    }
                }
            }

            /**
             * Ustaw wybrane.
             *
             * @param pozycja the pozycja
             */
            private void ustawWybrane(int pozycja) {
                trasy.setWybrana(pozycja);
                trasy.setWybranyPunkt(holder.lista.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }

    /**
     * Uzupelnij liste mozliwych punktow.
     *
     * @param position     the position
     * @param trasy         the trasy
     * @param helper        the helper
     */
    private void uzupelnijListeMozliwychPunktow(int position, ZaplanujTraseRzad trasy, ArrayList<String> helper) {
        if(position == 0 ){
            for (TrasaPunktowana t : trasy.getTrasy()){
                if(!helper.contains(t.getMiejscePoczatkowe()))
                helper.add(t.getMiejscePoczatkowe());
            }
        }
        else{
            String punktStartu =mData.get(position-1).getWybranyPunkt();
            for (TrasaPunktowana t : trasy.getTrasy()){
                if(!helper.contains(t.getMiejsceKoncowe()) && !Objects.equals(t.getMiejsceKoncowe(), punktStartu))
                    helper.add(t.getMiejsceKoncowe());
                if(!helper.contains(t.getMiejscePoczatkowe()) && !Objects.equals(t.getMiejscePoczatkowe(), punktStartu))
                    helper.add(t.getMiejscePoczatkowe());
            }
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * The type View holder.
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The Punkt.
         */
        TextView punkt;
        /**
         * The Lista.
         */
        Spinner lista;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            punkt = itemView.findViewById(R.id.okresleniePunktu);
            lista= itemView.findViewById(R.id.listaMozliwychPunktow);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    /**
     * Gets item.
     *
     * @param id the id
     * @return the item
     */
    ZaplanujTraseRzad getItem(int id) {
        return mData.get(id);
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
    void setClickListener(ZaplanujTraseAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * The interface Item click listener.
     */
    public interface ItemClickListener {
        /**
         * On item click.
         *
         * @param view     the view
         * @param position the position
         */
        void onItemClick(View view, int position);
    }
}
