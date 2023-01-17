package com.immerok.cookbook.schemas;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;

public class Schemas {
    public static final Schema myStatus() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("status", DataTypes.STRING().notNull())
                .column("ts", DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                .watermark("ts","ts - INTERVAL '30' SECOND")
                // kafka doesn't seem to support this, kafka-upsert does
                //.primaryKey("id")
                .build();
    }

    public static final Schema enrichment() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("enrichment", DataTypes.STRING().notNull())
                .column("ts",DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                // setting kafka-upsert
                .primaryKey("id")
                .watermark("ts","ts - INTERVAL '30' SECOND")
                .build();
    }
    public static final Schema joined() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("status", DataTypes.STRING().notNull())
                .column("enrichment", DataTypes.STRING().notNull())
                .column("ts",DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                // setting for kafka-upsert
                .primaryKey("id")
                .watermark("ts","ts - INTERVAL '30' SECOND")
                .build();
    }
}