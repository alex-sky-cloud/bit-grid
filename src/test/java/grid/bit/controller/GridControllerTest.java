package grid.bit.controller;

import grid.bit.AbstractIntegrationTest;
import grid.bit.model.Grid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;
import java.util.UUID;

import static grid.bit.utils.RandomFakeDataUtils.getRandomStr;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GridControllerTest extends AbstractIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String BASE_URL = "/grid/v1";

    @Sql(scripts = {"classpath:grid/bit/controller/gridControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Test
    public void getCollectionOfGrid() throws Exception {

        ResultMatcher resultMatcher = content()
                .json(
                        "[" +
                                "{id: 55500055510, name: 'A', cellSize: 5}, " +
                                "{id: 55500055520, name: 'B', cellSize: 10}" +
                                "]",
                        true);

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(resultMatcher);
    }

    @Transactional
    @Sql(scripts = {"classpath:grid/bit/controller/gridControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Test
    public void createNewGrid() throws Exception {

        String nameRandom = UUID.randomUUID().toString();

        String cellSize = "100";
        String contentInJson = generateJsonForGridDto(nameRandom, cellSize);

        mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(contentInJson)
        )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("id").exists());
        flushAndClear(entityManager);

        Optional<Grid> gridByName = findByName(Grid.class, nameRandom, entityManager);
        Grid grid = gridByName.orElseThrow();

        assertThat(grid).isNotNull();

        int cellSizeExpected = 100;

        assertThat(grid.getCellSize()).isEqualTo(cellSizeExpected);

        /*Тест проверяет операцию сохранения,
        только один объект типа grid, без ассоацивных связей. И не должен проверять
        * ассоциативные связи с дочерними таблицами.*/
       /* assertThat(grid.getColumns()).hasSize(1);
        assertThat(grid.getRows()).hasSize(1);*/
    }

    @Test
    public void validateConstraintNameColumnNotBlankAndLengthForSaveOperation() throws Exception {

        /*constraint: notBlank for name of column*/
        mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(generateJsonForGridDto(" ", "100"))
        )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().string(containsString("must not be blank")));


        /*constraint: string length > 200  for name of column*/
        String sizeStr = "201";
        String randomStr = getRandomStr(sizeStr);
        String cellSize = "100";
        mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(generateJsonForGridDto(randomStr, cellSize))
        )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().string(containsString("size must be between 1 and 200")));
    }


    @Test
    public void validateConstraintCellSizeColumnSizeAndPositiveForSaveOperation() throws Exception {

        String nameRandom = UUID.randomUUID().toString();

        String cellSizeNegative = "-100";

        String contentInJson = generateJsonForGridDto(nameRandom, cellSizeNegative);

        /*constraint: cellSize > 0 and != 0*/
        mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(contentInJson)
        )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().string(containsString("must be greater than 0"))
                );

        /*constraint: cellSize <= 100_000 */
        String cellSizeGreater = "100001";

        contentInJson = generateJsonForGridDto(nameRandom, cellSizeGreater);

        mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(contentInJson)
        )
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content().string(containsString("must be less than or equal to 100000"))
                );

    }


    @Transactional
    @Sql(scripts = {"classpath:grid/bit/controller/gridControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Test
    public void updateChangesGridName() throws Exception {

        String nameForUpdate = "Bar";
        String cellSize = "100";

        String contentInJson = generateJsonForGridDto(nameForUpdate, cellSize);

        mockMvc.perform(
                put(BASE_URL + "/{id}", 55500055510L)
                        .contentType(APPLICATION_JSON)
                        .content(contentInJson)
        )
                .andExpect(status().isNoContent());
        flushAndClear(entityManager);

        Optional<Grid> gridByName = findByName(Grid.class, "Bar", entityManager);
        Grid grid = gridByName.orElseThrow();

        assertThat(grid).isNotNull();
        assertThat(grid.getId()).isEqualTo(55500055510L);
    }


    @Test
    public void validateConstraintUpdateGridName() throws Exception {

        String sizeStr = "201";
        String randomStr = getRandomStr(sizeStr);
        String cellSize = "100";

        String contentInJson = generateJsonForGridDto(randomStr, cellSize);

        mockMvc.perform(
                put(BASE_URL + "/{id}", 55500055510L)
                        .contentType(APPLICATION_JSON)
                        .content(contentInJson)
        )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content()
                        .string(containsString("A string must be less than or equal " +
                                "to 100000 and not equal 0"))
                );
    }


    @Transactional
    @Sql(scripts = {"classpath:grid/bit/controller/gridControllerTest.sql"},
            executionPhase = BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:grid/bit/clean/clean_database.sql"},
            executionPhase = AFTER_TEST_METHOD)
    @Test
    public void deleteGridAndItsData() throws Exception {

        mockMvc.perform(delete(BASE_URL + "/{id}", 55500055510L))
                .andExpect(status().isNoContent());

        flush(entityManager);

        assertThat(find(Grid.class, 55500055510L, entityManager)).isNull();
    }

    @Test
    public void validateOperationDeleteOnGrid() throws Exception {

        long idWrong = -100L;

        mockMvc.perform(delete(BASE_URL + "/{id}", idWrong))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(content()
                        .string(containsString("A 'ID' must not be less than 1 or equal to 0"))
                );
    }
}