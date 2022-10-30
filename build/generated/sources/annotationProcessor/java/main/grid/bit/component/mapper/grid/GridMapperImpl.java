package grid.bit.component.mapper.grid;

import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.model.Grid;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;
import grid.bit.service.dto.GridDto;
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
public class GridMapperImpl implements GridMapper {

    @Override
    public GridDto toDto(Grid e, CycleAvoidingMappingContext context) {
        GridDto target = context.getMappedInstance( e, GridDto.class );
        if ( target != null ) {
            return target;
        }

        if ( e == null ) {
            return null;
        }

        GridDto gridDto = new GridDto();

        context.storeMappedInstance( e, gridDto );

        gridDto.setId( e.getId() );
        gridDto.setName( e.getName() );
        gridDto.setCellSize( e.getCellSize() );
        List<GridColumn> list = e.getColumns();
        if ( list != null ) {
            gridDto.setColumns( new ArrayList<GridColumn>( list ) );
        }
        List<GridRow> list1 = e.getRows();
        if ( list1 != null ) {
            gridDto.setRows( new ArrayList<GridRow>( list1 ) );
        }

        return gridDto;
    }

    @Override
    public Grid toEntity(GridDto d, CycleAvoidingMappingContext context) {
        Grid target = context.getMappedInstance( d, Grid.class );
        if ( target != null ) {
            return target;
        }

        if ( d == null ) {
            return null;
        }

        Grid grid = new Grid();

        context.storeMappedInstance( d, grid );

        grid.setId( d.getId() );
        grid.setName( d.getName() );
        grid.setCellSize( d.getCellSize() );
        List<GridColumn> list = d.getColumns();
        if ( list != null ) {
            grid.setColumns( new ArrayList<GridColumn>( list ) );
        }
        List<GridRow> list1 = d.getRows();
        if ( list1 != null ) {
            grid.setRows( new ArrayList<GridRow>( list1 ) );
        }

        return grid;
    }

    @Override
    public List<GridDto> toListDto(List<Grid> entityList, CycleAvoidingMappingContext context) {
        List<GridDto> target = context.getMappedInstance( entityList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( entityList == null ) {
            return null;
        }

        List<GridDto> list = new ArrayList<GridDto>( entityList.size() );
        context.storeMappedInstance( entityList, list );

        for ( Grid grid : entityList ) {
            list.add( toDto( grid, context ) );
        }

        return list;
    }

    @Override
    public List<Grid> toListEntity(List<GridDto> dtoList, CycleAvoidingMappingContext context) {
        List<Grid> target = context.getMappedInstance( dtoList, List.class );
        if ( target != null ) {
            return target;
        }

        if ( dtoList == null ) {
            return null;
        }

        List<Grid> list = new ArrayList<Grid>( dtoList.size() );
        context.storeMappedInstance( dtoList, list );

        for ( GridDto gridDto : dtoList ) {
            list.add( toEntity( gridDto, context ) );
        }

        return list;
    }
}
