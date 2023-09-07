package org.davidcastellanos.jdbc.dataSourcesYPools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TiempoConexionDManager {
    /*En éste ejercicio se evidencia que crear muchas conexiones
    * y luego cerrarlas suele afectar el performance de las conexiones
    * esto habrirá paso al concepto de POOLS DE CONEXIONES: */

    public static void main(String[] args) throws SQLException{
        final int NUM_CONNECTIONS = 100;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            connection.close();
        }
        System.out.println("(lastTime - startTime) = " + ((System.currentTimeMillis() - startTime)/1000) + " Segundos");

    }
}
