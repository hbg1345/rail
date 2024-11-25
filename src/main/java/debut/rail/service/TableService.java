package debut.rail.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import debut.rail.domain.Schedule;
import debut.rail.repository.ScheduleRepository;
import debut.rail.utils.ExcelReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TableService {
    @PersistenceContext
    private EntityManager entityManager;

    private ScheduleRepository scheduleRepository;
    @AutoWired
    TableService(ScheduleRepository scheduleRepository){
        this.scheduleRepository = scheduleRepository;
    }
    
    private static final String filePath = "/rail/src/main/java/debut/rail/utils/2024-11-2-KTX.xlsx";
    private static List<Character> colList = List.of
        ('B', 'X', 'A', 'V', 'B', 'P', 'B', 'W', 
            'B', 'X', 'B', 'V', 'B', 'Q', 'B', 'L');
    private static List<Integer> rowList = List.of
        (7, 6, 6, 6, 6, 7, 7,7);
    
    public void createTableIfNotExists(String tableName) {
        
        String tableCheckQuery = String.format(
            "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = '%s'", 
            tableName
        );
        Object result = entityManager.createNativeQuery(tableCheckQuery).getSingleResult();

        if (Integer.parseInt(result.toString()) == 0 || true) {
            String createTableQuery = String.format("""
                drop table if exists schedule;
                CREATE TABLE %s (
                    id serial PRIMARY KEY,
                    chain_name VARCHAR(255),
                    train_name VARCHAR(255),
                    train_number VARCHAR(50),
                    from_station VARCHAR(255),
                    to_station VARCHAR(255),
                    day VARCHAR(50),
                    departure_time TIME,
                    arrival_time TIME
                );""", tableName);
            
            entityManager.createNativeQuery(createTableQuery).executeUpdate();
            System.out.println(String.format("Table %s created." ,tableName));
            try (FileInputStream fis = new FileInputStream(new File(filePath));
                Workbook workbook = new XSSFWorkbook(fis)){
                
                for(int i=0; i<colList.size(); i++){
                    Sheet sheet = workbook.getSheetAt(i/2);
                    scheduleRepository.saveAll(ExcelReader.getSchedule(colList.get(i), rowList.get(i/2)-1, sheet));
                }
            } 
            catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(String.format("Table %s already exists.", tableName));
        }
    }
}

