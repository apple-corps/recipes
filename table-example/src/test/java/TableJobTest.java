import static com.immerok.cookbook.Constants.MY_STATUS_TOPIC;

import com.immerok.cookbook.CookbookKafkaCluster;
import com.immerok.cookbook.MiniClusterExtensionFactory;
import com.immerok.cookbook.TableJob;
import java.util.Properties;
import net.mguenther.kafka.junit.TopicConfig;
import org.apache.flink.test.junit5.MiniClusterExtension;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

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
           kafka.createTopic(TopicConfig.withName(MY_STATUS_TOPIC).withAll(props).build());
           TableJob.executeSQL();
       }
    }
}
