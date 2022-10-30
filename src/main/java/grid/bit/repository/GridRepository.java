package grid.bit.repository;

import grid.bit.model.Grid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GridRepository extends JpaRepository<Grid, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true )
    @Query(value = " UPDATE grid set name = ?1 where id = ?2", nativeQuery = true)
    void updateName(String name, Long id);


    void deleteById(Long id);

}