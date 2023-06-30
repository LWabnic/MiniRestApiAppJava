package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(MockitoJUnitRunner.class)
public class CsvReaderServiceTest {
    @Test
    public void shouldPostToApiForEveryRecordInCsv() {
        RestTemplate restTemplate = Mockito.mock(RestTemplate.class);
        CsvReaderService csvReaderService = new CsvReaderService(restTemplate);

        csvReaderService.processCsv("test.csv");

        Mockito.verify(restTemplate, Mockito.times(5)).postForEntity(Mockito.anyString(), Mockito.any(), Mockito.eq(String.class));
    }
}
