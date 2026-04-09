package kr.or.kids.domain.ca.external.core;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        int status = response.getRawStatusCode();
        String body = StreamUtils.copyToString(response.getBody(), StandardCharsets.UTF_8);

        throw new ExternalApiException(
                "External API error: HTTP " + status,
                status,
                body
        );
    }
}