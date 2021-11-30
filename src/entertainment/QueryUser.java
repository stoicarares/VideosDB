package entertainment;

public abstract class QueryUser extends Query {

    public QueryUser(String objectType, String sortType, String criteria, int number) {
        super(objectType, sortType, criteria, number);
    }

    public abstract StringBuilder applyQuery();
}
