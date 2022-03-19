package com.mb.checker.shared.common.util;

public class JwtUtils {

    private static String accessToken ;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        JwtUtils.accessToken = accessToken;
    }
}
