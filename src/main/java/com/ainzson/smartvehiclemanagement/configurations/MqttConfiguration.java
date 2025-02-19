package com.ainzson.smartvehiclemanagement.configurations;

import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfiguration {

    @Bean
    public MqttConnectionOptions mqttConnectOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setServerURIs(new String[]{"tcp://localhost:1884"}); // Change to your EMQX broker
        options.setCleanStart(true);
        options.setAutomaticReconnect(true);
        return options;
    }
}
