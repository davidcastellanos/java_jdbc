package org.davidcastellanos.jdbc.dataSourcesYPools.hikariConnectionPool;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/*HikariCp es un pool de conexiones aislado de la base de datos,
* es el pool de conexiones con mejor performance, para usarlo
* requiere una dependencia en el pom.xml
* */
public class HikariCP {
    public static void main(String[] args) throws SQLException {
        // Configurando usuario, password y definiendo motor de db a conectar (Configuraciones)
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:h2:~/test");
        hikariConfig.setUsername("");
        hikariConfig.setPassword("");

        //Creando el pool de conexiones a la base de datos pasando como argumento la configuación realizada
        HikariDataSource connectionPool = new HikariDataSource(hikariConfig);

        //-------------------------Prueba de velocidad de conexiones--------------------------------------------------
        long startTime = System.currentTimeMillis();
        final int NUM_CONNECTIONS = 500;
        for (int i = 0; i < NUM_CONNECTIONS; i++) {
            Connection connection = connectionPool.getConnection();
            connection.close();
        }
        System.out.println("lastime - startTime = " + (System.currentTimeMillis() - startTime) + " MilliSeconds");
        // Como se termina evidenciando HikariCP es más veloz que la conexion pool de jdbc y la conexión de DriverManager

        //-------------------------Prueba de velocidad de conexiones--------------------------------------------------

        /*PROPIEDADES HikariCP:
        * autoCommit: todas las conexiones del pool de conexiones van a requerir un commit explícito
        *
        * connectionTimeout: Define el tiempo en milisegundos que se va a esperar por una conexión en
        * el pool de conexiones (si están todas las conexiones ocupadas, cuánto tiempo voy a esperar para que se libere y usarla).
        *
        * idleTimeout: Define el tiempo en que una conexión puede estar sin ser usada en el pool de conexiones,
        * sólo es aplicable cuando mimimumIdle se define y es menor al maximunPoolSize
        *
        * maxLifeTime: Controla el tiempo en el que una conexión puede vivir en el pool de conexiones,
        * si se coloca 0 será tiempo infinito, el valor mínimo soportado es (30.000ms o 30 segundos)
        * y el valor por default 1'800.000 milisegundos o 30 minutos, la idea es que mi lifeTime sea menor al de la db
        * para que se cierre antes y evite problemas en la aplicación.
        *
        * connectionTestQuery: Se usa en drivers Legacy inferiores a JDBC 4 que NO
        * implementan Connection.isValid()
        * El string definido debe ser un Query de SQL y se ejecuta siempre ANTES de dar una conexión
        * del pool de conexiones, porque lo que hace es validar las conexiones antes de devolverlas.
        *
        * minimimIdle: Define el mínimo número de conexiones inactivas que Hikari buscará tener en el pool
        * si por ejenplo tengo definido un mínimo de conexiones inactivas en 3 y un máximo de 5 inactivas,
        * pero actualmente sólo dispongo de 2 conexiones inactivas, Hikari creará la nueva conexión inactiva para
        * cumplir con el mínimo de 3.
        * La idea de tener conexiones inactivas se debe a que estarán listas para usar en caso de manejo, de por ejemplo,
        * una gran carga de datos o peticiones.
        *
        * maximumPoolSize: Controla el máximo número de conexiones en el pool, esto incluye:
        * conexiones en uso o inactivas, el tamaño por defecto es 10.
        *
        * metricRegistry: Si se usan frameworks como Micrometer o Dropwizard, es posible
        * recolectar métricas del pool de conexiones.
        *
        * healthCheckRegistry: es usado para reportar la salud del pool de conexiones usando "actuator" o similares..
        *
        * poolName: Da un nombre a un pool de conexiones, es útil cuando se tiene múltiples
        * pools de conexiones en una aplicación.
        *
        * */

        //-----------------------------CONFIGURACIÓN LOGBACK------------------------------------------------
        /*Se pueden configurar logs en HikariCP*/
        connectionPool.close();



    }
}
