package kr.or.kids.domain.ca.external.core;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExternalApiClient {

    private final RestTemplateFactory factory;

    public ExternalApiClient(RestTemplateFactory factory) {
        this.factory = factory;
    }

    public <T> T get(String serviceKey, String path,
                     Map<String, String> headers,
                     Map<String, ?> queryParams,
                     Class<T> responseType) {
        return exchange(serviceKey, HttpMethod.GET, path, headers, queryParams, null, responseType);
    }

    public <B, T> T post(String serviceKey, String path,
                         Map<String, String> headers,
                         Map<String, ?> queryParams,
                         B body,
                         Class<T> responseType) {
        return exchange(serviceKey, HttpMethod.POST, path, headers, queryParams, body, responseType);
    }

    public <B, T> T put(String serviceKey, String path,
                        Map<String, String> headers,
                        Map<String, ?> queryParams,
                        B body,
                        Class<T> responseType) {
        return exchange(serviceKey, HttpMethod.PUT, path, headers, queryParams, body, responseType);
    }

    public <T> T delete(String serviceKey, String path,
                        Map<String, String> headers,
                        Map<String, ?> queryParams,
                        Class<T> responseType) {
        return exchange(serviceKey, HttpMethod.DELETE, path, headers, queryParams, null, responseType);
    }

    private <B, T> T exchange(String serviceKey,
                              HttpMethod method,
                              String path,
                              Map<String, String> headers,
                              Map<String, ?> queryParams,
                              B body,
                              Class<T> responseType) {

        RestTemplate rt = factory.forService(serviceKey);

        String baseUrl = factory.baseUrl(serviceKey);
        URI uri = buildUri(baseUrl, path, queryParams);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(MediaType.parseMediaTypes(MediaType.APPLICATION_JSON_VALUE));
        if (headers != null) headers.forEach(httpHeaders::add);

        HttpEntity<?> entity;
        if (body != null) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            entity = new HttpEntity<>(body, httpHeaders);
        } else {
            entity = new HttpEntity<>(httpHeaders);
        }

        ResponseEntity<T> resp = rt.exchange(uri, method, entity, responseType);
        return resp.getBody();
    }

    private URI buildUri(String baseUrl, String path, Map<String, ?> queryParams) {
        UriComponentsBuilder b = UriComponentsBuilder
                .fromHttpUrl(baseUrl)
                .path(path);

        if (queryParams != null) {
            queryParams.forEach(b::queryParam);
        }

        return b.build(true).toUri();
    }
}
