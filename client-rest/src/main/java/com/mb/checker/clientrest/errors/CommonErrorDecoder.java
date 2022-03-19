package com.mb.checker.clientrest.errors;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CommonErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonErrorDecoder.class);

    @Override
    public Exception decode(String methodKey, Response response) {
        String responseBody = "No error description";
        try {
            responseBody = new String(IOUtils.toByteArray(response.body().asInputStream()), StandardCharsets.UTF_8);
            LOGGER.error("The call to '{}' returned an HTTP error response '{}' with body '{}', but another error occurred attempting to read the HTTP",
                    methodKey,response,responseBody);
            final byte[] bytes = IOUtils.toByteArray(response.body().asInputStream());

            return new HttpClientErrorException(HttpStatus.valueOf(response.status()),responseBody,bytes,StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error("The call to '{}' returned an HTTP error response '{}' with body '{}', but another error occurred attempting to read the HTTP error: {} .",
                    methodKey,response,responseBody,e.getMessage(),e);
        }

        return defaultErrorDecoder.decode(methodKey, response);
    }
}
