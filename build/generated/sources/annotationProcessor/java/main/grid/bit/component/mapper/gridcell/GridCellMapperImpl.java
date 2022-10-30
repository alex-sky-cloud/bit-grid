package grid.bit.component.mapper.gridcell;

import grid.bit.component.dto.PrefixDto;
import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.model.GridCell;
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
public class GridCellMapperImpl implements GridCellMapper {

    @Override
    public PrefixDto toDto(GridCell e, CycleAvoidingMappingContext context) {
        PrefixDto target = context.getMappedInstance( e, PrefixDto.class );
        if ( target != null ) {
            return target;
        }

        if ( e == null ) {
            return null;
        }

        String value = null;

        value = e.getValue();

        PrefixDto prefixDto = new PrefixDto( value );

        context.storeMappedInstance( e, prefixDto );

        return prefixDto;
    }

    @Override
    public GridCell toEntity(PrefixDto d, CycleAvoidingMappingContext context) {
        GridCell target = context.getMappedInstance( d, GridCell.class );
        if ( target != null ) {
            return target;
        }

        if ( d == null ) {
            return null;
        }

        GridCell gridCell = new GridCell();

        context.storeMappedInstance( d, gridCell );

        gridCell.setValue( d.getValue() );

        return gridCell;
    }

    @Override
    public List<PrefixDto> toListDto(List<GridCell> entityList, CycleAvoidingMappingContext context) {
        List<PrefixDto> target = context.getMappedInstance( entityList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entityList == null ) {
            return null;
        }

        List<PrefixDto> list = new ArrayList<PrefixDto>( entityList.size() );
        context.storeMappedInstance( entityList, list );

        for ( GridCell gridCell : entityList ) {
            list.add( toDto( gridCell, context ) );
        }

        return list;
    }

    @Override
    public List<GridCell> toListEntity(List<PrefixDto> dtoList, CycleAvoidingMappingContext context) {
        List<GridCell> target = context.getMappedInstance( dtoList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtoList == null ) {
            return null;
        }

        List<GridCell> list = new ArrayList<GridCell>( dtoList.size() );
        context.storeMappedInstance( dtoList, list );

        for ( PrefixDto prefixDto : dtoList ) {
            list.add( toEntity( prefixDto, context ) );
        }

        return list;
    }
}
