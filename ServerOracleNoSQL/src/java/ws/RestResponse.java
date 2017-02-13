/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mathieu
 */
public class RestResponse<T> implements Serializable {
    private String status;
    private String message;
    private List<T> objectList;

    public RestResponse() {
        objectList = new ArrayList<>();
    }
    
    public RestResponse(String status) {
        this.status = status;
        this.message = getMessageError(status);
        this.objectList = new ArrayList<>();
    }
    
    public RestResponse(String status, List<T> objectList) {
        this.status = status;
        this.message = getMessageError(status);
        this.objectList = objectList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getObjectList() {
        return objectList;
    }

    public void setObjectList(List<T> objectList) {
        this.objectList = objectList;
    }

    public void addObjectList(T object) {
        this.objectList.add(object);
    }

    public static String getMessageError(String error) {
        String message;
        switch(error) {
            case "200": message = "ok";break;
            
            case "300": message = "Auteur déjà créé";break;
            case "301": message = "Livre déjà créé";break;
            case "302": message = "Relation AEcrit déjà créée";break;
            case "303": message = "Relation AEteEcrit déjà créée";break;
            case "304": message = "Laboratoire déjà créé";break;
            case "305": message = "Université déjà créé";break;
            case "306": message = "Relation rattache déjà créée";break;
            case "307": message = "Keyword déjà créé";break;
            
            case "400": message = "Auteur inconnu";break;
            case "401": message = "Livre inconnu";break;
            case "402": message = "Relation AEcrit inconnue";break;
            case "403": message = "Relation AEteEcrit inconnue";break;
            case "404": message = "Laboratoire inconnu";break;
            case "405": message = "Université inconnu";break;
            case "406": message = "Relation rattache inconnue";break;
            case "407": message = "Keyword inconnu";break;
            
            default: message = "Unknown error";
        }
        
        return message;
    }    
}
