package com.hsbc.qe.api.httpservicemanager;

import com.hsbc.qe.api.constants.ConfigConstants;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.config.JsonPathConfig;

import java.io.PrintStream;

import static io.restassured.config.DecoderConfig.ContentDecoder.DEFLATE;
import static io.restassured.config.DecoderConfig.decoderConfig;
import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.config.LogConfig.logConfig;
import static io.restassured.config.RedirectConfig.redirectConfig;
import static io.restassured.config.RestAssuredConfig.newConfig;

public class RestServiceConfigManager {

    private ConfigManager world;

    private RestAssuredConfig restAssuredConfig;
    private PrintStream logStream;

    public RestServiceConfigManager(ConfigManager configManager) {
        this.world = configManager;
    }

    public static com.github.dzieciou.testing.curl.Options getCurlOptions() {
        return com.github.dzieciou.testing.curl.Options.builder()
                .updateCurl(curl -> curl.setInsecure(false)
                        .removeHeader("Host")
                        .removeHeader("User-Agent")
                        .removeHeader("Connection"))
                .printMultiliner()
                .useLongForm()
                .build();
    }

    public RestAssuredConfig getConfig() {
        if (restAssuredConfig == null) {
            restAssuredConfig = buildRestAssuredConfig();
        }
        return restAssuredConfig;
    }

    private RestAssuredConfig buildRestAssuredConfig() {
        RestAssuredConfig config = newConfig()
                .jsonConfig(jsonConfig().numberReturnType(JsonPathConfig.NumberReturnType.BIG_DECIMAL))
                .redirect(redirectConfig().followRedirects(world.getBoolean(ConfigConstants.FOLLOW_REDIRECTS, true)))
                .sslConfig(new SSLConfig().allowAllHostnames());

        if (logStream != null)
            config = config.logConfig(logConfig().defaultStream(logStream));
        if (!world.getBoolean(ConfigConstants.GZIP_SUPPORT, true)) {
            config = config.decoderConfig(decoderConfig().contentDecoders(DEFLATE));
        }
//        config = CurlLoggingRestAssuredConfigFactory.updateConfig(config, getCurlOptions());
        return config;
    }

    public void setDefaultStream(PrintStream logStream) {
        this.logStream = logStream;
    }

    public void reset() {
        restAssuredConfig = null;
    }
}
