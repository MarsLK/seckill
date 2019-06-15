package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

/**
 * @author kankan
 * @creater 2019-06-05 9:57
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit spring配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        //System.out.println(list);
        logger.info("list={}", list);
    }
    //[Seckill{seckillId=1000, name='1000元秒杀iPhone6', number=99, startTime=Tue Jun 04 14:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019},
    // Seckill{seckillId=1001, name='500元秒杀iPad2', number=200, startTime=Tue Jun 04 00:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019},
    // Seckill{seckillId=1002, name='300元秒杀小米4', number=300, startTime=Tue Jun 04 00:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019},
    // Seckill{seckillId=1003, name='200元秒杀红米note', number=400, startTime=Tue Jun 04 00:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019}]

    @Test
    public void getById() throws Exception {
        long seckillId = 1000;
        Seckill seckill = seckillService.getById(seckillId);
        //System.out.println(seckill);
        logger.info("seckill={}", seckill);
        //Seckill{seckillId=1000, name='1000元秒杀iPhone6', number=99, startTime=Tue Jun 04 14:00:00 CST 2019, endTime=Wed Jun 05 00:00:00 CST 2019, createTime=Sun Jun 02 08:20:32 CST 2019}
    }

    //在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
    @Test
    public void exportSeckillUrl() throws Exception {
        long seckillId = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        //System.out.println(exposer);
        logger.info("exposer={}", exposer);
        //exposer=Exposer{exposed=false, md5='null', seckillId=1000, now=1559715170616, start=1559628000000, end=1559664000000}
    }

    //执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
    @Test
    public void executeSeckill(){
        long seckillId = 1000;
        long userPhone = 13476191879L;
        String md5 = "bf204e2683e7452aa7db1a50b5713bae";

        /*SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId,userPhone,md5);

        //System.out.println(seckillExecution);
        logger.info("seckillExecution={}",seckillExecution);*/
        //此时再次执行该方法，控制台报错，因为用户重复秒杀了。
        //我们应该在该测试方法中添加try catch,将程序允许的异常包起来而不去向上抛给junit
        try {
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
            System.out.println(seckillExecution);
        } catch (RepeatKillException e) {
            System.out.println(e);//org.seckill.exception.RepeatKillException: seckill repeated
        } catch (SeckillCloseException e1) {
            System.out.println(e1);
        }
    }

    //第四个方法只有拿到了第三个方法暴露的秒杀商品的地址后才能进行测试
    //也就是说只有在第三个方法运行后才能运行测试第四个方法
    //而实际开发中我们不是这样的，需要将第三个测试方法和第四个方法合并到一个方法从而组成一个完整的逻辑流程:
    @Test
    public void executeSeckillProcedure() {
        long seckillId = 1000;
        long phone = 1313131313;
        //在秒杀开启时输出秒杀接口的地址，否则输出系统时间和秒杀时间
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        //查看是否开启秒杀
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            try {
                //执行秒杀操作，有可能失败，有可能成功，所以要抛出我们允许的异常
                //SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
                SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId, phone, md5);
                logger.info(seckillExecution.getStateInfo());
            } catch (RepeatKillException e) {
                System.out.println(e);//org.seckill.exception.RepeatKillException: seckill repeated
            } catch (SeckillCloseException e1) {
                System.out.println(e1);
            }
        }
    }

}