package com.heaven.website.shopping.resource.configuration;

import org.apache.http.Header;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaoxi.li
 * @date 2019/03/22 16:10
 * @description
 */
@Configuration
@EnableResourceServer
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {

        // BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        // basicAuthenticationEntryPoint.setRealmName("asd");


        // resources.tokenServices(remoteTokenServices());

    }

    private RemoteTokenServices remoteTokenServices() {

        // httpComponentsClientHttpRequestFactory.setHttpClient(new);

        // RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());


        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setClientId("client_id");
        remoteTokenServices.setClientSecret("666");
        remoteTokenServices.setCheckTokenEndpointUrl("http://localhost:9910/authorization/oauth/check_token");
        // remoteTokenServices.setRestTemplate(restTemplate);

        return remoteTokenServices;
    }

    private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
                = new HttpComponentsClientHttpRequestFactory();

        clientHttpRequestFactory.setHttpClient(httpClient());

        return clientHttpRequestFactory;
    }

    private HttpClient httpClient() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("client_id", "666"));

        // Header header = new BasicHeader("Authorization", "Basic Y2xpZW50X2lkOjY2Ng==");
        //
        // List<Header> headerList = new ArrayList<>();
        // headerList.add(header);

        HttpClient client = HttpClientBuilder
                .create()
                // .setDefaultHeaders(headerList)
                .setDefaultCredentialsProvider(credentialsProvider)
                .build();
        return client;
    }
}
