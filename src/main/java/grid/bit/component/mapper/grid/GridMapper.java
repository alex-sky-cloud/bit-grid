package grid.bit.component.mapper.grid;

import grid.bit.model.Grid;
import grid.bit.service.dto.GridDto;
import grid.bit.component.mapper.CommonMapperForCycleAvoiding;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GridMapper extends CommonMapperForCycleAvoiding <GridDto, Grid> {
}