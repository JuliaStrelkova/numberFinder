package numbersFinder.controller;

import numbersFinder.dto.Result;
import numbersFinder.service.IndexBuilderService;
import numbersFinder.service.NumberFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("number")
public class Controller {
    @Autowired
    NumberFinderService numberFinderService;
    @Autowired
    IndexBuilderService indexingFilesService;

    /**
     * method search number from files
     * @param number - number, which must be found in the files
     * @return object Result
     */
    @GetMapping("{n}")
    public Result getOne(@PathVariable("n") Integer number) {
        return numberFinderService.searchForNumber(number);
    }

    /**
     * method for index new files or reindexing existing files
     */
    @PostMapping("/reindex")
    public void reindex() {
        try {
            indexingFilesService.indexFilesInDir();
        } catch (Throwable e) {
           throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error, retry request", e);
        }
    }
}

