package si.academia.unit29.vaja4;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Path("/orders")
@Singleton
public class OrdersResource {

    // Order order = new Order(1, "Gorazd", new Date(), 3.8f, "eur");
    private Hashtable<Integer, Order> orders = new Hashtable<>();
    private Hashtable<Integer, Hashtable<Integer, Item>> items = new Hashtable<>();

    public OrdersResource() {
    }

    @Path("/{id}")
    @GET
    @Produces("application/json")
    public Order getOrderById(@PathParam("id") Integer id) {
        return orders.get(id);
    }

    @GET
    @Produces("application/json")
    public List<Order> getOrders() {
        return new ArrayList<Order>(orders.values());
    }

    @POST
    @Consumes("application/json")
    public Response createOrder(Order order, @Context UriInfo uriInfo) {
        Integer orderId = orders.size() + 1;
        order.setId(orderId);
        items.put(orderId, new Hashtable<>());
        UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
        uriBuilder.path(Integer .toString(orderId));
        return Response.created(uriBuilder.build()).build();
    }
    @Path("/{id}")
    @PUT
    @Consumes("application/json")
    public void updateOrder(@PathParam("id") int id, Order order) {
        orders.remove(id);
        orders.put(id, order);
        if(!items.containsKey(id)) {
            items.put(id, new Hashtable<>());
        }
    }
    @Path("/{id}")
    @DELETE
    public void deleteOrder(@PathParam("id") int id) {
        orders.remove(id);
        items.remove(id);
    }

    // ----------------------------------------------------------------------

    @Path("/{orderId}/items/{id}")
    @GET
    @Produces("application/json")
    public Item getItemById(@PathParam("orderId") int orderId, @PathParam("id") int itemId) {
        if (!items.containsKey(orderId)) { return null; }
        return items.get(orderId).get(itemId);
    }

    @Path("/{orderId}/items")
    @GET
    @Produces("application/json")
    public List<Item> getItems(@PathParam("orderId") int order_id) {
        if (!items.containsKey(order_id)) { return null; }
        return new ArrayList<Item>(items.get(order_id).values());
    }

    @Path("{orderId}/items")
    @POST
    @Consumes("application/json")
    public Response createItem(@PathParam("orderId") int orderId, Item item, @Context UriInfo info) {
        if (!items.containsKey(orderId)) { return Response.status(Response.Status.NOT_FOUND).build(); }
        int itemId = items.get(orderId).size() + 1;
        item.setId(itemId);
        items.get(orderId).put(itemId, item);
        UriBuilder builder = info.getAbsolutePathBuilder();
        builder.path(Integer .toString(itemId));
        return Response.created(builder.build()).build();
    }

    @Path("/{orderId}/items/{itemId}")
    @PUT
    @Consumes("application/json")
    public Response updateItem(@PathParam("orderId") int orderId, @PathParam("itemId") int id, Item item) {
        if(!items.containsKey(orderId)) { return Response.status(Response.Status.NOT_FOUND).build(); }
        items.get(orderId).remove(id);
        items.get(orderId).put(id, item);
        return Response.noContent().build();
    }

    @Path("/{orderId}/items/{id}")
    public Response deleteItem(@PathParam("orderId") int orderId, @PathParam("id") int id) {
        if (!items.containsKey(orderId)) { return Response.status(Response.Status.NOT_FOUND).build(); }
        items.get(orderId).remove(id);
        return Response.noContent().build();
    }

}
