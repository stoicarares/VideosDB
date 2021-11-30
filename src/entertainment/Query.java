package entertainment;

import java.util.ArrayList;

public abstract class Query {
    private String objectType;
    private String sortType;
    private String criteria;
    private int number;

    public Query(final String objectType, final String sortType,
                 final String criteria, final int number) {
        this.objectType = objectType;
        this.sortType = sortType;
        this.criteria = criteria;
        this.number = number;
    }

//    public abstract StringBuilder applyQuery();

    public String getObjectType() {
        return objectType;
    }

    public String getSortType() {
        return sortType;
    }

    public String getCriteria() {
        return criteria;
    }

    public int getNumber() {
        return number;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
