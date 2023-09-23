package org.davidcastellanos.cursospringjdbc.jdbcTemplate;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/*JDBC TEMPLATE:
* Es una clase Central de Spring JDBC que permite trabajar con JDBC de manera simple y sin errores:
* Es responsable de:
* Ejecutar Consultas
* Insertar Datos
* Actualizarlos
* extraer los resultados de un ResultSet
* ejecutar preparedStatement
* etc*/
@SpringBootApplication
public class JdbcTemplateUso implements ApplicationRunner { // Se puede implementar la interfaz ApplicationRunner que permite ejecutar el método run fuera del contexto estático
    //Para acceder a jdbcTemplate se hace de 2 formas principalmente:

    //1. A través de @Autowired:
    // usando: private JdbcTemplate template;
    @Autowired
    private JdbcTemplate jdbcTemplate;



    //Logger que reemplaza a System.out.println:
    private final Logger log = LoggerFactory.getLogger(JdbcTemplateUso.class);//slf4j


    public static void main(String[] args) {
        //2. Solicitando el objeto al contexto o contenedor de Spring:
        ConfigurableApplicationContext context = SpringApplication.run(JdbcTemplateUso.class, args);
        // JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
    }

    // Gracias a la interfaz AplicationRunner se puede usar éste método, para ejecutar variables fuera de un contexto estático
    @Override
    public void run(ApplicationArguments args) throws Exception {

        /*En ésta caso al usar @Autowired, tuve que agregar el código en
        * éste método de ApplicationRunner, si hubiese usado el óro método,
        * la consulta la debería haber hecho en el método main.
        *
        *  el método queryForObject() recibe como primer parámetro la query de SQL,
        * como segundo parámetro, recibe el tipo de dato esperado
        * y al final devuelve el valor para almacenar en una variable. */
        Integer maxSalary = jdbcTemplate.queryForObject("SELECT MAX(salary) from employee", Integer.class);
        log.info("Max salary: {}", maxSalary);

    }

    // AMBAS FORMAS DE CREAR UN JdbcTemplate SON CORRECTAS Y DEDEPENDE DEL CONTEXTO Y PRÁCTICAS DE LA APP CUAL USAR

}
