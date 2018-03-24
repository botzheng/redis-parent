package com.lyc.redis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
public class RedisPoolUtil {

    /**
     * 设置key的有效期，单位是秒
     * @param key
     * @param exTime
     * @return
     */
    public static Long expire(String key,int exTime){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.expire(key,exTime);
        } catch (Exception e) {
            log.error("setex key:{} exTime：{} error",key,exTime,e);
            return result;
        } finally {   //在操作完后，无论是否异常，都要关闭redis连接池
            RedisPool.closeRedisPool();
        }
        return result;
    }

    /**
     * 在redis中存放信息，同时设置信息的过期时间
     * @param key
     * @param value
     * @param exTime  秒
     * @return
     */
    public static String setEx(String key,String value,int exTime){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.setex(key,exTime,value);
        } catch (Exception e) {
            log.error("setex key:{} exTime：{} value:{} error",key,value,e);
            return result;
        } finally {
            RedisPool.closeRedisPool();
        }
        return result;
    }

    /**
     * 在redis中存放信息
     * @param key
     * @param value
     * @return
     */
    public static String set(String key,String value){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.set(key,value);
        } catch (Exception e) {
            log.error("set key:{} value:{} error",key,value,e);
            return result;
        } finally {
            RedisPool.closeRedisPool();
        }
        return result;
    }

    /**
     * 从redis中获取key
     * @param key
     * @return
     */
    public static String get(String key){
        Jedis jedis = null;
        String result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            return result;
        } finally {
            RedisPool.closeRedisPool();
        }
        return result;
    }

    /**
     * 从redis中删除key
     * @param key
     * @return
     */
    public static Long del(String key){
        Jedis jedis = null;
        Long result = null;
        try {
            jedis = RedisPool.getJedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("get key:{} error",key,e);
            return result;
        } finally {
            RedisPool.closeRedisPool();
        }
        return result;
    }

    public static void main(String[] args) {
        Jedis jedis = RedisPool.getJedis();
        RedisPoolUtil.set("keyTest","value");
        String value = RedisPoolUtil.get("keyTest");
        RedisPoolUtil.setEx("keyex","valueex",60 * 10);
        RedisPoolUtil.expire("keyTest",60 * 20);
        RedisPoolUtil.del("keyTest");
        log.info("end");
    }





}
