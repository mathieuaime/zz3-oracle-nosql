package ws;
import java.text.ParseException;
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
import daos.AEcritDAO;
import daos.AuthorDAO;
import daos.ArticleDAO;
import daos.EstRattacheDAO;
import daos.LaboratoireDAO;
import daos.RattacheDAO;
import daos.UniversiteDAO;
import entities.AEcrit;
import entities.Author;
import entities.Article;
import entities.EstRattache;
import entities.Laboratoire;
import entities.Rattache;
import entities.Universite;

@Path("auteur")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorWS {
    
  private AuthorDAO adao = new AuthorDAO();
  private ArticleDAO ldao = new ArticleDAO();
  private AEcritDAO aedao = new AEcritDAO();
  private RattacheDAO rdao = new RattacheDAO();
  private EstRattacheDAO erdao = new EstRattacheDAO();
  private UniversiteDAO udao = new UniversiteDAO();
  private LaboratoireDAO labodao = new LaboratoireDAO();

    public AuthorWS() {
    }
    
    @GET
    public RestResponse<Author> getAll() {       
        List<Author> auteurs = adao.read();
        RestResponse<Author> resp = new RestResponse<>("200", auteurs);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Author> getAuteur(@PathParam("id") int id) {       
        Author auteur = adao.read(id);
        String status = (auteur != null ? "200" : "400");
        
        RestResponse<Author> resp = new RestResponse<>(status);
        resp.addObjectList(auteur);
        return resp;
    }

    @POST
    public RestResponse<Author> addAuteur(Author auteur) {
        String status = adao.create(auteur);
        
        RestResponse<Author> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Author> updateAuteur(@PathParam("id") int id, Author auteur) {
        String status = adao.update(id, auteur);
        
        RestResponse<Author> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Author> deleteAuteur(@PathParam("id") int id) {
        String status = adao.delete(id);  
        
        RestResponse<Author> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article")
    @GET
    public RestResponse<Article> listArticle(@PathParam("id") int idAuteur) throws ParseException {
        Author a = adao.read(idAuteur);
        RestResponse<Article> resp = new RestResponse<>("400");
        
        if(a != null) resp = listArticle(adao.read(idAuteur).getNom());
        
        return resp;
    }  
    
    @Path("{nom}/articleFromName")
    @GET
    public RestResponse<Article> listArticle(@PathParam("nom") String nomAuteur) throws ParseException {
        
        String status = "200";
       
        RestResponse<Article> resp = new RestResponse<>(status);
        
        for(AEcrit ae : aedao.read(nomAuteur)) {
            Article article = ldao.read(ae.getIdArticle());
            status = (!status.equals("401") && article != null ? "200" : "401");
            if (article != null) resp.addObjectList(article);
            resp.setStatus(status);
            resp.setMessage(RestResponse.getMessageError(status));
        }
        
        return resp;
    }  
    
    @Path("{id}/article")
    @POST
    public RestResponse<AEcrit> addArticle(@PathParam("id") int idAuteur, AEcrit aEcrit) throws ParseException {
        Author a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = addArticle(adao.read(idAuteur).getNom(), aEcrit);
        
        return resp;
    }
    
    @Path("{nom}/articleFromName")
    @POST
    public RestResponse<AEcrit> addArticle(@PathParam("nom") String nomAuteur, AEcrit aEcrit) throws ParseException {
        aEcrit.setAuteurNom(nomAuteur);
        String status = aedao.create(aEcrit);
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article/{idArticle}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("id") int idAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) throws ParseException {
        Author a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = updateArticle(a.getNom(), idArticle, newAEcrit);
        
        return resp;
        
    }
    
    @Path("{nom}/articleFromName/{idArticle}/{rang}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("nom") String nomAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) throws ParseException {
        String status = aedao.update(nomAuteur, idArticle, newAEcrit.getIdArticle());
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article")
    @DELETE
    public RestResponse<AEcrit> deleteAllArticle(@PathParam("id") int idAuteur) {
        Author a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = deleteAllArticle(adao.read(idAuteur).getNom());
        
        return resp;
    }
    
    @Path("{nom}/articleFromName")
    @DELETE
    public RestResponse<AEcrit> deleteAllArticle(@PathParam("nom") String nomAuteur) {
        String status = aedao.delete(nomAuteur);
      
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article/{idArticle}")
    @DELETE
    public RestResponse<AEcrit> deleteArticle(@PathParam("id") int idAuteur, @PathParam("idArticle") int idArticle) {
        Author a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = deleteArticle(adao.read(idAuteur).getNom(), idArticle);
        
        return resp;
    }
    
    @Path("{nom}/articleFromName/{idArticle}")
    @DELETE
    public RestResponse<AEcrit> deleteArticle(@PathParam("nom") String nomAuteur, @PathParam("idArticle") int idArticle) {
        String status = aedao.delete(nomAuteur, idArticle);
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/university")
    @GET
    public RestResponse<Universite> listUniversities(@PathParam("id") int idAuteur) {
        Author a = adao.read(idAuteur);
      
        RestResponse<Universite> resp = new RestResponse<>("400");
        
        if(a != null) resp = listUniversities(adao.read(idAuteur).getNom());
        
        return resp;
    }
    
    @Path("{nom}/universityFromName")
    @GET
    public RestResponse<Universite> listUniversities(@PathParam("nom") String nomAuteur) {
        String status = "200";
       
        RestResponse<Universite> resp = new RestResponse<>(status);
        
        for(EstRattache r : erdao.read(nomAuteur, Universite.MAJOR_KEY)) {
            Universite universite = udao.read(r.getValue());
            status = (!status.equals("400") && universite != null ? "200" : "400");
            if (universite != null) resp.addObjectList(universite);
        }
        
        resp.setStatus(status);
        resp.setMessage(RestResponse.getMessageError(status));
        
        return resp;
    }
    @Path("{id}/university")
    @POST
    public RestResponse<Rattache> addUniversities(@PathParam("id") int idAuteur, EstRattache estRattache) {
        Author a = adao.read(idAuteur);
        String status = "400";
      
        RestResponse<Rattache> resp = new RestResponse<>(status);
        
        if(a != null) {
            estRattache.setNomAuteur(a.getNom());
            
            Universite univ = udao.read(estRattache.getValue());
            
            if(univ != null) {
                resp = new RestResponse<>("200");
                
                status = erdao.create(estRattache);
                if(!status.equals("200")) resp = new RestResponse<>(status);
                
                status = rdao.create(Universite.MAJOR_KEY, univ.getNom(), idAuteur);
                if(!status.equals("200")) resp = new RestResponse<>(status);
            } else {
                resp = new RestResponse<>("405");
            }
        }
        
        return resp;
    }
    
    @Path("{id}/universityFromName/{idUniversity}/{rank}")
    @PUT
    public RestResponse<Universite> updateUniversities(@PathParam("nom") int idAuteur, @PathParam("idUniversity") int idUniversity, @PathParam("rank") int rank, EstRattache newEstRattache) {
        Author auteur = adao.read(idAuteur);
        String status = "400";
        RestResponse<Universite> resp = new RestResponse<>(status);
        if(auteur != null) {
            Universite oldUniv = udao.read(idUniversity);
            if (oldUniv != null) {
                status = erdao.update(auteur.getNom(), Universite.MAJOR_KEY, rank, newEstRattache);
                if(!status.equals("200")) resp = new RestResponse<>(status);

                Universite univ = udao.read(newEstRattache.getValue());
                if (univ != null) {
                    status = rdao.delete(Universite.MAJOR_KEY, oldUniv.getNom(), 1);
                    resp = new RestResponse<>(status);
                    if (!status.equals("200")) {
                        status = rdao.update(Universite.MAJOR_KEY, univ.getNom(), idAuteur, 1);
                        if(!status.equals("200")) resp = new RestResponse<>(status);
                    }
                } else {
                    status = "405";
                    resp = new RestResponse<>(status);
                }
            } else {
                status = "405";
                resp = new RestResponse<>(status);
            }
        }
        
        return resp;
        
    }
    
    @Path("{id}/universityFromName/{idUniversity}/{rank}")
    @DELETE
    public RestResponse<Universite> deleteUniversities(@PathParam("nom") int idAuteur, @PathParam("idUniversity") int idUniversity, @PathParam("rank") int rank) {
        Author auteur = adao.read(idAuteur);
        String status = "400";
        RestResponse<Universite> resp = new RestResponse<>(status);
        if(auteur != null) {
            Universite univ = udao.read(idUniversity);
            if (univ != null) {
                status = erdao.delete(auteur.getNom(), Universite.MAJOR_KEY, rank);
                resp = new RestResponse<>(status);
                
                status = rdao.delete(Universite.MAJOR_KEY, univ.getNom(), 1);
                if(!status.equals("200")) resp = new RestResponse<>(status);
            } else {
                status = "405";
                resp = new RestResponse<>(status);
            }
        }
        
        return resp;
    }
    
    @Path("{id}/laboratoire")
    @GET
    public RestResponse<Laboratoire> listLaboratories(@PathParam("id") int idAuteur) {
        Author a = adao.read(idAuteur);
      
        RestResponse<Laboratoire> resp = new RestResponse<>("400");
        
        if(a != null) resp = listLaboratories(adao.read(idAuteur).getNom());
        
        return resp;
    }
    
    @Path("{nom}/laboratoireFromName")
    @GET
    public RestResponse<Laboratoire> listLaboratories(@PathParam("nom") String nomAuteur) {
        String status = "200";
       
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        
        for(EstRattache r : erdao.read(nomAuteur, Laboratoire.MAJOR_KEY)) {
            Laboratoire laboratoire = labodao.read(r.getValue());
            status = (!status.equals("400") && laboratoire != null ? "200" : "400");
            if (laboratoire != null) resp.addObjectList(laboratoire);
        }
        
        resp.setStatus(status);
        resp.setMessage(RestResponse.getMessageError(status));
        
        return resp;
    }
    @Path("{id}/laboratoire")
    @POST
    public RestResponse<Rattache> addLaboratories(@PathParam("id") int idAuteur, EstRattache estRattache) {
        Author a = adao.read(idAuteur);
        String status = "400";
      
        RestResponse<Rattache> resp = new RestResponse<>(status);
        
        if(a != null) {
            estRattache.setNomAuteur(a.getNom());
            
            Laboratoire labo = labodao.read(estRattache.getValue());
            
            if(labo != null) {
                resp = new RestResponse<>("200");
                
                status = erdao.create(estRattache);
                if(!status.equals("200")) resp = new RestResponse<>(status);
                
                status = rdao.create(Laboratoire.MAJOR_KEY, labo.getNom(), idAuteur);
                if(!status.equals("200")) resp = new RestResponse<>(status);
            } else {
                resp = new RestResponse<>("404");
            }
        }
        
        return resp;
    }
    
    @Path("{id}/laboratoireFromName/{idLaboratory}/{rank}")
    @PUT
    public RestResponse<Laboratoire> updateLaboratories(@PathParam("nom") int idAuteur, @PathParam("idLaboratory") int idLaboratory, @PathParam("rank") int rank, EstRattache newEstRattache) {
        Author auteur = adao.read(idAuteur);
        String status = "400";
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        if(auteur != null) {
            Laboratoire oldLabo = labodao.read(idLaboratory);
            if (oldLabo != null) {
                status = erdao.update(auteur.getNom(), Laboratoire.MAJOR_KEY, rank, newEstRattache);
                if(!status.equals("200")) resp = new RestResponse<>(status);

                Laboratoire labo = labodao.read(newEstRattache.getValue());
                if (labo != null) {
                    status = rdao.delete(Laboratoire.MAJOR_KEY, oldLabo.getNom(), 1);
                    resp = new RestResponse<>(status);
                    if (!status.equals("200")) {
                        status = rdao.update(Laboratoire.MAJOR_KEY, labo.getNom(), idAuteur, 1);
                        if(!status.equals("200")) resp = new RestResponse<>(status);
                    }
                } else {
                    status = "404";
                    resp = new RestResponse<>(status);
                }
            } else {
                status = "404";
                resp = new RestResponse<>(status);
            }
        }
        
        return resp;
        
    }
    
    @Path("{id}/laboratoireFromName/{idLaboratory}/{rank}")
    @DELETE
    public RestResponse<Laboratoire> deleteLaboratories(@PathParam("nom") int idAuteur, @PathParam("idLaboratory") int idLaboratory, @PathParam("rank") int rank) {
        Author auteur = adao.read(idAuteur);
        String status = "400";
        RestResponse<Laboratoire> resp = new RestResponse<>(status);
        if(auteur != null) {
            Laboratoire labo = labodao.read(idLaboratory);
            if (labo != null) {
                status = erdao.delete(auteur.getNom(), Laboratoire.MAJOR_KEY, rank);
                resp = new RestResponse<>(status);
                
                status = rdao.delete(Laboratoire.MAJOR_KEY, labo.getNom(), 1);
                if(!status.equals("200")) resp = new RestResponse<>(status);
            } else {
                status = "404";
                resp = new RestResponse<>(status);
            }
        }
        
        return resp;
    }
}
