package top.hhduan.market.utils;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作工具类
 * @author duanhaohao
 */
@Slf4j
public class RedisUtils {
    private final static int DATABASE = Integer.parseInt(PropertiesUtil.get("spring.redis.database"));
    private final static String HOST = PropertiesUtil.get("spring.redis.host");
    private final static int PORT = Integer.parseInt(PropertiesUtil.get("spring.redis.port"));
    private final static String PASSWORD = PropertiesUtil.get("spring.redis.password");
    private final static int MAXIDLE = Integer.parseInt(PropertiesUtil.get("spring.redis.jedis.pool.max-idle"));
    private final static int TIMEOUT = Integer.parseInt(PropertiesUtil.get("spring.redis.timeout"));
    private static JedisPoolHandle jedisPool;

    static {
        log.info("======================receiver redis config========================");
        log.info("HOST=" + HOST + ",PORT=" + PORT + ",MAXIDLE=" + MAXIDLE + ",TIMEOUT=" + TIMEOUT + "PASSWORD=" + PASSWORD);
        JedisPoolConfig config = new JedisPoolConfig();
        // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(MAXIDLE);
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(true);
        RedisUtils.jedisPool = new JedisPoolHandle(config, HOST, PORT, TIMEOUT, PASSWORD, DATABASE);
    }

    /**
     * 获取jedis
     */
    private static Jedis getResource() {
        return jedisPool.getResource();
    }

    /**
     * 返回jedis
     * @param jedis
     */
    private static void returnResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResourceObject(jedis);
        }
    }

    /**
     * 返回jedis
     * @param jedis
     */
    private static void returnBrokenResource(Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnBrokenResource(jedis);
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public static void batchDel(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            Set<String> set = jedis.keys(key + "*");
            for (String keyStr : set) {
                System.out.println(keyStr);
                jedis.del(keyStr);
            }
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 删除key
     *
     * @param key
     */
    public static void del(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            // 获取redis中的value
            jedis.del(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key
     */
    public static Boolean exists(String key) {
        Jedis jedis = null;
        Boolean flag = false;
        try {
            jedis = getResource();
            // 获取redis中的value
            flag = jedis.exists(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return flag;
    }

    /**
     * 判断名称为key的hash中是否存在键为field的域(redis map操作)
     *
     * @param key
     * @param field(于RedisMap中的key)
     */
    public static Boolean hexists(String key, String field) {
        Jedis jedis = null;
        Boolean flag = false;
        try {
            jedis = getResource();
            // 获取redis中的value
            flag = jedis.hexists(key, field);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return flag;
    }

    /**
     * 将 value 的值写入 Redis Map中(redis map操作) HSET 将哈希表 key 中的域 field 的值设为 value
     * 。如果 key 不存在，一个新的哈希表被创建并进行 HSET 操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。 如果 field
     * 是哈希表中的一个新建域，并且值设置成功，返回 1 。如果哈希表中域 field 已经存在且旧值已被新值覆盖，返回 0 。
     *
     * @param key
     * @param field(于RedisMap中的key)
     * @param value(于RedisMap中的value)
     */
    public static void hset(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            // 获取redis中的value
            jedis.hset(key, field, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将 value 的值写入 Redis Map中(redis map操作) HSETNX 将哈希表 key 中的域 field 的值设置为
     * value ，当且仅当域 field 不存在。若域 field 已经存在，该操作无效。如果 key 不存在，一个新哈希表被创建并执行 HSETNX
     * 命令。 设置成功，返回 1 。如果给定域已经存在且没有操作被执行，返回 0.
     *
     * @param key
     * @param field(于RedisMap中的key)
     * @param value(于RedisMap中的value)
     * @param seconds(失效时间)
     */
    public static void hsetEx(String key, String field, String value, int seconds) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            // 获取redis中的value
            jedis.hset(key, field, value);
            jedis.expire(key, seconds);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将 value 的值写入 Redis Map中(redis map操作) 永久有效 HSETNX 将哈希表 key 中的域 field 的值设置为
     * value ，当且仅当域 field 不存在。若域 field 已经存在，该操作无效。如果 key 不存在，一个新哈希表被创建并执行 HSETNX
     * 命令。 设置成功，返回 1 。如果给定域已经存在且没有操作被执行，返回 0.
     *
     * @param key
     * @param field(于RedisMap中的key)
     * @param value(于RedisMap中的value)
     */
    public static void hsetEx(String key, String field, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            // 获取redis中的value
            jedis.hset(key, field, value);
            // 移除给定key的生存时间(设置这个key永不过期)
            jedis.persist(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 返回哈希表key中所有域和值(redis map操作)
     *
     * @param key
     * @return
     */
    public static Map<String, String> hgetAll(String key) {
        Jedis jedis = null;
        Map<String, String> map = new HashMap<>(16);
        try {
            jedis = getResource();
            // 获取redis中的value
            map = jedis.hgetAll(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return map;
    }

    /**
     * 返回名称为key的hash中field对应的value(redis map操作)
     *
     * @param key
     * @param field(于RedisMap中的key)
     */
    public static String hget(String key, String field) {
        Jedis jedis = null;
        String value = "";
        try {
            jedis = getResource();
            // 获取redis中的value
            value = jedis.hget(key, field);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 返回名称为key的hash中field对应的value(redis map操作)
     *
     * @param key
     * @param fields(于RedisMap中的key)(可以传入数组)
     */
    public static List<String> hmget(String key, String... fields) {
        Jedis jedis = null;
        List<String> value = null;
        try {
            jedis = getResource();
            // 获取redis中的value
            value = jedis.hmget(key, fields);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 将数据写入redis List(String 类型的操作)
     *
     * @param key
     * @param values
     */
    public static void lpush(String key, String... values) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.lpush(key, values);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将数据更新入redis List(String 类型的操作)
     *
     * @param key
     * @param index
     * @param value
     */
    public static void lset(String key, long index, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.lset(key, index, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将数据写入redis List(String 类型的操作)
     *
     * @param key
     * @param seconds(失效时间)
     * @param values
     */
    public static void lpushEx(String key, int seconds, String... values) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.lpush(key, values);
            jedis.expire(key, seconds);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 获取redis中 List(String 类型的操作)
     *
     * @param key
     * @param start(开始索引)
     * @param end(结束索引)
     */
    public static List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List<String> values = null;
        try {
            jedis = getResource();
            values = jedis.lrange(key, start, end);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return values;
    }

    /**
     * 获取redis中 List指定索引的值(String 类型的操作)
     *
     * @param key
     * @param index
     */
    public static String lindex(String key, long index) {
        Jedis jedis = null;
        String value = null;
        try {
            jedis = getResource();
            value = jedis.lindex(key, index);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 获取redis中 List的长度(String 类型的操作)
     *
     * @param key
     */
    public static long llen(String key) {
        Jedis jedis = null;
        long length = -1;
        try {
            jedis = getResource();
            length = jedis.llen(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return length;
    }

    /**
     * 将数据写入redis(String 类型的操作)
     *
     * @param key
     * @param value
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.set(key, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将数据写入redis并设置失效日期(String 类型的操作)
     *
     * @param key     于Redis中的key
     * @param seconds 超时时间（单位：秒）
     * @param value   存储数据
     */
    public static void setEx(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.setex(key, seconds, value);
        } catch (Throwable e) {

            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 从redis中读取数据(String 类型的操作)
     *
     * @param key
     */
    public static String get(String key) {
        Jedis jedis = null;
        String value = "";
        try {
            jedis = getResource();
            // 获取redis中的value
            value = jedis.get(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return value;
    }

    /**
     * 将数据写入redis
     *
     * @param key
     * @param value
     */
    public static Long setNx(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.setnx(key, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将数据写入redis并设置失效日期
     *
     * @param key
     * @param value
     * @param seconds
     */
    public static Long setNx(String key, int seconds, String value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            if (jedis.setnx(key, value) == 1) {
                jedis.expire(key, seconds);
                return 1L;
            }
            return 0L;
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
            return null;
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 将键自增一
     *
     * @param key
     */
    public static void incr(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.incr(key);
        } catch (Throwable e) {

            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 向集合set中存放元素【自动去重】
     *
     * @param key
     * @param value
     * @return
     */
    public static Long sadd(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.sadd(key, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return null;
    }

    /**
     * Redis Smembers 命令返回集合中的所有的成员。 不存在的集合 key 被视为空集合。
     *
     * @param key
     * @return
     */
    public static Set<String> smembers(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.smembers(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return null;
    }

    /**
     * Redis Srem 命令用于移除集合中的一个或多个成员元素，不存在的成员元素会被忽略。
     *
     * @param key
     * @param value
     * @return
     */
    public static Long srem(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.srem(key, value);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return null;
    }

    /**
     * 给键的重新设置键的有效期
     *
     * @param key
     * @param seconds
     */
    public static void expire(String key, Integer seconds) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            jedis.expire(key, seconds);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
    }

    /**
     * 返回key的剩余有效时间
     * @param key
     */
    public static long pttl(String key) {
        Jedis jedis = null;
        try {
            jedis = getResource();
            return jedis.pttl(key);
        } catch (Throwable e) {
            // 异常情况释放redis对象
            returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedis);
        }
        return -2;
    }

    private static class JedisPoolConfig extends redis.clients.jedis.JedisPoolConfig {

    }

    private static class JedisPoolHandle extends JedisPool {

        private JedisPoolHandle(JedisPoolConfig config, String host, int port, int timeout, String password, int database) {
            super(config, host, port, timeout, password, database);
        }

    }
}
