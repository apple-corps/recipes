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
           //props.put("delete.retention.ms", "3000");
           props.put("cleanup.policy", "compact");
           //props.put("delete.retention.ms", "100");
           //props.put("segment.ms", "100");
           //props.put("min.cleanable.dirty.ratio", "0.001");
           //kafka.createTopic(TopicConfig.withName(MY_STATUS_TOPIC).build());
           kafka.createTopic(TopicConfig.withName(ENRICHMENT_TST_TPC).withAll(props).build());
           kafka.createTopic(TopicConfig.withName(ENRICHMENT_TPC).withAll(props).build());
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
                           kafka.sendKeyedEvent(ENRICHMENT_TPC, fu.jsonTestEvent(Schemas.enrichment(), Arrays.asList(UUID.randomUUID().toString(), "friend"), Instant.now()));
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
