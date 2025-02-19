package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Node;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.Repository.FrequencyDAO;
import com.Bingo.SlotGame.Repository.TableDAO;
import com.Bingo.SlotGame.Storage.GameStorage;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.concurrent.*;

@Service
public class GameManager {
    Game game;
    Table table;
    Bet bet;
    GameStorage gameStorage;
    GameScheduler gameScheduler;
    GameService gameService;
    TableDAO tableDAO;
    public GameManager(Game game,Table table, Bet bet,GameStorage gameStorage,GameScheduler gameScheduler,GameService gameService, TableDAO tableDAO) {
        this.game = game;
        this.table = table;
        this.bet = bet;
        this.gameStorage = gameStorage;
        this.gameScheduler = gameScheduler;
        this.gameService = gameService;
        this.tableDAO=tableDAO;
    }

    public void newGame(){
        table.setNetChange(0);
        table.setTotalBet(0);
        table.resetNumberQueue();
        bet.firstWin=true;
        bet.resetBet();
        gameStorage.setVerticalMultiplier(0);
        gameStorage.setHorizontalMultiplier(0);
        gameStorage.clearOneToWin();
        gameScheduler.startCountdown();
        gameStorage.resetBonusWinning();
        for(int r=0;r<3;r++){
            for(int e=0;e<3;e++){
                if(r!=0){
                    game.nodes[r - 1][e].setVertical(game.nodes[r][e]);
                }
                if(e!=0){
                    game.nodes[r][e - 1].setHorizontal(game.nodes[r][e]);
                }
            }
        }
        for(int r=0;r<3;r++){
            gameService.setNodeVertical(r,game.nodes[0][r]);
            gameService.setNodeHorizontal(r,game.nodes[r][0]);
        }
    }

    public Integer closeGame(){
        int winnings=0;

        switch (bet.lineWin) {
            case 1: winnings += bet.getColumn1() * 9; break;
            case 2: winnings += bet.getColumn2() * 9; break;
            case 3: winnings += bet.getColumn3() * 9; break;
            case 4: winnings += bet.getRow1() * 9; break;
            case 5: winnings += bet.getRow2() * 9; break;
            case 6: winnings += bet.getRow3() * 9; break;
            default: break;
        }
        table.setNetChange(table.getTotalBet()-winnings);
        tableDAO.insertTable(table);
        return winnings;
    }
    public Integer getBonusWinning(){
        return gameStorage.getBonusWinning();
    }
}
