package com.hsbc.qe.api.httpservicemanager;

import com.google.common.base.Strings;
import com.hsbc.qe.api.constants.ConfigConstants;
import com.hsbc.qe.common.utils.YamlReaderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.*;

public class ConfigManager {

    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);

    private Map<String, String> envProperties;

    public void initEnvProperties(String envName){
        envProperties = new HashMap<>();
        loadConfigFile(envName);
    }

    private void loadConfigFile(String envFileName) {
        String environmentConfigPath = "config/envconfig.yml";
        Properties prop = new Properties();
        InputStream iStream;
        try {
            iStream = ClassLoader.getSystemResourceAsStream(environmentConfigPath);
            YamlReaderUtils yamlReaderUtils = new YamlReaderUtils(iStream);
            yamlReaderUtils.getYamlObj(ConfigConstants.COMMON_PROPERTIES).entrySet().stream().forEach(en -> envProperties.put(en.getKey(),(String)en.getValue()));
            yamlReaderUtils.getYamlObj(envFileName).entrySet().stream().forEach(en -> envProperties.put(en.getKey(),(String)en.getValue()));
            envProperties.entrySet().stream().forEach(en -> logger.info(en.getKey() + ":" + en.getValue()));
        } catch (Exception e) {
            logger.error("Error in loading the environment yml file {}",environmentConfigPath);
        }
    }

    public String getEnvProperty(String key) {
        return envProperties.get(key);
    }

    public static String getSystemPropertyOrSetDefault(String key, final String defaultValue) {
        String property = System.getProperty(key);
        if(Strings.isNullOrEmpty(property)){
            logger.info("Env value is not given and setting it to the default: " + defaultValue);
            System.setProperty(ConfigConstants.ENV_TYPE,defaultValue);
            return defaultValue;
        }
        logger.info("Env value setting to the : " + property);
        return property;
    }

    public String getEnvPropertyOrSetDefault(String key, String defaultValue) {
        Optional<String> value = Optional.ofNullable(envProperties.get(key));
        return value.orElse(defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return Boolean.parseBoolean(getEnvPropertyOrSetDefault(key, Boolean.toString(defaultValue)));
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public void put(String key, String value) {
        envProperties.put(key, value);
    }
}
