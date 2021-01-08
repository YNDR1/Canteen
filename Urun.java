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
@Table(name = "URUN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Urun.findAll", query = "SELECT u FROM Urun u")
    , @NamedQuery(name = "Urun.findByStokno", query = "SELECT u FROM Urun u WHERE u.stokno = :stokno")
    , @NamedQuery(name = "Urun.findByAdet", query = "SELECT u FROM Urun u WHERE u.adet = :adet")
    , @NamedQuery(name = "Urun.findByAd", query = "SELECT u FROM Urun u WHERE u.ad = :ad")
    , @NamedQuery(name = "Urun.findByAlisfiyat", query = "SELECT u FROM Urun u WHERE u.alisfiyat = :alisfiyat")
    , @NamedQuery(name = "Urun.findBySatisfiyat", query = "SELECT u FROM Urun u WHERE u.satisfiyat = :satisfiyat")
    , @NamedQuery(name = "Urun.findByBarkod", query = "SELECT u FROM Urun u WHERE u.barkod = :barkod")})
public class Urun implements Serializable {

    public Urun(Integer stokno,String ad, Integer adet,  Double alisfiyat, Double satisfiyat, String barkod) {
        this.stokno = stokno;
        this.adet = adet;
        this.ad = ad;
        this.alisfiyat = alisfiyat;
        this.satisfiyat = satisfiyat;
        this.barkod = barkod;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "STOKNO")
    private Integer stokno;
    @Column(name = "ADET")
    private Integer adet;
    @Column(name = "AD")
    private String ad;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ALISFIYAT")
    private Double alisfiyat;
    @Column(name = "SATISFIYAT")
    private Double satisfiyat;
    @Column(name = "BARKOD")
    private String barkod;

    public Urun() {
    }

    public Urun(Integer stokno) {
        this.stokno = stokno;
    }

    public Integer getStokno() {
        return stokno;
    }

    public void setStokno(Integer stokno) {
        this.stokno = stokno;
    }

    public Integer getAdet() {
        return adet;
    }

    public void setAdet(Integer adet) {
        this.adet = adet;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public Double getAlisfiyat() {
        return alisfiyat;
    }

    public void setAlisfiyat(Double alisfiyat) {
        this.alisfiyat = alisfiyat;
    }

    public Double getSatisfiyat() {
        return satisfiyat;
    }

    public void setSatisfiyat(Double satisfiyat) {
        this.satisfiyat = satisfiyat;
    }

    public String getBarkod() {
        return barkod;
    }

    public void setBarkod(String barkod) {
        this.barkod = barkod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stokno != null ? stokno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Urun)) {
            return false;
        }
        Urun other = (Urun) object;
        if ((this.stokno == null && other.stokno != null) || (this.stokno != null && !this.stokno.equals(other.stokno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Urun[ stokno=" + stokno + " ]";
    }
    
}
