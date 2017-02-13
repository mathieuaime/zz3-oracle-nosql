package ws;
import java.util.List;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import daos.LaboratoireDAO;
import entities.Laboratoire;

@Path("laboratory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LaboratoryWS {
    
  private LaboratoireDAO ldao = new LaboratoireDAO();

    public LaboratoryWS() {
    }
    
    @GET
    public RestResponse<Laboratoire> getAll() {       
        List<Laboratoire> laboratoires = ldao.read();
        RestResponse<Laboratoire> resp = new RestResponse<>("200", laboratoires);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Laboratoire> getLaboratoire(@PathParam("id") int id) {       
        Laboratoire laboratoire = ldao.read(id);
        String status = (laboratoire != null ? "200" : "404");
        
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        resp.addObjectList(laboratoire);
        return resp;
    }

    @POST
    public RestResponse<Laboratoire> addLaboratoire(Laboratoire laboratoire) {
        String status = ldao.create(laboratoire);
        
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Laboratoire> updateLaboratoire(@PathParam("id") int id, Laboratoire laboratoire) {
        String status = ldao.update(id, laboratoire);
        
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Laboratoire> deleteLaboratoire(@PathParam("id") int id) {
        String status = ldao.delete(id);  
        
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        return resp;
    }
}
