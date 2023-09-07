package org.davidcastellanos.jdbc;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class JdbcConnections {

    public static void main(String[] args) {

        try {
            //Se usa DriverManager que es una clase de JDBC (java.sql) así conectamos a H2 y tenemos una sesión activa
            // (el método tiene sobrecarga y otros modos de implementar)
            System.out.println("Connecting");
           Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            System.out.println("Connected");

            /* con la sesión activa y una ruta de un  script SQL, usamos la clase llamada
            * RunScript que es una clase exclusiva de H2 y lo que requiere su método execute como argumentos
            * es la conexión activa y la ruta del script sql (el método tiene sobrecarga y otros modos de implementar)*/
            System.out.println("Executing Script");
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            System.out.println("Script Executed");

            /*connection tiene también un método que permite hacer una consulta SQL que va a retornar un objeto del tipo
            * PreparedStatment, cuando se crea el statement, se puede usar el método setXXX que recibe argumento de index
            * (comenzando en 1) y el valor para agregar en el query en el comodín: ?
            * De ésta manera quedará completa la conrulta de insertar datos, éste método (setXXX) se puede ejecutar
            * varias veces y así crear varios registros*/
            System.out.println("init prepared statement");
            PreparedStatement statement = connection
                    .prepareStatement("INSERT INTO person (name, last_name, nickname) VALUES (?, ?, ?)");


            statement.setString(1, "Yese");
            statement.setString(2, "Arévalo");
            statement.setString(3, "YesePao");

           int rows = statement.executeUpdate();
            System.out.println("rows impacted = " + rows);

            /*OJO: Antes de ejecutar el nuevo setString primero hay que hacer el executeUpdate o si no,
            * SE SOBRE ESCRIBE EL PRIMER REGISTRO O FILA*/
            statement.setString(1, "David");
            statement.setString(2, "Castellanos");
            statement.setString(3, "DeividCastell");

            rows = statement.executeUpdate();
            System.out.println("new rows impacted = " + rows);

            statement.close();
            System.out.println("ExecuteUpdate Statement Closed");



            /*En lugar de hacer un executeUpdate, se hace un executeQuery para obtener un ResultSet
            * el resultSet se recorre desde index 1 en adelante (columna por columna) hasta que la columna no exista más */
            ResultSet resultSet = connection.prepareStatement("SELECT * FROM person").executeQuery();
            System.out.println("resultSet.getClass() = " + resultSet.getClass());

            while (resultSet.next()) {

                System.out.printf("\nId: [%d]\tName: [%s]\tLast_Name: [%s]\tNickname: [%s]",
                        resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4));
                /*System.out.println(resultSet.getInt(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));
                System.out.println(resultSet.getString(4));*/
            }

            resultSet.close();
            System.out.println("ResulSet Closed");

            System.out.println();
            System.out.println();


            /*USANDO execute()
            * éste execute devuelve booleano, si devuelve true, puede devolver un ResultSet con el método de statement getResultSet
            * si devuelve false, puede devolver un entero de registros impactados con el método de statement getUpdateCount */
            PreparedStatement statement1 = connection.prepareStatement("SELECT * FROM person");
            boolean execute = statement1.execute();
            System.out.println("Is ResulSet: " + execute);
            ResultSet resultSet1 =statement1.getResultSet();

            while (resultSet1.next()) {
                System.out.printf("\nId: [%d]\tName: [%s]\tLast_Name: [%s]\tNickname: [%s]",
                        resultSet1.getInt(1), resultSet1.getString(2),
                        resultSet1.getString(3), resultSet1.getString(4));
            }

            resultSet1.close();

            System.out.println();
            PreparedStatement statement2 = connection.prepareStatement("INSERT INTO person (name, last_name, nickname) VALUES (?, ?, ?)");
            statement2.setString(1, "John");
            statement2.setString(2, "Doe");
            statement2.setString(3, "Batman");
            boolean execute2 = statement2.execute();
            System.out.println("Is Insert and Not-ResultSet? " + (!execute2));
            System.out.println("Numero de filas impactadas: " + statement2.getUpdateCount());
            statement2.close();


            /*El mismo método de conexión que retorna PreparedStatement, también me permite ejecutar otras query como
            * eliminar lod datos.
            *
            * En ambos querys, se actualiza la consulta con el método executeUpdate() que devuelve la cantidad de
            * filas que fueron impactadas por la consulta */
            PreparedStatement statementDelected = connection.prepareStatement("DELETE FROM person");
            int rowsDelected = statementDelected.executeUpdate();
            System.out.println("\nrowsDelected = " + rowsDelected);

            /*Se deben cerrar los querys Hechos*/

            statementDelected.close();
            System.out.println("prepared statement (DELETE) finished");

            /*Se debe cerrar la conexión a la DB terminando la sesión*/
            System.out.println("Closing Connection");
            connection.close();
            System.out.println("Connection Closed");


            /*Tanto el intento de la Conexión a la db: Connection.
            Así como tratar de leer el script sql de la ruta dada, pueden generar excepciones.
            */
        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
