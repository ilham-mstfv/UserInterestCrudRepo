package com.example.userinterestcrudrepo.models;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Lang {
    AFR("Afrikaans"),
    ALB("Albanian"),
    ARB("Arabic"),
    ARM("Armenian"),
    AZE("Azerbaijani"),
    BEN("Bengali"),
    BOS("Bosnian"),
    BUL("Bulgarian"),
    CAT("Catalan"),
    CHI("Chinese"),
    CRO("Croatian"),
    CZE("Czech"),
    DAN("Danish"),
    DUT("Dutch"),
    ENG("English"),
    EST("Estonian"),
    FIN("Finnish"),
    FRE("French"),
    GEO("Georgian"),
    GER("German"),
    GRE("Greek"),
    HIN("Hindi"),
    HUN("Hungarian"),
    ICE("Icelandic"),
    IND("Indonesian"),
    ITA("Italian"),
    JAP("Japanese"),
    KOR("Korean"),
    LAT("Latvian"),
    LIT("Lithuanian"),
    MAC("Macedonian"),
    MAL("Malay"),
    NOR("Norwegian"),
    PER("Persian"),
    POL("Polish"),
    POR("Portuguese"),
    ROM("Romanian"),
    RUS("Russian"),
    SER("Serbian"),
    SLO("Slovak");

    private final String lang;

    Lang(String lang) {
        this.lang = lang;
    }
}
