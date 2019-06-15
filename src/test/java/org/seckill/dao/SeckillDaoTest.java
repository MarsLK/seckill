package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author kankan
 * @creater 2019-06-03 11:05
 */
/**
* 配置spring和Junit的整合，Junit启动时加载springIOC容器
* spring-test，junit
*/
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    //注入Dao实现类依赖
    @Resource
    private SeckillDao seckillDao;

    /**
     * java.sql.SQLException: An attempt by a client to checkout a Connection has timed out.
     at com.mchange.v2.sql.SqlUtils.toSQLException(SqlUtils.java:106)
     at com.mchange.v2.sql.SqlUtils.toSQLException(SqlUtils.java:65)
     at com.mchange.v2.c3p0.impl.C3P0PooledConnectionPool.checkoutPooledConnection(C3P0PooledConnectionPool.java:527)
     at com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource.getConnection(AbstractPoolBackedDataSource.java:128)
     at org.springframework.jdbc.datasource.DataSourceUtils.doGetConnection(DataSourceUtils.java:111)
     at org.springframework.jdbc.datasource.DataSourceUtils.getConnection(DataSourceUtils.java:77)

     1000元秒杀iPhone6
     Seckill{seckillId=1000, name='1000元秒杀iPhone6', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Jun 02 08:20:32 CST 2019}

     */
    @Test
    public void queryById() {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    /**
     * org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
     *
     *
     * Seckill{seckillId=1000, name='1000元秒杀iPhone6', number=100, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Jun 02 08:20:32 CST 2019}
     Seckill{seckillId=1001, name='500元秒杀iPad2', number=200, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Jun 02 08:20:32 CST 2019}
     Seckill{seckillId=1002, name='300元秒杀小米4', number=300, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Jun 02 08:20:32 CST 2019}
     Seckill{seckillId=1003, name='200元秒杀红米note', number=400, startTime=Sun Nov 01 00:00:00 CST 2015, endTime=Mon Nov 02 00:00:00 CST 2015, createTime=Sun Jun 02 08:20:32 CST 2019}

     */
    @Test
    public void queryAll() {
        //List<Seckill> queryAll(int offset, int limit);
        //java没有保存形参的记录queryAll(int offset, int limit)-->queryAll(arg0, arg1)
        //有多个参数的时候，在dao层形参前用@Param
        List<Seckill> seckills = seckillDao.queryAll(0,100);
        for (Seckill seckill : seckills){
            System.out.println(seckill);
        }
    }

    /**
     *  org.apache.ibatis.binding.BindingException: Parameter 'seckillId' not found. Available parameters are [0, 1, param1, param2]
     *
     *  updateCount=1
     */
    @Test
    public void reduceNumber() {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount=" + updateCount);
    }
}