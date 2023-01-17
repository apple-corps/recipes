import com.fasterxml.jackson.core.JsonProcessingException;
import com.immerok.cookbook.CookbookKafkaCluster;
import com.immerok.cookbook.FlinkUtil;
import com.immerok.cookbook.MiniClusterExtensionFactory;
import com.immerok.cookbook.TableJob;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import java.util.UUID;
import com.immerok.cookbook.schemas.Schemas;
import net.mguenther.kafka.junit.TopicConfig;
import org.apache.flink.test.junit5.MiniClusterExtension;
import org.apache.kafka.common.protocol.types.Field;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import static com.immerok.cookbook.Constants.*;

public class TableJobTest {
    @RegisterExtension
    static final MiniClusterExtension FLINK =
            MiniClusterExtensionFactory.withDefaultConfiguration();

    // manual test for running the job which creates an endless stream
    @Test
    public void testExecuteSQL() throws Exception {

       try (final CookbookKafkaCluster kafka = new CookbookKafkaCluster()) {

           final Properties overrides = new Properties();

           final Properties props = new Properties();
           
           //https://docs.confluent.io/platform/current/installation/configuration/topic-configs.html#confluent-value-subject-name-strategy
           // The amount of time to retain delete tombstone markers for log compacted topics. 86400000 (1 day)
           props.put("delete.retention.ms", "86400000");
           // enables log compaction
           props.put("cleanup.policy", "compact");
           // the period of time after which Kafka will force the log to roll 604800000 (7 days)
           props.put("segment.ms", "604800000");
           //This configuration controls how frequently the log compactor will attempt to clean the log. Default 0.5
           props.put("min.cleanable.dirty.ratio", "0.5");
           //kafka.createTopic(TopicConfig.withName(MY_STATUS_TOPIC).build());
           kafka.createTopic(TopicConfig.withName(MY_STATUS_TOPIC).build());
           kafka.createTopic(TopicConfig.withName(ENRICHMENT_TPC).withAll(props).build());
           kafka.createTopic(TopicConfig.withName(ENRICHED_OUT_TPC));
           //TableJob.executeSQL();
           FlinkUtil fu = new FlinkUtil();
           int i = 0;
           Runnable runabble= new Runnable() {
               @Override
               public void run() {
                   boolean isTrue = true;
                   while (isTrue) {
                       try {
                           kafka.sendKeyedEvent(ENRICHMENT_TPC, fu.jsonTestEvent(Schemas.enrichment(), Arrays.asList(UUID.randomUUID().toString(), "foe"), Instant.now()));
                       } catch (UnsupportedEncodingException e) {
                           throw new RuntimeException(e);
                       } catch (JsonProcessingException e) {
                           throw new RuntimeException(e);
                       }
                       try {
                           kafka.sendKeyedEvent(MY_STATUS_TOPIC, fu.jsonTestEvent(Schemas.myStatus(), Arrays.asList(UUID.randomUUID().toString(), "friend"), Instant.now()));
                       } catch (UnsupportedEncodingException e) {
                           throw new RuntimeException(e);
                       } catch (JsonProcessingException e) {
                           throw new RuntimeException(e);
                       }
                       try {
                           Thread.sleep(1000);
                       } catch (InterruptedException e) {
                           throw new RuntimeException(e);
                       }
                   }
               }

           };
           Thread thread = new Thread(runabble);
           thread.start();
           TableJob.executeSQL();
      }
    }
}
