package entertainment;

import java.util.List;

public abstract class QueryActor extends Query {
    public QueryActor(final String objectType, final String sortType,
                      final String criteria, final int number) {
        super(objectType, sortType, criteria, number);
    }

    public abstract StringBuilder applyQuery();


}
