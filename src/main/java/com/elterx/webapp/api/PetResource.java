package com.elterx.webapp.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.elterx.webapp.db.HibernateUtil;
import com.elterx.webapp.db.HibernateUtil.Wrapper;
import com.elterx.webapp.db.model.Pet;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("Pet Resource")
@Path("/resource/pet")
public class PetResource {
	@GET @Path("/list")
	@Produces("application/json; charset=utf-8")
	@ApiOperation(value = "List pets",  response = Pet[].class)
	public Response list(){
		Wrapper<List<Pet>> wrapper = new Wrapper<>();
		Exception ex = HibernateUtil.exec((s,t)->{
			wrapper.set(((List<?>)s.createCriteria(Pet.class).list()).stream().map(o->(Pet)o).collect(Collectors.toList()));
		});
		return ex==null?JSend.success(wrapper.get()):JSend.error(ex);
 	}
}
