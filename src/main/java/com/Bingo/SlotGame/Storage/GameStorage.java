package com.Bingo.SlotGame.Storage;

import com.Bingo.SlotGame.Entity.Node;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component  // Ensures Singleton in Spring context
public class GameStorage {
    private Node[] nodeVertical = new Node[3];
    private Node[] nodeHorizontal = new Node[3];
    public Set<Integer> OneToWin = new HashSet<>();
    private Integer verticalMultiplier = 0;
    private Integer horizontalMultiplier = 0;
    private boolean betsOpen = false;
    public Integer bonusWinning = 0;

    // Getters and Setters
    public Node[] getNodeVertical() {
        return nodeVertical;
    }

    public void setNodeVertical(Node[] nodeVertical) {
        this.nodeVertical = nodeVertical;
    }

    public Node[] getNodeHorizontal() {
        return nodeHorizontal;
    }

    public void setNodeHorizontal(Node[] nodeHorizontal) {
        this.nodeHorizontal = nodeHorizontal;
    }

    public Set<Integer> getOneToWin() {
        return OneToWin;
    }

    public void addOneToWin(Integer num) {
        this.OneToWin.add(num);
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

    public boolean isBetsOpen() {
        return betsOpen;
    }

    public void setBetsOpen(boolean betsOpen) {
        this.betsOpen = betsOpen;
    }

    public Integer getBonusWinning() {
        return bonusWinning;
    }

    public void setBonusWinning(Integer bonusWinning) {
        this.bonusWinning = bonusWinning;
    }
}
