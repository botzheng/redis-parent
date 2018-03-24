package com.lyc.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //Jedis连接池
    private static JedisPool redisPool;
    //最大连接数
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("redis.max.total","20"));
    //jedisPool中最大的idle状态（空闲的）jedis实例的个数
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle","10"));
    //jedisPool中最小的idle状态（空闲的）jedis实例的个数
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle","2"));
    //在borrow一个jedis实例的时候，是否要进行验证操作，如果为true，
    // 则得到的jedis实例肯定是可以用的
    private static Boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow","true"));
    //在return一个jedis实例的时候，是否要进行验证操作，如果为true，
    // 则放回jedispool的jedis实例肯定是可以用的。
    private static Boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("reids.test.return","true"));
    //redis的连接地址
    private static String redisIp = PropertiesUtil.getProperty("redis.ip");
    //redis的连接端口号
    private static Integer redisPort = Integer.parseInt(PropertiesUtil.getProperty("reids.port"));

    /**
     * 初始化redisPool
     */
    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);

        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        //连接耗尽的时候是否阻塞，true是阻塞直到超时，而false则是抛出一个异常
        config.setBlockWhenExhausted(true);

        redisPool = new JedisPool(config,redisIp,redisPort,1000 * 2);
    }

    /**
     * 加载redisPool
     */
    static {
        initPool();
    }

    /**
     * 从redisPool中获取资源信息
     * @return
     */
    public static Jedis getJedis(){
        return redisPool.getResource();
    }

    /**
     * 关闭RedisPool
     */
    public static void closeRedisPool(){
        if(!redisPool.isClosed()){
            redisPool.close();
        }
    }

    /**
     * 销毁RedisPool
     */
    public static void destroyRedisPool(){
        redisPool.destroy();
    }

    public static void main(String[] args) {
        Jedis jedis = redisPool.getResource();
        jedis.set("geelykey","geelyvalue");
        closeRedisPool();
        destroyRedisPool();
        System.out.println("program is end");
    }
}
