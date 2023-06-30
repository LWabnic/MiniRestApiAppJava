package services;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReaderService {
    private static final String API_URL = "http://localhost:8080/customers";
    private RestTemplate restTemplate;

    public CsvReaderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void processCsv(String csvFilePath) {
        try {

            ClassLoader classLoader = getClass().getClassLoader();
            InputStream is = classLoader.getResourceAsStream(csvFilePath);
            if (is == null) {
                throw new IllegalArgumentException("File not found!");
            } else {

                CSVReader reader = new CSVReader(new InputStreamReader(is));
                List<String[]> records = reader.readAll();
                reader.close();
                // Skipping the header row
                for (int i = 1; i < records.size(); i++) {
                    Map<String, Object> customerData = createCustomerFromRecord(records.get(i));
                    sendDataToAPI(customerData);
                }

            }

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }


    private Map<String, Object> createCustomerFromRecord(String[] record) {
        Map<String, Object> customerData = new HashMap<>();
        customerData.put("customerRef", record[0]);
        customerData.put("customerName", record[1]);
        customerData.put("addressLine1", record[2]);
        customerData.put("addressLine2", record[3]);
        customerData.put("town", record[4]);
        customerData.put("county", record[5]);
        customerData.put("country", record[6]);
        customerData.put("postcode", record[7]);

        return customerData;
    }

    private void sendDataToAPI(Map<String, Object> customerData) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(customerData, headers);
        restTemplate.postForEntity(API_URL, entity, String.class);
    }
}
