package org.davidcastellanos.jdbc.diferenciaStatements;

import org.h2.tools.RunScript;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

/*DIFERENCIA ENTRE STATEMENT, PREPARESTATEMENT, CALLABLESTATEMENT*/
public class Statements {

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            /*Statement es una Interfaz:
             * Un objeto co referencia Statement Permite ejecutar sentencias SQL
             * pero no permite cambiar los parámetros de entrada en tiempo de ejecución
             *  */
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM person");


            /*PreparedStatement Interfaz que hereda de Statement:
             * Una sentencia que se compila una única vez y que se ejecuta multiples veces
             * variando los parámetros que puede recibir*/
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person (username, password) VALUES (?, ?)");
            //Aquí si se aceptan los ? ? que son paárametros comodines modificables.
            preparedStatement.setString(1, "root");
            preparedStatement.setString(2, "password123");
            preparedStatement.executeUpdate();


            /*CallableStatement Interfaz que hereda de PreparedStatement:
            * tiene métodos para ejecutar y obtener resultados de un store proccedure
            * Actualmente está en menos uso
            * */
            CallableStatement callableStatement = connection.prepareCall("{call myprocedure(?, ?)}");

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


    }


}
