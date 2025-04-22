package com.emt.springbackendapi.security;

public class JWTConstants {
    public static String SECRET_KEY = "3795ad710b4806b664c12af0c3d66b5ea2625abd2cfe28e949463356c8a009320316a8f1a59e212b1239bc996cd6d920b8c622297b3f27e3d3c69dd160132505dca88dde04e222bc76debdfc9b9016f0c0d75c24ace92a6b25906b7daf44b1f1";
    public static final Long EXPIRATION_TIME = 864000000L;
    public static final String HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
}
