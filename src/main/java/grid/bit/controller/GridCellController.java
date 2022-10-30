package grid.bit.controller;

import grid.bit.component.dto.GridCellDto;
import grid.bit.component.dto.PrefixDto;
import grid.bit.service.gridcell.GridCellService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static grid.bit.controller.validateutils.IdValidateUtils.validateId;

@RequestMapping("grid/cell")
@RestController
public class GridCellController {
    // ToDo: implement setValue

    private final GridCellService gridCellService;

    public GridCellController(GridCellService gridCellService) {
        this.gridCellService = gridCellService;
    }

    @GetMapping("v1/{id}/common-prefix")
    public PrefixDto getLongestCommonPrefix(@PathVariable Long id){

        validateId(id);

        return gridCellService.findCommonPrefix(id);
    }

    @PostMapping("v1/{columnId}/{rowId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setValueInCell(@RequestBody GridCellDto gridCellDto,
                               @PathVariable Long columnId,
                               @PathVariable Long rowId){

        validateId(columnId);
        validateId(rowId);

        gridCellDto.setColumnId(columnId);
        gridCellDto.setRowId(rowId);

        gridCellService.setValueInCell(gridCellDto);
    }
}