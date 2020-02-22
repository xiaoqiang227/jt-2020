package com.jt.test;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;


public class redisTest {
    @Test
    public void testCluster() {
        Set<HostAndPort> node = new HashSet<HostAndPort>();
        node.add(new HostAndPort("192.168.43.4", 7001));
        node.add(new HostAndPort("192.168.43.4", 7002));
        node.add(new HostAndPort("192.168.43.4", 7003));
        node.add(new HostAndPort("192.168.43.4", 7004));
        node.add(new HostAndPort("192.168.43.4", 7005));
        node.add(new HostAndPort("192.168.43.4", 7006));
        node.add(new HostAndPort("192.168.43.4", 7007));
        node.add(new HostAndPort("192.168.43.4", 7008));
        JedisCluster cluster = new JedisCluster(node);
        cluster.set("1906", "redis集群测试成功!!!!");
        System.out.println(cluster.get("1906"));
    }
}
