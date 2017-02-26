package ws;

import daos.AEcritDAO;
import daos.ArticleDAO;
import daos.AuthorDAO;
import daos.RattacheDAO;
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
import entities.AEcrit;
import entities.Article;
import entities.Author;
import entities.Rattache;
import entities.Universite;
import java.text.ParseException;

@Path("university")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UniversityWS {

    private final UniversiteDAO udao = new UniversiteDAO();
    private final RattacheDAO rdao = new RattacheDAO();
    private final AuthorDAO adao = new AuthorDAO();
    private final ArticleDAO ldao = new ArticleDAO();
    private final AEcritDAO aedao = new AEcritDAO();

    public UniversityWS() {
    }

    @GET
    public RestResponse<Universite> getAll() {
        List<Universite> universites = udao.read();
        RestResponse<Universite> resp = new RestResponse<>(0, universites);
        return resp;
    }

    @Path("{id}")
    @GET
    public RestResponse<Universite> getUniversite(@PathParam("id") int id) {
        Universite universite = udao.read(id);
        int status = (universite != null ? 0 : 155);

        RestResponse<Universite> resp = new RestResponse<>(status);
        resp.addObjectList(universite);
        return resp;
    }

    @POST
    public RestResponse<Universite> addUniversite(Universite universite) {
        int status = udao.create(universite);

        RestResponse<Universite> resp = new RestResponse<>(status, (status != 0 ? "409" : "201"));
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Universite> updateUniversite(@PathParam("id") int id, Universite universite) {
        int status = udao.update(id, universite);

        RestResponse<Universite> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Universite> deleteUniversite(@PathParam("id") int id) {
        int status = udao.delete(id);

        RestResponse<Universite> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/auteurs")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("id") int id) {
        Universite u = udao.read(id);
        RestResponse<Author> resp = new RestResponse<>(155, "204");

        if (u != null) {
            resp = listAuteur(u.getNom());
        }

        return resp;
    }

    @Path("{nom}/auteursFromName")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("nom") String name) {

        int status = 0;

        RestResponse<Author> resp = new RestResponse<>(status);

        for (Rattache r : rdao.read(Universite.MAJOR_KEY, name)) {
            Author auteur = adao.read(r.getIdAuteur());
            status = (status != 150 && auteur != null ? 0 : 150);

            if (auteur != null) {
                resp.addObjectList(auteur);
            }

            resp.setResponseCode(RestResponse.getStatus(status));
            resp.setCode(status);
            resp.setMessage(RestResponse.getMessage(status));
        }

        return resp;
    }

    @Path("{id}/articles")
    @GET
    public RestResponse<Article> listArticle(@PathParam("id") int id) throws ParseException {
        Universite u = udao.read(id);
        RestResponse<Article> resp = new RestResponse<>(155, "204");

        if (u != null) {
            resp = listArticle(u.getNom());
        }

        return resp;
    }

    @Path("{nom}/articlesFromName")
    @GET
    public RestResponse<Article> listArticle(@PathParam("nom") String name) throws ParseException {

        int status = 0;

        RestResponse<Article> resp = new RestResponse<>(status);

        for (Rattache r : rdao.read(Universite.MAJOR_KEY, name)) {
            Author auteur = adao.read(r.getIdAuteur());
            if (auteur != null) {
                for (AEcrit ae : aedao.read(auteur.getNom())) {
                    status = (status != 151 ? 0 : 151);

                    resp.addObjectList(ldao.read(ae.getIdArticle()));

                    resp.setResponseCode(RestResponse.getStatus(status));
                    resp.setCode(status);
                    resp.setMessage(RestResponse.getMessage(status));
                }
            } else {
                resp = new RestResponse<>(150, "204");
            }
        }

        return resp;
    }
}
