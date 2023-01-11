package com.immerok.cookbook.schemas;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;

public class Schemas {
    public static final Schema myStatus() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("status", DataTypes.STRING())
                .column("ts", DataTypes.TIMESTAMP(3))
                .watermark("ts","ts")
                //.primaryKey("id")
                .build();
    }

    public static final Schema enrichment() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("enrichment", DataTypes.STRING().notNull())
                .column("ts",DataTypes.TIMESTAMP(3))
                .primaryKey("id")
                .watermark("ts","ts")
                .build();
    }
}