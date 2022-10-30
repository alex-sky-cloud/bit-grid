package grid.bit.controller;

import grid.bit.exceptionhandlers.exceptions.IdConstraintExceptions;
import grid.bit.exceptionhandlers.exceptions.StringConstraintExceptions;
import grid.bit.service.dto.GridDto;
import grid.bit.service.grid.GridService;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static grid.bit.controller.validateutils.IdValidateUtils.validateId;

@RequestMapping("grid")
@RestController
public class GridController {

    private final GridService gridService;

    public GridController(GridService gridService) {
        this.gridService = gridService;
    }

    // ToDo: implement getAll, create, update and delete

    @GetMapping("v1")
    public List<GridDto> getGridList (){

       return gridService.getAllGrid();
    }

    @PostMapping("v1")
    @ResponseStatus(HttpStatus.CREATED)
    public GridDto saveGrid (@Validated @RequestBody  GridDto dto){

        return gridService.createGrid(dto);
    }

    @PutMapping("v1/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateGrid (@RequestBody GridDto dto, @PathVariable Long id){

        String nameForUpdate = dto.getName();

        int minLength = 1;
        int maxLength = 200;

        if(nameForUpdate.length() < minLength || nameForUpdate.length() > maxLength){
            throw new StringConstraintExceptions("A string must be less than or equal to 100000 and not equal 0");
        }


        gridService.updateName(nameForUpdate, id);
    }

    @DeleteMapping("v1/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteGrid (@PathVariable Long id){

        validateId(id);

        gridService.deleteGridById(id);
    }
}