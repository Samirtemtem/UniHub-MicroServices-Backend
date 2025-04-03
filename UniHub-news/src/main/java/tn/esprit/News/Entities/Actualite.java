package tn.esprit.News.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Actualite implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idActualite;
    String titreActualite;

    @Column(columnDefinition = "TEXT")
    String descriptionActualite;

    @Temporal(TemporalType.TIMESTAMP)
    Date dateActualite;
    String imageActualite;

    // Manual getters and setters
    public Long getIdActualite() {
        return idActualite;
    }

    public void setIdActualite(Long idActualite) {
        this.idActualite = idActualite;
    }

    public String getTitreActualite() {
        return titreActualite;
    }

    public void setTitreActualite(String titreActualite) {
        this.titreActualite = titreActualite;
    }

    public String getDescriptionActualite() {
        return descriptionActualite;
    }

    public void setDescriptionActualite(String descriptionActualite) {
        this.descriptionActualite = descriptionActualite;
    }

    public Date getDateActualite() {
        return dateActualite;
    }

    public void setDateActualite(Date dateActualite) {
        this.dateActualite = dateActualite;
    }

    public String getImageActualite() {
        return imageActualite;
    }

    public void setImageActualite(String imageActualite) {
        this.imageActualite = imageActualite;
    }
}
