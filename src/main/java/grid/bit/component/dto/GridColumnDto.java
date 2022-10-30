package grid.bit.component.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import grid.bit.model.Grid;

import java.util.Objects;

public class GridColumnDto {

    Long id;

    private int number;

    @JsonIgnore
    private Grid grid;

    public GridColumnDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridColumnDto that = (GridColumnDto) o;
        return number == that.number &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "GridColumnDto{" +
                "id=" + id +
                ", number=" + number +
                ", gridId=" + grid.getId() +
                '}';
    }
}
