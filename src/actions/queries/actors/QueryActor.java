package actions.queries.actors;

import actions.queries.Query;

public abstract class QueryActor extends Query {
    public QueryActor(final String objectType, final String sortType,
                      final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    /**
     * Abstract method for apply the given query for actors.
     * @return The string resulted due to the action
     */
    public abstract StringBuilder applyQuery();
}
