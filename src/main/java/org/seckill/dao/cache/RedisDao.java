package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;



/**
 * @author kankan
 * @creater 2019-06-15 8:34
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final JedisPool jedisPool;
    //构造方法
    public RedisDao(String id,int port) {
        jedisPool = new JedisPool(id,port);
    }

    //只需要知道这个对象是什么class，内部有一个schema描述这个class是什么结构
    //.class是字节码文件，代表这个类的字节码对象，通过反射可以知道字节码文件对应对象有哪些属性和方法。序列化的本质：通过字节码和字节码对应的对象有哪些属性，把字节码的数据传递给那些属性
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    //不用访问DB直接通过redis拿到Seckill对象
    public Seckill getSeckill(long seckillId) {
        //redis操作逻辑
        try {
            //JedisPool相当于数据库连接池,Jedis相当于数据库的Connection
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                //redis或它原生的jedis并没有实现内部序列化操作，不像memcached内部做了序列化
                //典型的缓存访问逻辑：get->得到一个二进制数组byte[]（无论是什么对象或图片或文字存储都是二进制数组）->反序列化得到Object(Seckill)
                //高并发里面容易被忽视的一个点，序列化问题，jdk自带的序列化机制serializable效率比较低
                //采用自定义序列化,使用开源社区的方案，pom.xml中导入protostuff的依赖
                //protostuff把一个对象转化为二进制数组，传入redis当中。只需要知道这个对象是什么class，内部有一个schema描述这个class是什么结构。要求该对象为pojo，即有getter setter方法的普通java对象
                byte[] bytes = jedis.get(key.getBytes());
                //缓存中获取到
                if(bytes != null) {
                    Seckill seckill = schema.newMessage(); //Seckill的空对象
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);   //把字节数组的数据传入空对象中
                    //Seckill被反序列化
                    return seckill;
                }
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }

    //当缓存没有时将Seckill放入redis缓存中
    public String putSeckill(Seckill seckill) {
        //把Seckill对象序列化为字节数组放入redis
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                //超时缓存
                int timeout = 60 * 60;//1小时
                String result = jedis.setex(key.getBytes(),timeout,bytes);  //String类型的result，如果错误会返回错误信息，如果成功会返回ok

                return result;
            }finally {
                jedis.close();
            }
        }catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

        return null;
    }
}
