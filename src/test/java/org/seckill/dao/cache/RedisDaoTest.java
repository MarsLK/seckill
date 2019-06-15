package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SeckillDao;
import org.seckill.entity.Seckill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author kankan
 * @creater 2019-06-15 8:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring的配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;

    @Resource
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() {
        //全局测试 get and put
        //不用访问DB直接通过redis拿到Seckill对象
        // 1.访问redis
        Seckill seckill = redisDao.getSeckill(id);
        if(null == seckill) {
            // 2.缓存中没有，访问数据库
            seckill = seckillDao.queryById(id);
            if(seckill != null) {
                //当缓存没有时将Seckill放入redis缓存中
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                //不用访问DB直接通过redis拿到Seckill对象
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }

    }
}