package top.hhduan.market.constants;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/10/16 9:27
 * @description: rabbitMq常量
 * @version: 1.0
 */
public class RabbitMqConstants {

    /**
     * 死信队列交换机
     */
    public final static String DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";

    /**
     * 死信队列路由key
     */
    public final static String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
}
