/*
Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */

package com.immerok.cookbook;

import net.mguenther.kafka.junit.EmbeddedKafkaCluster;
import net.mguenther.kafka.junit.EmbeddedKafkaClusterConfig;
import net.mguenther.kafka.junit.SendValues;
import net.mguenther.kafka.junit.TopicConfig;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.io.UnsupportedEncodingException;
import java.util.stream.Stream;

/** A slim wrapper around <a href="https://mguenther.github.io/kafka-junit/">kafka-junit</a>. */
public class TestKafkaCluster extends EmbeddedKafkaCluster {

    public TestKafkaCluster() {
        super(EmbeddedKafkaClusterConfig.defaultClusterConfig());

        this.start();
    }

    /**
     * Creates a topic with the given name and synchronously writes all data from the given stream
     * to that topic.
     *
     * @param topic topic to create
     * @param topicData data to write
     */
    public void createTopic(String topic, Stream<byte[]> topicData) {
        createTopic(TopicConfig.withName(topic));
        topicData.forEach(
                record -> {
                    try {
                        sendEvent(topic, record);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    /**
     * Creates a topic with the given name and asynchronously writes all data from the given stream
     * to that topic.
     *
     * @param topic topic to create
     * @param topicData data to write
     */
    public void createTopicAsync(String topic, Stream<byte[]> topicData) {
        createTopic(TopicConfig.withName(topic));
        new Thread(
                        () ->
                                topicData.forEach(
                                        record -> {
                                            try {
                                                sendEvent(topic, record);
                                            } catch (UnsupportedEncodingException e) {
                                                throw new RuntimeException(e);
                                            }
                                        }),
                        "Generator")
                .start();
    }

    /**
     * Sends one event to the topic and sleeps for 100ms.
     *
     * @param event An event to send to the topic.
     */
    public void sendEvent(String topic, byte[] event) throws UnsupportedEncodingException {
        try {
            final SendValues<byte[]> sendRequest =
                    SendValues.to(topic, event)
                            .with(
                                    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                                    "org.apache.kafka.common.serialization.ByteArraySerializer")
                            .build();
            this.send(sendRequest);
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void createTopic(TopicConfig topicConfig){
        createTopic(topicConfig);
    }
}
