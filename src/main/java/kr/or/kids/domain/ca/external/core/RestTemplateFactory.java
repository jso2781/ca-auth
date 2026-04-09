package kr.or.kids.domain.ca.external.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import kr.or.kids.domain.ca.external.config.ExternalServicesProperties;

@Component
public class RestTemplateFactory {

    private final ExternalServicesProperties props;
    private final Map<String, RestTemplate> cache = new ConcurrentHashMap<>();

    public RestTemplateFactory(ExternalServicesProperties props) {
        this.props = props;
    }

    public RestTemplate forService(String serviceKey) {
        return cache.computeIfAbsent(serviceKey, this::create);
    }

    private RestTemplate create(String serviceKey) {
        var service = props.getServices().get(serviceKey);
        if (service == null) {
            throw new IllegalArgumentException("Unknown external service key: " + serviceKey);
        }

        var rf = new SimpleClientHttpRequestFactory();
        rf.setConnectTimeout(service.getConnectTimeoutMs());
        rf.setReadTimeout(service.getReadTimeoutMs());

        RestTemplate rt = new RestTemplate(rf);

        // 공통 인터셉터(예: 추적ID/로깅/공통헤더)
        ClientHttpRequestInterceptor traceInterceptor = (request, body, execution) -> {
            // 예: 요청에 추적 헤더 추가
            request.getHeaders().add("X-Client", "my-backend");
            return execution.execute(request, body);
        };

        rt.setInterceptors(List.of(traceInterceptor));

        // 공통 에러 핸들러(4xx/5xx 예외 매핑)
        rt.setErrorHandler(new RestTemplateErrorHandler());

        return rt;
    }

    public String baseUrl(String serviceKey) {
        var service = props.getServices().get(serviceKey);
        if (service == null) throw new IllegalArgumentException("Unknown external service key: " + serviceKey);
        return service.getBaseUrl();
    }
}
