package ptsi.service.centers.boundary;

import java.net.URI;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import ptsi.service.centers.entity.TrainingCenterEntity;

@Stateless
@Path("centers")
public class TrainingCenterResource {

	@Inject
	TrainingCenterManager manager;

	// POST should return 201 with Location URI in header
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(TrainingCenterEntity center, @Context UriInfo info) {
		TrainingCenterEntity saved = this.manager.save(center);
		String id = saved.getProgramCode();
		URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
		return Response.created(uri).build();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response find(@PathParam("id") String id) {
		TrainingCenterEntity center = this.manager.findById(id);
		if (center != null) {
			return Response.ok(center).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {
		List<TrainingCenterEntity> centers = this.manager.findAll();
		if (!centers.isEmpty() && centers != null) {
			return Response.ok(centers).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") String id, TrainingCenterEntity center) {
		TrainingCenterEntity centerToUpdate = this.manager.findById(id);
		if (centerToUpdate != null) {
			center.setProgramCode(id);
			centerToUpdate = this.manager.save(center);
			if (centerToUpdate != null) {
				return Response.ok(centerToUpdate).build();
			} else {
				return Response.status(Response.Status.CONFLICT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") String id) {
		if (this.manager.delete(id)) {
			return Response.ok().build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
