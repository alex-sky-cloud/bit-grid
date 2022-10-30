package grid.bit.component.mapper.row;

import grid.bit.component.dto.GridRowDto;
import grid.bit.component.mapper.CommonMapperForCycleAvoiding;
import grid.bit.model.GridRow;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RowMapper extends CommonMapperForCycleAvoiding<GridRowDto, GridRow> {
}
