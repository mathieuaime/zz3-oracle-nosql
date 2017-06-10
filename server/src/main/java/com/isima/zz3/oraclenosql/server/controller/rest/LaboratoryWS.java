package com.isima.zz3.oraclenosql.server.controller.rest;

import daos.AEcritDAO;
import daos.ArticleDAO;
import daos.AuthorDAO;
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
import daos.RattacheDAO;
import com.isima.zz3.oraclenosql.server.relation.AEcrit;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.entity.Author;
import com.isima.zz3.oraclenosql.server.entity.Laboratory;
import com.isima.zz3.oraclenosql.server.relation.Rattache;
import java.text.ParseException;

@Path("laboratory")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LaboratoryWS {

    private final LaboratoireDAO labodao = new LaboratoireDAO();
    private final RattacheDAO rdao = new RattacheDAO();
    private final AuthorDAO adao = new AuthorDAO();
    private final ArticleDAO ldao = new ArticleDAO();
    private final AEcritDAO aedao = new AEcritDAO();

    public LaboratoryWS() {
    }

    @GET
    public RestResponse<Laboratory> getAll() {
        List<Laboratory> laboratoires = labodao.read();
        RestResponse<Laboratory> resp = new RestResponse<>(0, laboratoires);
        return resp;
    }

    @Path("{id}")
    @GET
    public RestResponse<Laboratory> getLaboratoire(@PathParam("id") int id) {
        Laboratory laboratoire = labodao.read(id);
        int status = (laboratoire != null ? 0 : 154);

        RestResponse<Laboratory> resp = new RestResponse<>(status);
        resp.addObjectList(laboratoire);
        return resp;
    }

    @POST
    public RestResponse<Laboratory> addLaboratoire(Laboratory laboratoire) {
        int status = labodao.create(laboratoire);

        RestResponse<Laboratory> resp = new RestResponse<>(status, (status != 0 ? "409" : "201"));
        resp.addObjectList(laboratoire);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Laboratory> updateLaboratoire(@PathParam("id") int id, Laboratory laboratoire) {
        int status = labodao.update(id, laboratoire);

        RestResponse<Laboratory> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        resp.addObjectList(laboratoire);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Laboratory> deleteLaboratoire(@PathParam("id") int id) {
        int status = labodao.delete(id);

        RestResponse<Laboratory> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/auteurs")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("id") int id) {
        Laboratory l = labodao.read(id);
        RestResponse<Author> resp = new RestResponse<>(154, "204");

        if (l != null) {
            resp = listAuteur(l.getNom());
        }

        return resp;
    }

    @Path("{nom}/auteursFromName")
    @GET
    public RestResponse<Author> listAuteur(@PathParam("nom") String name) {

        int status = 0;

        RestResponse<Author> resp = new RestResponse<>(status);

        for (Rattache r : rdao.read(Laboratory.MAJOR_KEY, name)) {
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
        Laboratory l = labodao.read(id);
        RestResponse<Article> resp = new RestResponse<>(154, "204");

        if (l != null) {
            resp = listArticle(l.getNom());
        }

        return resp;
    }

    @Path("{nom}/articlesFromName")
    @GET
    public RestResponse<Article> listArticle(@PathParam("nom") String name) throws ParseException {

        int status = 0;

        RestResponse<Article> resp = new RestResponse<>(status);

        for (Rattache r : rdao.read(Laboratory.MAJOR_KEY, name)) {
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
