package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Entity.Bet;
import com.Bingo.SlotGame.Entity.Game;
import com.Bingo.SlotGame.Entity.Table;
import com.Bingo.SlotGame.Repository.RedisHashMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    RedisHashMapRepository redisHashMapRepository;
    Table table;
    Game game;
    public RedisService(RedisHashMapRepository redisHashMapRepository,Table table,Game game){
        this.redisHashMapRepository=redisHashMapRepository;
        this.game=game;
        this.table=table;
    }
    public void addUserGame(String userId, Bet bet,Integer gameId){
        table.addTotalBet(bet.getTotalBet());
        System.out.println(bet.getTotalBet()+" c1");
        game.players.add(userId);
        redisHashMapRepository.putValueInHashMap(userId+"Bet",gameId,bet);
    }
    public void addUserHistory(String userId,Integer gameId,Integer netChnage){
        redisHashMapRepository.putValueInHashMap(userId,gameId,netChnage);
    }
    public Bet getBetOfUser(String userId,Integer gameId){
       return (Bet) redisHashMapRepository.getValueFromHashMap(userId,gameId);
    }

}
