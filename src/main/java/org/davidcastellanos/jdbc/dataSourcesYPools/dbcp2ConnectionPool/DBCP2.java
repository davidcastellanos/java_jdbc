package org.davidcastellanos.jdbc.dataSourcesYPools.dbcp2ConnectionPool;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/*DBCP2:
* Es otra opción de pool de conexiones como Hikari,
* también requiere de dependencia en el pom.xml
* */
public class DBCP2 {
    public static void main(String[] args) throws SQLException {
        //Configurando y creando el pool de conexiones para poder hacer la conexión
        BasicDataSource conectionsPool = new BasicDataSource();
        conectionsPool.setUrl("jdbc:h2:~/test");
        conectionsPool.setUsername("");
        conectionsPool.setPassword("");

        long startTime = System.currentTimeMillis();
        final int NUM_CONNECTIONS = 500;
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            //Creando las conexiones  y terminándolas usando el pool de conexiones
            Connection connection = conectionsPool.getConnection();
            connection.close();
        }
        System.out.println("lastime - startTime = " + (System.currentTimeMillis() - startTime) + " MilliSeconds");
        conectionsPool.close();


    }
}
