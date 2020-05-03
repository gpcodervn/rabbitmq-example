package com.gpcoder.exchange2exchange;

public final class Constant {

    // Exchange

    public static final String HEADERS_EXCHANGE_NAME = "GPCoderHeadersExchange";

    public static final String TOPIC_EXCHANGE_NAME = "GPCoderTopicExchange";

    // Queue

    public static final String DEV_QUEUE_NAME = "QDeveloper";

    public static final String MANAGER_QUEUE_NAME = "QManager";

    public static final String PUBLISHED_QUEUE_NAME = "QPublished";

    public static final String JAVA_QUEUE_NAME = "QJava";

    public static final String ALL_QUEUE_NAME = "QAll";

    // Routing key pattern

    public static final String JAVA_ROUTING_KEY = "java.*.gpcoder.com";

    public static final String GPCODER_ROUTING_KEY = "#.gpcoder.com";

    // Message key

    public static final String JAVA_CORE_MSG_KEY = "java.core.gpcoder.com";

    public static final String JAVA_MSG_KEY = "java.gpcoder.com";

    public static final String DESIGN_PATTERN_MSG_KEY = "design-pattern.gpcoder.com";

    public static final String NOT_MATCHING_MSG_KEY = "java.collection.gpcoder.com.vn";

    private Constant() {
        super();
    }
}
