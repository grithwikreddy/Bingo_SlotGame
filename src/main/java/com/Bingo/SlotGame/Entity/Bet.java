package com.Bingo.SlotGame.Entity;

import java.io.Serializable;

public class Bet implements Serializable {
    Integer Row1;
    Integer Row2;
    Integer Row3;
    Integer Column1;
    Integer Column2;
    Integer Column3;


    public Integer getTotalBet(){
        return Row1+Row2+Row3+Column1+Column2+Column3;
    }
    public Bet(Integer row1, Integer row2, Integer row3, Integer column1, Integer column2, Integer column3){
        Row1=row1;
        Row2=row2;
        Row3=row3;
        Column1=column1;
        Column2=column2;
        Column3=column3;
    }
    public Integer getRow1(){
        return Row1;
    }
    public Integer getRow2(){
        return Row2;
    }
    public Integer getRow3(){
        return Row3;
    }
    public Integer getColumn1(){
        return Column1;
    }
    public Integer getColumn2(){
        return Column2;
    }
    public Integer getColumn3(){
        return Column3;
    }
    @Override
    public String toString() {
        return "Bet{" +
                "Row1=" + Row1 +
                ", Row2=" + Row2 +
                ", Row3=" + Row3 +
                ", Column1=" + Column1 +
                ", Column2=" + Column2 +
                ", Column3=" + Column3 +
                '}';
    }
}
