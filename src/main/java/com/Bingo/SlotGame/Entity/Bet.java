package com.Bingo.SlotGame.Entity;

import org.springframework.stereotype.Component;

@Component
public class Bet {
    Table table;
    public int lineWin=0;
    public boolean firstWin=true;
    Integer Row1=0;
    Integer Row2=0;
    Integer Row3=0;
    Integer Column1=0;
    Integer Column2=0;
    Integer Column3=0;
    public Bet(Table table){
        this.table=table;
    }
    public void addBet(Integer Row1,Integer Row2,Integer Row3,Integer Column1,Integer Column2,Integer Column3){
        table.updateTotalBet(Row1+Row2+Row3+Column1+Column2+Column3);
        this.Row1+=Row1;
        this.Row2+=Row2;
        this.Row3+=Row3;
        this.Column1+=Column1;
        this.Column2+=Column2;
        this.Column3+=Column3;
    }
    public void resetBet(){
        Row1=0;
        Row2=0;
        Row3=0;
        Column1=0;
        Column2=0;
        Column3=0;
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
}
