package com.immerok.cookbook;

import static com.immerok.cookbook.ReadJson.TRANSACTION_TOPIC;

import com.immerok.cookbook.extensions.MiniClusterExtensionFactory;
import com.immerok.cookbook.utils.CookbookKafkaCluster;
import java.util.stream.Stream;
import org.apache.flink.test.junit5.MiniClusterExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

class ReadJsonTest {
    @RegisterExtension
    static final MiniClusterExtension FLINK =
            MiniClusterExtensionFactory.withDefaultConfiguration();

}
