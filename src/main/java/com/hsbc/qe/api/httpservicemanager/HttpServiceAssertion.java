package com.hsbc.qe.api.httpservicemanager;

import com.google.common.base.Strings;
import com.hsbc.qe.common.utils.FileUtil;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpServiceAssertion {

    private HttpResponseManager httpResponseManager;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public HttpServiceAssertion(HttpResponseManager http){
        httpResponseManager = http;
    }

    public void statusCodeIs(final int expectedStatusCode) {
        int actualStatusCode = httpResponseManager.getResponse().getStatusCode();
        String assertionReason = "Expected Status code should match the actual status code.";
        assertThat(assertionReason, actualStatusCode, is(equalTo(expectedStatusCode)));
    }

    public void statusCodeIsNot(int statusCode) {
        int actualStatusCode = httpResponseManager.getResponse().getStatusCode();
        String assertionReason = "Expected status code should not match the actual status code.";
        assertThat(assertionReason, actualStatusCode, is(not(equalTo(statusCode))));
    }

    public void bodyContainsPropertyWithValue(String propertyPath, String expectedPropertyValue) {
        JsonPath actualResponse = httpResponseManager.getResponse().jsonPath();
        String propertyValueFromResponse = null;
        if (!Strings.isNullOrEmpty(actualResponse.getString(propertyPath))) {
            propertyValueFromResponse = actualResponse.getString(propertyPath);
        }
        String assertionReason = String.format("Response field '%s' value is not equal to '%s' value.",propertyPath,expectedPropertyValue);
        assertThat(assertionReason, propertyValueFromResponse, is(equalTo(expectedPropertyValue)));
    }

    public void bodyDoesNotContainPath(String path) {
        JsonPath responsePath = httpResponseManager.getResponse().body().path(path);
        assertThat(responsePath, nullValue());
    }

    public void bodyContainskey(String key) throws InterruptedException {
        String propertyVal= httpResponseManager.getResponse().jsonPath().getString(key);
        assertThat("Key not found",!(Strings.isNullOrEmpty(propertyVal)));
    }

    public void validateTheJsonResponseSchema(String jsonSchemaPath) {
        try {
            JsonSchemaValidator validator = JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonSchemaPath);
            httpResponseManager.getResponse().then().assertThat().body(validator);
            logger.info("json schema validation is OK");
        } catch (Exception oEx) {
            logger.error("json schema validation is NOT OK");
            logger.debug("BasicChecks - validateSchema >> validation schema failed. -- exception: "
                    + oEx.getLocalizedMessage());
        }
    }

    /**
     * Compare two JSON strings
     * JSONassert Java library helps to assert JSON equality effectively.
     * Under the covers, JSONassert converts your string into a JSON object and compares the logical structure and data with the actual JSON.
     * When strict is set to false (recommended), it forgives reordering data and extending results (as long as all the expected elements are there), making tests less brittle.
     * @see <https://github.com/skyscreamer/JSONassert>
     * @param expectedResponseFilePath exptected JSON response file path
     * @param comparisonModeType select the type of the comparison to assert the data
     * @throws Exception
     */
    public void validateTheJSONResponse(String expectedResponseFilePath, String comparisonModeType) throws Exception {
        String actualResponse = httpResponseManager.getResponse().asString();
        String expectedResponse = FileUtil.readFileAsString(expectedResponseFilePath);
        switch (comparisonModeType.toUpperCase()) {
            case "LENIENT":
                JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.LENIENT);
                break;
            case "STRICT":
                JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT);
                break;
            case "STRICT_ORDER":
                JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.STRICT_ORDER);
                break;
            case "NON_EXTENSIBLE":
                JSONAssert.assertEquals(expectedResponse, actualResponse, JSONCompareMode.NON_EXTENSIBLE);
                break;
            default:
                throw new Exception("Invalid comparison mode: " + comparisonModeType);
        }
    }

    public String getJsonPathValue(String jsonPath){
        JsonPath actualResponse = httpResponseManager.getResponse().jsonPath();
        return actualResponse.getString(jsonPath);
    }

    public List<Object> getListofJsonPathValue(String jsonPath) {
        JsonPath actualResponse = httpResponseManager.getResponse().jsonPath();
        return actualResponse.getList(jsonPath);
    }

    public List<Map<String, Object>> getListofJsonPathValue1(String jsonPath) {
        JsonPath actualResponse = httpResponseManager.getResponse().jsonPath();
        return actualResponse.getList(jsonPath);
    }

    public List<Map<String, String>> getResponseAsList(){
        return httpResponseManager.getResponse().as(List.class);
    }

    public List<Map<String, Object>> getResponseAsList1(){
        return httpResponseManager.getResponse().as(List.class);
    }

}
