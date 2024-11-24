package debut.rail.service;

import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TableService {
    @PersistenceContext
    private EntityManager entityManager;
    
    public void createTableIfNotExists() {
        String tableCheckQuery = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = 'my_table'";
        Object result = entityManager.createNativeQuery(tableCheckQuery).getSingleResult();

        if (Integer.parseInt(result.toString()) == 0) {
            String createTableQuery = """
                CREATE TABLE my_table (
                    id serial PRIMARY KEY,
                    name VARCHAR(255) NOT NULL
                )
            """;
            entityManager.createNativeQuery(createTableQuery).executeUpdate();
            System.out.println("Table my_table created.");
        } else {
            System.out.println("Table my_table already exists.");
        }
    }
}

