package numbersFinder.event;

import numbersFinder.service.IndexBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AppEvents {
    @Autowired
    IndexBuilderService indexingFilesService;

    @EventListener(ApplicationReadyEvent.class)
    public void startApp() {
        try {
            indexingFilesService.indexFilesInDir();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
