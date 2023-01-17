package com.immerok.cookbook;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.catalog.*;

import java.util.HashMap;

import static com.immerok.cookbook.Constants.*;
import static com.immerok.cookbook.TableDescriptors.TableDescriptors.*;

public class TableJob {

    public static void main(String[] args) throws Exception {
        executeSQL();
    }
    public static void executeSQL() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        final StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);
        Catalog catalog = new GenericInMemoryCatalog(CATALOG_NAME);
        tableEnv.registerCatalog(CATALOG_NAME,catalog);
        tableEnv.useCatalog(CATALOG_NAME);
        catalog.createDatabase(DATABASE_NAME, new CatalogDatabaseImpl(new HashMap<>(),""),false);
        tableEnv.createTemporaryTable(ENR_SRC_TABLE_PATH, ENR_DESCRIPTOR);
        tableEnv.createTemporaryTable(SRC_TABLE_PATH, STATUS_DESCRIPTOR);
        tableEnv.createTemporaryTable(ENR_DST_TABLE_PATH, ENR_OUT);
        tableEnv.executeSql(QUERY).print();
    }
}
