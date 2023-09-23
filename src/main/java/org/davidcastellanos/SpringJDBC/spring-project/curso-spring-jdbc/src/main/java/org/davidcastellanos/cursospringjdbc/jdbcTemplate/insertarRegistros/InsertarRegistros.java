package org.davidcastellanos.cursospringjdbc.jdbcTemplate.insertarRegistros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication

public class InsertarRegistros implements ApplicationRunner {

    private final Logger log = LoggerFactory.getLogger(InsertarRegistros.class);//slf4j


    @Autowired
    private JdbcTemplate jdbcTemplate;


    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(InsertarRegistros.class, args);
        //JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        /*jdbcTemplate.update es un preparedStatement de JDBC (Similar a executeUpdate())
        * que se usa para actualizar registros, o agregar nuevos
        * también devuelve un valor INT que indica la cantidad de registros o filas impactadas
        * */
        int rowsImpacted = jdbcTemplate.update("INSERT INTO address (street, st_number, pc, employee_id) " +
                "VALUES (?,?,?,?)", "AV Suba", 33, "Zip369", 1);
        log.info("ROWS IMPACTED----:> {}", rowsImpacted);
    }
}
