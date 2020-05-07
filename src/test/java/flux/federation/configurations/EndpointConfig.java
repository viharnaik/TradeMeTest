package flux.federation.configurations;

import com.consol.citrus.dsl.endpoint.CitrusEndpoints;
import com.consol.citrus.http.client.HttpClient;
import org.springframework.context.annotation.Bean;

public class EndpointConfig {

    @Bean
    public HttpClient sandboxAPI() {
        return CitrusEndpoints.http()
                .client()
                .requestUrl("https://api.tmsandbox.co.nz/v1/")
                .build();
    }

}
