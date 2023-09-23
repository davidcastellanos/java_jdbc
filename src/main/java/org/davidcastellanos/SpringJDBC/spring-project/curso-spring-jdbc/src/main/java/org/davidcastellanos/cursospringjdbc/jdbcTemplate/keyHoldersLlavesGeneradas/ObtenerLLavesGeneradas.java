package org.davidcastellanos.cursospringjdbc.jdbcTemplate.keyHoldersLlavesGeneradas;

/*Un ejemplo de cuando se genera llaves es cuando se hace el método Post en un REST,
* cuando se inserta el registro en la DB, la buena práctica dice que hay que devolver los datos ingresados
* ADEMÁS DE LAS LLAVES QUE SE GENERARON EN LA BASE DE DATOS */

import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class ObtenerLLavesGeneradas implements ApplicationRunner {
    Faker faker = new Faker();
    private static final Logger log = LoggerFactory.getLogger(ObtenerLLavesGeneradas.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Se crea el almacén de las key y se pasa como param junto a PreparedStatementCreator
        KeyHolder holder = new GeneratedKeyHolder();

        // SE Usa el método update pero éste vez recibe como Params:
        //  PreparedStatementCreator() y un KeyHolder holder
        int rowsImpacted = jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                // éste preparedStatment funciona similar al nativo de JDBC y también devuelve el num de registros y usa una CONEXIÓN
                PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO address (street, st_number, pc, employee_id) " +
                        "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS); // La novedad es que se requiere que devuelva la llave generada

                preparedStatement.setString(1, faker.address().streetName());
                preparedStatement.setInt(2, faker.number().randomDigitNotZero());
                preparedStatement.setString(3, faker.address().zipCode());
                preparedStatement.setInt(4, faker.number().numberBetween(1,7));
                return preparedStatement;
            }
        }, holder);

        log.info("ROWS IMPACTED -----> {}", rowsImpacted);
        log.info("GENERATED KEY -----> {}", holder.getKey().intValue()); // Al final del holder se puede obtener el key generado
    }

    public static void main(String[] args) {
        SpringApplication.run(ObtenerLLavesGeneradas.class, args);
    }
}
