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
    public List<Auteur> getAll() {       
        return adao.read();
    }
  
    @Path("{id}")
    @GET
    public Auteur getAuteur(@PathParam("id") int id) {       
        return adao.read(id);
    }

    @POST
    public String addAuteur(Auteur auteur) {
        return adao.create(auteur);
    }

    @Path("{id}")
    @PUT
    public String updateAuteur(@PathParam("id") int id, Auteur auteur) {
        return adao.update(id, auteur);
    }

    @Path("{id}")
    @DELETE
    public String deleteAuteur(@PathParam("id") int id) {
        return adao.delete(id);  
    }
    
    @Path("{id}/livre")
    @GET
    public List<Livre> listLivre(@PathParam("id") int idAuteur) {
        return listLivre(adao.read(idAuteur).getNom());
    }  
    
    @Path("{nom}/livreFromName")
    @GET
    public List<Livre> listLivre(@PathParam("nom") String nomAuteur) {
        ArrayList<Livre> a = new ArrayList<>();
        
        for(AEcrit ae : aedao.read(nomAuteur)) {
            a.add(ldao.read(ae.getIdLivre()));
        }
        
        return a;
    }  
    
    @Path("{id}/livre")
    @POST
    public String addLivre(@PathParam("id") int idAuteur, AEcrit aEcrit) {
        return addLivre(adao.read(idAuteur).getNom(), aEcrit);
    }
    
    @Path("{nom}/livreFromName")
    @POST
    public String addLivre(@PathParam("nom") String nomAuteur, AEcrit aEcrit) {
        aEcrit.setAuteurNom(nomAuteur);
        return aedao.create(aEcrit);
    }
    
    @Path("{id}/livre/{idLivre}")
    @PUT
    public String updateLivre(@PathParam("id") int idAuteur, @PathParam("idLivre") int idLivre, AEcrit newAEcrit) {
        return updateLivre(adao.read(idAuteur).getNom(), idLivre, newAEcrit);
    }
    
    @Path("{nom}/livreFromName/{idLivre}/{rang}")
    @PUT
    public String updateLivre(@PathParam("nom") String nomAuteur, @PathParam("idLivre") int idLivre, AEcrit newAEcrit) {
        return aedao.update(nomAuteur, idLivre, newAEcrit.getIdLivre());
    }
    
    @Path("{id}/livre")
    @DELETE
    public String deleteAllLivre(@PathParam("id") int idAuteur) {
        return deleteAllLivre(adao.read(idAuteur).getNom());
    }
    
    @Path("{nom}/livreFromName")
    @DELETE
    public String deleteAllLivre(@PathParam("nom") String nomAuteur) {
        return aedao.delete(nomAuteur);
    }
    
    @Path("{id}/livre/{idLivre}")
    @DELETE
    public String deleteLivre(@PathParam("id") int idAuteur, @PathParam("idLivre") int idLivre) {
        return deleteLivre(adao.read(idAuteur).getNom(), idLivre);
    }
    
    @Path("{nom}/livreFromName/{idLivre}")
    @DELETE
    public String deleteLivre(@PathParam("nom") String nomAuteur, @PathParam("idLivre") int idLivre) {
        return aedao.delete(nomAuteur, idLivre);
    }
}
