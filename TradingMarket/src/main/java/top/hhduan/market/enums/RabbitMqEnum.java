package top.hhduan.market.enums;


/**
 * 定义rabbitMq需要的常量
 * @author duanhaohao
 */
public class RabbitMqEnum {

    /**
     * 队列名称
     */
    public enum QueueName {
        /**
         * 队列名称枚举
         */
        SECONDARY_ORDER_QUEUE("SECONDARY_ORDER_QUEUE", "正常订单队列"),
        SECONDARY_ORDER_DEAD_LETTER_QUEUE("SECONDARY_ORDER_DEAD_LETTER_QUEUE", "订单死信队列"),
        ;

        private String code;
        private String name;

        QueueName(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

    }

    /**
     * describe: 定义routing_key
     * creat_user: admin
     * creat_date: 2017/10/31
     **/
    public enum RoutingEnum {
        /**
         * 路由名称枚举
         */
        SECONDARY_ORDER_ROUTING("SECONDARY_ORDER_ROUTING", "正常订单路由"),
        SECONDARY_ORDER_DEAD_LETTER_ROUTING("SECONDARY_ORDER_DEAD_LETTER_ROUTING", "订单死信路由"),
        ;


        private String code;
        private String name;

        RoutingEnum(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
    /**
     * @Description:定义数据交换方式
     * @author admin
     */
    public enum Exchange {
        /**
         * 交换机枚举
         */
        SECONDARY_CONTRACT_DIRECT("SECONDARY_CONTRACT_DIRECT", "点对点"),
        SECONDARY_ORDER_DEAD_LETTER_ECHANGE("SECONDARY_ORDER_DEAD_LETTER_ECHANGE", "订单死信交换机"),
        ;

        private String code;
        private String name;

        Exchange(String code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }
}
