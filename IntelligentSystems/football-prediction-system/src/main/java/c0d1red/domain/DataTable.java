package c0d1red.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DataTable {
    private final List<String> headers;
    private final List<List<String>> data;

    public DataTable(String... headerNames) {
        data = new ArrayList<>();
        headers = Arrays.asList(headerNames);
    }

    public int rowCount() {
        return data.size();
    }

    public void addRow(List<String> row) {
        data.add(row);
    }

    public List<String> getColumnBy(String header) {
        int columnIdx = headers.indexOf(header);
        return data.stream()
                .map(row -> row.get(columnIdx))
                .collect(Collectors.toList());
    }

    public DataTable getFilteredBy(String filterHeader, String... filterValue) {
        int columnIdx = headers.indexOf(filterHeader);
        return createFilteredDataTable(columnIdx, filterValue);
    }

    public String getValueBy(int rowNum, String header) {
        int columnIdx = headers.indexOf(header);
        List<String> row = data.get(rowNum);
        return row.get(columnIdx);
    }

    private DataTable createFilteredDataTable(int columnIdx, String... filterValues) {
        DataTable filteredDataTable = new DataTable(headers.toArray(new String[0]));
        data.stream()
                .filter(row -> isRowToFilter(row, columnIdx, filterValues))
                .forEach(filteredDataTable::addRow);
        return filteredDataTable;
    }

    private boolean isRowToFilter(List<String> row, int columnIdx, String... filterValues) {
        for (String filterValue : filterValues) {
            if (row.get(columnIdx).equals(filterValue)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(headers.toString());
        for (List<String> row : data) {
            result.append(row.toString());
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataTable dataTable = (DataTable) o;
        return Objects.equals(headers, dataTable.headers) &&
                Objects.equals(data, dataTable.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(headers, data);
    }

}
