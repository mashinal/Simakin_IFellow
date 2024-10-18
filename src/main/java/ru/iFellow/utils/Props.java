package ru.iFellow.utils;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources("classpath:generaltest.properties")
public interface Props extends Config {

    Props props = ConfigFactory.create(Props.class);

    @Key("rickandmorty.base.url")
    String rickAndMortyBaseUrl();

    @Key("createuser.base.url")
    String createuserBaseUrl();
}
