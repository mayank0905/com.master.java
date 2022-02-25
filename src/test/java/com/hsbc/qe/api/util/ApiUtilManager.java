package com.hsbc.qe.api.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Strings;
import com.hsbc.qe.api.constants.ConfigConstants;
import com.hsbc.qe.api.enums.ApiContext;
import com.hsbc.qe.api.stepdefs.TestManagerContext;
import com.hsbc.qe.constants.Entity;
import com.hsbc.qe.api.constants.FilePaths;
import com.hsbc.qe.common.utils.DateUtils;
import com.hsbc.qe.common.utils.YamlReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class ApiUtilManager {

  private static final Logger logger = LoggerFactory.getLogger(ApiUtilManager.class);
  public String request;

  public String env = System.getProperty(ConfigConstants.ENV_TYPE).split("_")[0];

  public String getRequestBody(TestManagerContext testManagerContext, String customer)
      throws IOException, URISyntaxException, ParseException {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    ObjectMapper mapper = new ObjectMapper();
    String api = (String) testManagerContext.getScenarioContext().getContext(ApiContext.API_NAME);
    ObjectNode defaults =
        mapper.readValue(
            new File(
                getClass()
                    .getResource(
                        FilePaths.API_PATH_REQUEST_JSON
                            .replaceAll(Entity.API_PATH, api.toLowerCase())
                            .replaceAll(Entity.ENV_TYPE, env))
                    .toURI()),
            ObjectNode.class);
    if (!Strings.isNullOrEmpty(customer)) {
      ObjectReader updater = mapper.readerForUpdating(defaults.get(Entity.REQUEST));
      ObjectMapper jsonWriter = new ObjectMapper();
      request = updater.readTree(jsonWriter.writeValueAsString(getData(customer, api))).toString();
    } else {
      request = mapper.readTree(mapper.writeValueAsString(defaults.get(Entity.REQUEST))).toString();
    }
    return request
        .replaceAll("\\{RandomString}", getRandomAlphaString())
        .replaceAll(
            "\\{ID}",
            (String) testManagerContext.getScenarioContext().getContext(ApiContext.ID))
        .replaceAll("\\{RandomUUID}", getUniqueCorrelationid())
        .replaceAll("\\{currentdate}", DateUtils.getTodayDateInString());
  }

  public Map<String, String> getHeader(String api, String customerID)
      throws IOException, URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode defaults =
        mapper.readValue(
            new File(
                getClass()
                    .getResource(
                        FilePaths.API_PATH_REQUEST_JSON
                            .replaceAll(Entity.API_PATH, api.toLowerCase())
                            .replaceAll(Entity.ENV_TYPE, env))
                    .toURI()),
            ObjectNode.class);
    return mapper.convertValue(
        defaults.get(Entity.HEADER), new TypeReference<Map<String, String>>() {});
  }

  public String getBasePath(String api) throws IOException, URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode defaults =
        mapper.readValue(
            new File(
                getClass()
                    .getResource(
                        FilePaths.API_PATH_REQUEST_JSON
                            .replaceAll(Entity.API_PATH, api.toLowerCase())
                            .replaceAll(Entity.ENV_TYPE, env))
                    .toURI()),
            ObjectNode.class);
    return getJsonNodeValue(defaults, Entity.BASE_PATH);
  }

  public String getSchemaFilePath(TestManagerContext testManagerContext){
    String api = (String) testManagerContext.getScenarioContext().getContext(ApiContext.API_NAME);
    return FilePaths.EXPECTED_RESPONSE_SCHEMA
            .replaceAll(Entity.API_PATH, api.toLowerCase())
            .replaceAll(Entity.ENV_TYPE, env);
  }

  public String getExpectedJSONResponseFilePath(TestManagerContext testManagerContext){
    String api = (String) testManagerContext.getScenarioContext().getContext(ApiContext.API_NAME);
    return FilePaths.EXPECTED_JSON_RESPONSE
            .replaceAll(Entity.API_PATH, api.toLowerCase())
            .replaceAll(Entity.ENV_TYPE, env);
  }

  public void setEntityHostURI(String api, TestManagerContext testManagerContext)
      throws IOException, URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode defaults =
        mapper.readValue(
            new File(
                getClass()
                    .getResource(
                        FilePaths.API_PATH_REQUEST_JSON
                            .replaceAll(Entity.API_PATH, api.toLowerCase())
                            .replaceAll(Entity.ENV_TYPE, env))
                    .toURI()),
            ObjectNode.class);
    String entity_host_uri = testManagerContext.configManager.getEnvProperty(ConfigConstants.ENTITY_HOST_URI);

    if (!Strings.isNullOrEmpty(entity_host_uri)) {
      testManagerContext.configManager.put(ConfigConstants.ENTITY_HOST_URI, "");
    }

    entity_host_uri = getJsonNodeValue(defaults, Entity.HOST_URI);

    if (!Strings.isNullOrEmpty(entity_host_uri)) {
      testManagerContext.configManager.put(ConfigConstants.ENTITY_HOST_URI, entity_host_uri);
    }
  }

  public Object getData(String customer, String api) {
    String filePath =
        FilePaths.TEST_DATA_FILE_PATH
            .replaceAll(Entity.API_PATH, api.toLowerCase())
            .replaceAll(Entity.ENV_TYPE, env);
    YamlReaderUtils yamlReaderUtils = new YamlReaderUtils(ClassLoader.getSystemResourceAsStream(filePath));
    return yamlReaderUtils.getValue(customer);
  }

  public Map<String, String> getParams(String api) throws IOException, URISyntaxException {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode defaults =
        mapper.readValue(
            new File(
                getClass()
                    .getResource(
                        FilePaths.API_PATH_REQUEST_JSON
                            .replaceAll(Entity.API_PATH, api.toLowerCase())
                            .replaceAll(Entity.ENV_TYPE, env))
                    .toURI()),
            ObjectNode.class);

    return mapper.convertValue(
        defaults.get(Entity.PARAMS), new TypeReference<Map<String, String>>() {});
  }

  public Map<String, Object> getExpectedResponseData(String schemaKey, String api) {
    String filePath =
        FilePaths.EXPECTED_RESPONSE_DATA_MAPPING
            .replaceAll(Entity.API_PATH, api.toLowerCase())
            .replaceAll(Entity.ENV_TYPE, env);
    YamlReaderUtils yamlReaderUtils = new YamlReaderUtils(ClassLoader.getSystemResourceAsStream(filePath));
    return yamlReaderUtils.getYamlObj(schemaKey);
  }

  public String getValue(String schemaKey, String api) {
    String filePath = FilePaths.EXPECTED_RESPONSE_DATA_MAPPING.replaceAll(Entity.API_PATH, api.toLowerCase()).replaceAll(Entity.ENV_TYPE, env);
    YamlReaderUtils yamlReaderUtils = new YamlReaderUtils(ClassLoader.getSystemResourceAsStream(filePath));
    return (String) yamlReaderUtils.getValue(schemaKey);
  }

  public List<String> getListValue(String schemaKey, String api) {
    String filePath = FilePaths.EXPECTED_RESPONSE_DATA_MAPPING.replaceAll(Entity.API_PATH, api.toLowerCase()).replaceAll(Entity.ENV_TYPE, env);
    YamlReaderUtils yamlReaderUtils = new YamlReaderUtils(ClassLoader.getSystemResourceAsStream(filePath));
    return (List<String>) yamlReaderUtils.getList(schemaKey);
  }

  public static String getJsonNodeValue(String jsonString, String nodeKey) throws IOException {
    String value = null;
    ObjectMapper mapper = new ObjectMapper();
    JsonNode data = mapper.readTree(jsonString);
    JsonNode node = data;
    if (data.isArray()) {
      if (data.size() == 0) {
        return null;
      } else if (data.size() == 1) {
        node = data.get(0);
      } else {
        //                logger.atWarning().log(
        //                        "Node array is %s and has more than one elements. Returning first
        // element.", data.asText());
        node = data.get(0);
      }
    }
    if (nodeKey.contains("/")) {
      value = node.at(nodeKey) == null ? null : node.at(nodeKey).asText();
    } else {
      value = node.get(nodeKey) == null ? null : node.get(nodeKey).asText();
    }
    return value;
  }

  public String getJsonNodeValue(JsonNode data, String nodeKey) {
    String value = null;
    JsonNode node = data;
    if (data.isArray()) {
      if (data.size() == 0) {
        return null;
      } else if (data.size() == 1) {
        node = data.get(0);
      } else {
        //                logger.atWarning().log(
        //                        "Node array is %s and has more than one elements. Returning first
        // element.", data.asText());
        node = data.get(0);
      }
    }

    if (nodeKey.contains("/")) {
      value = node.at(nodeKey) == null ? null : node.at(nodeKey).asText();
    } else {
      value = node.get(nodeKey) == null ? null : node.get(nodeKey).asText();
    }
    return value;
  }

  public String getUniqueCorrelationid() {
    return UUID.randomUUID().toString();
  }

  public String getRandomAlphaString() {
    int length = 10;
    String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrstuvxyz";
    StringBuilder sb = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < length; i++) {
      sb.append(candidateChars.charAt(random.nextInt(candidateChars.length())));
    }
    return sb.toString();
  }
}
