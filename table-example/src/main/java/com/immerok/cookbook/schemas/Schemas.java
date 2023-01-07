package com.immerok.cookbook.schemas;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;

public class Schemas {


    public static final Schema myStatus() {
        return Schema.newBuilder()
                .column("myUniqueID", DataTypes.STRING())
                .column("status", DataTypes.STRING())
                //.primaryKey("myUniqueID")
                .build();
    }
       }