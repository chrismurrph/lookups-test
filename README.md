# lookups-test
Ideally the server would be able to return only the id for lookup entities that already exist in state.

resolvers/product-resolver-1 works as expected. When instead use resolvers/product-resolver-1 the issue is
illustrated. For things to work you need the same product information to be returned from the server as already exists in
app state. Couldn't the merge recognise that having a map with just an id in it means this is a lookup
reference, not a chance to delete everything about product that in app state, apart from their ids.
