package ptsi.service.classification.classifications.boundary;

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

import ptsi.service.classification.classifications.entity.ClassificationEntity;

@Stateless
@Path("classifications")
public class ClassificationsResource {

	@Inject
	ClassificationsManager manager;

	// POST should return 201 with Location URI in header
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response save(ClassificationEntity classification, @Context UriInfo info) {
		ClassificationEntity saved = this.manager.save(classification);
		Long id = saved.getId();
		URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
		return Response.created(uri).build();
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response find(@PathParam("id") Long id) {
		ClassificationEntity classification = this.manager.findById(id);
		if (classification != null) {
			return Response.ok(classification).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Path("byprogram/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByProgram(@PathParam("id") String id) {
		List<ClassificationEntity> classifications = this.manager.findByProgram(id);
		if (!classifications.isEmpty() && classifications != null) {
			return Response.ok(classifications).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {
		List<ClassificationEntity> classifications = this.manager.findAll();
		if (!classifications.isEmpty() && classifications != null) {
			return Response.ok(classifications).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response update(@PathParam("id") Long id, ClassificationEntity classification) {
		ClassificationEntity classificationToUpdate = this.manager.findById(id);
		if (classificationToUpdate != null) {
			classification.setId(id);
			classificationToUpdate = this.manager.save(classification);
			if (classificationToUpdate != null) {
				return Response.ok(classificationToUpdate).build();
			}
			else {
				return Response.status(Response.Status.CONFLICT).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@DELETE
	@Path("{id}")
	public Response delete(@PathParam("id") Long id) {
		if (this.manager.delete(id)) {
			return Response.ok().build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
