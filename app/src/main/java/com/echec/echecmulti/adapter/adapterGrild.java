package com.echec.echecmulti.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.echec.echecmulti.R;

import java.util.ArrayList;
import java.util.List;

public class adapterGrild extends BaseAdapter {
    private Context context;
    private String[] Bord;
    private String[] color;
    private LayoutInflater inflater;
    private int couleur;
    ArrayList<String> colorActionPion;

    public adapterGrild(Context context,String[] Bord,String[] color,ArrayList<String> colorActionPion){
        this.context = context;
        this.Bord = Bord;
        this.color =color;
        this.colorActionPion = colorActionPion;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {// demande le nombre d'itéme sur lécran
        return Bord.length;
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
        view = inflater.inflate(R.layout.adapter_layout,null);
        ImageView image = view.findViewById(R.id.item_icon);
        String resourceName=piécePose(i);
        if (!Bord[i].equals("")){
            int resId = context.getResources().getIdentifier(resourceName, "drawable",context.getPackageName());
            image.setImageResource(resId);
        }

        int color;
        color = couleur(i);
        if (piéceColorDeplacement(i) !=0)
            color = piéceColorDeplacement(i);
        view.setBackgroundColor(color);

    return view;
    }
    private String piécePose(int i){
        String resourceName="";
        if (Bord[i].equals("T") && color[i].equals("B"))
            resourceName = "tourb";
        else if (Bord[i].equals("T") && color[i].equals("N"))
            resourceName = "tourn";
        else if (Bord[i].equals("C") && color[i].equals("B"))
            resourceName = "cavalierb";
        else if (Bord[i].equals("C") && color[i].equals("N"))
            resourceName = "cavaliern";
        else if (Bord[i].equals("F") && color[i].equals("B"))
            resourceName = "foub";
        else if (Bord[i].equals("F") && color[i].equals("N"))
            resourceName = "foun";
        else if (Bord[i].equals("D") && color[i].equals("B"))
            resourceName = "dameb";
        else if (Bord[i].equals("D") && color[i].equals("N"))
            resourceName = "damen";
        else if (Bord[i].equals("R") && color[i].equals("B"))
            resourceName = "roib";
        else if (Bord[i].equals("R") && color[i].equals("N"))
            resourceName = "roin";
        else if (Bord[i].equals("P") && color[i].equals("B"))
            resourceName = "pionb";
        else if (Bord[i].equals("P") && color[i].equals("N"))
            resourceName = "pionn";
        return resourceName;
    }

    private int couleur(int i){
        int color,couleur=0;

        int[] positionColor = new int[]{1, 3, 5, 7, 8, 10, 12, 14, 17, 19, 21, 23, 24, 26, 28, 30, 33, 35, 37, 39, 40, 42, 44, 46, 49, 51, 53, 55, 56, 58, 60, 62};
        for(int j=0;j<32;j++){
            if(i == positionColor[j])
                couleur =1;
        }
        if (couleur == 1)
            color = Color.DKGRAY; // noir
        else
            color = Color.LTGRAY; // blanc
        return color;
    }
    private int piéceColorDeplacement(int position) {
        int color=0,couleurNb=0,fin=0;
        couleur=0;
        ArrayList<Integer> PositionValble = new ArrayList<>();
        String[] Lettre = new String[colorActionPion.size()];
        Integer[] coordonner = new Integer[colorActionPion.size()];
        for (int i=0;i<colorActionPion.size();i++){
            coordonner[i] = Integer.parseInt(colorActionPion.get(i).substring(colorActionPion.get(i).indexOf(":")+1,colorActionPion.get(i).length()));
            Lettre[i] = colorActionPion.get(i).substring(0,colorActionPion.get(i).indexOf(":"));

            if(position == coordonner[i]){
                if (Lettre[i].equals("O")){
                    fin=1;
                } else if (Lettre[i].equals("A")){
                    couleurNb = 3;
                    PositionValble.add(coordonner[i]);
                }
                else if (Lettre[i].equals("D") && fin == 0){
                    couleurNb = 2;
                    PositionValble.add(coordonner[i]);
                }else if (Lettre[i].equals("R1")){
                    couleurNb = 4;
                    PositionValble.add(coordonner[i]);
                }else if (Lettre[i].equals("R2")){
                    couleurNb = 4;
                    PositionValble.add(coordonner[i]);
                }
            }
        }
        if(couleurNb == 2 )
            color = Color.GREEN;
        else if(couleurNb == 3)
            color = Color.RED;
        else if (couleurNb == 4)
            color = Color.rgb(51,102,0);//vert claire
        return color;
    }
}
