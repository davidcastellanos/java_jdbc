package org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.rowMapperClaseAnonima;

import org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.rowMapperClaseAnonima.models.EmployeeDos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@SpringBootApplication
public class ClaseAnonRowMapper implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(ClaseAnonRowMapper.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ClaseAnonRowMapper.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*En éste ejemplo en lugar de tener una clase que implementa la interfaz
        * RowMapper a parte, se usa directamente una clase anónima que implementa la Interfaz
        * en el método query */
        List<EmployeeDos> employeesList = jdbcTemplate.query("SELECT * FROM employee", new RowMapper<EmployeeDos>() {
            @Override
            public EmployeeDos mapRow(ResultSet rs, int rowNum) throws SQLException {
                EmployeeDos employee = new EmployeeDos();
                employee.setId(rs.getInt(1));
                employee.setName(rs.getString("name")); //Se puede o especificar el nombre de la columna o su índice
                employee.setLastName(rs.getString(3));
                employee.setAge(rs.getInt(4));
                employee.setSalary(rs.getInt(5));
                return employee;
            }
        });

        for (EmployeeDos emp : employeesList) {
            log.info("Id: {} -- Name: {} -- Last_Name: {} -- Age: {} -- Salary: {}", emp.getId(),
                    emp.getName(), emp.getLastName(), emp.getAge(), emp.getSalary());
        }

    }
}
