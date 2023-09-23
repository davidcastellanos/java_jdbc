package org.davidcastellanos.cursospringjdbc.jdbcTemplate.pasarParametrosAQuerys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class PasarParamsAQuery implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(PasarParamsAQuery.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*Usando el método queryForList que usa 2 params
        * 1. Un query de SQL
        * 2. el tipo de dato que espera
        * Esto devolverá una lista con todos los registros que se obtengan y del tipo dado
        * */


        /*Para pasar params se usa el comodín ?:
        * además del tipo de dato en el queryForList se pasa un ArrayDeObjetos,
        * éste array contendrá el valor o valores que reemplazarán al comodín y que se evalúa en el where*/

        List<String> names = jdbcTemplate.queryForList("SELECT name FROM employee where age = ? OR age = ?",
                new Object[] {31, 32}, // EL número de elementos del array debe ser equivalente al de comodines de lo contrario dará Excepción
                String.class);
        for (String name : names) {
            log.info("Employee Name: {}\n", name);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(PasarParamsAQuery.class);
    }
}
