package grid.bit.component.dto;

import java.util.Objects;

public class PrefixDto {

    private String value;

    public PrefixDto(String value) {
        this.value = value;
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
        PrefixDto prefixDto = (PrefixDto) o;
        return Objects.equals(value, prefixDto.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "PrefixDto{" +
                "value='" + value + '\'' +
                '}';
    }
}
