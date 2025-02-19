package com.Bingo.SlotGame.Storage;

import com.Bingo.SlotGame.Entity.Node;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class GameStorage {
    Set<Integer> OneToWin = new HashSet<>();
    Integer verticalMultiplier = 0;
    Integer horizontalMultiplier = 0;
    Integer bonusWinning = 0;

    public Set<Integer> getOneToWin() {
        return OneToWin;
    }

    public void addOneToWin(Integer num) {
        this.OneToWin.add(num);
    }
    public void removeOneToWIn(Integer number){
        OneToWin.remove(number);
    }
    public void clearOneToWin() {
        this.OneToWin.clear();
    }

    public Integer getVerticalMultiplier() {
        return verticalMultiplier;
    }

    public void setVerticalMultiplier(Integer verticalMultiplier) {
        this.verticalMultiplier = verticalMultiplier;
    }
    public void incrementVerticalMultiplier() {
        verticalMultiplier++;
    }
    public Integer getHorizontalMultiplier() {
        return horizontalMultiplier;
    }
    public void incrementHorizontalMultiplier() {
         horizontalMultiplier++;
    }
    public void setHorizontalMultiplier(Integer horizontalMultiplier) {
        this.horizontalMultiplier = horizontalMultiplier;
    }

    public Integer getBonusWinning() {
        return bonusWinning;
    }

    public void addBonusWinning(Integer winning) {
        bonusWinning += winning;
    }
    public void resetBonusWinning(){
        bonusWinning=0;
    }
}
