package numbersFinder.repo;

import numbersFinder.entity.IndexedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface IndexedFileRepo extends JpaRepository<IndexedFile, String> {
    Optional<IndexedFile> findByFilename(@Param("filename") String filename);

    @Transactional
    Long deleteByFilename(@Param("filename") String filename);
}
