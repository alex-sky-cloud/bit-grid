package grid.bit.model;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.Objects;

@Table(name = "grid_cell")
@Entity
@IdClass(GridCellPK.class)
public class GridCell {

    @Id
    @Column(name = "grid_column_id")
    private Long gridColumnId;

    @Id
    @Column(name = "grid_row_id")
    private Long gridRowId;

    private String value;

    @PostConstruct
    void init(){
        this.value = "";
    }

    public GridCell() {
    }

    public Long getGridColumnId() {
        return gridColumnId;
    }

    public void setGridColumnId(Long gridColumnId) {
        this.gridColumnId = gridColumnId;
    }

    public Long getGridRowId() {
        return gridRowId;
    }

    public void setGridRowId(Long gridRowId) {
        this.gridRowId = gridRowId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCell gridCell = (GridCell) o;
        return Objects.equals(gridColumnId, gridCell.gridColumnId) &&
                Objects.equals(gridRowId, gridCell.gridRowId) &&
                Objects.equals(value, gridCell.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridColumnId, gridRowId, value);
    }

    @Override
    public String toString() {
        return "GridCell{" +
                "gridColumnId=" + gridColumnId +
                ", gridRowId=" + gridRowId +
                ", value='" + value + '\'' +
                '}';
    }
}
