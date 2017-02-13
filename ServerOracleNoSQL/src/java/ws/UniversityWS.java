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
import daos.UniversiteDAO;
import entities.Universite;

@Path("laboratory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UniversityWS {
    
  private UniversiteDAO udao = new UniversiteDAO();

    public UniversityWS() {
    }
    
    @GET
    public RestResponse<Universite> getAll() {       
        List<Universite> universites = udao.read();
        RestResponse<Universite> resp = new RestResponse<>("200", universites);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Universite> getUniversite(@PathParam("id") int id) {       
        Universite universite = udao.read(id);
        String status = (universite != null ? "200" : "405");
        
        RestResponse<Universite> resp = new RestResponse<>(status);
        resp.addObjectList(universite);
        return resp;
    }

    @POST
    public RestResponse<Universite> addUniversite(Universite universite) {
        String status = udao.create(universite);
        
        RestResponse<Universite> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Universite> updateUniversite(@PathParam("id") int id, Universite universite) {
        String status = udao.update(id, universite);
        
        RestResponse<Universite> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Universite> deleteUniversite(@PathParam("id") int id) {
        String status = udao.delete(id);  
        
        RestResponse<Universite> resp = new RestResponse<>(status);
        return resp;
    }
}
