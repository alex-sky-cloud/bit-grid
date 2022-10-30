package grid.bit.model;


import java.io.Serializable;
import java.util.Objects;

public class GridCellPK implements Serializable {

    protected Long gridColumnId;

    protected Long gridRowId;

    public GridCellPK() {
    }

    public GridCellPK(Long gridColumnId, Long gridRowId) {
        this.gridColumnId = gridColumnId;
        this.gridRowId = gridRowId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GridCellPK that = (GridCellPK) o;
        return Objects.equals(gridColumnId, that.gridColumnId) &&
                Objects.equals(gridRowId, that.gridRowId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridColumnId, gridRowId);
    }

    @Override
    public String toString() {
        return "GridCellPK{" +
                "gridColumnId=" + gridColumnId +
                ", gridRowId=" + gridRowId +
                '}';
    }
}
