package com.Bingo.SlotGame.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class FrequencyDAO {
    private final JdbcTemplate jdbcTemplate;

    public FrequencyDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void incrementFrequency(Integer number){
        String sql = "update frequency set count=count+1 where number=(?)";
        jdbcTemplate.update(sql,number);
        sql= "update count set count_number=count_number+1";
        jdbcTemplate.update(sql);
    }
    public List<Map<String, Object>> getAllFrequencies() {
        String query = "SELECT number,(count/(select count_number from count))*100 as frequencyPercentage  FROM frequency";
        return jdbcTemplate.queryForList(query);
    }
    public void increaceLineFrequency(Integer line){
        String sql="UPDATE linefrequency SET count = count + 1 WHERE linenumber =(?);";
        jdbcTemplate.update(sql,line);
        sql="update gamecount set game_count=game_count+1";
        jdbcTemplate.update(sql);
    }
    public List<Map<String, Object>> getAllLineFrequencies() {
        String query = "SELECT line,(count/(select game_count from gamecount))*100 as LineFrequencyPercentage  FROM linefrequency";
        return jdbcTemplate.queryForList(query);
    }
    public Integer getGameId(){
        String sql= "Select game_count from gamecount";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
