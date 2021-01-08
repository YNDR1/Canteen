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
@Table(name = "STOKISLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stokislem.findAll", query = "SELECT s FROM Stokislem s")
    , @NamedQuery(name = "Stokislem.findByIslemno", query = "SELECT s FROM Stokislem s WHERE s.islemno = :islemno")
    , @NamedQuery(name = "Stokislem.findByTarih", query = "SELECT s FROM Stokislem s WHERE s.tarih = :tarih")
    , @NamedQuery(name = "Stokislem.findByTur", query = "SELECT s FROM Stokislem s WHERE s.tur = :tur")
    , @NamedQuery(name = "Stokislem.findByAciklama", query = "SELECT s FROM Stokislem s WHERE s.aciklama = :aciklama")
    , @NamedQuery(name = "Stokislem.findByAdet", query = "SELECT s FROM Stokislem s WHERE s.adet = :adet")
    , @NamedQuery(name = "Stokislem.findByStokno", query = "SELECT s FROM Stokislem s WHERE s.stokno = :stokno")
    , @NamedQuery(name = "Stokislem.findByAlisfiyat", query = "SELECT s FROM Stokislem s WHERE s.alisfiyat = :alisfiyat")
    , @NamedQuery(name = "Stokislem.findBySatisfiyat", query = "SELECT s FROM Stokislem s WHERE s.satisfiyat = :satisfiyat")})
public class Stokislem implements Serializable {

    public Stokislem(Integer islemno, String tarih, String tur, String aciklama, Integer adet, Integer stokno, Double alisfiyat, Double satisfiyat) {
        this.islemno = islemno;
        this.tarih = tarih;
        this.tur = tur;
        this.aciklama = aciklama;
        this.adet = adet;
        this.stokno = stokno;
        this.alisfiyat = alisfiyat;
        this.satisfiyat = satisfiyat;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ISLEMNO")
    private Integer islemno;
    @Column(name = "TARIH")
    private String tarih;
    @Column(name = "TUR")
    private String tur;
    @Column(name = "ACIKLAMA")
    private String aciklama;
    @Column(name = "ADET")
    private Integer adet;
    @Column(name = "STOKNO")
    private Integer stokno;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ALISFIYAT")
    private Double alisfiyat;
    @Column(name = "SATISFIYAT")
    private Double satisfiyat;

    public Stokislem() {
    }

    public Stokislem(Integer islemno) {
        this.islemno = islemno;
    }

    public Integer getIslemno() {
        return islemno;
    }

    public void setIslemno(Integer islemno) {
        this.islemno = islemno;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Integer getAdet() {
        return adet;
    }

    public void setAdet(Integer adet) {
        this.adet = adet;
    }

    public Integer getStokno() {
        return stokno;
    }

    public void setStokno(Integer stokno) {
        this.stokno = stokno;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (islemno != null ? islemno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stokislem)) {
            return false;
        }
        Stokislem other = (Stokislem) object;
        if ((this.islemno == null && other.islemno != null) || (this.islemno != null && !this.islemno.equals(other.islemno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Stokislem[ islemno=" + islemno + " ]";
    }
    
}
