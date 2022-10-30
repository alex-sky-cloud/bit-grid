package grid.bit.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grid.bit.model.GridColumn;
import grid.bit.model.GridRow;

import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

public class GridDto {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 200)
    private String name;

    @NotNull
    @Positive
    @Min(1)
    @Max(100_000)
    private int cellSize;

    @JsonIgnore
    private List<GridColumn> columns;

    @JsonIgnore
    private List<GridRow> rows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public List<GridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GridColumn> columns) {
        this.columns = columns;
    }

    public List<GridRow> getRows() {
        return rows;
    }

    public void setRows(List<GridRow> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridDto gridDto = (GridDto) o;
        return cellSize == gridDto.cellSize &&
                Objects.equals(id, gridDto.id) &&
                Objects.equals(name, gridDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cellSize);
    }

    @Override
    public String toString() {
        return "GridDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cellSize=" + cellSize +
                ", columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}
