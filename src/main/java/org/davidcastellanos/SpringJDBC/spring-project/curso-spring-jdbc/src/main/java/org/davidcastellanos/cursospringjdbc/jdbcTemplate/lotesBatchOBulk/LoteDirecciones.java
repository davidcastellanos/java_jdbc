package org.davidcastellanos.cursospringjdbc.jdbcTemplate.lotesBatchOBulk;

import com.github.javafaker.Faker;
import org.davidcastellanos.cursospringjdbc.jdbcTemplate.lotesBatchOBulk.models.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class LoteDirecciones implements ApplicationRunner {
    Faker faker = new Faker();
    private static Logger log = LoggerFactory.getLogger(LoteDirecciones.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LoteDirecciones.class, args);

    }

    private void insertAddresses(List<Address> addressList) {

        int[] arrAfectedRows = jdbcTemplate.batchUpdate("INSERT INTO address (street, st_number, pc, employee_id) " +
                "VALUES (?,?,?,?)", new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                //éste método Indica cómo vamos a seguir construyendo los objetos del tipo Address
                // en éste caso por cada dirección en la lista, va añadiéndolo al preparedStatement
                Address address = addressList.get(i);
                ps.setString(1, address.getStreet());
                ps.setInt(2, address.getStNumber());
                ps.setString(3, address.getPc());
                ps.setInt(4, address.getEmployeeId());

            }

            @Override
            public int getBatchSize() { // ESPECIFICA LA CANTIDAD DE REGISTROS QUE QUIERO INSERTAR
                return addressList.size(); // Ej: la misma cantidad de direcciones que contenga la lista.
            }
        });

        // Recorreindo el array de registros afectados por cada consulta dentro del lote
        for (int row : arrAfectedRows) {
            log.info("Rows Impacted {}", row);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertAddresses(Arrays.asList(
                new Address(faker.address().streetName(),
                Integer.parseInt(faker.address().streetAddressNumber()), faker.address().zipCode(), 4),
                new Address(faker.address().streetName(),
                        Integer.parseInt(faker.address().streetAddressNumber()), faker.address().zipCode(), 5),
                new Address(faker.address().streetName(),
                        Integer.parseInt(faker.address().streetAddressNumber()), faker.address().zipCode(), 6),
                new Address(faker.address().streetName(),
                        Integer.parseInt(faker.address().streetAddressNumber()), faker.address().zipCode(), 7),
                new Address(faker.address().streetName(),
                        Integer.parseInt(faker.address().streetAddressNumber()), faker.address().zipCode(), 2)));
    }
}
