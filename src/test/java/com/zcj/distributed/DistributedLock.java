package com.zcj.distributed;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.UUID;

/**
 * redis 简单分布式锁的代码实现
 */
public class DistributedLock {
    private final JedisPool jedisPool;

    public DistributedLock(JedisPool jedisPool) {
        this.jedisPool = jedisPool;

    }

    /**
     * 获取锁
     *
     * @param lockName       锁的名称
     * @param acquireTimeout 获取锁的超时间
     * @param timeout        锁的超时间
     * @return
     */
    public String lockWithTimeOut(String lockName, long acquireTimeout, long timeout) {
        Jedis connection = null;
        String retIdentifier = null;
        try {
            //获取连接
            connection = jedisPool.getResource();
            //随机生成一个value
            String identifier = UUID.randomUUID().toString();
            //锁的名称 即key值
            String lockKey = "lock:" + lockName;
            //超时时间 上锁后超过此时间则自动释放锁
            int lockExpire=(int)(timeout/1000);
            //获取锁的超时时间，超过此时间则释放锁
            long end=System.currentTimeMillis()+acquireTimeout;
            while (System.currentTimeMillis()-end<0){
                //connection.setnx 不覆盖的增加数量 （重复的不覆盖）
                if(connection.setnx(lockKey, identifier)==1){
                    connection.expire(lockKey,lockExpire);//设置键为key的过期时间为i秒
                    retIdentifier=identifier;
                    return retIdentifier;
                }
                if(connection.ttl(lockKey)==-1){ //返回-1表示没有给该锁设置超时时间
                    connection.expire(lockKey,lockExpire);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JedisException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.close();
            }
        }
        return retIdentifier;
    }

    /**
     * 释放锁
     * @param lockName 锁的key
     * @param identifier 释放锁的标识
     * @return
     */
    public Boolean releaseLock(String lockName,String identifier){
        System.out.println(lockName+"....."+identifier);
        Jedis connection=null;
        String lockKey="lock:"+lockName;
        boolean retFlag=false;
        try {
            connection = jedisPool.getResource();
            while(true){
                //监控key 准备开启事务
                connection.watch(lockKey);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                if((connection.get(lockKey).equals(identifier))){
                    Transaction transaction = connection.multi();
                    transaction.decr(lockKey);
                    List<Object> result = transaction.exec();
                    if (result == null) {
                        continue;
                    }
                    retFlag=true;
                }
                connection.unwatch();
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                connection.close();
            }
        }
        return retFlag;
    }

}
