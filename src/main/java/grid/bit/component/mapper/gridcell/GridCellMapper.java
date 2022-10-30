package grid.bit.component.mapper.gridcell;

import grid.bit.component.dto.PrefixDto;
import grid.bit.component.mapper.CommonMapperForCycleAvoiding;
import grid.bit.model.GridCell;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GridCellMapper extends CommonMapperForCycleAvoiding<PrefixDto, GridCell> {
}
