package debut.rail.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import debut.rail.service.TableService;

@Component
public class StartupRunner implements CommandLineRunner {

    private final TableService tableService;

    @Autowired
    public StartupRunner(TableService tableService) {
        this.tableService = tableService;
    }

    @Override
    public void run(String... args) throws Exception {
        tableService.createTableIfNotExists();
        System.out.println("StartupRunner executed.");
    }
}