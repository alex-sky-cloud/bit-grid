package grid.bit.component.mapper.column;

import grid.bit.component.dto.GridColumnDto;
import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.model.GridColumn;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-21T19:01:58+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.3.3.jar, environment: Java 11.0.9.1 (AdoptOpenJDK)"
)
@Component
public class ColumnMapperImpl implements ColumnMapper {

    @Override
    public GridColumnDto toDto(GridColumn e, CycleAvoidingMappingContext context) {
        GridColumnDto target = context.getMappedInstance( e, GridColumnDto.class );
        if ( target != null ) {
            return target;
        }

        if ( e == null ) {
            return null;
        }

        GridColumnDto gridColumnDto = new GridColumnDto();

        context.storeMappedInstance( e, gridColumnDto );

        gridColumnDto.setId( e.getId() );
        gridColumnDto.setNumber( e.getNumber() );
        gridColumnDto.setGrid( e.getGrid() );

        return gridColumnDto;
    }

    @Override
    public GridColumn toEntity(GridColumnDto d, CycleAvoidingMappingContext context) {
        GridColumn target = context.getMappedInstance( d, GridColumn.class );
        if ( target != null ) {
            return target;
        }

        if ( d == null ) {
            return null;
        }

        GridColumn gridColumn = new GridColumn();

        context.storeMappedInstance( d, gridColumn );

        gridColumn.setId( d.getId() );
        gridColumn.setNumber( d.getNumber() );
        gridColumn.setGrid( d.getGrid() );

        return gridColumn;
    }

    @Override
    public List<GridColumnDto> toListDto(List<GridColumn> entityList, CycleAvoidingMappingContext context) {
        List<GridColumnDto> target = context.getMappedInstance( entityList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entityList == null ) {
            return null;
        }

        List<GridColumnDto> list = new ArrayList<GridColumnDto>( entityList.size() );
        context.storeMappedInstance( entityList, list );

        for ( GridColumn gridColumn : entityList ) {
            list.add( toDto( gridColumn, context ) );
        }

        return list;
    }

    @Override
    public List<GridColumn> toListEntity(List<GridColumnDto> dtoList, CycleAvoidingMappingContext context) {
        List<GridColumn> target = context.getMappedInstance( dtoList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtoList == null ) {
            return null;
        }

        List<GridColumn> list = new ArrayList<GridColumn>( dtoList.size() );
        context.storeMappedInstance( dtoList, list );

        for ( GridColumnDto gridColumnDto : dtoList ) {
            list.add( toEntity( gridColumnDto, context ) );
        }

        return list;
    }
}
