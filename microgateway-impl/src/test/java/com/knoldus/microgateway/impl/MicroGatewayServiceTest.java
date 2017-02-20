package com.knoldus.microgateway.impl;

import com.knoldus.microgateway.api.MicrogatewayService;
import com.lightbend.lagom.javadsl.testkit.ServiceTest.TestServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.startServer;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

public class MicroGatewayServiceTest {

  private static TestServer testServer;

  @BeforeClass public static void setUp() {
    testServer = startServer(defaultSetup().withCluster(false));
  }

  @AfterClass public static void tearDown() {
    if (testServer != null) {
      testServer.stop();
      testServer = null;
    }
  }

  @Test public void shouldRepresentHealthStatus() throws Exception {
    MicrogatewayService microgatewayService = testServer.client(MicrogatewayService.class);
    String healthStatus =
        microgatewayService.health().invoke().toCompletableFuture().get(5, SECONDS);
    String expected = "Health is up";
    assertEquals(expected, healthStatus);
  }
}
