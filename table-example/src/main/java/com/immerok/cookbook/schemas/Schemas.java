package com.immerok.cookbook.schemas;

import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.DataTypes;

public class Schemas {
    public static final Schema myStatus() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("status", DataTypes.STRING())
                .column("ts", DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                .watermark("ts","ts")
                //.primaryKey("id")
                .build();
    }

    public static final Schema enrichment() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("enrichment", DataTypes.STRING().notNull())
                .column("ts",DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                .primaryKey("id")
                .watermark("ts","ts")
                .build();
    }
    public static final Schema joined() {
        return Schema.newBuilder()
                .column("id", DataTypes.STRING().notNull())
                .column("status", DataTypes.STRING().notNull())
                .column("enrichment", DataTypes.STRING().notNull())
                .column("ts",DataTypes.TIMESTAMP(3).bridgedTo(java.sql.Timestamp.class))
                .primaryKey("id")
                .watermark("ts","ts")
                .build();
    }
    
    org.apache.flink.table.api.ValidationException: Column types of query result and sink for 'temporaryCatalog.default.enrichmentOut' do not match.
Cause: Different number of columns.

Query schema: [id: STRING NOT NULL, enrichment: STRING NOT NULL, ts: TIMESTAMP(3) *ROWTIME*]
Sink schema:  [id: STRING, status: STRING, enrichment: STRING, ts: TIMESTAMP(3)]


}