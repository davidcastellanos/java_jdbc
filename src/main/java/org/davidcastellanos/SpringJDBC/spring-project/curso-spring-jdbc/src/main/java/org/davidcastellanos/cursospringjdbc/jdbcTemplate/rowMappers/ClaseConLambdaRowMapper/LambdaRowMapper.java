package org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.ClaseConLambdaRowMapper;

import org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.ClaseConLambdaRowMapper.models.EmployeeTres;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.util.List;

@SpringBootApplication
public class LambdaRowMapper implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(LambdaRowMapper.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(LambdaRowMapper.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*En lugar de usar una clase anónima o usar una clase separada,
        * se puede usar el RowMapper como Lambda dentro del método query(), pasando directamente los params
        * del método mapRow() quien se ejecuta de manera implícita
        * */
        List<EmployeeTres> emploList =
                jdbcTemplate.query("SELECT * FROM employee", (ResultSet rs, int rowNum)-> {
            EmployeeTres employee = new EmployeeTres();
            employee.setId(rs.getInt(1));
            employee.setName(rs.getString("name")); //Se puede o especificar el nombre de la columna o su índice
            employee.setLastName(rs.getString(3));
            employee.setAge(rs.getInt(4));
            employee.setSalary(rs.getInt(5));
            return employee;
        });

        for (EmployeeTres employeeT : emploList) {
            log.info("Id: {} -- Name: {} -- Last_Name: {} -- Age: {} -- Salary: {}", employeeT.getId(),
                    employeeT.getName(), employeeT.getLastName(), employeeT.getAge(), employeeT.getSalary());

        }
    }
}
