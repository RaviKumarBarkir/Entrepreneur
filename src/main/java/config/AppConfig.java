package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.UpperCamelCaseStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import util.RequestResponseLoggingInterceptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("PMD")
@Configuration
public class AppConfig {

    @Value("${spring.rest.connection-timeout}")
    private int connectionTimeOut;

    @Value("${spring.rest.read-timeout}")
    private int readTimeOut;

    @Bean
    RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
        clientHttpRequestFactory.setConnectTimeout(connectionTimeOut);
        clientHttpRequestFactory.setConnectionRequestTimeout(connectionTimeOut);
        clientHttpRequestFactory.setReadTimeout(readTimeOut);
        ClientHttpRequestFactory factory = new BufferingClientHttpRequestFactory(clientHttpRequestFactory);
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.setInterceptors(Collections.singletonList(new RequestResponseLoggingInterceptor()));
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
        jsonMessageConverter.setObjectMapper(jacksonObjectMapper());
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(jsonMessageConverter);
        messageConverters.add(new ByteArrayHttpMessageConverter());
        messageConverters.add(new ResourceHttpMessageConverter(false));
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    @Bean
    public ObjectMapper jacksonObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public PropertyNamingStrategy propertyNamingStrategy() {
        return new UpperCamelCaseStrategy();
    }
}