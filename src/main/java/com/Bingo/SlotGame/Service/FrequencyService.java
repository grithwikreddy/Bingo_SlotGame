package com.Bingo.SlotGame.Service;

import com.Bingo.SlotGame.Repository.FrequencyDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FrequencyService {
    FrequencyDAO frequencyDAO;
    public FrequencyService(FrequencyDAO frequencyDAO){
        this.frequencyDAO=frequencyDAO;
    }
    public List<Map<String, Object>> getFrequencies(){
        return frequencyDAO.getAllFrequencies();
    }
    public List<Map<String, Object>> getLineFrequencies(){
        return frequencyDAO.getAllLineFrequencies();
    }
}
