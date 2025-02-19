package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Model.GameState;
import org.springframework.stereotype.Service;

@Service
public class GameStateService {
    private GameState currentState = GameState.NOGAME; // Default state

   public boolean isStateBetsOpen(){
       return currentState==GameState.BETSOPEN;
   }

    public void updateToBetsOpen() {
        this.currentState=GameState.BETSOPEN;
    }

    public void updateToGame() {
        this.currentState=GameState.INGAME;
    }
    public void updateToNoGame(){
       this.currentState=GameState.NOGAME;
    }

    public boolean isStateInGame() {
       return currentState==GameState.INGAME;
    }
}
