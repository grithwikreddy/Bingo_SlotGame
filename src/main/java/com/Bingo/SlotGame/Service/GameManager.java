package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.DAO.TableDAO;
import com.Bingo.SlotGame.Storage.GameStorage;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class GameManager {
    Game game;
    Table table;
    GameStorage gameStorage;
    GameScheduler gameScheduler;
    GameService gameService;
    TableDAO tableDAO;
    RedisService redisService;
    FrequencyService frequencyService;
    public GameManager(Game game,Table table,GameStorage gameStorage,GameScheduler gameScheduler,GameService gameService, TableDAO tableDAO,RedisService redisService,FrequencyService frequencyService) {
        this.game = game;
        this.table = table;
        this.gameStorage = gameStorage;
        this.gameScheduler = gameScheduler;
        this.gameService = gameService;
        this.tableDAO=tableDAO;
        this.redisService=redisService;
        this.frequencyService=frequencyService;
    }

    public void newGame(){
        game.players.clear();
        table.setNetChange(0);
        table.setTotalBet(0);
        table.resetNumberQueue();
        game.firstWin=true;
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

    public StringBuilder closeGame(){
        int totalWinnings=0;
        int gameId= frequencyService.getGameId();
        StringBuilder stringBuilder=new StringBuilder();
        for(String userId: game.players){
            int winnings=0;
            Bet userBet=redisService.getBetOfUser(userId+"Bet",gameId-1);

            System.out.println(userId.toString());
            switch (game.lineWin) {
                case 1:
                    winnings += userBet.getColumn1() * 9;
                    break;
                case 2:
                    winnings += userBet.getColumn2() * 9;
                    break;
                case 3:
                    winnings += userBet.getColumn3() * 9;
                    break;
                case 4:
                    winnings += userBet.getRow1() * 9;
                    break;
                case 5:
                    winnings += userBet.getRow2() * 9;
                    break;
                case 6:
                    winnings += userBet.getRow3() * 9;
                    break;
                default:
                    break;
            }
            if(winnings>0)
            stringBuilder.append(userId).append(" Win on the line :").append(winnings).append('\n');
            totalWinnings+=winnings;
        }
        stringBuilder.append("Total Win on the line :").append(totalWinnings).append('\n');
        table.setNetChange(table.getTotalBet()-totalWinnings);
        tableDAO.insertTable(table);
        return stringBuilder;
    }
    public Integer getBonusWinning(){
        return gameStorage.getBonusWinning();
    }
}
