package debut.rail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import debut.rail.utils.ExcelReader;

@SpringBootApplication

public class RailApplication {
	
	public static void main(String[] args) {
		// ExcelReader excelReader = new ExcelReader();
		// excelReader.func('B', 6, "/rail/src/main/java/debut/rail/utils/2024-11-2-KTX.xlsx");
		SpringApplication.run(RailApplication.class, args);
	}

}
