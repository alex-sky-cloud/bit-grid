package grid.bit.component.dto;

import grid.bit.model.Grid;

import java.util.Objects;

public class GridRowDto {

    private Long id;

    private int number;

    private Grid grid;

    public GridRowDto() {
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
        GridRowDto that = (GridRowDto) o;
        return number == that.number &&
                Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "GridRowDto{" +
                "id=" + id +
                ", number=" + number +
                ", gridId=" + grid.getId() +
                '}';
    }
}
