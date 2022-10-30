package grid.bit.repository;

import grid.bit.model.GridRow;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GridRowRepository extends JpaRepository<GridRow, Long> {

    @EntityGraph(attributePaths = {"grid"})
    List<GridRow> findByGridId(Long id);
}