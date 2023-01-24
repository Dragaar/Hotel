package com.rosivanyshyn.utils;

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
    public void join(String table, String label, String tableField, String field) {
        if (join == null) {
            join = new StringBuilder();
        }
        join.append(" INNER JOIN `" + table + "` " + label);
        join.append(" ON " + label + ".`" + tableField + "` = " + this.label + ".`" + field + "`");

    }

    //-------------------------------------------------------------\\
    //Пошук за полем
    //------------------ Searching by field------------------------\\
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

    public void where(String field, boolean and) {
        String clause = label + ".`" + field + "` = ? ";
        addWhere(clause, and);
    }
    //-------------------------------------------------------------\\

    //---------------------- Filtrating ----------------------------\\

    public void limit(Integer offset, Integer limit) {
        //in database count start from 0
        offset-=1;
        this.limit = " LIMIT " + offset + ", " + limit;
    }

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

    public void clear() {
        order = null;
        limit = null;
        where = null;
    }
}
