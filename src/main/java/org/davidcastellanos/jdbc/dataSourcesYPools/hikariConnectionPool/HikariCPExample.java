package org.davidcastellanos.jdbc.dataSourcesYPools.hikariConnectionPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariCPExample {
    public static void main(String[] args) throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:~/test");
        hikariConfig.setUsername("");
        hikariConfig.setPassword("");

        hikariConfig.setMaximumPoolSize(5);
        /*Aquí definií un pool con hasta 5 conexiones permitidas,
        * si agrego una 6a conexión, por ejemplo,
        * entrará en connectionTimeout por 30 segundos (por defecto pero se puede cambiar el tiempo) esperando una conexión
        * después de ese tiempo lanzará una excepción*/

        HikariDataSource connectionPool = new HikariDataSource(hikariConfig);
        Connection connection1 = connectionPool.getConnection();
        Connection connection2 = connectionPool.getConnection();
        Connection connection3 = connectionPool.getConnection();
        Connection connection4 = connectionPool.getConnection();
        Connection connection5 = connectionPool.getConnection();

        connection1.close();
        //PARA QUE LA CONEXIÓN 6 Funcione, se debe pedir después de haber cerrado al menos una conexión:
        Connection connection6 = connectionPool.getConnection();
        connection2.close();

        /* si quiero crear una conexión 7, tendré que cerrar otra, y así sucesivamente,
        * en la medida que llego al límite de conexión y quiero usar una nueva, debería cerrar una también,
        * así que cuando tengo un pool lleno, si quiero crear n número de conexiones nuevas en ése pool:
        * debería, o bien, ampliar el maximumPoolSize, o bien, cerrar la misma cantidad de conexiones que
        * las que quiero volver a abrir, para que ahora esas conexiones sean usadas por las nuevas.*/
        Connection connection7 = connectionPool.getConnection(); //Creado después de cerrar la 2da conexión
        connection3.close();
        connection4.close();
        connection5.close();
        connection6.close();
        connection7.close();


        connectionPool.close();
    }
}
