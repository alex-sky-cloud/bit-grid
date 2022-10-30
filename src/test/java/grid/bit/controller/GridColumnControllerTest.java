
package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.Grid;
import grid.bit.model.GridColumn;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*Ожидаемые значения в asserts 'тестовых узлов', находятся в соответствии с данными,
 в предоставленном Sql-script*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GridColumnControllerTest extends AbstractIntegrationTest {

    private static final String BASE_URL = "/grid/column/v1";

    @PersistenceContext
    private EntityManager entityManager;


    @Sql(scripts = {"classpath:grid/bit/controller/gridColumnControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void addNewColumnToGrid() throws Exception {

        /* .param("afterColumnId", "5550005551201")
         * в задаче сказано:
         * 5.	Insert a new column next to the given column.
         * The column must be assigned the correct number. For example,
         * if a column is inserted after the column
         * number 5 then its number must be 6.
         *
         * the column  5550005551201, не является последним. Таким образом,
         * либо нужно было сделать вставку между columns и поменять их позиции (number)
         * либо добавить поле последнего.
         * В этом случае постановка непонятна. Поэтому в тестовых данных указал
         * idColumn = 5550005551202 и на основании этого выполнил задание.
         */

        mockMvc.perform(
                post(BASE_URL)
                        .param("afterColumnId", "5550005551202")
                        .accept(APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()));

        flushAndClear(entityManager);

        String gridName = "A";

        Optional<Grid> gridByName = findByName(Grid.class, gridName, entityManager);
        Grid grid = gridByName.orElseThrow();

        int sizeColumnListExpected = 3;

        assertThat(grid.getColumns()).hasSize(sizeColumnListExpected);

        int columnIndexStart = 0;
        GridColumn gridColumnStart = grid.getColumns().get(columnIndexStart);

        /*!!!! magic number лучше убрать и не использовать в тестах вообще. Данные должны быть получены из базы
         * непосредственным обращением в базу, так как скрипт может быть изменен и тесты не будут выполняться*/
        assertThat(gridColumnStart).hasFieldOrPropertyWithValue("id", 5550005551201L);
        assertThat(gridColumnStart).hasFieldOrPropertyWithValue("number", 1);

        int columnIndexFirst = 1;
        GridColumn gridColumnFirst = grid.getColumns().get(columnIndexFirst);

        assertThat(gridColumnFirst).hasFieldOrPropertyWithValue("id", 5550005551202L);
        assertThat(gridColumnFirst).hasFieldOrPropertyWithValue("number", 2);

        int columnIndexSecond = 2;
        GridColumn gridColumnSecond = grid.getColumns().get(columnIndexSecond);
        assertThat(gridColumnSecond).hasFieldOrPropertyWithValue("id", 5550005552001L);
        assertThat(gridColumnSecond).hasFieldOrPropertyWithValue("number", 3);
    }

    @Sql(scripts = {"classpath:grid/bit/controller/gridColumnControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void deleteColumnFromGrid() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/{id}", 5550005551202L))
                .andExpect(status().isNoContent());

        flushAndClear(entityManager);

        String gridName = "A";

        Optional<Grid> gridByName = findByName(Grid.class, gridName, entityManager);
        Grid grid = gridByName.orElseThrow();

        int sizeExpectedList = 1;
        int startIndex = 0;
        assertThat(
                grid.getColumns()
        ).hasSize(sizeExpectedList);

        assertThat(
                grid.getColumns()
                        .get(startIndex)
        ).hasFieldOrPropertyWithValue("id", 5550005551201L);

        assertThat(
                grid.getColumns()
                        .get(startIndex)
        ).hasFieldOrPropertyWithValue("number", 1);
    }

    @Sql(scripts = {"classpath:grid/bit/controller/gridColumnControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Transactional
    @Test
    public void getCommonPrefix_evaluatesCorrectly() throws Exception {

        String baseUrl =  "/grid/cell/v1";

        mockMvc.perform(
                get(baseUrl + "/{id}/common-prefix", 5550005551201L)
                        .accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().json("{value: '11010'}"));
    }
}

