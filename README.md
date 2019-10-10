# lookups-test
Ideally the server would be able to return only the id for lookup entities that already exist in state.

With `app.resolvers/product-resolver` in `app.resolvers/resolvers` then everything works as expected:

![](imgs/works.png)

When instead take out `product-resolver` the issue is illustrated:

![](imgs/does_not_work.png)

Fulcro requires the same product information to be returned from the server as already exists in
app state. Couldn't the merge recognise that having a map with just an id in it means this is a lookup
reference, not a directive to delete product attribute values, apart from their ids?

Looking at `invoice-resolver` you can see that for example `{:product/id 2}` is returned in the map.
But that is just a trigger for `product-resolver` to lookup the rest of the product details. Couldn't
client merging behaviour do the same thing?
