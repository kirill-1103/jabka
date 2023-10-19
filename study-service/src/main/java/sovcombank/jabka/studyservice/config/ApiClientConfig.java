package sovcombank.jabka.studyservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.sovcombank.openapi.user.ApiClient;
import ru.sovcombank.openapi.user.api.UserApi;
import sovcombank.jabka.libs.security.state.TokenKeeper;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class ApiClientConfig {

    @Value("${user-service.base-url}")
    private String baseUserServiceUrl;

    @Value("${spring.application.name}")
    private String appName;

    private final TokenKeeper tokenKeeper;


    @Bean
    public RestTemplate restTemplate(){
        RestTemplate r = new RestTemplate();
        r.setInterceptors(List.of((request, body, execution) -> {

            request.getHeaders().set("Authorization", "Bearer "+tokenKeeper.getToken(
                    appName, List.of("ROLE_ADMIN")
            ));
            return execution.execute(request,body);
        }));
        return r;
    }

    @Bean
    public ApiClient userClient(RestTemplate restTemplate){
        return new ApiClient(restTemplate);
    }

    @Bean
    public UserApi userApi(ApiClient apiClient){
        return new UserApi(apiClient);
    }

}
