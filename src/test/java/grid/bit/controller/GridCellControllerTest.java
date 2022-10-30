package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.GridCell;
import grid.bit.repository.GridCellRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GridCellControllerTest extends AbstractIntegrationTest {

    private static final String BASE_URL = "/grid/cell/v1";

    @Autowired
    private GridCellRepository gridCellRepository;

    @Sql(scripts = {"classpath:grid/bit/controller/gridCellControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Test
    public void setValueInCell() throws Exception {

        Long columnIdExpected = 55500055530L;
        Long rowIdExpected = 55500055540L;
        String valueExpected = "100110111011";

        mockMvc.perform(
                post(BASE_URL + "/{columnId}/{rowId}", columnIdExpected, rowIdExpected)
                        .contentType(APPLICATION_JSON)
                        .content("{\"value\":\"100110111011\"}"))
                .andExpect(status().isNoContent());

        String messageErr = "There is not such an element with given composite primary key!";
        GridCell gridCell = gridCellRepository
                .findByGridColumnIdAndGridRowId(columnIdExpected, rowIdExpected)
                .orElseThrow(() -> new RuntimeException(messageErr));

        String valueActual = gridCell.getValue();
        Assertions.assertEquals(valueExpected, valueActual);
    }
}
