package org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers;

import org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.models.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@SpringBootApplication
public class MainRowMapper implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(MainRowMapper.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(MainRowMapper.class, args);
        //JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        /*La clase EmployeeRowMapper tradujo una consulta de resulSet a un Employee por cada fila/registro, y
        * con el método query() a continuación, se pasa dicho Mapper y se obtiene entonces:
        * La lista de objetos Empleado */
        List<Employee> employeeList = jdbcTemplate.query("SELECT * FROM employee", new EmployeeRowMapper());

        for (Employee employee : employeeList) {
            log.info("Id: {} -- Name: {} -- Last_Name: {} -- Age: {} -- Salary: {}", employee.getId(),
                    employee.getName(), employee.getLastName(), employee.getAge(), employee.getSalary());
        }
    }
}
