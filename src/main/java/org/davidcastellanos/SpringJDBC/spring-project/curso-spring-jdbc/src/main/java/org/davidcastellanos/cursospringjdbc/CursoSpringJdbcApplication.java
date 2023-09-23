package org.davidcastellanos.cursospringjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;

@SpringBootApplication
public class CursoSpringJdbcApplication {

	//log se usa para imprimir similar a como lo hace System.out.println pero cuando se pone en producción,
	// se eliminan los logs
	private static final Logger log = LoggerFactory.getLogger(CursoSpringJdbcApplication.class);//slf4j

	public static void main(String[] args) {
		//----- Conocer la implementación de Pool en Spring, en éste caso es Hikari, pero así se puede saber:

		// El SpringApplication.run() es capaz de devolver un ConfigurableApplicationContext:
		ConfigurableApplicationContext context = SpringApplication.run(CursoSpringJdbcApplication.class, args);

		/*Ese contexto que ha sido obtenido, me puede ayudar a obtener un BEAN:
		* Los Beans Son instancias de clases Java.
		* Son administrados por el contenedor de Spring.
		* Los beans se pueden utilizar para encapsular componentes de una aplicación,
		* como servicios, utilidades y funcionalidades.
		* */

		// En éste caso estoy usando el método getBean() para obtener una instancia de DATASOURCE desde el
		// contenedor de Spring.
		// El método getBean() toma un tipo de bean como parámetro. En este caso, estamos pasando DataSource.class,
		// que es el tipo de bean que estamos buscando.
		DataSource dataSource = context.getBean(DataSource.class);
		//En este caso, el contenedor de Spring encontrará un bean de DataSource que ha sido declarado en
		// la configuración de Spring. Este bean será una instancia de una clase que implementa la interfaz DataSource

		//finalmente se obtiene el bean de datasource y se obtiene que es Hikari (aunque esto puede variar):
		log.info("Datasource implementation ----> {}", dataSource.getClass().getName());
	}

}
