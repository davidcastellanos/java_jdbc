package org.davidcastellanos.jdbc.transacciones.javaFaker;

import com.github.javafaker.Faker;

/*Es una API que permite generar datos aleatorios con el objetivo de
* probar nuestras aplicaciones
* Se debe incluir la dependencia com.github.javafaker dentro del pom.xml
* */
public class FakerAPI {
    public static void main(String[] args) {
        Faker faker = new Faker();
        //VA A GENERAR LOS DATOS DE MANERA ALEATORIA

        System.out.println("Name: " + faker.name().firstName());
        System.out.println("LastName: " + faker.name().lastName());
        System.out.println("NickName:" + faker.hobbit().character());
    }
}
