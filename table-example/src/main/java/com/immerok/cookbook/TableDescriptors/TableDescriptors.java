package com.immerok.cookbook.TableDescriptors;

import com.immerok.cookbook.TableJob;
import org.apache.flink.table.api.TableDescriptor;

import static com.immerok.cookbook.Constants.*;
import static com.immerok.cookbook.schemas.Schemas.*;

public class TableDescriptors {

    public static final TableDescriptor STATUS_DESCRIPTOR =
            TableDescriptor.forConnector("kafka")
                    .schema(myStatus())
                    .option("topic", MY_STATUS_TOPIC)
                    .option("properties.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("properties.group.id", MY_STATUS_GROUP_ID)
                    .option("format","json")
                    .option("json.fail-on-missing-field", "false")
                    .option("json.ignore-parse-errors", "true")
                    .option("properties.auto.offset.reset","latest")
                    .build();

    public static final TableDescriptor ENR_DESCRIPTOR =
            //TableDescriptor.forConnector("upsert-kafka")
            TableDescriptor.forConnector("kafka")
                    .schema(enrichment())
                    .option("topic",ENRICHMENT_TPC)
                    .option("properties.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("properties.group.id", ENRICHMENT_GROUP_ID)
                    //setting for kafka
                    .option("format","json")
                    // unsetting for kafka
                    //.option("key.format","raw")
                    //.option("value.format","json")
                    .option("properties.auto.offset.reset","latest")
                    .build();

    public static final TableDescriptor ENR_OUT =
            //TableDescriptor.forConnector("upsert-kafka")
            TableDescriptor.forConnector("kafka")
                    .schema(joined())
                    .option("topic",ENRICHED_OUT_TPC)
                    .option("properties.bootstrap.servers", BOOTSTRAP_SERVERS)
                    .option("properties.group.id", ENRICHMENT_GROUP_ID)
                    .option("format", "json")
                    // unsetting for kafka
                    //.option("key.format","raw")
                    //.option("value.format","json")
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
