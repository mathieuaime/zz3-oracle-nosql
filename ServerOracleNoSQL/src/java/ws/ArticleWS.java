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
import daos.AEteEcritDAO;
import daos.ArticleDAO;
import daos.AuthorDAO;
import daos.HasKeywordDAO;
import daos.KeywordDAO;
import entities.AEteEcrit;
import entities.Author;
import entities.Article;
import entities.HasKeyword;
import entities.Keyword;

@Path("article")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArticleWS {

    private final ArticleDAO ldao = new ArticleDAO();
    private final AuthorDAO adao = new AuthorDAO();
    private final AEteEcritDAO aedao = new AEteEcritDAO();
    private final HasKeywordDAO hkdao = new HasKeywordDAO();
    private final KeywordDAO kdao = new KeywordDAO();

    public ArticleWS() {
    }

    @GET
    public RestResponse<Article> getAll() throws ParseException {
        List<Article> articles = ldao.read();

        RestResponse<Article> resp = new RestResponse<>(0, articles);
        return resp;
    }

    @Path("{id}")
    @GET
    public RestResponse<Article> getArticle(@PathParam("id") int id) throws ParseException {
        Article article = ldao.read(id);
        int code = (article != null ? 0 : 151);

        RestResponse<Article> resp = new RestResponse<>(code);
        resp.addObjectList(article);
        return resp;
    }

    @POST
    public RestResponse<Article> addArticle(Article article) {
        int status = ldao.create(article);

        RestResponse<Article> resp = new RestResponse<>(status, (status != 0 ? "409" : "201"));
        resp.addObjectList(article);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Article> updateArticle(@PathParam("id") int id, Article article) throws ParseException {
        int status = ldao.update(id, article);

        RestResponse<Article> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        resp.addObjectList(article);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Article> deleteArticle(@PathParam("id") int id) throws ParseException {
        int status = ldao.delete(id);

        RestResponse<Article> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/auteur")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("id") int idArticle) throws ParseException {
        RestResponse<Author> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = listAuteur(ldao.read(idArticle).getTitre());
        }

        return resp;
    }

    @Path("{titre}/auteurFromTitle")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("titre") String titreArticle) {

        int status = 0;

        RestResponse<Author> resp = new RestResponse<>(status);

        for (AEteEcrit aee : aedao.read(titreArticle)) {
            Author auteur = adao.read(aee.getIdAuteur());
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

    @Path("{id}/auteur")
    @POST
    public RestResponse<AEteEcrit> addAuteur(@PathParam("id") int idArticle, AEteEcrit aEteEcrit) throws ParseException {
        RestResponse<AEteEcrit> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = addAuteur(ldao.read(idArticle).getTitre(), aEteEcrit);
        }

        return resp;
    }

    @Path("{titre}/auteurFromTitle")
    @POST
    public RestResponse<AEteEcrit> addAuteur(@PathParam("titre") String titreArticle, AEteEcrit aEteEcrit) {
        aEteEcrit.setArticleTitre(titreArticle);
        int status = aedao.create(aEteEcrit);
        RestResponse<AEteEcrit> resp = new RestResponse<>(status, (status >= 150 ? "204" : (status >= 100 ? "409" : "201")));
        return resp;
    }

    @Path("{id}/auteur/{idAuteur}")
    @PUT
    public RestResponse<AEteEcrit> updateAuteur(@PathParam("id") int idArticle, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) throws ParseException {
        RestResponse<AEteEcrit> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = updateAuteur(ldao.read(idArticle).getTitre(), idAuteur, newAEteEcrit);
        }

        return resp;
    }

    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @PUT
    public RestResponse<AEteEcrit> updateAuteur(@PathParam("titre") String titreArticle, @PathParam("idAuteur") int idAuteur, AEteEcrit newAEteEcrit) {
        int status = aedao.update(titreArticle, idAuteur, newAEteEcrit.getIdAuteur());

        RestResponse<AEteEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/auteur")
    @DELETE
    public RestResponse<AEteEcrit> deleteAllAuteur(@PathParam("id") int idArticle) throws ParseException {
        RestResponse<AEteEcrit> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = deleteAllAuteur(ldao.read(idArticle).getTitre());
        }

        return resp;
    }

    @Path("{titre}/auteurFromTitle")
    @DELETE
    public RestResponse<AEteEcrit> deleteAllAuteur(@PathParam("titre") String titreArticle) {
        int status = aedao.delete(titreArticle);

        RestResponse<AEteEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/auteur/{idAuteur}")
    @DELETE
    public RestResponse<AEteEcrit> deleteAuteur(@PathParam("id") int idArticle, @PathParam("idAuteur") int idAuteur) throws ParseException {
        RestResponse<AEteEcrit> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = deleteAuteur(ldao.read(idArticle).getTitre(), idAuteur);
        }

        return resp;
    }

    @Path("{titre}/auteurFromTitle/{idAuteur}")
    @DELETE
    public RestResponse<AEteEcrit> deleteAuteur(@PathParam("titre") String titreArticle, @PathParam("idAuteur") int idAuteur) {
        int status = aedao.delete(titreArticle, idAuteur);

        RestResponse<AEteEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/keyword")
    @GET
    public RestResponse<String> listKeywords(@PathParam("id") int idArticle) throws ParseException {
        RestResponse<String> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = listKeywords(ldao.read(idArticle).getTitre());
        }

        return resp;
    }

    @Path("{titre}/keywordFromTitle")
    @GET
    public RestResponse<String> listKeywords(@PathParam("titre") String titreArticle) {
        RestResponse<String> resp = new RestResponse<>(0);

        for (HasKeyword hk : hkdao.read(titreArticle)) {
            String keyword = hk.getKeyword();
            if (keyword != null) {
                resp.addObjectList(keyword);
            }
        }

        return resp;
    }

    @Path("{id}/keyword")
    @POST
    public RestResponse<Keyword> addKeywords(@PathParam("id") int idArticle, Keyword keyword) throws ParseException {
        RestResponse<Keyword> resp = new RestResponse<>(151, "204");

        if (ldao.exist(idArticle)) {
            resp = new RestResponse<>(0, "201");

            int status = kdao.create(keyword.getKeyword(), idArticle);
            if (status != 0) {
                resp = new RestResponse<>(status, "409");
            }

            status = hkdao.create(ldao.read(idArticle).getTitre(), keyword.getKeyword());
            if (status != 0) {
                resp = new RestResponse<>(status, "409");
            }
        }

        return resp;
    }

    @Path("{id}/keyword/{keyword}/{rank}")
    @PUT
    public RestResponse<Keyword> updateKeywords(@PathParam("id") int idArticle, @PathParam("idKeyword") String keyword, @PathParam("rank") int rank, Keyword newKeyword) throws ParseException {
        int status = 151;

        if (ldao.exist(idArticle)) {
            Keyword keyw = kdao.read(keyword, rank);
            if (keyw != null) {
                status = kdao.update(keyword, idArticle, newKeyword.getIdArticle());
                int update = hkdao.update(ldao.read(idArticle).getTitre(), keyword, newKeyword.getKeyword());
                if (status != 0) {
                    status = update;
                }
            } else {
                status = 157;
            }
        }

        return new RestResponse<>(status, (status != 0 ? "204" : "200"));
    }

    @Path("{id}/keyword/{keyword}/{rank}")
    @DELETE
    public RestResponse<Keyword> deleteKeywords(@PathParam("id") int idArticle, @PathParam("keyword") String keyword, @PathParam("rank") int rank) throws ParseException {
        int status = 151;

        if (ldao.exist(idArticle)) {
            Keyword keyw = kdao.read(keyword, rank);
            if (keyw != null) {
                status = kdao.delete(keyword);
                for (HasKeyword hk : hkdao.read(ldao.read(idArticle).getTitre())) {
                    int delete = hkdao.delete(hk.getTitreArticle(), hk.getRank());
                    if (status != 0) {
                        status = delete;
                    }
                }
            } else {
                status = 157;
            }
        }

        return new RestResponse<>(status, (status != 0 ? "204" : "200"));
    }
}
