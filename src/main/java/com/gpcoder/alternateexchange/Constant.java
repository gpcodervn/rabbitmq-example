package com.gpcoder.alternateexchange;

public final class Constant {

    // Exchange

    public static final String TOPIC_EXCHANGE_NAME = "GPCoder.AltTopicExchange";

    public static final String ALTERNATE_EXCHANGE_NAME = "GPCoder.AltFanoutExchange";

    // Queue

    public static final String JAVA_QUEUE_NAME = "QJava";

    public static final String ALL_QUEUE_NAME = "QAll";

    public static final String UNKNOWN_QUEUE_NAME = "QUnknown";

    // Routing key pattern

    public static final String JAVA_ROUTING_KEY = "java.*.gpcoder.com";

    public static final String GPCODER_ROUTING_KEY = "#.gpcoder.com";

    // Message key

    public static final String JAVA_MSG_KEY = "java.gpcoder.com";

    private Constant() {
        super();
    }
}
