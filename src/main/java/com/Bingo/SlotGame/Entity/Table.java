package com.Bingo.SlotGame.Entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class Table {
    int id;
    int totalBet=0;
    int netChange=0;
    String queue="";
    StringBuilder numberQueue=new StringBuilder();
    public Table(){}
    public Table(int id, int totalBet, int netChange,String queue) {
        this.id=id;
        this.totalBet=totalBet;
        this.netChange=netChange;
        this.queue=queue;
        if (queue != null && !queue.isEmpty()) {
            String[] numbers = queue.split(",");
            for (String num : numbers) {
                numberQueue.append(num).append(",");
            }
        }
    }
    public void insertQueue(int number){
        numberQueue.append(number).append(",");
    }
    public void resetNumberQueue(){
        numberQueue.setLength(0);
        queue="";
    }
    public String getQueue(){
        if (numberQueue.length() > 0) {
            numberQueue.deleteCharAt(numberQueue.length() - 1);
        }
        queue=numberQueue.toString();
        System.out.println(queue);
        return queue;
    }
    public void setTotalBet(int totalBet){
        this.totalBet=totalBet;
    }
    public Integer getId(){
        return id;
    }
    public void updateTotalBet(int bet){
        this.totalBet+=bet;
    }
    public Integer getTotalBet(){
        return totalBet;
    }
    public void setNetChange(int change){
        this.netChange=change;
    }
    public Integer getNetChange(){
        return netChange;
    }
}
