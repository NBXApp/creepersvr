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
public class WatchItem implements RowMapper<WatchItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String productName;
    private String siteId;
    private String url;
    private String gmtCreate;
    private String gmtModified;
    private long state;

    public WatchItem() {

    }

    @Override
    public WatchItem mapRow(ResultSet resultSet, int i) throws SQLException {
        WatchItem watchItem = new WatchItem();
        watchItem.id = resultSet.getLong("id");
        watchItem.productName = resultSet.getString("productName");
        watchItem.siteId = resultSet.getString("siteId");
        watchItem.url = resultSet.getString("url");
        watchItem.gmtCreate = resultSet.getString("gmtCreate");
        watchItem.gmtModified = resultSet.getString("gmtModified");
        watchItem.state = resultSet.getLong("state");

        return watchItem;
    }
}
