package grid.bit.component.mapper.row;

import grid.bit.component.dto.GridRowDto;
import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.model.GridRow;
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
public class RowMapperImpl implements RowMapper {

    @Override
    public GridRowDto toDto(GridRow e, CycleAvoidingMappingContext context) {
        GridRowDto target = context.getMappedInstance( e, GridRowDto.class );
        if ( target != null ) {
            return target;
        }

        if ( e == null ) {
            return null;
        }

        GridRowDto gridRowDto = new GridRowDto();

        context.storeMappedInstance( e, gridRowDto );

        gridRowDto.setId( e.getId() );
        gridRowDto.setNumber( e.getNumber() );
        gridRowDto.setGrid( e.getGrid() );

        return gridRowDto;
    }

    @Override
    public GridRow toEntity(GridRowDto d, CycleAvoidingMappingContext context) {
        GridRow target = context.getMappedInstance( d, GridRow.class );
        if ( target != null ) {
            return target;
        }

        if ( d == null ) {
            return null;
        }

        GridRow gridRow = new GridRow();

        context.storeMappedInstance( d, gridRow );

        gridRow.setId( d.getId() );
        gridRow.setNumber( d.getNumber() );
        gridRow.setGrid( d.getGrid() );

        return gridRow;
    }

    @Override
    public List<GridRowDto> toListDto(List<GridRow> entityList, CycleAvoidingMappingContext context) {
        List<GridRowDto> target = context.getMappedInstance( entityList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entityList == null ) {
            return null;
        }

        List<GridRowDto> list = new ArrayList<GridRowDto>( entityList.size() );
        context.storeMappedInstance( entityList, list );

        for ( GridRow gridRow : entityList ) {
            list.add( toDto( gridRow, context ) );
        }

        return list;
    }

    @Override
    public List<GridRow> toListEntity(List<GridRowDto> dtoList, CycleAvoidingMappingContext context) {
        List<GridRow> target = context.getMappedInstance( dtoList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtoList == null ) {
            return null;
        }

        List<GridRow> list = new ArrayList<GridRow>( dtoList.size() );
        context.storeMappedInstance( dtoList, list );

        for ( GridRowDto gridRowDto : dtoList ) {
            list.add( toEntity( gridRowDto, context ) );
        }

        return list;
    }
}
