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
@Table(name = "KASAISLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kasaislem.findAll", query = "SELECT k FROM Kasaislem k")
    , @NamedQuery(name = "Kasaislem.findByIslemno", query = "SELECT k FROM Kasaislem k WHERE k.islemno = :islemno")
    , @NamedQuery(name = "Kasaislem.findByTur", query = "SELECT k FROM Kasaislem k WHERE k.tur = :tur")
    , @NamedQuery(name = "Kasaislem.findByTarih", query = "SELECT k FROM Kasaislem k WHERE k.tarih = :tarih")
    , @NamedQuery(name = "Kasaislem.findByAciklama", query = "SELECT k FROM Kasaislem k WHERE k.aciklama = :aciklama")
    , @NamedQuery(name = "Kasaislem.findByPara", query = "SELECT k FROM Kasaislem k WHERE k.para = :para")})
public class Kasaislem implements Serializable {

    public Kasaislem(Integer islemno, String tur, String tarih, String aciklama, Double para) {
        this.islemno = islemno;
        this.tur = tur;
        this.tarih = tarih;
        this.aciklama = aciklama;
        this.para = para;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ISLEMNO")
    private Integer islemno;
    @Column(name = "TUR")
    private String tur;
    @Column(name = "TARIH")
    private String tarih;
    @Column(name = "ACIKLAMA")
    private String aciklama;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PARA")
    private Double para;

    public Kasaislem() {
    }

    public Kasaislem(Integer islemno) {
        this.islemno = islemno;
    }

    public Integer getIslemno() {
        return islemno;
    }

    public void setIslemno(Integer islemno) {
        this.islemno = islemno;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public Double getPara() {
        return para;
    }

    public void setPara(Double para) {
        this.para = para;
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
        if (!(object instanceof Kasaislem)) {
            return false;
        }
        Kasaislem other = (Kasaislem) object;
        if ((this.islemno == null && other.islemno != null) || (this.islemno != null && !this.islemno.equals(other.islemno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Kasaislem[ islemno=" + islemno + " ]";
    }
    
}
