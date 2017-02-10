package oracle.nosql.ws;
import java.util.ArrayList;
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
    public List<Livre> getAll() {       
        return ldao.read();
    }
  
    @Path("{id}")
    @GET
    public Livre getLivre(@PathParam("id") int id) {       
        return ldao.read(id);
    }

    @POST
    public String addLivre(Livre livre) {       
      return ldao.create(livre);
    }

    @Path("{id}")
    @PUT
    public String updateLivre(@PathParam("id") int id, Livre livre) {
        return ldao.update(id, livre);
    }

    @Path("{id}")
    @DELETE
    public String deleteLivre(@PathParam("id") int id) {
        return ldao.delete(id);  
    }
    
    @Path("{id}/auteur")
    @GET
    public List<Auteur> listAuteur(@PathParam("titre") int idLivre) {
        return listAuteur(ldao.read(idLivre).getTitre());
    }  
    
    @Path("{titre}/auteurFromTitle")
    @GET
    public List<Auteur> listAuteur(@PathParam("titre") String titreLivre) {
        ArrayList<Auteur> a = new ArrayList<>();
        
        for(AEteEcrit aee : aedao.read(titreLivre)) {
            a.add(adao.read(aee.getIdAuteur()));
        }
        
        return a;
    }  
    
    @Path("{id}/auteur")
    @POST
    public String addAuteur(@PathParam("id") int idLivre, AEteEcrit aEteEcrit) {
        return addAuteur(ldao.read(idLivre).getTitre(), aEteEcrit);
    }
    
    @Path("{titre}/auteurFromTitle")
    @POST
    public String addAuteur(@PathParam("titre") String titreLivre, AEteEcrit aEteEcrit) {
        aEteEcrit.setLivreTitre(titreLivre);
        return aedao.create(aEteEcrit);
    }
    
    @Path("{id}/auteur/{idAuteur}")
    @PUT
    public String updateAuteur(@PathParam("id") int idLivre, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) {
        return updateAuteur(ldao.read(idLivre).getTitre(), idAuteur, newAEteEcrit);
    }
    
    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @PUT
    public String updateAuteur(@PathParam("titre") String titreLivre, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) {
        return aedao.update(titreLivre, idAuteur, newAEteEcrit.getIdAuteur());
    }
    
    @Path("{id}/auteur")
    @DELETE
    public String deleteAllAuteur(@PathParam("id") int idLivre) {
        return deleteAllAuteur(ldao.read(idLivre).getTitre());
    }
    
    @Path("{titre}/auteurFromTitle")
    @DELETE
    public String deleteAllAuteur(@PathParam("titre") String titreLivre) {
        return aedao.delete(titreLivre);
    }
    
    @Path("{id}/auteur/{idAuteur}")
    @DELETE
    public String deleteAuteur(@PathParam("id") int idLivre, @PathParam("idAuteur") int idAuteur) {
        return deleteAuteur(ldao.read(idLivre).getTitre(), idAuteur);
    }
    
    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @DELETE
    public String deleteAuteur(@PathParam("titre") String titreLivre, @PathParam("idAuteur") int idAuteur) {
        return aedao.delete(titreLivre, idAuteur);
    }
}
