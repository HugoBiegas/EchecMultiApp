package com.echec.echecmulti;

public class Tour {
    public String[] déplacementTour(String[] Echiquier,int coordoner,String[] color) {
        String[] déplacement = new String[16];
        int[] border = new int[]{0, 8, 16, 24, 32, 40, 48, 56, 7, 15, 23, 31, 39, 47, 55, 63};
        int écar = coordoner+=8;
        int non = 0,cpt=0;
        //tour pour manger ver le haut
        for(int i=0;i<4;i++){
            if (non == 0){
                if(Echiquier[écar].equals("")){
                    déplacement[cpt] = "D:"+écar;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écar;
                    cpt++;
                    non =1;
                 }
            }
            écar+=8;
        }
        non=0;
        écar = coordoner-=8;
        //tour pour manger ver le bat
        for (int i=0;i<4;i++){
            if (non == 0){
                if(Echiquier[écar].equals("")){
                    déplacement[cpt] = "D:"+écar;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écar;
                    cpt++;
                    non =1;
                }
            }
            écar-=8;
        }
        non=0;
        écar = coordoner++;
        //tour pour manger sur la gauche
        for (int i=0;i<8;i++){
            for (int j=0;j<16;j++){
                if(border[i] == écar && non==0){
                    non=1;
                    if (!Echiquier[écar].equals("")){
                        déplacement[cpt] = "A:"+écar;
                        cpt++;
                    }
                }
            }
            if (non==0){
                if(Echiquier[écar].equals("")){
                    déplacement[cpt] = "D:"+écar;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écar;
                    cpt++;
                    non =1;
                }
            }
            écar++;
        }
        non=0;
        écar = coordoner--;
        //tour pour manger sur la droit
        for (int i=0;i<8;i++){
            for (int j=0;j<16;j++){
                if(border[i] == écar && non==0){
                    non=1;
                    if (!Echiquier[écar].equals("")){
                        déplacement[cpt] = "A:"+écar;
                        cpt++;
                    }
                }
            }
            if (non==0){
                if(Echiquier[écar].equals("")){
                    déplacement[cpt] = "D:"+écar;
                    cpt++;
                }else{
                    déplacement[cpt] = "A:"+écar;
                    cpt++;
                    non =1;
                }
            }
            écar--;
        }
        return déplacement;
    }
}
