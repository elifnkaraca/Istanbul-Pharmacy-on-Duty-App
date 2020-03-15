package com.example.nobetcieczane;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.nobetcieczane.Models.EczaneDetay;

import java.util.List;

public class EczaneAdapter extends BaseAdapter {

    List<EczaneDetay> list;
    Context context;
    Activity activity;

    public EczaneAdapter(List<EczaneDetay> list, Context context,Activity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.layout,parent,false);

        TextView eczaneIsim, eczaneAdres, eczaneTel, eczaneFax, eczaneAdresTarif;
        Button haritadaGoster, aramaYap;
        eczaneIsim = convertView.findViewById(R.id.eczaneIsmi);
        eczaneAdres = convertView.findViewById(R.id.eczaneAdres);
        eczaneTel = convertView.findViewById(R.id.eczaneTelefon);
        eczaneFax = convertView.findViewById(R.id.eczaneFax);
        eczaneAdresTarif = convertView.findViewById(R.id.eczaneTarif);
        haritadaGoster = convertView.findViewById(R.id.btn_haritadaGoster);
        aramaYap = convertView.findViewById(R.id.btn_aramaYap);
        eczaneIsim.setText(list.get(position).getEczaneIsmi());
        eczaneAdres.setText(list.get(position).getAdres());
        eczaneTel.setText(list.get(position).getTelefon());
        eczaneFax.setText(list.get(position).getFax());
        eczaneAdresTarif.setText(list.get(position).getTarif());
        aramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setData(Uri.parse("tel:"+list.get(position).getTelefon()));
                activity.startActivity(intent);

            }
        });

        return convertView;
    }
}
