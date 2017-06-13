/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.isima.zz3.oraclenosql.server.entity;

import com.isima.zz3.oraclenosql.server.entity.exception.ArticleBuildException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author mathieu
 */
@Entity
public class Article implements Serializable {
    
    @Id
    private long id;
    
    @Column
    private String title;
    
    @Column
    private String resume;
    
    @Column
    private float price;
    
    @Column
    private LocalDate reception;
    
    @Column
    private LocalDate acceptation;
    
    @Column
    private LocalDate publication;

    @ManyToMany(mappedBy="authors")
    private List<Author> authors;

    public Article() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
        
        public Article build(String[] article) throws ArticleBuildException {
            if (article.length < 6) {
                throw new ArticleBuildException();
            }
            return new Builder(article[0])
                    .resume(article[1])
                    .price(Float.parseFloat(article[2]))
                    .reception(LocalDate.parse(article[3]))
                    .acceptation(LocalDate.parse(article[4]))
                    .publication(LocalDate.parse(article[5]))
                    .build();
        }
    }

    @Override
    public String toString() {
        return title + "/" + resume + "/" + price + "/" + reception + "/" + acceptation + "/" + publication;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.title);
        hash = 53 * hash + Objects.hashCode(this.resume);
        hash = 53 * hash + Float.floatToIntBits(this.price);
        hash = 53 * hash + Objects.hashCode(this.reception);
        hash = 53 * hash + Objects.hashCode(this.acceptation);
        hash = 53 * hash + Objects.hashCode(this.publication);
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
