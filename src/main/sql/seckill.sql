-- 秒杀执行存储过程  把插入秒杀详情页面与更新秒杀列表页整合
DELIMITER $$ -- onsole ; 转换为 $$
-- 定义存储过程
-- 参数：in 输入参数; out 输出参数
-- row_count():返回上一条修改类型sql(delete,insert,upodate)的影响行数
-- row_count: 0:未修改数据; >0:表示修改的行数; <0:sql错误/未执行修改sql
CREATE PROCEDURE `seckill`.`execute_seckill`
  (IN v_seckill_id BIGINT, IN v_phone BIGINT,
   IN v_kill_time TIMESTAMP, OUT r_result INT)
  BEGIN
    -- 定义变量
    DECLARE insert_count INT DEFAULT 0;
    -- 定义一个事务
    START TRANSACTION;
    -- 插入表中数据
    INSERT ignore INTO success_killed (seckill_id, user_phone, create_time)
    VALUES(v_seckill_id, v_phone, v_kill_time);
    -- 返回上一条修改类型sql(delete,insert,upodate)的影响行数 给变量 insert_count
    SELECT ROW_COUNT() INTO insert_count;
    -- insert_count 0:未修改数据; >0:表示修改的行数; <0:sql错误/未执行修改sql
    -- 0:未修改数据 重复秒杀
    IF (insert_count = 0) THEN
      ROLLBACK;
      SET r_result = -1;
    -- <0:sql错误/未执行修改sql  系统异常
    ELSEIF (insert_count < 0) THEN
      ROLLBACK ;
      SET r_result = -2;
    -- >0,为1 "秒杀成功" 但是需要去修改seckill数据库信息
    ELSE
      -- 秒杀成功,去修改seckill中的number
      UPDATE seckill SET number = number - 1
      WHERE seckill_id = v_seckill_id AND end_time > v_kill_time
            AND start_time < v_kill_time AND number > 0;
      -- 返回上一条修改类型sql(delete,insert,upodate)的影响行数 给变量 insert_count
      SELECT ROW_COUNT() INTO insert_count;
      -- insert_count说明插入没有成功--秒杀结束（参照数据字典）
      IF (insert_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      -- insert_count <0:sql错误/未执行修改sql--系统异常
      ELSEIF (insert_count < 0) THEN
        ROLLBACK;
        SET r_result = -2;
      ELSE
        -- >0,为1 "秒杀成功"
        COMMIT;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$
-- 代表存储过程定义结束

DELIMITER ;

SET @r_result = -3;
-- 执行存储过程
call execute_seckill(1003, 13631231234, now(), @r_result);
-- 获取结果
SELECT @r_result;

-- 存储过程
-- 1.存储过程优化：事务行级锁持有的时间
-- 2.不要过度依赖存储过程
-- 3.简单的逻辑可以应用存储过程
-- 4.QPS:一个秒杀单6000/qps