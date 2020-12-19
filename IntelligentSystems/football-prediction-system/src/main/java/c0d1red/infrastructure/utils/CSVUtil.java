package c0d1red.infrastructure.utils;

import c0d1red.domain.DataTable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class CSVUtil {

    public static DataTable readData(String fileName, String columnSeparator) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String[] headerValues = br.readLine().split(columnSeparator);
            DataTable dataTable = new DataTable(headerValues);

            String dataRow;
            while ((dataRow = br.readLine()) != null) {
                String[] dataValues = dataRow.split(columnSeparator);
                dataTable.addRow(Arrays.asList(dataValues));
            }
            return dataTable;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
