package com.example.student238033.got;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Student238033 on 04.01.2019.
 */
public class TrasyAdapter extends RecyclerView.Adapter<TrasyAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TrasaPunktowana> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;


    /**
     * Instantiates a new Trasy adapter.
     *
     * @param context the context
     * @param data    the data
     */
    TrasyAdapter(Context context, ArrayList<TrasaPunktowana> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    /**
     * On create view holder ViewHolder.
     *
     * @param parent     the parent
     * @param viewType the viewType
     * @return viewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.trasa_row, parent, false);
        return new ViewHolder(view);
    }

    /**
     * On bind view holder.
     *
     * @param holder     the holder
     * @param position the position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TrasaPunktowana trasa = mData.get(position);
        String tekst = trasa.getMiejscePoczatkowe()+"-"+trasa.getMiejsceKoncowe();
        holder.myTextView.setText(tekst);

        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context , holder.buttonViewOption);
                popup.inflate(R.menu.menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.modyfikuj:
                                Intent i = new Intent(context, ModyfikujTrase.class);
                                i.putExtra("index", position);
                                context.startActivity(i);
                                return true;
                            case R.id.wyswietl:
                                Intent i1 = new Intent(context, WyswietlTrase.class);
                                i1.putExtra("index", position);
                                context.startActivity(i1);
                                return true;
                            case R.id.usun:
                                budujOknoAlertuUsun(position);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popup.show();
            }
        });
    }

    /**
     * Buduj okno alertu usun.
     */
    private void budujOknoAlertuUsun(final int position) {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Czy na pewno chcesz usunąć wybraną trasę?")
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mData.remove(position);

                        budujOknoAlertuPomyslneUsuniecie();
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        budujOknoAlertuPrzerwanie();
                    }
                })
                .show();
    }

    /**
     * Buduj okno alertu przerwanie.
     */
    private void budujOknoAlertuPrzerwanie() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Akcja została przerwana.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                       budujOknoAlertuKontynuacja();
                    }
                })
                .show();
    }

    /**
     * Buduj okno alertu pomyslne usuniecie.
     */
    private void budujOknoAlertuPomyslneUsuniecie() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Trasa została pomyślnie usunięta.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        budujOknoAlertuKontynuacja();
                    }
                })
                .show();
    }

    /**
     * Buduj okno alertu kontynuacja.
     */
    private void budujOknoAlertuKontynuacja() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Czy chcesz nadal zarządzać trasami?")
                .setPositiveButton("TAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, ZarzadanieTrasami.class));
                    }
                })
                .setNegativeButton("NIE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(context, Main.class));
                    }
                })
                .show();
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    /**
     * The type View holder.
     */
// stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        /**
         * The My text view.
         */
        TextView myTextView;
        /**
         * The Button view option.
         */
        TextView buttonViewOption;

        /**
         * Instantiates a new View holder.
         *
         * @param itemView the item view
         */
        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.tekst);
            buttonViewOption = itemView.findViewById(R.id.strzalka);
            itemView.setOnClickListener(this);
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
// convenience method for getting data at click position
    TrasaPunktowana getItem(int id) {
        return mData.get(id);
    }

    /**
     * Sets click listener.
     *
     * @param itemClickListener the item click listener
     */
// allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    /**
     * The interface Item click listener.
     */
// parent activity will implement this method to respond to click events
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
