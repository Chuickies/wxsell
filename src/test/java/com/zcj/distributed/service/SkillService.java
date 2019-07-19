package com.zcj.distributed.service;

import com.zcj.distributed.DistributedLock;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class SkillService {
    private static JedisPool pool;
    private DistributedLock distributedLock=new DistributedLock(pool);
    int n=500;
    static {
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数
        config.setMaxTotal(200);
        //设置最大空闲数
        config.setMaxIdle(8);
        //设置最大空闲时间
        config.setMaxWaitMillis(1000*100);
        // 在borrow一个jedis实例时，是否需要验证，若为true，则所有jedis实例均是可用的
        config.setTestOnBorrow(true);
        pool=new JedisPool(config,"localhost",6379,3000);
    }
    public void skill(){
        String identifier = distributedLock.lockWithTimeOut("resource", 5000, 100);
        System.out.println(Thread.currentThread().getName()+"获取了锁");
        System.out.println(--n);
        distributedLock.releaseLock("resource",identifier);
    }
}
