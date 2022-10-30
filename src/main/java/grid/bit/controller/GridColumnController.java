package grid.bit.controller;

import grid.bit.component.dto.GridColumnDto;
import grid.bit.exceptionhandlers.exceptions.IdConstraintExceptions;
import grid.bit.service.column.GridColumnService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static grid.bit.controller.validateutils.IdValidateUtils.validateId;

@RequestMapping("grid/column")
@RestController
public class GridColumnController {
    private final GridColumnService gridColumnService;

    public GridColumnController(GridColumnService gridColumnService) {
        this.gridColumnService = gridColumnService;
    }

    // ToDo: implement insert, delete and getCommonPrefix

    @PostMapping("v1")
    @ResponseStatus(HttpStatus.CREATED)
    public GridColumnDto addColumnAfterPointedColumn(@RequestParam (name = "afterColumnId") Long id){

        validateId(id);

        GridColumnDto gridColumnDto = gridColumnService.addColumn(id);

        return gridColumnDto;
    }

    @DeleteMapping("v1/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteColumn(@PathVariable Long id){

        validateId(id);

        gridColumnService.deleteColumn(id);
    }
}