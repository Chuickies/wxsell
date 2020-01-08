package com.zcj.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WxOpenRedis {
    public Jedis getJedis(){
        //JedisPool pool = new JedisPool("192.168.1.164",6380);
        JedisPool pool = new JedisPool(new GenericObjectPoolConfig(),"192.168.1.167",6380,600000);
        return pool.getResource();
    }
    @Test
    public void SetStringValue(){
        Jedis jedis = getJedis();
        jedis.set("name","小明");
        jedis.close();
    }
}
