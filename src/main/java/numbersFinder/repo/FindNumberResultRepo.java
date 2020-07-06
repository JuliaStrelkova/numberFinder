package numbersFinder.repo;

import numbersFinder.entity.NumberSearchingResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FindNumberResultRepo extends JpaRepository<NumberSearchingResult, Long> {
}
