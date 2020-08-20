package ptsi.service.centers.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ptsi.service.centers.entity.TrainingCenter;

@Stateless
@Path("centers")
public class CenterResource {

	@Inject
	CenterManager manager;

    // POST should return 201 with Location URI in header
    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response save(TrainingCenter center, @Context UriInfo info) {
        TrainingCenter saved = this.manager.save(center);
        String id = saved.getProgramCode();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public TrainingCenter find(@PathParam("id") String id) {
        return this.manager.findById(id);
    }
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<TrainingCenter> findAll() {
    	return this.manager.findAll();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public TrainingCenter update(@PathParam("id") String id, TrainingCenter center) {
        center.setProgramCode(id);
        return this.manager.save(center);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") String id) {
        this.manager.delete(id);
    }
}
