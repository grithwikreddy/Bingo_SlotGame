package com.Bingo.SlotGame.Entity;

import com.Bingo.SlotGame.Repository.TableDAO;
import org.springframework.stereotype.Component;

import java.util.*;
@Component
public class Game {
    int grid[][]={{1,15,21},{4,16,24},{7,19,27}};
    public Node[][] nodes=new Node[3][3];
    public Map<Integer,Integer> mapVertical=new HashMap<>();
    public Map<Integer,Integer> mapHorizontal=new HashMap<>();
    public Game(){
        for(int r=0;r<3;r++){
            for(int e=0;e<3;e++){
                mapHorizontal.put(grid[r][e],r);
                mapVertical.put(grid[r][e],e);
                nodes[r][e]=new Node(grid[r][e]);
            }
        }
    }
}
