package grid.bit.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "grid_column")
public class GridColumn extends AbstractEntity<Long> {

    private int number;
    private Grid grid;


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne()
    @JoinColumn(name = "grid_id")
    public Grid getGrid() {
        return grid;
    }

    public void setGrid(Grid grid) {
        this.grid = grid;
    }

    public GridColumn() {
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        GridColumn that = (GridColumn) o;
        return number == that.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), number);
    }

    @Override
    public String toString() {
        return "GridColumn{" +
                "number=" + number +
                ", gridId=" + grid.getId() +
                '}';
    }

}