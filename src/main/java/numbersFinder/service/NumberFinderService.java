package numbersFinder.service;

import lombok.extern.slf4j.Slf4j;
import numbersFinder.dto.Result;
import numbersFinder.entity.NumberSearchingResult;
import numbersFinder.entity.NumbersInvertedIndex;
import numbersFinder.repo.FindNumberResultRepo;
import numbersFinder.repo.NumbersInvertedIndexRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * class NumberFinderService with one public properties <b>searchForNumber</b>.
 * Class for search for number in the repository
 */
@Slf4j
public class NumberFinderService {
    final private String codeNotFound = "01.Result.NotFound";
    final private String codeOk = "00.Result.OK";
    final private String codeError = "02.Result.Error";
    final private String ErrorNotFound = "Number not found";
    final private String ErrorText = "Error, retry request";


    @Autowired
    NumbersInvertedIndexRepo numbersInvertedIndexRepo;
    @Autowired
    FindNumberResultRepo findNumberResultRepo;

    /**
     * method for searching numbers in the repository
     * @param number - received number, which must be found in the repository
     * @return object Result
     */
    public Result searchForNumber(Integer number) {
        try {
            Result result = new Result();
            List<NumbersInvertedIndex> resultFromDB = numbersInvertedIndexRepo.findAllByNumber(number);
            if (resultFromDB.isEmpty()) {
                result.setCode(codeNotFound);
                result.setError(ErrorNotFound);
                log.debug("Number: {} not found", number);
                updateNumberSearchingResult(number, codeNotFound, null, ErrorNotFound );
                return result;
            }
            result.setCode(codeOk);
            List<String> listFiles = new ArrayList<>();
            for (NumbersInvertedIndex options : resultFromDB) {
                listFiles.add(options.getFilename());
            }
            result.setFileNames(listFiles);
            log.debug("Number: {} found in files: {}", number, listFiles);
            updateNumberSearchingResult(number, codeOk, listFiles, null);
            return result;
        } catch (Throwable e) {
            Result resultError = new Result();
            resultError.setCode(codeError);
            resultError.setError(ErrorText);
            log.error("Error: {}", e.getMessage());
            return resultError;
        }
    }

    private void updateNumberSearchingResult(Integer number, String code, List<String> listFiles, String errorText) {
        NumberSearchingResult numberSearchingResult = new NumberSearchingResult();
        numberSearchingResult.setCode(code);
        numberSearchingResult.setFilenames(listFiles);
        numberSearchingResult.setError(errorText);
        numberSearchingResult.setNumber(number);
        findNumberResultRepo.save(numberSearchingResult);
        findNumberResultRepo.flush();
    }
}
