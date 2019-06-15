package org.seckill.exception;

/**
 * 秒杀相关的所有业务异常
 * @author kankan
 * @creater 2019-06-04 16:10
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
