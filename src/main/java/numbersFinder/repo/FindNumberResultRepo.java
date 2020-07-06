package numbersFinder.repo;

import numbersFinder.entity.NumberSearchingResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FindNumberResultRepo extends JpaRepository<NumberSearchingResult, Long> {
    Optional<NumberSearchingResult> findByNumber(@Param("number") Integer number);
}
