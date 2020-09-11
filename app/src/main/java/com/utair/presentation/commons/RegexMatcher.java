package com.utair.presentation.commons;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

public class RegexMatcher {

    @Inject
    public RegexMatcher() {
    }

    public boolean matches(String s, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(s);
        return m.matches();
    }

}
