package com.aceguardian.config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:properties", "classpath:config.properties", "classpath:allure.properties"})
public interface Configuration extends Config {

}
