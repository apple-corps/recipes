package com.immerok.cookbook;

public class Constants {
// TODO: embrace configuration through k8s for upcoming user story
    public static final String MY_STATUS_TOPIC = "my-status";
    public static final String MY_STATUS_OUTPUT_TOPIC = "my-status-output";
    public static final String BOOTSTRAP_SERVERS = "http://127.0.0.1:9092";

    public static final String PRODUCER_GROUP_ID = "myStatusConsumer-1";
    public static final String MY_STATUS_GROUP_ID = "my-status";
    public static final String MY_STATUS_GROUP_ID_OUT = "my-status-out";
    public static final String CATALOG_NAME = "temporaryCatalog";
    public static final String DATABASE_NAME = "mydb";
    public static final String SRC_TABLE_PATH = "myStatusIn";
    public static final String DST_TABLE_PATH = "myStatusOut";
    public static final String QUERY = "INSERT into "+DST_TABLE_PATH+" SELECT * FROM "+SRC_TABLE_PATH+" WHERE TRUE";
}
