package com.Bingo.SlotGame.Controller;


import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.Model.GameState;
import com.Bingo.SlotGame.Service.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Bingo")
public class GameController {
    GameService gameService;
    GameManager gameManager;
    RedisService redisService;
    FrequencyService frequencyService;
    Table table;
    GameStateService gameStateService;
    public GameController(GameService gameService,Table table,GameManager gameManager,FrequencyService frequencyService, GameStateService gameStateService,RedisService redisService){
        this.gameService=gameService;
        this.table=table;
        this.gameManager=gameManager;
        this.frequencyService=frequencyService;
        this.gameStateService=gameStateService;
        this.redisService=redisService;
    }
    @PostMapping("/number/{number}")
    public String gameResult(@PathVariable int number){
        if(gameStateService.isStateBetsOpen()){
            return "Game not Started. Bets are still open";
        }
        else if(gameStateService.isStateInGame()){
            return gameService.gameNumber(number);
        }
        else{
            return "No Game Active";
        }
    }
    @PostMapping("/close")
    public  String gameClosed(){
        gameStateService.updateToNoGame();
        StringBuilder returnString= gameManager.closeGame();
        int winOnBonus=gameManager.getBonusWinning();
        returnString.append("Win on bonus : ").append(winOnBonus).append('\n');

        return returnString.toString();
    }
    @PostMapping("/newGame")
    public String newGame(){
        gameManager.newGame();
        return "New Game Created & Bets are Opened";
    }
    @PostMapping("/bet")
    public String bets(@RequestParam String userId,@RequestParam Integer Row1,@RequestParam Integer Row2,@RequestParam Integer Row3,@RequestParam Integer Column1,@RequestParam Integer Column2,@RequestParam Integer Column3){
        Bet userBet=new Bet(Row1,Row2,Row3,Column1,Column2,Column3);
        System.out.println("total bet: "+userBet.toString());
        if(gameStateService.isStateBetsOpen()){
            redisService.addUserGame(userId,userBet,frequencyService.getGameId());
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
    @GetMapping("/frequency")
    public List<Map<String, Object>> getFrequencies() {
        return frequencyService.getFrequencies();
    }
    @GetMapping("/linefrequency")
    public List<Map<String, Object>> getLineFrequencies() {
        return frequencyService.getLineFrequencies();
    }
}

