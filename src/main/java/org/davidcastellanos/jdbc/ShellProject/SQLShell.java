package org.davidcastellanos.jdbc.ShellProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;

public class SQLShell {
    public static String readCommand() throws IOException {
        System.out.printf("\n david@turing-> ");
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        return bufferedReader.readLine().toLowerCase().trim();
    }

    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            String command = readCommand();

            while (!"quit".equals(command)) {

                try {

                    Statement statement = connection.createStatement();
                    boolean resultType = statement.execute(command);


                    if (resultType) {
                        ResultSet resultSet = statement.getResultSet();
                        while (resultSet.next()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                                String value = resultSet.getString(i);
                                System.out.printf("%s = %s\t\t", metaData.getColumnName(i), value);
                            }
                            System.out.println("Resulset ejecuted");
                        }
                    } else {
                        int updateCount = statement.getUpdateCount();
                        System.out.println("Rows impacted: " + updateCount);
                    }

                } catch (SQLException e) {
                    System.out.printf("\nError: %s executing command statement: %s", e.getMessage(), command);
                } finally {
                    command = readCommand();
                }

            }

            connection.close();

        } catch (SQLException | IOException e) {
            System.err.println(e);
        }


    }
}
