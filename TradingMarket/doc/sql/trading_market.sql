/*
Navicat MySQL Data Transfer

Source Database       : trading_market

Target Server Type    : MYSQL

Date: 2021-02-01 09:18:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
    `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
    `consignee_name` varchar(100) DEFAULT NULL COMMENT '收货人姓名',
    `consignee_mobile` varchar(20) DEFAULT NULL COMMENT '收货人手机号',
    `province` varchar(32) DEFAULT NULL COMMENT '省',
    `city` varchar(32) DEFAULT NULL COMMENT '市',
    `district` varchar(32) DEFAULT NULL COMMENT '区',
    `street` varchar(64) DEFAULT NULL COMMENT '街道',
    `address_detail` varchar(64) DEFAULT NULL COMMENT '详细地址',
    `is_default_address` tinyint(2) DEFAULT NULL COMMENT '是否为默认地址 1-默认 2-非默认',
    `create_time` datetime DEFAULT NULL COMMENT '创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '修改时间',
    `status`  tinyint(2) DEFAULT NULL COMMENT '状态 1-有效 2-删除',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='收货地址表';

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `title` varchar(50) DEFAULT NULL COMMENT '标题/说明',
  `image` varchar(150) DEFAULT NULL COMMENT '图片',
  `url` varchar(150) DEFAULT NULL COMMENT '跳转URL链接',
  `serial_num` int(4) DEFAULT '0' COMMENT '序号',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 1-有效 2-无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_man` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_man` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='Banner表';

-- ----------------------------
-- Table structure for chat
-- ----------------------------
DROP TABLE IF EXISTS `chat`;
CREATE TABLE `chat` (
  `id` varchar(32) NOT NULL COMMENT '聊天主表id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户id',
  `another_user_id` varchar(32) DEFAULT NULL COMMENT '对方用户id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天主表';

-- ----------------------------
-- Table structure for chat_detail
-- ----------------------------
DROP TABLE IF EXISTS `chat_detail`;
CREATE TABLE `chat_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `chat_id` varchar(32) DEFAULT NULL COMMENT '消息对话框编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '消息f发送者用户编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '消息f发送者用户昵称',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '消息f发送者用户头像',
  `content` varchar(1000) DEFAULT NULL COMMENT '发送内容',
  `type` tinyint(2) DEFAULT '1' COMMENT '消息类型 1-用户消息 2-系统消息',
  `is_latest` tinyint(2) DEFAULT '1' COMMENT '是否最后一条消息 1-是 2-否',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（发送时间）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=205 DEFAULT CHARSET=utf8 COMMENT='聊天详情表';

-- ----------------------------
-- Table structure for chat_list
-- ----------------------------
DROP TABLE IF EXISTS `chat_list`;
CREATE TABLE `chat_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主表自增id',
  `chat_id` varchar(32) DEFAULT NULL COMMENT '聊天主表id',
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '当前用户编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '当前用户昵称',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '当前用户头像',
  `another_user_id` varchar(32) DEFAULT NULL COMMENT '对方用户编号',
  `another_user_name` varchar(50) DEFAULT NULL COMMENT '对方用户昵称',
  `another_user_avatar` varchar(255) DEFAULT NULL COMMENT '对方用户头像',
  `is_online` tinyint(2) DEFAULT '2' COMMENT '是否在线 1-是 2-否',
  `unread` tinyint(4) DEFAULT '0' COMMENT '未读数',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态 1-有效 2-删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8 COMMENT='聊天列表';

-- ----------------------------
-- Table structure for comment_reply
-- ----------------------------
DROP TABLE IF EXISTS `comment_reply`;
CREATE TABLE `comment_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论id 主键',
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  `content` varchar(500) DEFAULT NULL COMMENT '评论内容',
  `from_user_id` varchar(32) DEFAULT NULL COMMENT '评论/回复用户id',
  `from_user_name` varchar(50) DEFAULT NULL COMMENT '评论/回复用户昵称',
  `from_user_avatar` varchar(255) DEFAULT NULL COMMENT '评论/回复用户头像',
  `create_time` datetime DEFAULT NULL COMMENT '评论/回复时间',
  `type` tinyint(2) DEFAULT NULL COMMENT '类型 1-评论 2-回复',
  `to_user_id` varchar(32) DEFAULT NULL COMMENT '回复人编号',
  `to_user_name` varchar(50) DEFAULT NULL COMMENT '回复人昵称',
  `to_user_avatar` varchar(255) DEFAULT NULL COMMENT '回复人头像',
  `reply_id` int(11) DEFAULT NULL COMMENT '回复id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单编号',
  `buying_user_id` varchar(32) DEFAULT NULL COMMENT '购买人id',
  `product_id` int(11) DEFAULT NULL,
  `trade_status` tinyint(2) DEFAULT '1' COMMENT '1-已下单 2-已取消 3-已结算',
  `pay_status` tinyint(2) DEFAULT '1' COMMENT '1-待付款 2-付款中 3-已付款 4-付款失败',
  `buying_status` tinyint(2) DEFAULT '1' COMMENT '购买状态 1-有效 2-删除',
  `selling_status` tinyint(2) DEFAULT '1' COMMENT '卖出状态 1-有效 2-删除',
  `order_amount` decimal(15,2) DEFAULT NULL COMMENT '订单金额',
  `pay_amount` decimal(15,2) DEFAULT NULL COMMENT '支付金额',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `completion_time` datetime DEFAULT NULL COMMENT '订单完成时间',
  `out_trade_no` varchar(100) DEFAULT NULL COMMENT '第三方支付流水号',
  `consignee_mobile` varchar(20) DEFAULT NULL COMMENT '收货人手机号',
  `consignee_name` varchar(32) DEFAULT NULL COMMENT '收货人姓名',
  `consignee_address` varchar(150) DEFAULT NULL COMMENT '收货地址',
  `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '订单备注',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Table structure for payment_log
-- ----------------------------
DROP TABLE IF EXISTS `payment_log`;
CREATE TABLE `payment_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `order_id` int(11) DEFAULT NULL COMMENT '商品 订单号',
  `trade_no` varchar(100) DEFAULT NULL COMMENT '支付请求订单号',
  `out_trade_no` varchar(100) DEFAULT NULL COMMENT '第三方支付流水号',
  `trade_status` varchar(50) DEFAULT NULL COMMENT '订单交易状态',
  `trade_amount` decimal(15,2) DEFAULT NULL COMMENT '交易金额',
  `actual_pay_amount` decimal(15,2) DEFAULT NULL COMMENT '实际支付金额',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户编号',
  `pay_channel` tinyint(4) DEFAULT NULL COMMENT '支付渠道 1-支付宝 2-微信',
  `request_time` datetime DEFAULT NULL COMMENT '支付请求时间',
  `completion_time` datetime DEFAULT NULL COMMENT '实际完成时间',
  `request_params` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8 COMMENT='支付流水表';

-- ----------------------------
-- Table structure for payment_notify_log
-- ----------------------------
DROP TABLE IF EXISTS `payment_notify_log`;
CREATE TABLE `payment_notify_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `trade_no` varchar(100) DEFAULT NULL COMMENT '第三方支付订单号',
  `out_trade_no` varchar(100) DEFAULT NULL COMMENT '系统订单号',
  `trade_status` varchar(50) DEFAULT NULL COMMENT '支付状态',
  `request_params` varchar(2000) DEFAULT NULL COMMENT '回调请求入参',
  `request_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='支付回调通知流水表';

-- ----------------------------
-- Table structure for praise
-- ----------------------------
DROP TABLE IF EXISTS `praise`;
CREATE TABLE `praise` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '点赞用户编号',
  `user_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `user_avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `status` tinyint(2) DEFAULT '1' COMMENT '点赞状态 1-点赞 2-取消赞',
  `praise_time` datetime DEFAULT NULL COMMENT '点赞时间/取消点赞时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='点赞表';

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品编号',
  `product_imgs` varchar(2000) DEFAULT NULL COMMENT '商品图片，多个图片地址用英文逗号隔开',
  `product_desc` varchar(500) DEFAULT NULL COMMENT '商品描述',
  `product_price` decimal(10,2) DEFAULT NULL COMMENT '商品价格',
  `product_type_id` int(6) DEFAULT NULL COMMENT '商品类别',
  `publish_user_id` varchar(32) DEFAULT NULL COMMENT '发布人',
  `product_address` varchar(500) DEFAULT NULL COMMENT '商品发布时地理位置',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（发布时间）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Table structure for product_type
-- ----------------------------
DROP TABLE IF EXISTS `product_type`;
CREATE TABLE `product_type` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '类别id',
  `name` varchar(100) DEFAULT NULL COMMENT '类别名称',
  `status` tinyint(2) DEFAULT NULL COMMENT '状态 1-有效；2-无效',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_man` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_man` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='商品类别表';

-- ----------------------------
-- Table structure for request_log
-- ----------------------------
DROP TABLE IF EXISTS `request_log`;
CREATE TABLE `request_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `ip` varchar(50) NOT NULL COMMENT '请求方ip',
  `url` varchar(500) DEFAULT NULL COMMENT '请求路径',
  `headers` varchar(2000) DEFAULT NULL COMMENT '头部信息',
  `request_type` varchar(50) DEFAULT NULL COMMENT '请求类型',
  `request_params` varchar(2000) DEFAULT NULL COMMENT '请求参数',
  `request_time` datetime DEFAULT NULL COMMENT '请求时间',
  `exception_detail` varchar(2000) DEFAULT NULL COMMENT '异常信息',
  `log_type` varchar(50) DEFAULT NULL COMMENT '日志级别 INFO;ERROR',
  `time` bigint(20) DEFAULT NULL COMMENT '请求时长',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10209 DEFAULT CHARSET=utf8 COMMENT='请求流水表';

-- ----------------------------
-- Table structure for trade_log
-- ----------------------------
DROP TABLE IF EXISTS `trade_log`;
CREATE TABLE `trade_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '交易流水号',
  `product_id` int(11) DEFAULT NULL COMMENT '商品编号',
  `pulish_user_id` varchar(32) DEFAULT NULL COMMENT '发布人',
  `buying_user_id` varchar(32) DEFAULT NULL COMMENT '购买人',
  `trade_time` datetime DEFAULT NULL COMMENT '交易时间',
  `trade_status` tinyint(2) DEFAULT '1' COMMENT '交易状态 1-未交易 2-交易中 3-交易成功 4-交易失败',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易流水表';

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(32) NOT NULL COMMENT '用户编号',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `user_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sign` varchar(255) DEFAULT NULL COMMENT '签名',
  `source` tinyint(4) DEFAULT NULL COMMENT '来源 1——H5',
  `user_avatar` varchar(150) DEFAULT NULL COMMENT '用户头像',
  `login_counts` int(6) DEFAULT '0' COMMENT '登录次数',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_status` tinyint(2) DEFAULT '0' COMMENT '是否登录 1-已登录 2-未登录',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态 1-正常 2-锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间（注册时间）',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `device_id` varchar(500) DEFAULT NULL COMMENT '设备号',
  `client_version` varchar(100) DEFAULT NULL COMMENT '客户端版本号',
  `token` varchar(100) DEFAULT NULL COMMENT '用户令牌',
  `token_expired` bigint(20) DEFAULT NULL COMMENT 'token失效时间',
  `address` varchar(500) DEFAULT NULL COMMENT '收获地址',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
