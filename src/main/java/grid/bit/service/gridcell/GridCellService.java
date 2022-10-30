package grid.bit.service.gridcell;

import grid.bit.component.dto.GridCellDto;
import grid.bit.component.dto.PrefixDto;
import grid.bit.component.mapper.gridcell.GridCellMapper;
import grid.bit.exceptionhandlers.exceptions.IdConstraintExceptions;
import grid.bit.exceptionhandlers.exceptions.ValueForCellException;
import grid.bit.model.Grid;
import grid.bit.model.GridCell;
import grid.bit.model.GridColumn;
import grid.bit.repository.GridCellRepository;
import grid.bit.repository.GridColumnRepository;
import grid.bit.repository.GridRepository;
import grid.bit.repository.GridRowRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static grid.bit.service.utils.StringUtils.findLongestCommonPrefix;

@Service
public class GridCellService {

    private final GridCellRepository gridCellRepository;

    private final GridColumnRepository gridColumnRepository;

    private final GridRowRepository gridRowRepository;

    private final GridRepository gridRepository;

    private final GridCellMapper gridCellMapper;

    public GridCellService(GridCellRepository gridCellRepository,
                           GridColumnRepository gridColumnRepository,
                           GridRowRepository gridRowRepository,
                           GridRepository gridRepository,
                           GridCellMapper gridCellMapper) {
        this.gridCellRepository = gridCellRepository;
        this.gridColumnRepository = gridColumnRepository;
        this.gridRowRepository = gridRowRepository;
        this.gridRepository = gridRepository;
        this.gridCellMapper = gridCellMapper;
    }


    /**
     * Hibernate uses a mechanism to track the modified object, called dirty-checking.
     * readOnly = true is disable this mechanism/
     */
    @Transactional(readOnly = true)
    public PrefixDto findCommonPrefix(Long id){

        List<GridCell> valueForGridList = gridCellRepository.findByGridColumnId(id);

        List<String> stringList = valueForGridList
                .stream()
                .map(gridCell -> gridCell.getValue())
                .collect(Collectors.toList());

        String longestCommonPrefix = findLongestCommonPrefix(stringList);

        PrefixDto prefixDto = new PrefixDto(longestCommonPrefix);

        return prefixDto;
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void setValueInCell(GridCellDto gridCellDto){

        validateIds(gridCellDto);

        validatedValueThatNeedSet(gridCellDto);

        GridCell gridCell = new GridCell();
        gridCell.setGridColumnId(gridCellDto.getColumnId());
        gridCell.setGridRowId(gridCellDto.getRowId());
        gridCell.setValue(gridCellDto.getValue());

        gridCellRepository.save(gridCell);

    }

    private void validatedValueThatNeedSet(GridCellDto gridCellDto){

        int cellSize = getCellSize(gridCellDto);

        String value = gridCellDto.getValue();

        int lengthValue = value.length();

        StringBuilder stringBuilder = new StringBuilder();
        String messageErr = stringBuilder.append("The length of the string, for the Value field (value = ")
                .append(value)
                .append(") must be equal to the specified size of the grid cell. (cellSize = ")
                .append(cellSize)
                .append(").").toString();


        if(cellSize != lengthValue){
            throw new ValueForCellException(messageErr);
        }
    }


    private int getCellSize(GridCellDto gridCellDto){

        Long columnId = gridCellDto.getColumnId();
        GridColumn gridColumn = gridColumnRepository
                .findById(columnId)
                .get();

        Grid grid = gridColumn.getGrid();
       return grid.getCellSize();
    }

    private void validateIds(GridCellDto gridCellDto){

        Long columnId = gridCellDto.getColumnId();
        validateExistenceRequestedColumn(columnId);

        Long rowId = gridCellDto.getRowId();
        validateExistenceRequestedRow(rowId);
    }


    private void validateExistenceRequestedColumn(Long columnId){

        boolean isExistColumn = gridColumnRepository.existsById(columnId);

        if(!isExistColumn){
            String messageErr = "A requested column this id : " + columnId + " is not found!";
            throw new IdConstraintExceptions(messageErr);
        }
    }

    private void validateExistenceRequestedRow(Long rowId){

        boolean isExistColumn = gridRowRepository.existsById(rowId);

        if(!isExistColumn){
            String messageErr = "A requested row this id : " + rowId + " is not found!";
            throw new IdConstraintExceptions(messageErr);
        }
    }
}
