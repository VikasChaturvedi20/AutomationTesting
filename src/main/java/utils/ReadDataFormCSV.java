/*package utils;

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
                // Include URL, username, password, input text
                String url = r.get("url");
                String username = r.get("username");
                String password = r.get("password");
                String input_text=r.get("input_text");
                String targetYear = r.get("targetYear");
                String targetMonth = r.get("targetMonth");
                String targetDay=r.get("targetDay");
                rows.add(new String[]{url, username, password, input_text, targetYear, targetMonth, targetDay});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}
*/

package utils;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

public class ReadDataFormCSV {

    public static List<Map<String, String>> read(String path) {
        List<Map<String, String>> rows = new ArrayList<>();
        try (Reader in = new FileReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader()                // use first row as header
                    .setSkipHeaderRecord(true)  // don't include header in data
                    .setIgnoreHeaderCase(true)  // case-insensitive header
                    .setTrim(true)              // remove extra spaces
                    .build()
                    .parse(in);

            for (CSVRecord record : records) {
                Map<String, String> row = new HashMap<>();
                for (String header : record.toMap().keySet()) {
                    row.put(header, record.get(header));
                }
                rows.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rows;
    }
}


