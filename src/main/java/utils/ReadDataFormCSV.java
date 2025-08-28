package utils;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ReadDataFormCSV {

    public static List<String[]> read(String path) {
        List<String[]> rows = new ArrayList<>();
        try (Reader in = new FileReader(path)) {
            // Read CSV with header
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(in);
            for (CSVRecord r : records) {
                // Include URL, username, password
                String url = r.get("url");
                String username = r.get("username");
                String password = r.get("password");

                rows.add(new String[]{url, username, password});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}
