package com.Bingo.SlotGame.Controller;


import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.Service.GameManager;
import com.Bingo.SlotGame.Service.GameScheduler;
import com.Bingo.SlotGame.Service.GameService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Bingo")
public class GameController {
    GameService gameService;
    GameManager gameManager;
    Bet bet;
    Table table;
    public GameController(GameService gameService,Bet bet,Table table,GameManager gameManager){
        this.gameService=gameService;
        this.bet=bet;
        this.table=table;
        this.gameManager=gameManager;
    }
    @PostMapping("/result/{number}")
    public String gameResult(@PathVariable int number){
        if(gameManager.getBetOpen()==true){
            return "Game not Started. Bets are still open";
        }
        else{
            return gameManager.GameNumber(number);
        }
    }
    @PostMapping("/close")
    public  String gameClosed(){
        int winOnline= gameManager.closeGame();
        int winOnBonus=gameManager.getBonusWinning();
        StringBuilder returnString=new StringBuilder();
        returnString.append("Win on bonus: ").append(winOnBonus).append('\n');
        if(winOnline>0) {
            returnString.append("Win on first line: ").append(winOnline).append('\n');
            returnString.append("Total win: ").append(winOnBonus+winOnline);
        }
        return returnString.toString();
    }
    @PostMapping("/newGame")
    public String newGame(){
        gameManager.newGame();
        return "New Game Created & Bets are Opened";
    }
    @PostMapping("/bet")
    public String bets(@RequestParam Integer Row1,@RequestParam Integer Row2,@RequestParam Integer Row3,@RequestParam Integer Column1,@RequestParam Integer Column2,@RequestParam Integer Column3){
        if(gameService.isBetOpen()==true){
            bet.addBet(Row1,Row2,Row3,Column1,Column2,Column3);
            System.out.println(this.bet.getColumn1());
            return "Bet accepted";
        }
        else{
            return "BetsClosed";
        }
    }
    @GetMapping("/history")
    public List<Table> gameHistory(){
        return gameService.gameHistory();
    }

}

