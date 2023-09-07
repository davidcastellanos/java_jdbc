package org.davidcastellanos.jdbc.transacciones.commitRollbackSavePoint;

import org.h2.tools.RunScript;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
//TODOS (AUTOCOMMIT, COMMIT, ROLLBACK, SAVEPOINT) SON MÉTODOS DE CONNECTION:
public class CommitsYRollback {
   // static int rowsImpacted;
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            RunScript.execute(connection, new FileReader("src/main/resources/schema.sql"));
            int rowsImpacted = 0;

            /*AUTO-COMMIT:
             * Cada vez que se realice un executeUpdate() se realiza un Commit
             * Este comportamiento no es muy recomendable, debería cambiarse para evitar errores:
             * se cambia así: connection.setAutoCommit(false)
             * Esto nos permitirá controlar cuando queremos hacer commit y
             * ENVIAR MÁS DE UNA SENTENCIA EN UNA SOLA TRANSACCIÓN
             * */
            connection.setAutoCommit(false);


            /*COMMIT Y ROLLBACK:
            * Cuando hagamos: connection.commit() Se completarán todos los update y/o query hechos
            * Aún sin el .commit() va a funcionar, se agregarán los datos, pero cuando termine el programa, esos datos
            * No persistieron en la DB.
            * en cambio, con el connection.commit() persestirán esos cambios en la base de datos de manera permanente.
            *
            * Cuando hagamos: connection.rollback() Se van a deshacer los cambios realizados en la base de datos,
            * bien sea hasta un save point o hasta su versión inicial antes de cualquier cambio.
            *
            * */
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO person (name, last_name, nickname) VALUES ( ?,?,? )");

            //COMMIT:
            preparedStatement.setString(1,"Juanito");
            preparedStatement.setString(2, "Pérez");
            preparedStatement.setString(3, "juanito-bendito");
            rowsImpacted += preparedStatement.executeUpdate(); //OK
            System.out.println("rowsImpacted = " + rowsImpacted);

            preparedStatement.setString(1, "David");
            preparedStatement.setString(2, "Castellanos");
            preparedStatement.setString(3, "Davidcito");
            rowsImpacted += preparedStatement.executeUpdate(); //OK
            System.out.println("rowsImpacted = " + rowsImpacted);
            // HAZTA ACÁ EL PROGRAMA FUNCIONARÁ IGUAL, PERO LOS DATOS NO HABRÁN PERSISTIDO EN LA DB


            connection.commit(); // AHORA SI VAN A PERSISTIR LOS DATOS AGREGADOS EN LA DB
            System.out.println("Records has been Persisted");



            //ROLLBACK
            //AHORA HACIENDO UN ROLLBACK:

            try {
                // Data OK:
                preparedStatement.setString(1,"Yesenia");
                preparedStatement.setString(2, "Arévalo Hernández");
                preparedStatement.setString(3, "Yesi");
                rowsImpacted += preparedStatement.executeUpdate();
                System.out.println("rowsImpacted YESENIA = " + rowsImpacted);

                // Dato erróneo cuando se creó la tabla se puso en Constrain NOT NULL en ésta columna name:
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, "Castellanos");
                preparedStatement.setString(3, "Nullcito");
                rowsImpacted += preparedStatement.executeUpdate();
                System.out.println("rowsImpacted = " + rowsImpacted);


                connection.commit(); // Se intenta hacer de nuevo un commit para éstas 2 inserciones pero No funciona
                System.out.println("Records has been Persisted");

            } catch (SQLException e) {
                /* Se captura la exepción y se hace un rollback y ni el código bueno ni el malo dentro del try persistirán
                * Cumpliendo con: "O se hace todo OK, O no pasa ninguno".
                * Los anteriores, por fuera del try si persistieron.
                * */
                connection.rollback();
                System.out.printf("Haciendo Rolling Back porque: %s", e.getMessage());



            } finally {

                //---------------------Bloque para hacer query y obtener resultSET -------------------------
                PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT * FROM person");
                ResultSet resultSet = preparedStatement2.executeQuery();
                //---------------------en estos ejemplos lo uso en los finally para imprimir resultados ----
                System.out.println("\n");
                System.out.println("resultSet.getMetaData().toString() = " + resultSet.getMetaData().toString());

                while (resultSet.next()) {
                    System.out.printf("id: [%d] \tname: [%s] \tlast_name: [%s] \tnick: [%s]\n",
                            resultSet.getInt(1), resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4));
                }
                System.out.println();

                preparedStatement2.close(); //QUERY RESULTSET
                resultSet.close(); // RESULTSET LOCAL

            }


            /*SAVE POINT:
             * Es posible crear un punto de control con un nombre, dentro de una transacción,
             * con esto podemos hacer rollback hasta ese punto que está OK y no peder todos los cambios realizados.
             * y que no se deshaga toda la transacción. Se usa el método:
             *
             * SavePoint savePoint = connection.savePoint("nombrePuntodeControl")
             *
             * */

            /*BORRAR UN SAVE POINT:
            * Se usa: connection.releaseSavePoint(savePoint);
            * Esto libera los recursos asignados a él
            * y si alguna otra operación trata de utilizarlo genera un SQLException*/

            //SAVEPOINT:

            Savepoint savepoint = null;

            try {
                // Data OK:
                preparedStatement.setString(1,"Yese");
                preparedStatement.setString(2, "Arévalo");
                preparedStatement.setString(3, "YesePao");
                rowsImpacted += preparedStatement.executeUpdate();

                preparedStatement.setString(1,"Melva");
                preparedStatement.setString(2, "Peña");
                preparedStatement.setString(3, "MelvysVargas");
                rowsImpacted += preparedStatement.executeUpdate();

                savepoint = connection.setSavepoint("dbSavePoint"); //SAVE POINT ASIGNADO YA NO ES NULL
                System.out.println("rowsImpacted YESE AREVALO CON SAVEPOINT = " + rowsImpacted);


                // Dato erróneo cuando se creó la tabla se puso en Constrain NOT NULL en ésta columna name:
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, "Null");
                preparedStatement.setString(3, "Nullcito");
                rowsImpacted += preparedStatement.executeUpdate();
                System.out.println("rowsImpacted = " + rowsImpacted);


                connection.commit(); // Se intenta hacer de nuevo un commit para ésta inserción pero No funciona
                System.out.println("Records has been Persisted");

            } catch (SQLException e) {
                if (savepoint == null) {
                    connection.rollback();
                    System.out.printf("\nHaciendo Rolling Back COMPLETO porque: %s y SavePoint es: NULL", e.getMessage());
                } else {
                    connection.rollback(savepoint);
                    System.out.printf("\nHaciendo Rolling Back HASTA DONDE SE HIZO EL SAVEPOINT porque: %s", e.getMessage());
                    connection.commit();
                    connection.releaseSavepoint(savepoint); //BORRANDO EL SAVEPOINT - LIBERANDO RECURSOS
                    System.out.println("\nCommit de lo que está OK y Liberando Save Point Usado.........");
                }



            } finally {
                ResultSet set = connection.prepareStatement("SELECT * FROM person").executeQuery();

                System.out.println("\n");
                System.out.println("resultSet.getMetaData().toString() = " + set.getMetaData().toString());

                while (set.next()) {
                    System.out.printf("id: [%d] \tname: [%s] \tlast_name: [%s] \tnick: [%s]\n",
                            set.getInt(1), set.getString(2),
                            set.getString(3), set.getString(4));
                }

                set.close(); //RESULT SET LOCAL

            }



            /* AUTOCOMMIT A TRUE:
             * AL FINAL DEL PROGRAMA, UNA BUENA PRACTICA DE PROGRAMACIÓN ES: después de hacer un savePoint() o connection.commit()
             * o connection.rollback()...
             * es volver a cambiar el AUTOCOMMIT A TRUE así: connection.setAutoCommit(true) PORQUE ÉSE ES SU VALOR
             * POR DEFECTO.
             *
             * */

            preparedStatement.close(); //INSERT INTO STATEMENT
            connection.setAutoCommit(true); // AUTOCOMMIT ACTIVED AGAIN
            connection.close(); //CONNECTION CLOSE
            


        } catch (SQLException | FileNotFoundException e) {

            System.err.println(e.getMessage());

        }


    }


}
