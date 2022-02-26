package com.echec.echecmulti;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class adapterMortBlanc extends BaseAdapter {
    private Context context;
    private ArrayList<String> MortB;
    private LayoutInflater inflater;

    public adapterMortBlanc(Context context,ArrayList<String> MortB){
        this.context=context;
        this.MortB =MortB;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return MortB.size();
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
        if (MortB.get(i).equals("T"))
            resourceName = "tourb";
        else if (MortB.get(i).equals("C"))
            resourceName = "cavalierb";
        else if (MortB.get(i).equals("F"))
            resourceName = "foub";
        else if (MortB.get(i).equals("D"))
            resourceName = "dameb";
        else if (MortB.get(i).equals("R"))
            resourceName = "roib";
        else if (MortB.get(i).equals("P"))
            resourceName = "pionb";
        return resourceName;

    }
}