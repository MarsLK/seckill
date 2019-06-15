package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;

/**
 * @author kankan
 * @creater 2019-06-02 9:56
 */
public interface SuccessKilledDao {

    /**
     * 插入购买明细,可过滤重复秒杀
     */
    int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone") long userPhone);


    /**
     * 根据ID查询SuccessKilled并携带秒杀产品对象实体
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId")long seckillId, @Param("userPhone")long userPhone);


}
