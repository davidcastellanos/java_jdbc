package org.davidcastellanos.jdbc.transacciones.lotes;

import com.github.javafaker.Faker;
import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Arrays;

/*CON UN LOTE O BATCH:
* Se van a insertar, NO un commit por cada registro
* sino que se van a hacer cargas por lote
* por ejemplo cargas de 100 3en 100, de 200 en 200,
* etc etc */

public class Batch {

    public static void main(String[] args) {
        Faker faker = new Faker();

        try (Connection connection = DriverManager.getConnection("jdbc:h2:~/test");) {

            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));

            PreparedStatement preparedStatement = connection.
                    prepareStatement("INSERT INTO person (name, last_name, nickname) VALUES (?,?,?)");



            try {
                connection.setAutoCommit(false);
                System.out.println("Creando Lote......");
                for (int i = 0; i < 100; i++) {
                    preparedStatement.setString(1, faker.hobbit().character());
                    preparedStatement.setString(2, faker.gameOfThrones().house());
                    preparedStatement.setString(3, faker.pokemon().name());

                    /*addBatch:
                     * Al final los anteriores Set alimentan un registro o fila, pero
                     * estos elementos se están guardando antes en un LOTE y no directamente en la DB
                     * */
                    preparedStatement.addBatch();

                }
                System.out.println("Lote Listo......");

                /*Una vez ARMADO el LOTE:
                 * éste .executeBatch()--- se ejecuta en LUGAR DE .commit() o executeUpdate() o executeQuery().
                 * de ésta manera el lote impacta completamente en la DB
                 * */
                System.out.println();
                int[] result = preparedStatement.executeBatch(); //Ejecutar LOTE
                connection.commit(); //Commitear e PERDURAR los datos del LOTE en la DB
                System.out.println("Lote Ejecutado y Comiteado Impactando la DB....\n");
                System.out.println("Arrays.toString(result) -> ARRAY IMPACTOS POR FILA= " + Arrays.toString(result));

                /*for (int dataRowsImpacted : result) {
                    System.out.println("dataRowsImpacted = " + dataRowsImpacted);
                }*/

            } catch (SQLException e) {
                connection.rollback();
                System.err.printf("Error al ejecutar el código SQL: %s", e.getMessage());

            } finally {
                PreparedStatement statementForSet = connection.prepareStatement("SELECT * FROM person");
                ResultSet resultSet = statementForSet.executeQuery();
                System.out.println("\nMOSTRANDO DATOS:");

                while (resultSet.next()) {
                    System.out.printf("id: [%d]\tname: [%s]\tlast: [%s]\tnick: [%s]\n",
                            resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                }

                statementForSet.close();
                resultSet.close();
            }

            connection.setAutoCommit(true);
            preparedStatement.close();


        } catch (SQLException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
