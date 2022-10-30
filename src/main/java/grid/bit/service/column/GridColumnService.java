package grid.bit.service.column;

import grid.bit.component.dto.GridColumnDto;
import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.component.mapper.column.ColumnMapper;
import grid.bit.exceptionhandlers.exceptions.NotFoundColumnException;
import grid.bit.model.Grid;
import grid.bit.model.GridCell;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridColumnRepository;
import grid.bit.repository.GridRowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GridColumnService {

    private final GridColumnRepository gridColumnRepository;

    private final GridCellRepository gridCellRepository;

    private final GridRowRepository gridRowRepository;

    private final ColumnMapper columnMapper;

    private final CycleAvoidingMappingContext cycleAvoidingMappingContext;

    public GridColumnService(GridColumnRepository gridColumnRepository,
                             GridCellRepository gridCellRepository,
                             GridRowRepository gridRowRepository, ColumnMapper columnMapper,
                             CycleAvoidingMappingContext cycleAvoidingMappingContext) {
        this.gridColumnRepository = gridColumnRepository;
        this.gridCellRepository = gridCellRepository;
        this.gridRowRepository = gridRowRepository;
        this.columnMapper = columnMapper;
        this.cycleAvoidingMappingContext = cycleAvoidingMappingContext;
    }

    @Transactional
    public void deleteColumn(Long id){

        gridColumnRepository.deleteById(id);
    }

    /*REPEATABLE_READ is in Postgresql  to except anomalies: Repeatable Read and Phantom Read*/
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public GridColumnDto addColumn(Long id) {

        GridColumn gridColumnNewAfterGivenColumn = saveColumnNew(id);

        GridColumn gridColumnNew = gridColumnRepository.save(gridColumnNewAfterGivenColumn);

        saveCellList(gridColumnNew);

        return toDto(gridColumnNew);
    }

    private GridColumn saveColumnNew(Long id){

        GridColumn gridColumnPrev = findGridColumnLast(id);

        Grid grid = gridColumnPrev.getGrid();
        int numberInPrevColumn = gridColumnPrev.getNumber();
        return prepareNewColumn(grid, numberInPrevColumn);
    }

    private void saveCellList(GridColumn gridColumnNew){

        List<GridRow> rows = gridColumnNew.getGrid().getRows();

        final Long gridColumnNewId = gridColumnNew.getId();
        List<GridCell> gridCellList = prepareGridCell(rows, gridColumnNewId);
        gridCellRepository.saveAll(gridCellList);
    }

    private List<GridCell> prepareGridCell(List<GridRow> rows, Long gridColumnNewId) {

        String emptyStr = "";

        return rows
                .stream()
                .map(row -> {
                    GridCell gridCell = new GridCell();
                    gridCell.setGridColumnId(gridColumnNewId);
                    gridCell.setGridRowId(row.getId());
                    gridCell.setValue(emptyStr);
                    return gridCell;
                })
                .collect(Collectors.toList());
    }

    private GridColumnDto toDto(GridColumn gridColumn) {
        return columnMapper.toDto(gridColumn, cycleAvoidingMappingContext);
    }

    private GridColumn prepareNewColumn(Grid grid, int numberInPrevColumn) {

        GridColumn gridColumn = new GridColumn();
        gridColumn.setNumber(++numberInPrevColumn);
        gridColumn.setGrid(grid);

        return gridColumn;
    }

    private GridColumn findGridColumnLast(Long id) {

        Optional<GridColumn> columnById = gridColumnRepository.findById(id);

        return columnById
                .orElseThrow(() -> new NotFoundColumnException("There is not such a column with pointed ID"));
    }
}