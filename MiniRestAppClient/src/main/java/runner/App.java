package runner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import services.CsvReaderService;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"controllers", "services"})
public class App {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(App.class, args);
        CsvReaderService csvReaderService = context.getBean(CsvReaderService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the path of the CSV file:");
        String csvFilePath = scanner.nextLine();

        csvReaderService.processCsv(csvFilePath);
    }
}
