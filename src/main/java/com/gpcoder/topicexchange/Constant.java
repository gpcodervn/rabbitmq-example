package com.gpcoder.topicexchange;

public final class Constant {

    // Exchange

    public static final String EXCHANGE_NAME = "GPCoderTopicExchange";

    // Queue

    public static final String JAVA_QUEUE_NAME = "QJava";

    public static final String GENERAL_QUEUE_NAME = "QAll";

    private Constant() {
        super();
    }

    // Routing key pattern

    public static final String JAVA_ROUTING_KEY = "java.*.gpcoder.com";

    public static final String GPCODER_ROUTING_KEY = "#.gpcoder.com";

    // Message key

    public static final String JAVA_CORE_MSG_KEY = "java.core.gpcoder.com";

    public static final String JAVA_MSG_KEY = "java.gpcoder.com";

    public static final String DESIGN_PATTERN_MSG_KEY = "design-pattern.gpcoder.com";

    public static final String NOT_MATCHING_MSG_KEY = "java.collection.gpcoder.com.vn";
}
