package com.Bingo.SlotGame.Repository;

import com.Bingo.SlotGame.Entity.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TableDAO {
    private final JdbcTemplate jdbcTemplate;

    public TableDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void insertTable(Table table){
        String sql = "INSERT INTO game_table (totalBet, netChange , queue) VALUES (?, ? , ?)";
        jdbcTemplate.update(sql, table.getTotalBet(), table.getNetChange() ,table.getQueue());
    }
    public List<Table> tableHistory() {
        String sql = "SELECT * FROM game_table";
        return jdbcTemplate.query(sql, tableRowMapper);
    }
    private final RowMapper<Table> tableRowMapper = (rs, rowNum) ->
            new Table(rs.getInt("id"), rs.getInt("totalBet"), rs.getInt("netChange"), rs.getString("queue"));
}
