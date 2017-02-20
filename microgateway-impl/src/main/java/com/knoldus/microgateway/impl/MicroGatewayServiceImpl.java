package com.knoldus.microgateway.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

import akka.NotUsed;
import com.knoldus.microgateway.api.MicrogatewayService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import org.json.JSONObject;

public class MicroGatewayServiceImpl implements MicrogatewayService {

  @Override public ServiceCall<NotUsed, Optional<String>> sayHello(String id) {
    return request -> {
      Properties prop = new Properties();
      String fileName = "/application.conf";
      InputStream inputStream = this.getClass().getResourceAsStream(fileName);
      String environmentUrl = "";
      try {
        if (inputStream != null) {
          prop.load(inputStream);
          String gateway_localUrl = prop.getProperty("GATEWAY_URL");
          Map<String, String> env = System.getenv();
          environmentUrl = env.getOrDefault("GATEWAY_URL",
              gateway_localUrl.substring(1, gateway_localUrl.length() - 1));

        } else {
          throw new FileNotFoundException(
              "property file '" + fileName + "' not found in the classpath");
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }

      Optional<String> userName = getUserName(id, environmentUrl);
      Optional<String> message = null;
      message = getHelloMessage(userName.orElse(" Anonymous !!"), environmentUrl);
      return CompletableFuture.completedFuture(message);
    };
  }

  /**
   * It returns the name of the user by id
   *
   * @param id
   */
  private Optional<String> getUserName(String id, String gatewayUrl) {
    Optional<String> name = null;
    try {
      URL url = new URL(gatewayUrl + "/api/user/" + id);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      } else {
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String userDetails;
        while ((userDetails = br.readLine()) != null) {
          if (userDetails != null) {
            JSONObject jsonObject = new JSONObject(userDetails);
            name = Optional.of(jsonObject.getString("name"));
          }
        }

      }
      conn.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return name;
  }

  /**
   * It returns the hello message by the user name
   *
   * @param name
   */
  private Optional<String> getHelloMessage(String name, String gatewayUrl) {
    Optional<String> messageResult = null;
    try {
      URL url = new URL(gatewayUrl + "/api/hello/" + name);
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      } else {
        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String message;
        while ((message = br.readLine()) != null) {
          if (message != null) {
            messageResult = Optional.of(message);
          }
        }

      }
      conn.disconnect();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return messageResult;
  }

  /**
   * It returns the status of the micro service
   */
  @Override public ServiceCall<NotUsed, String> health() {
    return request -> CompletableFuture.completedFuture("Health is up");
  }
}
