package numbersFinder;

import numbersFinder.service.IndexBuilderService;
import numbersFinder.service.NumberFinderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public NumberFinderService numberFinderService() {
        return new NumberFinderService();
    }
    @Bean
    public IndexBuilderService indexBuilderService() {
        return new IndexBuilderService();
    }
}