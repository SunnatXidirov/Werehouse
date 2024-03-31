package uz.dev.foodstorage.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Online Food Storage  Server-Api",
                version = "1.0-SNAPSHOT",
                contact = @Contact(
                        name = "Online Food Storage  By Sunnat Xidirov",
                        url = "https://swagger.io/docs",
                        email = "xidirovsunnat00@gmail.com"
                ),
                termsOfService = "Terms Of Service",
                description = "Description",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = @Server(
                url = "http://localhost:8081/",
                description = "test-server"
        )
)
public class OpenApiDefinition {
}
