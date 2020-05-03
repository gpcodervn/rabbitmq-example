package com.gpcoder.directexchange;

public final class Constant {

    // Exchange

    public static final String EXCHANGE_NAME = "GPCoderDirectExchange";

    // Routing key

    public static final String DEV_ROUTING_KEY = "devGroup";

    public static final String MANAGER_ROUTING_KEY = "managerGroup";

    public static final String GENERAL_ROUTING_KEY = "generalGroup";

    // Queue

    public static final String DEV_QUEUE_NAME = "QDeveloper";

    public static final String MANAGER_QUEUE_NAME = "QManager";

    public static final String GENERAL_QUEUE_NAME = "QGeneral";

    private Constant() {
        super();
    }
}
