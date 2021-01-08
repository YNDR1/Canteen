/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kantin;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author yusuf
 */
@Entity
@Table(name = "GIRIS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Giris.findAll", query = "SELECT g FROM Giris g")
    , @NamedQuery(name = "Giris.findByKullaniciadi", query = "SELECT g FROM Giris g WHERE g.kullaniciadi = :kullaniciadi")
    , @NamedQuery(name = "Giris.findBySifre", query = "SELECT g FROM Giris g WHERE g.sifre = :sifre")})
public class Giris implements Serializable {

    public Giris(String kullaniciadi, String sifre) {
        this.kullaniciadi = kullaniciadi;
        this.sifre = sifre;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "KULLANICIADI")
    private String kullaniciadi;
    @Column(name = "SIFRE")
    private String sifre;

    public Giris() {
    }

    public Giris(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getKullaniciadi() {
        return kullaniciadi;
    }

    public void setKullaniciadi(String kullaniciadi) {
        this.kullaniciadi = kullaniciadi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kullaniciadi != null ? kullaniciadi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Giris)) {
            return false;
        }
        Giris other = (Giris) object;
        if ((this.kullaniciadi == null && other.kullaniciadi != null) || (this.kullaniciadi != null && !this.kullaniciadi.equals(other.kullaniciadi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Giris[ kullaniciadi=" + kullaniciadi + " ]";
    }
    
}
