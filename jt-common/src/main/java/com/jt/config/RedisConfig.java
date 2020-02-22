package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
    @Value("${redis.nodes}")
    private String nodes;

    /**
     * 配置Redis集群
     * @return
     */
    @Bean
    public JedisCluster jedisCluster(){
        Set<HostAndPort> node = new HashSet<>();
        String[] s = nodes.split(",");
        for (String no : s) {
            String host = no.split(":")[0];
            int port = Integer.valueOf(no.split(":")[1]);
            node.add(new HostAndPort(host,port));
        }
        return new JedisCluster(node);
    }
}
