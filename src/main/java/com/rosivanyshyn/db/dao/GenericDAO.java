package com.rosivanyshyn.db.dao;

import com.rosivanyshyn.db.dao.entity.Apartment;

import java.sql.Connection;
import java.util.ArrayList;


public interface GenericDAO<T> {

    /** Add specific object to respective table.
     *
     * @param object object
     * @param con connection to database
     * @return Boolean operation result
     */
    Boolean         insert(Connection con, T object);

    /** Get object from respective table by id.
     *
     * @param id object id
     * @param con connection to database
     * @return T object result field
     */
    T               get(Connection con, Long id);

    /** Get all objects from respective table.
     *
     * @param con connection to database
     * @return ArrayList<T> result array
     */
    ArrayList<T>    getAll(Connection con);

    /** Get few objects from respective table.
     * <br>Example - start 15, total 50, return records from 15 to 65 (Not from 15 to 50!!!)
     * @param con connection to database
     * @param start which record to start sampling from
     * @param total records count to get
     * @return ArrayList<T> result array
     */

    ArrayList<T>    getFew(Connection con, int start, int total);

    /** Find entity from respective table by field and value.
     *
     * @param field user field. Doesn`t support foreign keys!!!
     * @param value user value
     * @param con connection to database
     * @return T field
     */
    T               getByField(Connection con, String field, Object value);

    /** Get objects from respective table by using query builder string part
     *
     * @param con connection to database
     * @param secondQueryPart query builder string part
     * @param fields fields for insertion in query builder string part statement
     * @return ArrayList<T> result array
     */
    ArrayList<T>    getWithDynamicQuery(Connection con, String secondQueryPart, Object... fields);

    /** Update object in respective table.
     *
     * @param object object to update
     * @param con connection to database
     * @return Boolean operation result
     */
    Boolean         update(Connection con, T object);

    /** Delete object from respective table by id.
     *
     * @param id object id
     * @param con connection to database
     * @return Boolean operation result
     */
    Boolean         delete(Connection con, Long id);

    /** Count the number of rows in the last query
     * Last query should have SQL_CALC_FOUND_ROWS part!!!
     * @param con connection to database
     * @return number of rows
     */
    int             countRowsInLastQuery(Connection con);

}
