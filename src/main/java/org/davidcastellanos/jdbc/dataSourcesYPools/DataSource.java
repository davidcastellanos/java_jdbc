package org.davidcastellanos.jdbc.dataSourcesYPools;

/* DATASOURCE interfaz... (pertenece al paquete java SE y está ubicado en javax.sql):
*
* Son objetos estándar que representan una FUENTE DE DATOS como una BASE DE DATOS
* esto hace que haya una forma más flexible de establecer conexiones a la base de datos
* diferente a cómo se hace a través de DriverManager
*
* usa el método .getConnection con o sin argumentos (credenciales user y pass)
*
* DE MANERA QUE EL DATASOURCE se vuelve el RESPONSABLE EN ADMINISTRAR LA CONEXIÓN A LA DB
* Administrar las conexiones con DataSource es más sencillo que con DriverManager*/


import org.h2.jdbcx.JdbcDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/*IMPLEMENTACIÓN DATASOURCE:
* al igual que con DriverManager, aquí también se usan los Drivers de los proveedores
* para poder crear conexiones*/
public class DataSource {
    public static void main(String[] args) {

        /*Aunque parece lo mismo que con Driver Manager, ésta clase podría usarse con patrón singleton
        * por ejemplo, en donde estarían las configuraciones de la conexión y había una
        * única conexión para la totalidad del programa.
        *
        * Ejemplo Creando conexión simple:
        *  */

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setUrl("jdbc:h2:~/test");

        try (Connection connection = dataSource.getConnection();) {


            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
