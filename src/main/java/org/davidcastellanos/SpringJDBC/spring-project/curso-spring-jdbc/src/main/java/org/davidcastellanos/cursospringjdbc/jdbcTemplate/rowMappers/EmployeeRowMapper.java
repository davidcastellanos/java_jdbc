package org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers;

import org.davidcastellanos.cursospringjdbc.jdbcTemplate.rowMappers.models.Employee;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*Los RowMappers son usados para
* TRADUCIR UN RESULSET EN POJOS definidos por nosotros.
* Se usa en una clase dada que implementa la interfaz:
* RowMapper<T>
* */

// Con esta implementación Vamos a hacer la traducción de un ResulSet a un Objeto del tipo especificado (Employee):

/*En síntesis:
* Su función principal es mapear o convertir filas de una consulta de base de datos en objetos Java.
*
* En éste caso, la clase EmployeeRowMapper implementa la interfaz RowMapper<Employee>
* lo que significa que está diseñada para convertir filas de resultados de una
* consulta SQL en objetos de tipo Employee*/

public class EmployeeRowMapper implements RowMapper<Employee> {

    /*El método mapRow:
    * es responsable de crear un objeto (Employee en éste caso) por cada
    * fila en el resultado de la consulta (la consulta se hizo en el método query() en la clase MainRowMapper),
    * y por cada fila que se obtuvo, se usarán sus datos y se creará un objeto Employee */
    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt(1));
        employee.setName(rs.getString("name")); //Se puede o especificar el nombre de la columna o su índice
        employee.setLastName(rs.getString(3));
        employee.setAge(rs.getInt(4));
        employee.setSalary(rs.getInt(5));
        return employee;
    }
}
