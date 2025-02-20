package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Node;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.DAO.FrequencyDAO;
import com.Bingo.SlotGame.DAO.TableDAO;
import com.Bingo.SlotGame.Storage.GameStorage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.*;

@Service
public class GameService {
    Game game;
    Table table;
    TableDAO tableDAO;
    GameStorage gameStorage;
    FrequencyDAO frequencyDAO;
    private Node[] nodeVertical = new Node[3];
    private Node[] nodeHorizontal = new Node[3];
    public GameService(Game game,TableDAO tableDAO,Table table,GameStorage gameStorage, FrequencyDAO frequencyDAO){
        this.game=game;
        this.table=table;
        this.tableDAO=tableDAO;
        this.gameStorage=gameStorage;
        this.frequencyDAO=frequencyDAO;
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
                if(game.firstWin==true) {
                    game.lineWin = verticalPostion + 1;
                    frequencyDAO.increaceLineFrequency(game.lineWin);
                    game.firstWin=false;
                }
                gameStorage.incrementVerticalMultiplier();
                gameStorage.removeOneToWIn(number);
                nodeVertical[verticalPostion]=null;
                return true;
            }
            else{
                nodeVertical[verticalPostion]=currentPostion.getVertical();
                if(nodeVertical[verticalPostion].getVertical()==null){
                    gameStorage.addOneToWin(nodeVertical[verticalPostion].getData());
                }
            }
        }
        else if(currentPostion.getVertical().getData()==number){
            currentPostion.setVertical(currentPostion.getVertical().getVertical());
            if(currentPostion.getVertical()==null){
                gameStorage.addOneToWin(currentPostion.getData());
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
                if(game.firstWin==true){
                    game.lineWin=horizontalPostion+4;
                    frequencyDAO.increaceLineFrequency(game.lineWin);
                    game.firstWin=false;
                }
                gameStorage.incrementHorizontalMultiplier();
                gameStorage.removeOneToWIn(number);
                nodeHorizontal[horizontalPostion]=null;
                return true;
            }
            else {
                nodeHorizontal[horizontalPostion]=currentPosstion.getHorizontal();
                if(nodeHorizontal[horizontalPostion].getHorizontal()==null){
                    gameStorage.addOneToWin(nodeHorizontal[horizontalPostion].getData());
                }
            }
        }
        else if(currentPosstion.getHorizontal().getData() ==number){
            currentPosstion.setHorizontal(currentPosstion.getHorizontal().getHorizontal());
            if(currentPosstion.getHorizontal()==null){
                gameStorage.addOneToWin(currentPosstion.getData());
            }
        }
        else {
            currentPosstion.getHorizontal().setHorizontal(null);
        }
        return false;
    }
    public int markNumber(int number){
        frequencyDAO.incrementFrequency(number);
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
    public String gameNumber(int number){
        table.insertQueue(number);
        int winMoney=markNumber(number);
        gameStorage.addBonusWinning(winMoney);
        StringBuilder returnString=new StringBuilder();
        if(winMoney>0){
            returnString.append("Win Amount: "+winMoney+"\n");
        }
        if(gameStorage.getOneToWin().size()>0){
            returnString.append("if you get these numbers you will win : ");
        }
        for(Integer oneTowin:gameStorage.getOneToWin()){
            returnString.append(oneTowin+" ");
        }
        return returnString.toString();
    }
    public List<Table> gameHistory(){
        return tableDAO.tableHistory();
    }


}

