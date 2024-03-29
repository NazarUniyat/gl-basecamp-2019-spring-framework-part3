package owu.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import owu.Utils.NumberGenerator;

@Configuration
@EnableWebMvc
@ComponentScan("owu.*")
public class WebConfig extends WebMvcConfigurerAdapter {

    @Bean
    public NumberGenerator numberGenerator() {
        NumberGenerator  numberGenerator = new NumberGenerator();
        return numberGenerator;
    }



}