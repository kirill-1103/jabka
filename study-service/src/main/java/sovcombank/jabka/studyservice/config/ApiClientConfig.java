package sovcombank.jabka.studyservice.config;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;
import ru.sovcombank.openapi.user.ApiClient;
import ru.sovcombank.openapi.user.ApiException;
import ru.sovcombank.openapi.user.api.AuthorizationApi;
import ru.sovcombank.openapi.user.api.UserApi;
import ru.sovcombank.openapi.user.model.JwtResponseOpenApi;
import ru.sovcombank.openapi.user.model.LoginRequestOpenApi;
import sovcombank.jabka.libs.security.state.TokenKeeper;

import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    @Value("${user-service.base-url}")
    private String baseUserServiceUrl;

    @Value("${spring.application.api-name}")
    private String accessApiName;

    private final TokenKeeper tokenKeeper;


    @Bean
    public ApiClient userClient() {
        ApiClient apiClient = ru.sovcombank.openapi.user.Configuration.getDefaultApiClient();
        apiClient.updateBaseUri(baseUserServiceUrl);

        apiClient.setRequestInterceptor((http) -> {
            AuthorizationApi authorizationApi = new AuthorizationApi();
            try {
                LoginRequestOpenApi loginRequestOpenApi = new LoginRequestOpenApi();
                loginRequestOpenApi.setLogin("API_ADMIN");
                loginRequestOpenApi.setPassword("jabka");
                JwtResponseOpenApi jwtResponseOpenApi = authorizationApi.authenticateUser(loginRequestOpenApi);
                http.setHeader("Authorization","Bearer "+ jwtResponseOpenApi.getAccessToken());
            } catch (ApiException e) {
                throw new RuntimeException(e);
            }
//            http.setHeader(
//                    "Authorization",
//                    "Bearer "
//                            + tokenKeeper.getToken(accessApiName, List.of("ROLE_ADMIN"))
//            );
        });

        return apiClient;
    }

    @Bean
    public UserApi userApi(ApiClient apiClient) {
        UserApi userApi = new UserApi(apiClient);
        return userApi;
    }

    @Bean
    public AuthorizationApi authorizationApi(ApiClient apiClient){
        return new AuthorizationApi(apiClient);
    }


//    @Bean
//    public ClientHttpRequestInterceptor interceptor(){
//        return (request, body, execution) -> {
//            request.getHeaders().add("Authorization", "Bearer "+tokenKeeper.getToken(
//                appName, List.of("ROLE_ADMIN")
//            ));
//            return execution.execute(request,body);
//        };
//    }

}
