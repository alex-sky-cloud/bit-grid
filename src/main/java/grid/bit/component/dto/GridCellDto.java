package grid.bit.component.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class GridCellDto {

    @JsonIgnore
    private Long columnId;

    @JsonIgnore
    private Long rowId;

    private String value;

    public GridCellDto() {
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
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
        GridCellDto that = (GridCellDto) o;
        return Objects.equals(columnId, that.columnId) &&
                Objects.equals(rowId, that.rowId) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columnId, rowId, value);
    }

    @Override
    public String toString() {
        return "GridCellDto{" +
                "columnId=" + columnId +
                ", rowId=" + rowId +
                ", value='" + value + '\'' +
                '}';
    }
}
