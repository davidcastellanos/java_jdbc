package org.davidcastellanos.jdbc.dataSourcesYPools.c3poConnectionPool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/*C3PO:
* Es un pool de conexiones como lo es HIKARI o DBCP2
* Requiere dependencia en el pom para funcionar
*  */
public class C3PO {
    public static void main(String[] args) throws SQLException {
        //Creación y configuración del pool C3PO
        ComboPooledDataSource connectionsPool = new ComboPooledDataSource();
        connectionsPool.setJdbcUrl("jdbc:h2:~/test");
        connectionsPool.setUser("");
        connectionsPool.setPassword("");

        final int NUM_CONNECTIONS = 500;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            Connection connection = connectionsPool.getConnection();
            connection.close();
        }
        connectionsPool.close();

        System.out.println("(finalTime - startTime) = " + (System.currentTimeMillis() - startTime) + " Milliseconds");

    }
}
