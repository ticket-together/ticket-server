package com.tickettogether.global.common;

// 프로젝트에서 공통적으로 사용하는 상수들
public class Constant {
    public static final String CATEGORY_CALENDAR = "calendar";

    // RabbitMQ
    public static final String CHAT_QUEUE_NAME = "chat.queue";

    public static final String CHAT_EXCHANGE_NAME = "chat.exchange";

    public static final String ROUTING_KEY = "room.*";

    public static final String DATETIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
}
