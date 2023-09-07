package org.davidcastellanos.jdbc.transacciones.commitRollbackSavePoint;

public class Conceptos {
    /*Una TRANSACCIÓN es un conjunto de sentencias SQL QUE SE EJECUTARÁN COMO UNA SOLA
    * de éste modo, si toda va bien se ejecutan todas las sentencias o no se ejecuta ninguna.
    * Por tanto, si en un paso intermedio algo falla, */


    /*ACID: Atomic - Consistent - Isolated - Durable... son propiedades de las TRANSACCIONES:
    * Atomic: Asegura que todas las operaciones de DB se ejecuten como una sola.
    * Consistent: Los cambios de la DB se mantienen después de hacer el commit (everything correcto, se puede hacer la transacción).
    * Isolated: Las transacciones (grupo de sentencias) se ejecutan de forma independiente entre sí (AISLAMIENTO DE TRANSACCIONES).
    * Durable: Los datos se mantienen en caso de fallo del sistema (Los datos deben permanecer por ejemplo tras un reinicio).
    * */


    /*CONTROL DE TRANSACCIONES:
    *
    * COMMIT: Confirma una transacción (porque funcionó ok)
    * ROLLBACK: Descarta los cambios realizados previamente (por un error)
    * SAVEPOINT: Crea un punto hacia el cual se puede hacer un rollback sin deshacer todos los cambios (funionó ok hasta aquí pero no acabó)
    * SET TRANSACTION: Asigna un nombre a una transacción
    * */
}
