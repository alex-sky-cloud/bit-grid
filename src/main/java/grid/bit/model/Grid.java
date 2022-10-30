package grid.bit.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "grid")
public class Grid extends AbstractEntity<Long> {

    private String name;

    private int cellSize;

    private List<GridColumn> columns;
    private List<GridRow> rows;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "cell_size")
    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = CascadeType.REMOVE)
    public List<GridColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<GridColumn> columns) {
        this.columns = columns;
    }

    @OrderBy("number")
    @OneToMany(mappedBy = "grid", cascade = CascadeType.REMOVE)
    public List<GridRow> getRows() {
        return rows;
    }

    public void setRows(List<GridRow> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Grid{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", cellSize=" + cellSize +
                ", columns=" + columns +
                ", rows=" + rows +
                '}';
    }
}