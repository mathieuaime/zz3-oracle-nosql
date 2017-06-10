package com.isima.zz3.oraclenosql.server.controller.rest;

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
import daos.HasKeywordDAO;
import daos.LaboratoireDAO;
import daos.RattacheDAO;
import daos.UniversiteDAO;
import com.isima.zz3.oraclenosql.server.relation.AEcrit;
import com.isima.zz3.oraclenosql.server.entity.Author;
import com.isima.zz3.oraclenosql.server.entity.Article;
import com.isima.zz3.oraclenosql.server.relation.EstRattache;
import com.isima.zz3.oraclenosql.server.relation.HasKeyword;
import com.isima.zz3.oraclenosql.server.entity.Laboratory;
import com.isima.zz3.oraclenosql.server.relation.Rattache;
import com.isima.zz3.oraclenosql.server.entity.University;

@Path("auteur")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthorWS {

    private final AuthorDAO adao = new AuthorDAO();
    private final ArticleDAO ldao = new ArticleDAO();
    private final AEcritDAO aedao = new AEcritDAO();
    private final RattacheDAO rdao = new RattacheDAO();
    private final EstRattacheDAO erdao = new EstRattacheDAO();
    private final UniversiteDAO udao = new UniversiteDAO();
    private final LaboratoireDAO labodao = new LaboratoireDAO();
    private final HasKeywordDAO hkdao = new HasKeywordDAO();

    public AuthorWS() {
    }

    @GET
    public RestResponse<Author> getAll() {
        List<Author> auteurs = adao.read();
        RestResponse<Author> resp = new RestResponse<>(0, auteurs);
        return resp;
    }

    @Path("{id}")
    @GET
    public RestResponse<Author> getAuteur(@PathParam("id") int id) {
        Author auteur = adao.read(id);
        int status = (auteur != null ? 0 : 150);

        RestResponse<Author> resp = new RestResponse<>(status);
        resp.addObjectList(auteur);
        return resp;
    }

    @POST
    public RestResponse<Author> addAuteur(Author auteur) {
        int status = adao.create(auteur);

        RestResponse<Author> resp = new RestResponse<>(status, (status != 0 ? "409" : "201"));
        resp.addObjectList(auteur);
        return resp;
    }

    @Path("{id}")
    @PUT
    public RestResponse<Author> updateAuteur(@PathParam("id") int id, Author auteur) {
        int status = adao.update(id, auteur);

        RestResponse<Author> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        resp.addObjectList(auteur);
        return resp;
    }

    @Path("{id}")
    @DELETE
    public RestResponse<Author> deleteAuteur(@PathParam("id") int id) {
        int status = adao.delete(id);

        RestResponse<Author> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/article")
    @GET
    public RestResponse<Article> listArticle(@PathParam("id") int idAuteur) throws ParseException {
        RestResponse<Article> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = listArticle(adao.read(idAuteur).getNom());
        }

        return resp;
    }

    @Path("{nom}/articleFromName")
    @GET
    public RestResponse<Article> listArticle(@PathParam("nom") String nomAuteur) throws ParseException {

        int status = 0;

        RestResponse<Article> resp = new RestResponse<>(status);

        for (AEcrit ae : aedao.read(nomAuteur)) {
            Article article = ldao.read(ae.getIdArticle());
            status = (status != 151 && article != null ? 0 : 151);
            if (article != null) {
                resp.addObjectList(article);
            }
            resp.setResponseCode(RestResponse.getStatus(status));
            resp.setCode(status);
            resp.setMessage(RestResponse.getMessage(status));
        }

        return resp;
    }

    @Path("{id}/article")
    @POST
    public RestResponse<AEcrit> addArticle(@PathParam("id") int idAuteur, AEcrit aEcrit) throws ParseException {
        RestResponse<AEcrit> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = addArticle(adao.read(idAuteur).getNom(), aEcrit);
        }

        return resp;
    }

    @Path("{nom}/articleFromName")
    @POST
    public RestResponse<AEcrit> addArticle(@PathParam("nom") String nomAuteur, AEcrit aEcrit) throws ParseException {
        int status = aedao.create(nomAuteur, aEcrit.getIdArticle());

        RestResponse<AEcrit> resp = new RestResponse<>(status, (status >= 150 ? "204" : (status >= 100 ? "409" : "201")));
        return resp;
    }

    @Path("{id}/article/{idArticle}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("id") int idAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) throws ParseException {
        RestResponse<AEcrit> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = updateArticle(adao.read(idAuteur).getNom(), idArticle, newAEcrit);
        }

        return resp;

    }

    @Path("{nom}/articleFromName/{idArticle}")
    @PUT
    public RestResponse<AEcrit> updateArticle(@PathParam("nom") String nomAuteur, @PathParam("idArticle") int idArticle, AEcrit newAEcrit) throws ParseException {
        int status = aedao.update(nomAuteur, idArticle, newAEcrit.getIdArticle());

        RestResponse<AEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/article")
    @DELETE
    public RestResponse<AEcrit> deleteAllArticle(@PathParam("id") int idAuteur) {
        RestResponse<AEcrit> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = deleteAllArticle(adao.read(idAuteur).getNom());
        }

        return resp;
    }

    @Path("{nom}/articleFromName")
    @DELETE
    public RestResponse<AEcrit> deleteAllArticle(@PathParam("nom") String nomAuteur) {
        int status = aedao.delete(nomAuteur);

        RestResponse<AEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/article/{idArticle}")
    @DELETE
    public RestResponse<AEcrit> deleteArticle(@PathParam("id") int idAuteur, @PathParam("idArticle") int idArticle) {
        RestResponse<AEcrit> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = deleteArticle(adao.read(idAuteur).getNom(), idArticle);
        }

        return resp;
    }

    @Path("{nom}/articleFromName/{idArticle}")
    @DELETE
    public RestResponse<AEcrit> deleteArticle(@PathParam("nom") String nomAuteur, @PathParam("idArticle") int idArticle) {
        int status = aedao.delete(nomAuteur, idArticle);

        RestResponse<AEcrit> resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
        return resp;
    }

    @Path("{id}/university")
    @GET
    public RestResponse<University> listUniversities(@PathParam("id") int idAuteur) {
        RestResponse<University> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = listUniversities(adao.read(idAuteur).getNom());
        }

        return resp;
    }

    @Path("{nom}/universityFromName")
    @GET
    public RestResponse<University> listUniversities(@PathParam("nom") String nomAuteur) {
        int status = 0;

        RestResponse<University> resp = new RestResponse<>(status);

        for (EstRattache r : erdao.read(nomAuteur, University.MAJOR_KEY)) {
            University universite = udao.read(r.getValue());
            status = (status != 150 && universite != null ? 0 : 150);
            if (universite != null) {
                resp.addObjectList(universite);
            }
        }

        resp.setResponseCode(RestResponse.getStatus(status));
        resp.setCode(status);
        resp.setMessage(RestResponse.getMessage(status));

        return resp;
    }

    @Path("{id}/university")
    @POST
    public RestResponse<Rattache> addUniversities(@PathParam("id") int idAuteur, EstRattache estRattache) {
        
        int status = 150;

        RestResponse<Rattache> resp = new RestResponse<>(status, "204");

        if (adao.exist(idAuteur)) {
            University univ = udao.read(estRattache.getValue());

            if (univ != null) {
                resp = new RestResponse<>(0, "201");

                status = erdao.create(adao.read(idAuteur).getNom(), University.MAJOR_KEY, estRattache.getValue());
                if (status != 0) {
                    resp = new RestResponse<>(status, (status >= 150 ? "204" : "409"));
                }

                status = rdao.create(University.MAJOR_KEY, univ.getNom(), idAuteur);
                if (status != 0) {
                    resp = new RestResponse<>(status, (status >= 150 ? "204" : "409"));
                }
            } else {
                resp = new RestResponse<>(155, "204");
            }
        }

        return resp;
    }

    @Path("{id}/universityFromName/{idUniversity}/{rank}")
    @PUT
    public RestResponse<University> updateUniversities(@PathParam("nom") int idAuteur, @PathParam("idUniversity") int idUniversity, @PathParam("rank") int rank, EstRattache newEstRattache) {
        int status = 150;
        RestResponse<University> resp = new RestResponse<>(status, "204");
        if (adao.exist(idAuteur)) {
            University oldUniv = udao.read(idUniversity);
            if (oldUniv != null) {
                status = erdao.update(adao.read(idAuteur).getNom(), University.MAJOR_KEY, rank, newEstRattache);
                if (status != 0) {
                    resp = new RestResponse<>(status, "204");
                }

                University univ = udao.read(newEstRattache.getValue());
                if (univ != null) {
                    status = rdao.delete(University.MAJOR_KEY, oldUniv.getNom(), 1);
                    resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
                    if (status != 0) {
                        status = rdao.update(University.MAJOR_KEY, univ.getNom(), idAuteur, 1);
                        if (status != 0) {
                            resp = new RestResponse<>(status, "204");
                        }
                    }
                } else {
                    status = 155;
                    resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
                }
            } else {
                status = 155;
                resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
            }
        }

        return resp;

    }

    @Path("{id}/universityFromName/{idUniversity}/{rank}")
    @DELETE
    public RestResponse<University> deleteUniversities(@PathParam("nom") int idAuteur, @PathParam("idUniversity") int idUniversity, @PathParam("rank") int rank) {
        int status = 150;
        RestResponse<University> resp = new RestResponse<>(status, "204");
        if (adao.exist(idAuteur)) {
            University univ = udao.read(idUniversity);
            if (univ != null) {
                status = erdao.delete(adao.read(idAuteur).getNom(), University.MAJOR_KEY, rank);
                resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));

                status = rdao.delete(University.MAJOR_KEY, univ.getNom(), 1);
                if (status != 0) {
                    resp = new RestResponse<>(status, "204");
                }
            } else {
                status = 155;
                resp = new RestResponse<>(status, "204");
            }
        }

        return resp;
    }

    @Path("{id}/laboratory")
    @GET
    public RestResponse<Laboratory> listLaboratories(@PathParam("id") int idAuteur) {
        RestResponse<Laboratory> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = listLaboratories(adao.read(idAuteur).getNom());
        }

        return resp;
    }

    @Path("{nom}/laboratoryFromName")
    @GET
    public RestResponse<Laboratory> listLaboratories(@PathParam("nom") String nomAuteur) {
        int status = 0;

        RestResponse<Laboratory> resp = new RestResponse<>(status);

        for (EstRattache r : erdao.read(nomAuteur, Laboratory.MAJOR_KEY)) {
            Laboratory laboratoire = labodao.read(r.getValue());
            status = (status != 150 && laboratoire != null ? 0 : 150);
            if (laboratoire != null) {
                resp.addObjectList(laboratoire);
            }
        }

        resp.setResponseCode(RestResponse.getStatus(status));
        resp.setCode(status);
        resp.setMessage(RestResponse.getMessage(status));

        return resp;
    }

    @Path("{id}/laboratory")
    @POST
    public RestResponse<Rattache> addLaboratories(@PathParam("id") int idAuteur, EstRattache estRattache) {
        int status = 150;

        RestResponse<Rattache> resp = new RestResponse<>(status, "204");

        if (adao.exist(idAuteur)) {
            Laboratory labo = labodao.read(estRattache.getValue());

            if (labo != null) {
                resp = new RestResponse<>(0, "201");

                status = erdao.create(adao.read(idAuteur).getNom(), Laboratory.MAJOR_KEY, estRattache.getValue());
                if (status != 0) {
                    resp = new RestResponse<>(status, (status >= 150 ? "204" : "409"));
                }

                status = rdao.create(Laboratory.MAJOR_KEY, labo.getNom(), idAuteur);
                if (status != 0) {
                    resp = new RestResponse<>(status, (status >= 150 ? "204" : "409"));
                }
            } else {
                resp = new RestResponse<>(154, "204");
            }
        }

        return resp;
    }

    @Path("{id}/laboratoryFromName/{idLaboratory}/{rank}")
    @PUT
    public RestResponse<Laboratory> updateLaboratories(@PathParam("id") int idAuteur, @PathParam("idLaboratory") int idLaboratory, @PathParam("rank") int rank, EstRattache newEstRattache) {
        int status = 150;
        RestResponse<Laboratory> resp = new RestResponse<>(status, "204");
        if (adao.exist(idAuteur)) {
            Laboratory oldLabo = labodao.read(idLaboratory);
            if (oldLabo != null) {
                status = erdao.update(adao.read(idAuteur).getNom(), Laboratory.MAJOR_KEY, rank, newEstRattache);
                if (status != 0) {
                    resp = new RestResponse<>(status, "204");
                }

                Laboratory labo = labodao.read(newEstRattache.getValue());
                if (labo != null) {
                    status = rdao.delete(Laboratory.MAJOR_KEY, oldLabo.getNom(), 1);
                    resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));
                    if (status != 0) {
                        status = rdao.update(Laboratory.MAJOR_KEY, labo.getNom(), idAuteur, 1);
                        if (status != 0) {
                            resp = new RestResponse<>(status, "204");
                        }
                    }
                } else {
                    status = 154;
                    resp = new RestResponse<>(status, "204");
                }
            } else {
                status = 154;
                resp = new RestResponse<>(status, "204");
            }
        }

        return resp;

    }

    @Path("{id}/laboratoryFromName/{idLaboratory}/{rank}")
    @DELETE
    public RestResponse<Laboratory> deleteLaboratories(@PathParam("id") int idAuteur, @PathParam("idLaboratory") int idLaboratory, @PathParam("rank") int rank) {
        int status = 150;
        RestResponse<Laboratory> resp = new RestResponse<>(status, "204");
        if (adao.exist(idAuteur)) {
            Laboratory labo = labodao.read(idLaboratory);
            if (labo != null) {
                status = erdao.delete(adao.read(idAuteur).getNom(), Laboratory.MAJOR_KEY, rank);
                resp = new RestResponse<>(status, (status != 0 ? "204" : "200"));

                status = rdao.delete(Laboratory.MAJOR_KEY, labo.getNom(), 1);
                if (status != 0) {
                    resp = new RestResponse<>(status, "204");
                }
            } else {
                status = 154;
                resp = new RestResponse<>(status, "204");
            }
        }

        return resp;
    }

    @Path("{id}/keyword")
    @GET
    public RestResponse<String> listKeywords(@PathParam("id") int idAuteur) throws ParseException {
        RestResponse<String> resp = new RestResponse<>(150, "204");

        if (adao.exist(idAuteur)) {
            resp = new RestResponse<>(151, "204");
            for (AEcrit ae : aedao.read(adao.read(idAuteur).getNom())) {
                if (ldao.exist(ae.getIdArticle())) {
                    resp.setCode(157);
                    resp.setMessage(RestResponse.getMessage(157));
                    resp.setResponseCode("204");
                    for (HasKeyword hk : hkdao.read(ldao.read(ae.getIdArticle()).getTitre())) {
                        resp.setCode(0);
                        resp.setMessage(RestResponse.getMessage(0));
                        resp.setResponseCode(RestResponse.getStatus(0));
                        resp.addObjectList(hk.getKeyword());
                    }
                }
            }
        }

        return resp;
    }
    
    @Path("debugDeleteAll")
    @GET
    public void deleteAll() {
        adao.deleteAll();
    }
    
    @Path("debugDisplayAll")
    @GET
    public void displayAll() {
        adao.displayAll();
    }
}
