package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * interceptor to log incoming requests
 */
@SuppressWarnings("PMD")
public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        logRequest(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        logResponse(response);
        return response;
    }

    private void logRequest(HttpRequest request, byte[] body) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("===========================request begin================================================");
            String payload = new String(body, "UTF-8");
            log.info("URI         : {}", request.getURI());
            log.info("Method      : {}", request.getMethod());
            log.info("Headers     : {}", request.getHeaders());
            log.info("Request body: {} - ", payload);
            log.info("==========================request end================================================");
        }
    }

    private void logResponse(ClientHttpResponse response) throws IOException {
        if (log.isInfoEnabled()) {
            log.info("============================response begin==========================================");
            log.info("Status code  : {}", response.getStatusCode());
            log.info("Status text  : {}", response.getStatusText());
            log.info("Headers      : {}", response.getHeaders());
            log.info("Response body: {}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
            log.info("=======================response end=================================================");
        }
    }
}