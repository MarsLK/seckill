package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author kankan
 * @creater 2019-06-04 15:16
 */
/**
 * 配置spring和Junit的整合，Junit启动时加载springIOC容器
 * spring-test，junit
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SuccessKilledDao successKilledDao;

    /**
     * 第一次：insertCount=1
     * 第一次：insertCount=0
     */
    @Test
    public void insertSuccessKilled() {
        long seckillId=1000;
        long userPhone=13476191877L;
        int insertCount= successKilledDao.insertSuccessKilled(seckillId,userPhone);
        System.out.println("insertCount="+insertCount);
    }

    /**
     * SuccessKilled{
     * seckillId=1000,
     * userPhone=13476191877,
     * state=0,
     * createTime=Tue Jun 04 15:32:06 CST 2019,
     * seckill=Seckill{seckillId=1000, name='1000元秒杀iPhone6',
     * number=0,
     * startTime=Tue Jun 04 14:00:00 CST 2019,
     * endTime=Wed Jun 05 00:00:00 CST 2019,
     * createTime=Sun Jun 02 08:20:32 CST 2019}}
     *
     *
     Seckill{seckillId=1000, name='1000元秒杀iPhone6', number=0, startTime=Tue Jun 04 14:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019}

     */
    @Test
    public void queryByIdWithSeckill() {
        long seckillId=1000L;
        long userPhone=13476191877L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId,userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}