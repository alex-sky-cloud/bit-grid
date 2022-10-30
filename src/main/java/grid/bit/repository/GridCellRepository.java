package grid.bit.repository;

import grid.bit.model.GridCell;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GridCellRepository extends CrudRepository<GridCell, Long> {

    List<GridCell> findByGridColumnId(Long id);

    Optional<GridCell> findByGridColumnIdAndGridRowId(Long columnId, Long rowId);
}
