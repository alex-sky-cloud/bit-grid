package grid.bit.service.row;

import grid.bit.component.mapper.CycleAvoidingMappingContext;
import grid.bit.component.mapper.row.RowMapper;
import grid.bit.repository.GridRowRepository;
import org.springframework.stereotype.Service;

@Service
public class GridRowService {

    private final GridRowRepository gridRowRepository;

    private final RowMapper rowMapper;

    private final CycleAvoidingMappingContext cycleAvoidingMappingContext;

    public GridRowService(GridRowRepository gridRowRepository,
                          RowMapper rowMapper,
                          CycleAvoidingMappingContext cycleAvoidingMappingContext) {
        this.gridRowRepository = gridRowRepository;
        this.rowMapper = rowMapper;
        this.cycleAvoidingMappingContext = cycleAvoidingMappingContext;
    }


}
