package grid.bit.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grid_row")
public class GridRow extends AbstractEntity<Long> {
    private int number;
    private Grid grid;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grid_id")
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
        if (!super.equals(o)) return false;
        GridRow gridRow = (GridRow) o;
        return number == gridRow.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }

    @Override
    public String toString() {
        return "GridRow{" +
                "number=" + number +
                ", gridId=" + grid.getId() +
                '}';
    }
}