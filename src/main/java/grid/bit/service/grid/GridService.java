package grid.bit.service.grid;

import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.component.mapper.grid.GridMapper;
import grid.bit.model.Grid;
import grid.bit.repository.GridRepository;
import grid.bit.service.dto.GridDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class GridService {

    private final GridRepository gridRepository;

    private final GridMapper gridMapper;

    private final CycleAvoidingMappingContext cycleAvoidingMappingContext;

    public GridService(GridRepository gridRepository, GridMapper gridMapper,
                       CycleAvoidingMappingContext cycleAvoidingMappingContext) {
        this.gridRepository = gridRepository;
        this.gridMapper = gridMapper;
        this.cycleAvoidingMappingContext = cycleAvoidingMappingContext;
    }

    @Transactional
    public List<GridDto> getAllGrid() {

        List<Grid> gridList = gridRepository.findAll();

        log.debug(" GridService.getAllGrid(): gridList is empty, {} " + gridList.isEmpty());

        List<GridDto> gridDtoList = transformToListDto(gridList);


        return gridDtoList;
    }


    @Transactional
    public GridDto createGrid(GridDto dto) {

        Grid gridEntity = transformToEntity(dto);

        Grid entitySaved = gridRepository.saveAndFlush(gridEntity);

        log.debug(" GridService.createGrid() : {} " + entitySaved.getId());

        return transformToDto(entitySaved);
    }


    @Transactional
    public void updateName(String name, Long idGrid) {

        gridRepository.updateName(name, idGrid);

        log.debug(" GridService.updateName() : {} " + name);
    }

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Transactional
    public void deleteGridById(Long id){

        gridRepository.deleteById(id);

        log.debug(" GridService.deleteGridById() : {} " + id);

    }

    private List<GridDto> transformToListDto(List<Grid> list) {
        return gridMapper.toListDto(list, this.cycleAvoidingMappingContext);
    }


    private GridDto transformToDto(Grid entity) {
        return gridMapper.toDto(entity, this.cycleAvoidingMappingContext);
    }

    private Grid transformToEntity(GridDto dto) {
        return gridMapper.toEntity(dto, this.cycleAvoidingMappingContext);
    }
}