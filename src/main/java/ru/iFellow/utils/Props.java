package ru.iFellow.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:generaltest.properties")
public interface Props extends Config {

    Props props = ConfigFactory.create(Props.class);

    @Key("password")
    String password();

    @Key("login")
    String login();

    @Key("base.url")
    String baseUrl();

    @Key("chromedriver.path")
    String chromedriverPath();
}
