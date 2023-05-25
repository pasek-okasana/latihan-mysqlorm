package org.acme.controller;

import java.util.List;
import java.util.Optional;

import org.acme.dto.LaptopDto;
import org.acme.entity.LaptopEntity;

import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/laptop")
public class LaptopResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getData() {
        // List<LaptopEntity> laptopList =
        // LaptopEntity.findAll(Sort.ascending("merk")).list();
        // List<LaptopEntity> laptopList = LaptopEntity.findAll().list();
        // List<LaptopEntity> laptopList = LaptopEntity.listAll();
        List<LaptopEntity> laptopList = LaptopEntity.listAll(Sort.ascending("merk"));
        return Response.ok(laptopList).build();
    }

    @POST
    @Transactional
    @Path("json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(LaptopDto lDto) {
        LaptopEntity lData = new LaptopEntity();
        lData.setMerk(lDto.getMerk());
        lData.setSeri(lDto.getSeri());
        lData.persist();

        return Response.ok().build();

    }

    @PUT
    @Transactional
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") long id, LaptopDto lDto) {
        Optional<LaptopEntity> lDataOpsional = LaptopEntity.findByIdOptional(id);

        if (lDataOpsional.isPresent()) {
            LaptopEntity laptop = lDataOpsional.get();
            laptop.setMerk(lDto.getMerk());
            laptop.setSeri(lDto.getSeri());
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Transactional
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response putJson(@PathParam("id") long id) {
        Optional<LaptopEntity> lDataOpsional = LaptopEntity.findByIdOptional(id);

        if (lDataOpsional.isPresent()) {
            lDataOpsional.get().delete();

            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
