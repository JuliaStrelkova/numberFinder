package numbersFinder.repo;


import numbersFinder.entity.NumbersInvertedIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface NumbersInvertedIndexRepo extends JpaRepository<NumbersInvertedIndex, Long> {
    List<NumbersInvertedIndex> findAllByNumber(@Param("number") Integer number);

    @Transactional
    Long deleteByFilename(@Param("filename") String filename);
}
