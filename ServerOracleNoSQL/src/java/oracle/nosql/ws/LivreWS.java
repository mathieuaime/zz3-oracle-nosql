package oracle.nosql.ws;
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
import oracle.nosql.daos.AEteEcritDAO;
import oracle.nosql.daos.LivreDAO;
import oracle.nosql.daos.AuteurDAO;
import oracle.nosql.entities.AEteEcrit;
import oracle.nosql.entities.Auteur;
import oracle.nosql.entities.Livre;

@Path("livre")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LivreWS {
    
  private LivreDAO ldao = new LivreDAO();
  private AuteurDAO adao = new AuteurDAO();
  private AEteEcritDAO aedao = new AEteEcritDAO();

    public LivreWS() {
    }
    
    @GET
    public RestResponse<Livre> getAll() {       
        List<Livre> livres = ldao.read();
        
        RestResponse<Livre> resp = new RestResponse<>("200", livres);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Livre> getLivre(@PathParam("id") int id) {       
        Livre livre = ldao.read(id);
        String status = (livre != null ? "200" : "401");
        
        RestResponse<Livre> resp = new RestResponse<>(status);
        resp.addObjectList(livre);
        return resp;
    }

    @POST
    public RestResponse<Livre> addLivre(Livre livre) {       
        String status = ldao.create(livre);
      
        RestResponse<Livre> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Livre> updateLivre(@PathParam("id") int id, Livre livre) {
        String status = ldao.update(id, livre);
        
        RestResponse<Livre> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Livre> deleteLivre(@PathParam("id") int id) {
        String status = ldao.delete(id);  
        
        RestResponse<Livre> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/auteur")
    @GET
    public RestResponse<Auteur> listAuteur(@PathParam("titre") int idLivre) {
        Livre l = ldao.read(idLivre);
        RestResponse<Auteur> resp = new RestResponse<>("401");
        
        if(l != null) resp = listAuteur(ldao.read(idLivre).getTitre());
        
        return resp;
    }  
    
    @Path("{titre}/auteurFromTitle")
    @GET
    public RestResponse<Auteur> listAuteur(@PathParam("titre") String titreLivre) {
        
        String status = "200";
        
        RestResponse<Auteur> resp = new RestResponse<>(status);
        
        for(AEteEcrit aee : aedao.read(titreLivre)) {
            Auteur auteur = adao.read(aee.getIdAuteur());
            status = (!status.equals("400") && auteur != null ? "200" : "400");
            if (auteur != null) resp.addObjectList(auteur);
            resp.setStatus(status);
            resp.setMessage(RestResponse.getMessageError(status));
        }
        
        return resp;
    }  
    
    @Path("{id}/auteur")
    @POST
    public RestResponse<AEteEcrit> addAuteur(@PathParam("id") int idLivre, AEteEcrit aEteEcrit) {
        Livre l = ldao.read(idLivre);
        RestResponse<AEteEcrit> resp = new RestResponse<>("401");
        
        if(l != null) resp = addAuteur(ldao.read(idLivre).getTitre(), aEteEcrit);
        
        return resp;
    }
    
    @Path("{titre}/auteurFromTitle")
    @POST
    public RestResponse<AEteEcrit> addAuteur(@PathParam("titre") String titreLivre, AEteEcrit aEteEcrit) {
        aEteEcrit.setLivreTitre(titreLivre);
        String status = aedao.create(aEteEcrit);
        
        RestResponse<AEteEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/auteur/{idAuteur}")
    @PUT
    public RestResponse<AEteEcrit> updateAuteur(@PathParam("id") int idLivre, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) {
        Livre l = ldao.read(idLivre);
        RestResponse<AEteEcrit> resp = new RestResponse<>("401");
        
        if(l != null) resp = updateAuteur(ldao.read(idLivre).getTitre(), idAuteur, newAEteEcrit);
        
        return resp;
    }
    
    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @PUT
    public RestResponse<AEteEcrit> updateAuteur(@PathParam("titre") String titreLivre, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) {
        String status = aedao.update(titreLivre, idAuteur, newAEteEcrit.getIdAuteur());
        
        RestResponse<AEteEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/auteur")
    @DELETE
    public RestResponse<AEteEcrit> deleteAllAuteur(@PathParam("id") int idLivre) {
        Livre l = ldao.read(idLivre);
        RestResponse<AEteEcrit> resp = new RestResponse<>("401");
        
        if(l != null) resp = deleteAllAuteur(ldao.read(idLivre).getTitre());
        
        return resp;
    }
    
    @Path("{titre}/auteurFromTitle")
    @DELETE
    public RestResponse<AEteEcrit> deleteAllAuteur(@PathParam("titre") String titreLivre) {
        String status = aedao.delete(titreLivre);
        
        RestResponse<AEteEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/auteur/{idAuteur}")
    @DELETE
    public RestResponse<AEteEcrit> deleteAuteur(@PathParam("id") int idLivre, @PathParam("idAuteur") int idAuteur) {
        Livre l = ldao.read(idLivre);
        RestResponse<AEteEcrit> resp = new RestResponse<>("401");
        
        if(l != null) resp = deleteAuteur(ldao.read(idLivre).getTitre(), idAuteur);
        
        return resp;
    }
    
    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @DELETE
    public RestResponse<AEteEcrit> deleteAuteur(@PathParam("titre") String titreLivre, @PathParam("idAuteur") int idAuteur) {
        String status = aedao.delete(titreLivre, idAuteur);
        
        RestResponse<AEteEcrit> resp = new RestResponse<>(status);
        return resp;
    }
}
