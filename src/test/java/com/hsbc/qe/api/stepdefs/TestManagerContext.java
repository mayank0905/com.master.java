package com.hsbc.qe.api.stepdefs;

import com.hsbc.qe.api.httpservicemanager.*;
import com.hsbc.qe.api.constants.ConfigConstants;
import org.assertj.core.api.SoftAssertions;

public class TestManagerContext {
  private final HttpRequestManager httpRequestManager;
  private final HttpServiceAssertion httpServiceAssertion;
  private final HttpResponseManager httpResponseManager;
  private final RestRequestManager restRequestManager;
  private final TestScenarioContext testScenarioContext;
  public ConfigManager configManager;
  private final SoftAssertions softAssertions;

  public TestManagerContext() {
    configManager = new ConfigManager();
    configManager.initEnvProperties(getEnvProperty());
    httpRequestManager = new HttpRequestManager(configManager);
    restRequestManager = new RestRequestManager(httpRequestManager);
    httpResponseManager = new HttpResponseManager(configManager, httpRequestManager, restRequestManager);
    httpServiceAssertion = new HttpServiceAssertion(httpResponseManager);
    testScenarioContext = new TestScenarioContext();
    softAssertions = new SoftAssertions();
  }

  public HttpRequestManager getHttpRequest() {
    return httpRequestManager;
  }

  public HttpServiceAssertion getHttpAssertion() {
    return httpServiceAssertion;
  }

  public HttpResponseManager getHttpResponse() {
    return httpResponseManager;
  }

  public RestRequestManager getRestRequest() {
    return restRequestManager;
  }

  public TestScenarioContext getScenarioContext() {
    return testScenarioContext;
  }

  public SoftAssertions getSoftAssertions() {
    return softAssertions;
  }

  private String getEnvProperty() {
    return ConfigManager.getSystemPropertyOrSetDefault("env.type", ConfigConstants.DEFAULT_ENV);
  }
}
