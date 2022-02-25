package com.hsbc.qe.api.httpservicemanager;

import com.hsbc.qe.api.httpoperations.HttpsCertTrust;
import com.hsbc.qe.api.httpoperations.WaitCondition;
import com.hsbc.qe.api.constants.ConfigConstants;
import com.hsbc.qe.api.customexceptions.CustomException;
import com.hsbc.qe.common.utils.TimeUtil;
import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;


public class HttpRequestManager {

    private static final Logger logger = LoggerFactory.getLogger(HttpRequestManager.class);

    public RestServiceConfigManager restConfig;
    public RequestSpecification specification;
    private WaitCondition waitCondition;
    private ConfigManager configManager;
    private String requestBody;


    public HttpRequestManager(ConfigManager configManager) {
        this.restConfig = new RestServiceConfigManager(configManager);
        this.configManager = configManager;
        specification = RestAssured.given();
    }

    public void onCreate() {
        Optional<String> baseUri = Optional.of(configManager.getEnvProperty(ConfigConstants.BASE_URI));

        baseUri.ifPresent(this::baseUri);

        Optional<String> proxy = Optional.ofNullable(configManager.getEnvProperty(ConfigConstants.PROXY));

        boolean urlEncodingEnabled = configManager.getBoolean(ConfigConstants.URL_ENCODING_ENABLED);
        specification.urlEncodingEnabled(urlEncodingEnabled);

        boolean relaxedHttps = configManager.getBoolean(ConfigConstants.RELAXED_HTTPS);
        if (relaxedHttps) {
            specification.relaxedHTTPSValidation();
            HttpsCertTrust.trustAllHttpsCertificates();
        }
    }

    public void initNewSpecification() {
        try {
            specification = RestAssured.given().config(restConfig.getConfig());
            waitCondition = null;
            onCreate();
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    public void param(String key, Object value) {
        specification.param(key, value);
    }

    public void queryParam(String parameterName, String parameterValue) {
        try {
            parameterValue = URLEncoder.encode(parameterValue, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // do nothing
        }
        if (!parameterValue.equalsIgnoreCase("null")) {
            specification.queryParam(parameterName, parameterValue);
        }
    }

    public void accept(String mediaTypes) {
        specification.accept(mediaTypes);
    }

    public void baseUri(String baseUri) {
        configManager.put(ConfigConstants.BASE_URI, baseUri);
        specification.baseUri(baseUri);
    }

    public void contentType(String contentType) {
        specification.contentType(contentType);
    }

    public void cookie(String cookieName, String cookieValue) {
        specification.cookie(cookieName, cookieValue);
    }

    public void header(String headerName, String headerValue) {
        specification.header(headerName, headerValue);
    }

    public void proxy(String scheme, String host, Integer port) {
        if (port == null) {
            port = 80;
        }
        specification.proxy(host, port, scheme);
    }

    public void sessionId(String sessionIdValue) {
        specification.sessionId(sessionIdValue);
    }

    public void sessionId(String sessionIdName, String sessionIdValue) {
        specification.sessionId(sessionIdName, sessionIdValue);
    }

    public void filter(Filter filter) {
        specification.filter(filter);
    }

    public void filters(List<Filter> filters) {
        specification.filters(filters);
    }

    public void authentication(String username, String password) {
        configManager.put(ConfigConstants.USERNAME, username);
        configManager.put(ConfigConstants.PASSWORD, password);
    }

    public void basicAuthentication(String username, String password) {
        specification.auth().preemptive().basic(username, password);
    }

    public void authenticationType(String authenticationType) {
        configManager.put(ConfigConstants.AUTH_TYPE, authenticationType);
    }

    public RequestSpecification value() {
        return specification;
    }

    public void shouldWaitWithIntervalUntilStatusCodeReceived(TimeUtil waitTime, TimeUtil interval, int sCode) {
        this.waitCondition = new WaitCondition(waitTime, interval, ApiResponseMatcher.aStatusCode(equalTo(sCode)));
    }

    public void shouldWaitWithIntervalUntilStatusCodeReceived(TimeUtil waitTime, TimeUtil interval, int sCode, int failCode) {
        this.waitCondition = new WaitCondition(waitTime, interval, ApiResponseMatcher.aStatusCode(equalTo(sCode)),
                ApiResponseMatcher.aStatusCode(equalTo(failCode)));
    }


    public void shouldWaitWithIntervalUntilHeaderEqualToValue(TimeUtil waitTime, TimeUtil interval, String header,
                                                              String value) {
        this.waitCondition = new WaitCondition(waitTime, interval, ApiResponseMatcher.aHeader(header, equalTo(value)));
    }

    public void shouldWaitWithIntervalUntilHeaderEqualToValue(TimeUtil waitTime, TimeUtil interval, String header, String value,
                                                              String failValue) {
        this.waitCondition = new WaitCondition(waitTime, interval, ApiResponseMatcher.aHeader(header, equalTo(value)),
                ApiResponseMatcher.aHeader(header, equalTo(failValue)));
    }

    public WaitCondition awaitCondition() {
        return waitCondition;
    }

    public void resetSpecification() {
        this.requestBody = null;
    }

    private RequestSpecification specification() {
        return value();
    }

    public void formParam(String parameterName, String parameterValue) {
        specification().formParam(parameterName, parameterValue);
    }

    public void multiPart(String contentBody, String controlName) {
        specification().multiPart(controlName, contentBody);
    }

    public void multiPart(byte[] contentBody, String controlName) {
        specification().multiPart(controlName, "file.txt", contentBody);
    }

    public void multiPart(String contentBody, String controlName, String mimeType) {
        specification().multiPart(controlName, contentBody, mimeType);
    }

    public void multiPart(byte[] contentBody, String controlName, String mimeType) {
        specification().multiPart(controlName, "file.txt", contentBody, mimeType);
    }

    public void body(String body) {
        specification().body(body);
    }


    public void body(byte[] body) {
        specification().body(body);
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public void clearRequestBody() {
        this.requestBody = null;
    }
}
