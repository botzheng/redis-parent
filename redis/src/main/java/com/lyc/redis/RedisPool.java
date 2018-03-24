package com.lyc.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;

@Slf4j
public class RedisPool {

    @Resource(name = "shardedJedisPool")
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedis instance(){
        return shardedJedisPool.getResource();
    }

    public void safeClose(ShardedJedis shardedJedis){
        try {
            if(shardedJedis != null ){
                shardedJedis.close();
            }
        } catch (Exception e) {
            log.error("return redis resource exception",e);
        }
    }

}