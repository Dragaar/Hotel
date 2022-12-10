package com.rosivanyshyn.db.dao;

import java.sql.Connection;
import java.util.ArrayList;


public interface GenericDAO<T> {

    /** Add specific object to respective table.
     *
     * @param object object
     * @param con connection to database
     * @return Boolean
     */
    Boolean         insert(Connection con, T object);

    /** Get object from respective table by id.
     *
     * @param id object id
     * @param con connection to database
     * @return T object
     */
    T               get(Connection con, Long id);

    /** Get all objects from table.
     *
     * @param con connection to database
     * @return ArrayList<T>
     */
    ArrayList<T>    getAll(Connection con);

    /** Get few objects from table.
     * <br>Example - start 15, total 50, return records from 15 to 65 (Not from 15 to 50!!!)
     * @param con connection to database
     * @param start which record to start sampling from
     * @param total records count to get
     * @return ArrayList
     */
    ArrayList<T>    getFew(Connection con, int start, int total);

    /** Find entity from respective table by field and value.
     *
     * @param field user field. Doesn`t support foreign keys!!!
     * @param value user value
     * @param con connection to database
     * @return T
     */
    T               getByField(Connection con, String field, Object value);

    /** Update object in respective table.
     *
     * @param object object to update
     * @param con connection to database
     * @return Boolean
     */
    Boolean         update(Connection con, T object);

    /** Delete object from respective table by id.
     *
     * @param id object id
     * @param con connection to database
     * @return Boolean
     */
    Boolean         delete(Connection con, Long id);



}
