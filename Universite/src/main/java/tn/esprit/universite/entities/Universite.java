package tn.esprit.universite.entities;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Universite implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUniversite;
    String nomUniversite;
    String adresseUniversite;
    String ville;
    String descriptionUniversite;
    int telUniversite;
    String emailUinversite;
    String image;

    // Manual getters and setters
    public long getIdUniversite() {
        return idUniversite;
    }

    public void setIdUniversite(long idUniversite) {
        this.idUniversite = idUniversite;
    }

    public String getNomUniversite() {
        return nomUniversite;
    }

    public void setNomUniversite(String nomUniversite) {
        this.nomUniversite = nomUniversite;
    }

    public String getAdresseUniversite() {
        return adresseUniversite;
    }

    public void setAdresseUniversite(String adresseUniversite) {
        this.adresseUniversite = adresseUniversite;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getDescriptionUniversite() {
        return descriptionUniversite;
    }

    public void setDescriptionUniversite(String descriptionUniversite) {
        this.descriptionUniversite = descriptionUniversite;
    }

    public int getTelUniversite() {
        return telUniversite;
    }

    public void setTelUniversite(int telUniversite) {
        this.telUniversite = telUniversite;
    }

    public String getEmailUinversite() {
        return emailUinversite;
    }

    public void setEmailUinversite(String emailUinversite) {
        this.emailUinversite = emailUinversite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
