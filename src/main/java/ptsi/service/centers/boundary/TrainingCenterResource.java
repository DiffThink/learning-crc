/*
 * Copyright (c) 2019, electrical training ALLIANCE
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are prohibited:
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package ptsi.service.centers.boundary;

import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import ptsi.service.centers.entity.TrainingCenter;

/**
 *
 * @author Stephen W. Boyd <sboyd@electricaltrainingalliance.org>
 */
@Stateless
@Path("centers")
public class TrainingCenterResource {

    @Inject
    TrainingCenterManager manager;

    // POST should return 201 with Location URI in header
    @POST
    public Response save(TrainingCenter center, @Context UriInfo info) {
        TrainingCenter saved = this.manager.save(center);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("{id}")
    public TrainingCenter find(@PathParam("id") long id) {
        return this.manager.findById(id);
    }

    @GET
    @Path("program/{id}")
    public TrainingCenter findByProgram(@PathParam("id") String programCode) {
        return this.manager.findByProgramCode(programCode);
    }

    @GET
    public List<TrainingCenter> all() {
        return this.manager.all();
    }

    @PUT
    @Path("{id}")
    public TrainingCenter update(@PathParam("id") long id, TrainingCenter center) {
        center.setId(id);
        return this.manager.save(center);
    }

    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        this.manager.delete(id);
    }
}
