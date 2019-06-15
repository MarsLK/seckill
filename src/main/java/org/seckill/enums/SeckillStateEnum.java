package org.seckill.enums;

/**
 *在return new SeckillExecution(seckillId,1,"秒杀成功",successKilled);
 *代码中，我们返回的state和stateInfo参数信息应该是输出给前端的
 * 但是我们不想在我们的return代码中硬编码这两个参数
 * 所以我们应该考虑用枚举的方式将这些常量封装起来
 *
 * @author kankan
 * @creater 2019-06-05 8:55
 */
public enum SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATE_REWRITE(-3,"数据篡改");

    private int state;
    private String info;
    //构造函数
    SeckillStateEnum(int state, String info) {
        this.state = state;
        this.info = info;
    }
    //两个get
    public int getState() {
        return state;
    }


    public String getInfo() {
        return info;
    }

    //做个静态方法
    //根据result显示状态
    public static SeckillStateEnum stateOf(int index)
    {
        for (SeckillStateEnum state : values())
        {
            if (state.getState()==index)
            {
                return state;
            }
        }
        return null;
    }

}
