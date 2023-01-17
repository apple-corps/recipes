package com.immerok.cookbook;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Constants {
// TODO: embrace configuration through k8s for upcoming user story
    public static final String MY_STATUS_TOPIC = "my-status";
    public static final String ENRICHMENT_TPC = "enrichment";
    public static final String ENRICHED_OUT_TPC = "enrichmentOut";
    public static final String MY_STATUS_OUTPUT_TOPIC = "my-status-output";
    public static final String BOOTSTRAP_SERVERS = "http://127.0.0.1:9092";
    public static final String PRODUCER_GROUP_ID = "myStatusConsumer-1";
    public static final String MY_STATUS_GROUP_ID = "my-status";
    public static final String ENRICHMENT_GROUP_ID = "enrichment";
    public static final String MY_STATUS_GROUP_ID_OUT = "my-status-out";
    public static final String CATALOG_NAME = "temporaryCatalog";
    public static final String DATABASE_NAME = "mydb";
    public static final String SRC_TABLE_PATH = "myStatusIn";
    public static final String ENR_SRC_TABLE_PATH = "enrichmentIn";
    public static final String ENR_TST_TABLE_PATH = "enrichmentOut";
    public static final String DST_TABLE_PATH = "enrichedOutput";
    public static final String ENR_DST_TABLE_PATH = "enrichmentOut";
    //public static final String QUERY = "INSERT INTO "+DST_TABLE_PATH+" SELECT * FROM "+SRC_TABLE_PATH+" WHERE TRUE";
    public static final String QUERY = "INSERT INTO "+ENR_TST_TABLE_PATH+" SELECT * FROM "+ENR_SRC_TABLE_PATH+" WHERE TRUE";
    public static final String JOIN_QUERY = "INSERT INTO "+ENR_DST_TABLE_PATH+" SELECT * FROM "+SRC_TABLE_PATH+ " JOIN "+ENR_SRC_TABLE_PATH+" FOR SYSTEM_TIME AS OF "+SRC_TABLE_PATH+".ts"+" ON "+SRC_TABLE_PATH+".id ="+ENR_SRC_TABLE_PATH+".id";
}
