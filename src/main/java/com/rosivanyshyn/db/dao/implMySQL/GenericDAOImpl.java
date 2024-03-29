package com.rosivanyshyn.db.dao.implMySQL;

import com.rosivanyshyn.db.dao.GenericDAO;
import com.rosivanyshyn.exeption.DAOException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Formatter;

import static com.rosivanyshyn.db.dao.constant.Query.COUNT_ROWS_IN_LAST_QUERY;
import static com.rosivanyshyn.exeption.Message.*;

/**
 * Generic DAO interface implementation.
 *
 * @author Rostyslav Ivanyshyn.
 * @param <T> Entity
 */
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

    // Fields for overriding by child

    //--------------------DB Queries -------------------------------\\
    abstract String insertQuery();
    abstract String selectQuery();
    abstract String selectAllQuery();
    abstract String selectFewQuery();
    abstract String selectByFieldQuery();
    abstract String updateQuery();
    abstract String deleteQuery();


    //--------------------DBStatementOperation ---------------------\\
    abstract DBStatementOperations<T> insertOperations();
    abstract DBStatementOperations<T> updateOperations();

    //--------------------EntityOperation --------------------------\\
    /** Set fields generated by the database to the Entity**/
    abstract SetGeneratedValuesToEntity<T> setGeneratedValuesToEntity();

    /** Create new Entity from DB ResultSet **/
    abstract ExtractEntity<T> entityFromGet();

    //--------------------------------------------------------------\\
    protected static final Logger LOG = Logger.getLogger(GenericDAOImpl.class);
    protected final String className = this.getClass().getSimpleName();

    @Override
    public Boolean insert(Connection con, T object) {
        LOG.info("Query: " + insertQuery());

        ResultSet rs;
        try (PreparedStatement stmt = con.prepareStatement(insertQuery(), Statement.RETURN_GENERATED_KEYS)) {

            insertOperations().substituteValuesInStatement(stmt, object);

        int countSuccessfulInsertions = stmt.executeUpdate();

            if(countSuccessfulInsertions >0){
                rs = stmt.getGeneratedKeys();
                if(rs.next()){
                    setGeneratedValuesToEntity().set(rs, object);
                    return true;
                }

            }
        } catch (SQLException ex){
            LOG.error(className + " " + INSERT_ERROR, ex);
            throw new DAOException(className + " " + INSERT_ERROR, ex);
        }
        return  false;
    }

    @Override
    public T get(Connection con, Long id) {
        LOG.info("Query: " + selectQuery());

        ResultSet rs;
        try (PreparedStatement stmt = con.prepareStatement(selectQuery()) ) {

            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            if(rs.next()){
                return  entityFromGet().extractEntity(rs);
            }

        } catch (SQLException ex){
            LOG.error(className + " " + SELECT_ERROR, ex);
            throw new DAOException(className + " " + SELECT_ERROR, ex);
        }
        return null;
    }

    @Override
    public ArrayList<T> getAll(Connection con) {
        LOG.info("Query: " + selectAllQuery());

        ArrayList<T> array = new ArrayList<>();
        ResultSet rs;
        try (PreparedStatement stmt = con.prepareStatement(selectAllQuery()) ) {
            rs = stmt.executeQuery();

            while (rs.next()) {
                array.add(entityFromGet().extractEntity(rs));
            }
            return array;
        } catch (SQLException ex){
            LOG.error(className + " " + SELECT_ALL_ERROR, ex);
            throw new DAOException(className + " " + SELECT_ALL_ERROR, ex);
        }
    }
    @Override
    public ArrayList<T> getFew(Connection con, int start, int total){
        LOG.info("Query: " + selectFewQuery());
        ArrayList<T> array = new ArrayList<>();

        ResultSet rs;
        try (PreparedStatement stmt = con.prepareStatement(selectFewQuery()) ) {
            stmt.setLong(1, start);
            stmt.setLong(2, total);

            rs = stmt.executeQuery();

            while (rs.next()) {
                array.add(entityFromGet().extractEntity(rs));
            }
            return array;
        } catch (SQLException ex){
            LOG.error(className + " " + SELECT_FEW_ERROR, ex);
            throw new DAOException(className + " " + SELECT_FEW_ERROR, ex);
        }

    }

    @Override
    public T getByField(Connection con, String field, Object value) {
        LOG.info("Query: " + selectByFieldQuery());

        ResultSet rs;
        Formatter formatter = new Formatter();
        formatter.format(selectByFieldQuery(), field);

        try (PreparedStatement stmt = con.prepareStatement(formatter.toString())) {

            setDynamicData(stmt,1, value);

            rs = stmt.executeQuery();
            while(rs.next()){
                return entityFromGet().extractEntity(rs);
            }

        } catch (SQLException ex){
            LOG.error(className + " " + SELECT_BY_FIELD_ERROR, ex);
            throw new DAOException(className + " " + SELECT_BY_FIELD_ERROR, ex);
        }
        return null;
    }
    @Override
    public ArrayList<T> getWithDynamicQuery(Connection con, String secondQueryPart, Object... fields){
        LOG.info("Query: " + selectAllQuery()+secondQueryPart);
        ArrayList<T> array = new ArrayList<>();
        int index = 1;

        ResultSet rs;
        try (PreparedStatement stmt = con.prepareStatement(selectAllQuery()+secondQueryPart) ) {
            if(fields.length>0){
                for(Object field : fields){
                    setDynamicData(stmt, index++, field);
                }
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                array.add(entityFromGet().extractEntity(rs));
            }
            return array;
        } catch (SQLException ex){
            LOG.error(className + " " + SELECT_DYNAMIC_ERROR, ex);
            throw new DAOException(className + " " + SELECT_DYNAMIC_ERROR, ex);
        }

    }

    @Override
    public Boolean update(Connection con, T object) {
        LOG.info("Query: " + updateQuery());

        try (PreparedStatement stmt = con.prepareStatement(updateQuery()) ) {

           updateOperations().substituteValuesInStatement(stmt, object);

            int countSuccessfulUpdates = stmt.executeUpdate();
            return countSuccessfulUpdates > 0;

        } catch (SQLException ex){
            LOG.error(className + " " + UPDATE_ERROR, ex);
            throw new DAOException(className + " " + UPDATE_ERROR, ex);
        }
    }

    // The mechanism of object cleaning should be implemented in the service layer
    @Override
    public Boolean delete(Connection con, Long id) {
        LOG.info("Query: " + deleteQuery());

        try (PreparedStatement stmt = con.prepareStatement(deleteQuery()) ) {
            stmt.setLong(1, id);

            int countSuccessfulDeletes = stmt.executeUpdate();
            return countSuccessfulDeletes  > 0;

        } catch (SQLException ex){
            LOG.error(className + " " + DELETE_ERROR, ex);
            throw new DAOException(className + " " + DELETE_ERROR, ex);
        }
    }

    @Override
    public int countRowsInLastQuery(Connection con){
        LOG.info("Query: " + COUNT_ROWS_IN_LAST_QUERY);
        ResultSet rs;

        try (PreparedStatement stmt = con.prepareStatement(COUNT_ROWS_IN_LAST_QUERY) ) {

            rs = stmt.executeQuery();

            if(rs.next()) {
               return rs.getInt("count");
            } else throw new RuntimeException();

        } catch (SQLException ex){
            LOG.error(className + " " + COUNT_ROWS_ERROR, ex);
            throw new DAOException(className + " " + COUNT_ROWS_ERROR, ex);
        }
    }


    // Defines the type of data which should be using for substitute fields in query
    private void setDynamicData(PreparedStatement stmt, int index, Object value) throws SQLException{
        if(value instanceof Long) { stmt.setLong(index, (Long) value);}
        else if(value instanceof String) { stmt.setString(index, (String) value);}
        else if(value instanceof Date) { stmt.setDate(index, (java.sql.Date) value);}
        else if(value instanceof Timestamp) { stmt.setTimestamp(index, (java.sql.Timestamp) value);}
    }

    /** Substitute Values in DB Queries through statement
     *
     * @param <T> Type of Entity
     */
    @FunctionalInterface
    protected interface DBStatementOperations<T> {
        /** Example -
         * <code>
         * <br> int k = 1;
         * <br> pstmt.setString(k++, object.getName());
         * <br> pstmt.setLong(k, object.getId());
         * </code>
         * <p> Do not execute stmt or do any other DB operation!!! </p>
         */
        void substituteValuesInStatement(PreparedStatement stmt, T entity) throws SQLException;
    }


    /** Receive getGeneratedKeys from preparedStatement
     * <br> and update current entity for which them was generated.
     * <br> It`s important for save object integrity
     */
    @FunctionalInterface
    protected interface SetGeneratedValuesToEntity<T> {
        /** Example -
         * <code>
         * <br> if(rs.next()){
         * <br>   entity.setId( rs.getInt(1) );
         * <br>  }
         * </code>
         * <p> Only for operation of receiving data and update input entity!  </p>
         */
         void set(ResultSet rs, T entity) throws SQLException;
    }


    /** Extract entity from ResultSet in JavaBean
     * @param <T> Type of Entity
     */
    @FunctionalInterface
    protected interface ExtractEntity <T> {
        /** Example -
         * <code>
         * <br> User user = new User
         * <br> user.setId(rs.getInt(1));
         * <br> user.setName(rs.getString("name"));
         * <br> return user
         *</code>
         * @return T - Filled Entity
         */
         T extractEntity(final ResultSet rs) throws SQLException;
    }


}


