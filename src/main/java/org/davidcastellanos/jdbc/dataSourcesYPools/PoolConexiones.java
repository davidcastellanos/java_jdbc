package org.davidcastellanos.jdbc.dataSourcesYPools;

/*POOL DE CONEXIONES:
* Es un conjunto de conexiones físicas que pueden ser
* usadas y reutilizadas a través de múltiples clientes.
*
* En lugar de abrir y cerrar conexiones por cada operación
* mantendremos vivas las conexiones y las reutilizaremos
*
* Entonces: Un pool son un número de conexiones existentes, éstas conexiones se inician
* cuando se inicia la aplicación.
* y cuando un cliente necesita una conexión la toma del pool de conexiones
* y cuando acabe de ocuparla la regresa al pool
* el pool completo se cierra cuando la aplicación termine.
* */


/*
* Pools:
* Cada Driver de cada proveedor (H2, MYSQL, ORACLE, etc) contiene
* su propio pool
* También hay pools aparte como:
* - HikariCp
* - Apache dbpc2
* - C3P0
* */

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

// POOL DE CONEXIONES CON H2:
public class PoolConexiones {
    public static void main(String[] args) throws SQLException {
        JdbcConnectionPool connectionPool = JdbcConnectionPool.create("jdbc:h2:~/test", "", "");
        final int NUM_CONNECTIONS = 100;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            Connection connection = connectionPool.getConnection();
            connection.close();
        }
        System.out.println("finalTime - startTime = " + ((System.currentTimeMillis() - startTime)/1000) + " Segundos");

    }


}
