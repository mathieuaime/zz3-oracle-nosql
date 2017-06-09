/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author mathieu
 */
public class Article {

    private int id;
    private String title;
    private String resume;
    private float price;
    private LocalDate reception;
    private LocalDate acceptation;
    private LocalDate publication;

    private Article() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getReception() {
        return reception;
    }

    public void setReception(LocalDate reception) {
        this.reception = reception;
    }

    public LocalDate getAcceptation() {
        return acceptation;
    }

    public void setAcceptation(LocalDate acceptation) {
        this.acceptation = acceptation;
    }

    public LocalDate getPublication() {
        return publication;
    }

    public void setPublication(LocalDate publication) {
        this.publication = publication;
    }

    public static class Builder {
        private final Article article;

        public Builder(String title) {
            article = new Article();
            article.title = title;
        }
        
        public Builder resume(String resume) {
            article.resume = resume;
            return this;
        }
        
        public Builder price(float price) {
            article.price = price;
            return this;
        }
        
        public Builder reception(LocalDate reception) {
            article.reception = reception;
            return this;
        }
        
        public Builder acceptation(LocalDate acceptation) {
            article.acceptation = acceptation;
            return this;
        }
        
        public Builder publication(LocalDate publication) {
            article.publication = publication;
            return this;
        }
        
        public Article build() {
            return article;
        }
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", title=" + title + ", resume=" + resume + ", price=" + price + ", reception=" + reception + ", acceptation=" + acceptation + ", publication=" + publication + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + this.id;
        hash = 19 * hash + Objects.hashCode(this.title);
        hash = 19 * hash + Objects.hashCode(this.resume);
        hash = 19 * hash + Float.floatToIntBits(this.price);
        hash = 19 * hash + Objects.hashCode(this.reception);
        hash = 19 * hash + Objects.hashCode(this.acceptation);
        hash = 19 * hash + Objects.hashCode(this.publication);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Article other = (Article) obj;
        if (this.id != other.id) {
            return false;
        }
        if (Float.floatToIntBits(this.price) != Float.floatToIntBits(other.price)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.resume, other.resume)) {
            return false;
        }
        if (!Objects.equals(this.reception, other.reception)) {
            return false;
        }
        if (!Objects.equals(this.acceptation, other.acceptation)) {
            return false;
        }
        return Objects.equals(this.publication, other.publication);
    }
}
