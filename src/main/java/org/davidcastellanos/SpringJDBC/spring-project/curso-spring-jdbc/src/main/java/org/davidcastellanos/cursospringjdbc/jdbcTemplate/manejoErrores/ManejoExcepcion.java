package org.davidcastellanos.cursospringjdbc.jdbcTemplate.manejoErrores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;

/*Spring NO usa SQLException para manejar las excepciones,
* en su lugar, usa DataAccessException, es una excepción Uncheked (No es obligatorio usar Try/Catch)
* y contendrá el error que se produjo durante la operación a la DB*/
@SpringBootApplication
public class ManejoExcepcion {
    private static final Logger log = LoggerFactory.getLogger(ManejoExcepcion.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ManejoExcepcion.class, args);
        JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
        try {
            // Error no hay un empleado con ID 27
            int impactedRows = jdbcTemplate.update("INSERT INTO address (street, st_number, pc, employee_id) " +
                    "VALUES (?,?,?,?)", "AV Libertad", 99, "Zip999", 27);

            // Ejemplo aparte de eliminar registro, funciona, es sólo ejemplo....
            //int eliminatedRows = jdbcTemplate.update("DELETE FROM address WHERE id = 6");
            //log.info("REGISTROS ELIMINADOS ---> {}", eliminatedRows);


        } catch (DataAccessException e) {
            //Si se pone una SQLException da una excepción por eso tener presente de usar siempre DataAccessException
            log.error("EXCEPTION RECEIVED ---> {}", e.getClass());
            log.info("\n");
            log.info("CAUSED BY -----> {}", e.getCause());

        }



    }
}
