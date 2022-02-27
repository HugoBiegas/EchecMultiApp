package com.echec.echecmulti.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.echec.echecmulti.R;

import java.util.ArrayList;

public class adapterMortNoir extends BaseAdapter {
    private Context context;
    private ArrayList<String> MortN;
    private LayoutInflater inflater;


    public adapterMortNoir(Context context,ArrayList<String> MortN){
        this.context=context;
        this.MortN =MortN;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MortN.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.adapter_layout_mort,null);
        ImageView image = view.findViewById(R.id.item_icon_mort);
        String resourceName=piécePose(i);
        int resId = context.getResources().getIdentifier(resourceName, "drawable",context.getPackageName());
        image.setImageResource(resId);

        return view;
    }

    private String piécePose(int i) {
        String resourceName="";
        if (MortN.get(i).equals("T"))
            resourceName = "tourn";
        else if (MortN.get(i).equals("C"))
            resourceName = "cavaliern";
        else if (MortN.get(i).equals("F"))
            resourceName = "foun";
        else if (MortN.get(i).equals("D"))
            resourceName = "damen";
        else if (MortN.get(i).equals("R"))
            resourceName = "roin";
        else if (MortN.get(i).equals("P"))
            resourceName = "pionn";
        return resourceName;
    }
}