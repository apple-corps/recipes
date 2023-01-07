package com.immerok.cookbook.TableDescriptors;

import org.apache.flink.table.api.TableDescriptor;

import static com.immerok.cookbook.Constants.*;
import static com.immerok.cookbook.schemas.Schemas.myStatus;

public class TableDescriptors {

    public static final TableDescriptor STATUS_DESCRIPTOR =
            TableDescriptor.forConnector("kafka")
                    .schema(myStatus())
                    .option("topic", MY_STATUS_TOPIC)
                    .option("properties.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("properties.group.id", MY_STATUS_GROUP_ID)
                    .option("key.fields","myUniqueID")
                    .option("key.format","raw")
                    .option("value.format","raw")
                    .option("properties.auto.offset.reset","latest")
                    .build();

    public static final TableDescriptor BLACKHOLE_DESCRIPTOR =
            TableDescriptor.forConnector("blackhole")
                    .schema(myStatus())
                    .build();

    public static final TableDescriptor STATUS_OUTPUT_DESC =
            TableDescriptor.forConnector("kafka")
                    .schema(myStatus())
                    .option("topic", MY_STATUS_OUTPUT_TOPIC)
                    .option("properties.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("properties.group.id", MY_STATUS_GROUP_ID_OUT)
                    //.option("key.fields","myUniqueID")
                    //.option("key.format","raw")
                    //.option("value.format","raw")
                    .option("properties.auto.offset.reset", "latest")
                    .build();

}
