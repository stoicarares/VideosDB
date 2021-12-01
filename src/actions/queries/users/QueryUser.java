package actions.queries.users;

import actions.queries.Query;

public abstract class QueryUser extends Query {
    public QueryUser(final String objectType, final String sortType,
                     final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    /**
     * Abstract method for apply the given query for users.
     * @return The string resulted due to the action
     */
    public abstract StringBuilder applyQuery();
}
