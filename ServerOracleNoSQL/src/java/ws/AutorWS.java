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
import daos.AutorDAO;
import daos.ArticleDAO;
import entities.AEcrit;
import entities.Autor;
import entities.Article;
import entities.Universite;

@Path("auteur")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AutorWS {
    
  private AutorDAO adao = new AutorDAO();
  private ArticleDAO ldao = new ArticleDAO();
  private AEcritDAO aedao = new AEcritDAO();

    public AutorWS() {
    }
    
    @GET
    public RestResponse<Autor> getAll() {       
        List<Autor> auteurs = adao.read();
        RestResponse<Autor> resp = new RestResponse<>("200", auteurs);
        return resp;
    }
  
    @Path("{id}")
    @GET
    public RestResponse<Autor> getAuteur(@PathParam("id") int id) {       
        Autor auteur = adao.read(id);
        String status = (auteur != null ? "200" : "400");
        
        RestResponse<Autor> resp = new RestResponse<>(status);
        resp.addObjectList(auteur);
        return resp;
    }

    @POST
    public RestResponse<Autor> addAuteur(Autor auteur) {
        String status = adao.create(auteur);
        
        RestResponse<Autor> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Autor> updateAuteur(@PathParam("id") int id, Autor auteur) {
        String status = adao.update(id, auteur);
        
        RestResponse<Autor> resp = new RestResponse<>(status);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Autor> deleteAuteur(@PathParam("id") int id) {
        String status = adao.delete(id);  
        
        RestResponse<Autor> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article")
    @GET
    public RestResponse<Article> listArticle(@PathParam("id") int idAuteur) throws ParseException {
        Autor a = adao.read(idAuteur);
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
    public RestResponse<AEcrit> addArticle(@PathParam("id") int idAuteur, AEcrit aEcrit) {
        Autor a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = addArticle(adao.read(idAuteur).getNom(), aEcrit);
        
        return resp;
    }
    
    @Path("{nom}/articleFromName")
    @POST
    public RestResponse<AEcrit> addArticle(@PathParam("nom") String nomAuteur, AEcrit aEcrit) {
        aEcrit.setAuteurNom(nomAuteur);
        String status = aedao.create(aEcrit);
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article/{idArticle}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("id") int idAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) {
        Autor a = adao.read(idAuteur);
        RestResponse<AEcrit> resp = new RestResponse<>("400");
        
        if(a != null) resp = updateArticle(a.getNom(), idArticle, newAEcrit);
        
        return resp;
        
    }
    
    @Path("{nom}/articleFromName/{idArticle}/{rang}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("nom") String nomAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) {
        String status = aedao.update(nomAuteur, idArticle, newAEcrit.getIdArticle());
        
        RestResponse<AEcrit> resp = new RestResponse<>(status);
        return resp;
    }
    
    @Path("{id}/article")
    @DELETE
    public RestResponse<AEcrit> deleteAllArticle(@PathParam("id") int idAuteur) {
        Autor a = adao.read(idAuteur);
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
        Autor a = adao.read(idAuteur);
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
    
    @Path("{nom}/universityFromName")
    @GET
    public RestResponse<Universite> getUniversities(@PathParam("nom") String nomAuteur) {
        return null;//TODO
    }
    
    @Path("{nom}/universityFromName")
    @POST
    public RestResponse<Universite> addUniversities(@PathParam("nom") String nomAuteur) {
        return null;//TODO
    }
    
    @Path("{nom}/universityFromName/{idUniversity}")
    @PUT
    public RestResponse<Universite> updateUniversities(@PathParam("nom") String nomAuteur, @PathParam("idUniversity") int idUniversity) {
        return null;//TODO
    }
    
    @Path("{nom}/universityFromName/{idUniversity}")
    @DELETE
    public RestResponse<Universite> deleteUniversities(@PathParam("nom") String nomAuteur, @PathParam("idUniversity") int idUniversity) {
        return null;//TODO
    }
}
