package grid.bit.repository;

import grid.bit.model.GridColumn;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GridColumnRepository extends JpaRepository<GridColumn, Long> {

    @EntityGraph(attributePaths = {"grid"})
    Optional<GridColumn> findById(Long id);

    void deleteById(Long id);
}