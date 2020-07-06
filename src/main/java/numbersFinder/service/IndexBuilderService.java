package numbersFinder.service;

import lombok.extern.slf4j.Slf4j;
import numbersFinder.entity.IndexedFile;
import numbersFinder.entity.NumbersInvertedIndex;
import numbersFinder.repo.IndexedFileRepo;
import numbersFinder.repo.NumbersInvertedIndexRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * class IndexBuilderService with one public properties <b>indexFilesInDir</b>.
 * Class for index new files or reindexing existing files
 */
@Slf4j
public class IndexBuilderService {

    public static final int BATCH_SIZE = 1000;
    public static final char NUMBERS_DELIMITER = ',';

    @Value("${app.files.dir}")
    private String filesDir;
    @Autowired
    IndexedFileRepo indexedFileRepo;
    @Autowired
    NumbersInvertedIndexRepo numbersInvertedIndexRepo;

    public void indexFilesInDir() {
        File[] fileList = new File(filesDir).listFiles();
        if (fileList == null) {
            log.debug("no files to index");
            return;
        }
        deleteUnusedFiles(fileList);
        Stream.of(fileList).parallel().forEach(this::readFile);
    }

    private void deleteUnusedFiles(File[] fileList) {
        indexedFileRepo
                .findAll()
                .stream()
                .map(IndexedFile::getFilename)
                .filter(element -> {
                            boolean n = true;
                            for (File file : fileList) {
                                if (file.getName().equals(element)) n = false;
                            }
                            return n;
                        }
                )
                .forEach(filename -> {
                    log.debug("delete unused file from repository: {}", filename);
                    indexedFileRepo.deleteByFilename(filename);
                    numbersInvertedIndexRepo.deleteByFilename(filename);

                });
        indexedFileRepo.flush();
        numbersInvertedIndexRepo.flush();
    }

    private void readFile(File file) {
        try (FileReader fr = new FileReader(file)) {
            IndexedFile indexedFile = new IndexedFile();
            String filename = file.getName();
            indexedFile.setFilename(filename);
            LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(file.lastModified()), ZoneId.systemDefault());
            indexedFile.setLastUpdate(ldt);
            Optional<IndexedFile> fileSearchingResult = indexedFileRepo.findByFilename(filename);
            if (fileSearchingResult.isPresent()) {
                if (fileSearchingResult.get().getLastUpdate().equals(ldt)) {
                    return;
                } else {
                    log.debug("delete modified file from repository: {}", filename);
                    numbersInvertedIndexRepo.deleteByFilename(filename);
                    numbersInvertedIndexRepo.flush();
                }
            }

            int counter = 0;
            int c;
            StringBuilder buffer = new StringBuilder();
            while ((c = fr.read()) != -1) {
                char character = (char) c;
                if (character != NUMBERS_DELIMITER) {
                    buffer.append(character);
                } else {
                    createNumberInvertedIndex(filename, buffer.toString());
                    buffer = new StringBuilder();
                    counter++;
                    if (counter == BATCH_SIZE) {
                        numbersInvertedIndexRepo.flush();
                    }
                }
            }

            indexedFileRepo.saveAndFlush(indexedFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createNumberInvertedIndex(String fileName, String number) {
        NumbersInvertedIndex numberInvertedIndex = new NumbersInvertedIndex();
        numberInvertedIndex.setFilename(fileName);
        numberInvertedIndex.setNumber(Integer.parseInt(number));
        numbersInvertedIndexRepo.save(numberInvertedIndex);
    }
}
