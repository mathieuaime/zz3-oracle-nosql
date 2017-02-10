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
import oracle.nosql.daos.AEcritDAO;
import oracle.nosql.daos.AuteurDAO;
import oracle.nosql.daos.LivreDAO;
import oracle.nosql.entities.AEcrit;
import oracle.nosql.entities.Auteur;
import oracle.nosql.entities.Livre;

@Path("auteur")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuteurWS {
    
  private AuteurDAO adao = new AuteurDAO();
  private LivreDAO ldao = new LivreDAO();
  private AEcritDAO aedao = new AEcritDAO();

    public AuteurWS() {
    }
    
    @GET
    public RestResponse<Auteur> getAll() {       
        List<Auteur> auteurs = adao.read();
        RestResponse<Auteur> resp = new RestResponse<>("200", auteurs);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Auteur> getAuteur(@PathParam("id") int id) {       
        Auteur auteur = adao.read(id);
        String status = (auteur != null ? "200" : "400");
        
        RestResponse<Auteur> resp = new RestResponse<>(status);
        resp.addObjectList(auteur);
        return resp;
    }

    @POST
    public RestResponse<Auteur> addAuteur(Auteur auteur) {
        String status = adao.create(auteur);
        
        RestResponse<Auteur> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Auteur> updateAuteur(@PathParam("id") int id, Auteur auteur) {
        String status = adao.update(id, auteur);
        
        RestResponse<Auteur> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Auteur> deleteAuteur(@PathParam("id") int id) {
        String status = adao.delete(id);  
        
        RestResponse<Auteur> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/livre")
    @GET
    public RestResponse<Livre> listLivre(@PathParam("id") int idAuteur) {
        Auteur a = adao.read(idAuteur);
        RestResponse<Livre> resp = new RestResponse<>("400");
        
        if(a != null) resp = listLivre(adao.read(idAuteur).getNom());
        
        return resp;
    }  
    
    @Path("{nom}/livreFromName")
    @GET
    public RestResponse<Livre> listLivre(@PathParam("nom") String nomAuteur) {
        
        String status = "200";
       
        RestResponse<Livre> resp = new RestResponse<>(status);
        
        for(AEcrit ae : aedao.read(nomAuteur)) {
            Livre livre = ldao.read(ae.getIdLivre());
            status = (!status.equals("401") && livre != null ? "200" : "401");
            if (livre != null) resp.addObjectList(livre);
            resp.setStatus(status);
            resp.setMessage(RestResponse.getMessageError(status));
        }
        
        return resp;
    }  
    
    @Path("{id}/livre")
    @POST
    public RestResponse<AEcrit> addLivre(@PathParam("id") int idAuteur, AEcrit aEcrit) {
        Auteur a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = addLivre(adao.read(idAuteur).getNom(), aEcrit);
        
        return resp;
    }
    
    @Path("{nom}/livreFromName")
    @POST
    public RestResponse<AEcrit> addLivre(@PathParam("nom") String nomAuteur, AEcrit aEcrit) {
        aEcrit.setAuteurNom(nomAuteur);
        String status = aedao.create(aEcrit);
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/livre/{idLivre}")
    @PUT
    public RestResponse<AEcrit> updateLivre(@PathParam("id") int idAuteur, @PathParam("idLivre") int idLivre, AEcrit newAEcrit) {
        Auteur a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = updateLivre(a.getNom(), idLivre, newAEcrit);
        
        return resp;
        
    }
    
    @Path("{nom}/livreFromName/{idLivre}/{rang}")
    @PUT
    public RestResponse<AEcrit> updateLivre(@PathParam("nom") String nomAuteur, @PathParam("idLivre") int idLivre, AEcrit newAEcrit) {
        String status = aedao.update(nomAuteur, idLivre, newAEcrit.getIdLivre());
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/livre")
    @DELETE
    public RestResponse<AEcrit> deleteAllLivre(@PathParam("id") int idAuteur) {
        Auteur a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = deleteAllLivre(adao.read(idAuteur).getNom());
        
        return resp;
    }
    
    @Path("{nom}/livreFromName")
    @DELETE
    public RestResponse<AEcrit> deleteAllLivre(@PathParam("nom") String nomAuteur) {
        String status = aedao.delete(nomAuteur);
      
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/livre/{idLivre}")
    @DELETE
    public RestResponse<AEcrit> deleteLivre(@PathParam("id") int idAuteur, @PathParam("idLivre") int idLivre) {
        Auteur a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = deleteLivre(adao.read(idAuteur).getNom(), idLivre);
        
        return resp;
    }
    
    @Path("{nom}/livreFromName/{idLivre}")
    @DELETE
    public RestResponse<AEcrit> deleteLivre(@PathParam("nom") String nomAuteur, @PathParam("idLivre") int idLivre) {
        String status = aedao.delete(nomAuteur, idLivre);
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
}
