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

import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration;
import org.apache.flink.runtime.testutils.MiniClusterResourceConfiguration.Builder;
import org.apache.flink.test.junit5.MiniClusterExtension;

/** Convenience class to create {@link MiniClusterExtension} with different configurations. */
public final class MiniClusterExtensionFactory {

    private static final int PARALLELISM = 1;

    public static MiniClusterExtension withDefaultConfiguration() {
        return new MiniClusterExtension(defaultBuilder().build());
    }

    public static MiniClusterExtension withCustomConfiguration(Configuration configuration) {
        return new MiniClusterExtension(defaultBuilder().setConfiguration(configuration).build());
    }

    private MiniClusterExtensionFactory() {}

    private static Builder defaultBuilder() {
        return new MiniClusterResourceConfiguration.Builder()
                .setNumberSlotsPerTaskManager(PARALLELISM)
                .setNumberTaskManagers(1);
    }
}
