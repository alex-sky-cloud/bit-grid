package grid.bit.component.mapper.column;

import grid.bit.component.dto.GridColumnDto;
import grid.bit.component.mapper.CommonMapperForCycleAvoiding;
import grid.bit.model.GridColumn;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ColumnMapper extends CommonMapperForCycleAvoiding<GridColumnDto, GridColumn> {
}
