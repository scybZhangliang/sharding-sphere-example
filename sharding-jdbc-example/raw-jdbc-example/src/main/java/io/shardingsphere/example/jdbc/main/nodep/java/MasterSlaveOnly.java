/*
 * Copyright 2016-2018 shardingsphere.io.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package io.shardingsphere.example.jdbc.main.nodep.java;

import io.shardingsphere.api.config.MasterSlaveRuleConfiguration;
import io.shardingsphere.example.jdbc.fixture.DataRepository;
import io.shardingsphere.example.jdbc.fixture.DataSourceUtil;
import io.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Please make sure master-slave data sync on MySQL is running correctly. Otherwise this example will query empty data from slave. 
 */
public class MasterSlaveOnly {
    
    public static void main(final String[] args) throws SQLException {
        new DataRepository(getDataSource()).demo();
    }
    
    private static DataSource getDataSource() throws SQLException {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("demo_ds_master_slave", "demo_ds_master", Arrays.asList("demo_ds_slave_0", "demo_ds_slave_1"));
        return MasterSlaveDataSourceFactory.createDataSource(createDataSourceMap(), masterSlaveRuleConfig, new ConcurrentHashMap<String, Object>(), new Properties());
    }
    
    private static Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        result.put("demo_ds_master", DataSourceUtil.createDataSource("demo_ds_master"));
        result.put("demo_ds_slave_0", DataSourceUtil.createDataSource("demo_ds_slave_0"));
        result.put("demo_ds_slave_1", DataSourceUtil.createDataSource("demo_ds_slave_1"));
        return result;
    }
}
