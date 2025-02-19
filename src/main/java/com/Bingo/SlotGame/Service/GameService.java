package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Node;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.Repository.TableDAO;
import com.Bingo.SlotGame.Storage.GameStorage;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class GameService {
    Game game;
    Bet bet;
    Table table;
    TableDAO tableDAO;
    GameStorage gameStorage;
    private Node[] nodeVertical = new Node[3];
    private Node[] nodeHorizontal = new Node[3];
    public GameService(Game game,Bet bet,TableDAO tableDAO,Table table,GameStorage gameStorage){
        this.game=game;
        this.bet=bet;
        this.table=table;
        this.tableDAO=tableDAO;
        this.gameStorage=gameStorage;
    }
    public void setNodeVertical(int postion,Node node){
        nodeVertical[postion]=node;
    }
    public void setNodeHorizontal(int postion,Node node){
        nodeHorizontal[postion]=node;
    }
    public boolean processVertical(int number,int verticalPostion){
        Node currentPostion=nodeVertical[verticalPostion];
        if(currentPostion.getData() ==number){
            if(currentPostion.getVertical()==null){
                if(bet.firstWin==true) {
                    bet.personWin = verticalPostion + 1;
                    bet.firstWin=false;
                }
                gameStorage.incrementVerticalMultiplier();
                gameStorage.OneToWin.remove(number);
                nodeVertical[verticalPostion]=null;
                return true;
            }
            else{
                nodeVertical[verticalPostion]=currentPostion.getVertical();
                if(nodeVertical[verticalPostion].getVertical()==null){
                    gameStorage.OneToWin.add(nodeVertical[verticalPostion].getData());
                }
            }
        }
        else if(currentPostion.getVertical().getData()==number){
            currentPostion.setVertical(currentPostion.getVertical().getVertical());
            if(currentPostion.getVertical()==null){
                gameStorage.OneToWin.add(currentPostion.getData());
            }
        }
        else{
            currentPostion.getVertical().setVertical(null);
        }
        return false;
    }
    public boolean processHorizontal(int number,int horizontalPostion){
        Node currentPosstion=nodeHorizontal[horizontalPostion];
        if(currentPosstion.getData()==number){
            if(currentPosstion.getHorizontal()==null){
                if(bet.firstWin==true){
                    bet.personWin=horizontalPostion+4;
                    bet.firstWin=false;
                }
                gameStorage.incrementHorizontalMultiplier();
                gameStorage.OneToWin.remove(number);
                nodeHorizontal[horizontalPostion]=null;
                return true;
            }
            else {
                nodeHorizontal[horizontalPostion]=currentPosstion.getHorizontal();
                if(nodeHorizontal[horizontalPostion].getHorizontal()==null){
                    gameStorage.OneToWin.add(nodeHorizontal[horizontalPostion].getData());
                }
            }
        }
        else if(currentPosstion.getHorizontal().getData() ==number){
            currentPosstion.setHorizontal(currentPosstion.getHorizontal().getHorizontal());
            if(currentPosstion.getHorizontal()==null){
                gameStorage.OneToWin.add(currentPosstion.getData());
            }
        }
        else {
            currentPosstion.getHorizontal().setHorizontal(null);
        }
        return false;
    }
    public int markNumber(int number){
        int horizontalPostion = game.mapHorizontal.get(number);
        int verticalPostion = game.mapVertical.get(number);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        try {
            Future<Boolean> verticalFuture = executorService.submit(() -> processVertical(number, verticalPostion));
            Future<Boolean> horizontalFuture = executorService.submit(() -> processHorizontal(number, horizontalPostion));
            boolean winVertical = verticalFuture.get();
            boolean winHorizontal = horizontalFuture.get();
            if (winHorizontal || winVertical) {
                if (gameStorage.getVerticalMultiplier() == 0 || gameStorage.getHorizontalMultiplier() == 0) {
                    return number;
                } else {
                    return number * (gameStorage.getHorizontalMultiplier() + gameStorage.getVerticalMultiplier());
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public List<Table> gameHistory(){
        return tableDAO.tableHistory();
    }
    public boolean isBetOpen(){
        return gameStorage.isBetsOpen();
    }

}

