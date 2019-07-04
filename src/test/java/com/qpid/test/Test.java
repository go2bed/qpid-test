package com.qpid.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;

public class Test {

    private static EmbeddedAMQPBroker broker;


    @BeforeClass
    public static void setUp() throws Exception {
        broker = new EmbeddedAMQPBroker();
        broker.createExchange();
    }

    @AfterClass
    public static void tearDown() {
        broker.deleteExchange();
    }


    @org.junit.Test
    public void test() throws Exception {
        Sender sender =  new Sender();

        sender.sendMessage();
    }
 }
