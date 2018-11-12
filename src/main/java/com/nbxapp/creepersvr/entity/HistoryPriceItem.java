package com.nbxapp.creepersvr.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.ResultSet;
import java.sql.SQLException;

@Setter
@Getter
public class HistoryPriceItem implements RowMapper<HistoryPriceItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private String price;
    private String promopt1;
    private String promopt2;
    private String promopt3;
    private String gmtCreate;

    public HistoryPriceItem() {

    }

    @Override
    public HistoryPriceItem mapRow(ResultSet resultSet, int i) throws SQLException {
        HistoryPriceItem historyPriceItem = new HistoryPriceItem();
        historyPriceItem.id = resultSet.getLong("id");
        historyPriceItem.productName = resultSet.getString("productName");
        historyPriceItem.price = resultSet.getString("price");
        historyPriceItem.promopt1 = resultSet.getString("promopt1");
        historyPriceItem.promopt2 = resultSet.getString("promopt2");
        historyPriceItem.promopt3 = resultSet.getString("promopt3");
        historyPriceItem.gmtCreate = resultSet.getString("gmtCreate");

        return historyPriceItem;
    }
}
