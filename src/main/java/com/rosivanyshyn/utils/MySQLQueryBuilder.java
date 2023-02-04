package com.rosivanyshyn.utils;

/**
 * MySQL Query Builder.
 * <br>It contains methods to build dynamic query depends on business requirements.
 *
 * @author Rostyslav Ivanyshyn.
 */
public class MySQLQueryBuilder {

    protected String label;

    private StringBuilder join;
    protected StringBuilder where;
    private StringBuilder order;
    private String limit;

    /** Setter
     * @param label name of DB table
     */
    public void setLabel(String label){
        this.label="`"+label+"`";
    }

    //------------------ Inner Join------------------------\\

    /**
     * Returns rows of current table, that are existed in table B
     * @param table table to comparisons
     * @param label abbreviation of the compared table name
     * @param tableField field name of the compared table
     * @param field field name of current table
     */
    public void join(String table, String label, String tableField, String field) {
        if (join == null) {
            join = new StringBuilder();
        }
        join.append(" INNER JOIN `" + table + "` " + label);
        join.append(" ON " + label + ".`" + tableField + "` = " + this.label + ".`" + field + "`");

    }
    //------------------ Exclude Join------------------------\\

    /**
     * Returns rows of current table, that are not exist in table B
     * @param table table to comparisons
     * @param label abbreviation of the compared table name
     * @param tableField field name of the compared table
     * @param field field name of current table
     */
    public void excludeJoin(String table, String label, String tableField, String field) {
        if (join == null) {
            join = new StringBuilder();
        }
        join.append(" LEFT JOIN `" + table + "` " + label);
        join.append(" ON " + label + ".`" + tableField + "` = " + this.label + ".`" + field + "`");
        join.append(" WHERE " + label + ".`" + tableField + "` IS NULL");

    }


    //-------------------------------------------------------------\\

    //------------------ Searching by field------------------------\\

    /** Filter the table by the specified field
     *
     * @param field field by which to filter
     * @param and combining with previous condition. True -> the preconditions, including the new one, must be true to return records. False -> return a records if any of the conditions is true.
     */
    public void where(String field, boolean and) {
        String clause = label + ".`" + field + "` = ? ";
        addWhere(clause, and);
    }

    /** Combine several filters
     *
     * @param clause new filter
     * @param and Combining with previous condition. True -> the preconditions, including the new one, must be true to return records. False -> return a records if any of the conditions is true.
     */
    protected void addWhere(String clause, boolean and) {
        if (where == null) {
            where = new StringBuilder(" WHERE ");
            where.append(clause);
        } else {
            if (and) {
                where.append("AND " + clause);
            } else {
                where.append("OR " + clause);
            }
        }
    }


    //-------------------------------------------------------------\\

    //---------------------- Filtrating ----------------------------\\

    /** Specify the number of records to return.
     *
     * @param offset which record to start sampling from
     * @param limit records count to get
     */

    public void limit(Integer offset, Integer limit) {
        //in database count start from 0
        offset-=1;
        this.limit = " LIMIT " + offset + ", " + limit;
    }

    /** Sort records by field in ascending or descending order.
     *
     * @param field field by which to sort
     * @param desc if true -> sort in descending order
     */

    public void order(String field, boolean desc) {
        if (this.order == null) {
            this.order = new StringBuilder();
            this.order.append(" ORDER BY " + this.label + ".`" + field + "`");
        } else {
            this.order.append(", " + this.label + ".`" + field + "`");
        }
        if (desc) {
            this.order.append(" DESC");
        }
    }
    //-------------------------------------------------------------\\

    //------------------ Build final Query -------------------------\\

    /** Build the query from all the previous added parts.
     *
     * @return string represent of query.
     */
    public String getQuery() {
        StringBuilder query = new StringBuilder();

        if (join != null) {
            query.append(join);
        }
        if (where != null) {
            query.append(where);
        }
        if (order != null) {
            query.append(order);
        }
        if (limit != null) {
            query.append(limit);
        }
        return query.toString();
    }

    /**
     * Clear query builder
     */
    public void clear() {

        join = null;
        where = null;
        order = null;
        limit = null;

    }
}
