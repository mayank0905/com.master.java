package com.hsbc.qe.api.stepdefs;

import com.hsbc.qe.api.httpservicemanager.HttpResponseManager;
import com.hsbc.qe.api.httpservicemanager.RestRequestManager;
import com.hsbc.qe.api.enums.ApiContext;
import com.hsbc.qe.api.util.ApiUtilManager;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

public class CommonWhenTestSteps {

  HttpResponseManager httpResponseManager;
  TestManagerContext testManagerContext;
  RestRequestManager restRequestManager;

  public CommonWhenTestSteps(TestManagerContext context) {
    testManagerContext = context;
    httpResponseManager = testManagerContext.getHttpResponse();
    restRequestManager = testManagerContext.getRestRequest();
  }

  @When("^the client performs (.+) request on API \"(.+)\"$")
  public void perform_Http_Request(String httpMethod, String url) throws Throwable {
    httpResponseManager.setResponsePrefix("");
    ApiUtilManager apiUtilManager = new ApiUtilManager();
    httpResponseManager.setReponse(httpResponseManager.doRequest(httpMethod, apiUtilManager.getBasePath(url)));
  }

  @When("I call method {string}")
  public void iCallMethodPOST(String httpMethod) throws Exception {
    httpResponseManager.setResponsePrefix("");
    String basePath = (String) testManagerContext.getScenarioContext().getContext(ApiContext.BASE_PATH);
    httpResponseManager.setReponse(httpResponseManager.doRequest(httpMethod, basePath));
  }

  @And("I get the response")
  public void iGetTheResponse() {
    testManagerContext
        .getScenarioContext()
        .setContext(ApiContext.RESPONSE_BODY, httpResponseManager.getResponse().asString());
  }

    @And("I save the initial response")
    public void iSaveTheInitialResponse() {
      testManagerContext
              .getScenarioContext()
              .setContext(ApiContext.INITIAL_RESPONSE_BODY, httpResponseManager.getResponse().asString());
    }
}
