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
@Table(name = "BAKIYEISLEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bakiyeislem.findAll", query = "SELECT b FROM Bakiyeislem b")
    , @NamedQuery(name = "Bakiyeislem.findByIslemno", query = "SELECT b FROM Bakiyeislem b WHERE b.islemno = :islemno")
    , @NamedQuery(name = "Bakiyeislem.findByTarih", query = "SELECT b FROM Bakiyeislem b WHERE b.tarih = :tarih")
    , @NamedQuery(name = "Bakiyeislem.findByMusterino", query = "SELECT b FROM Bakiyeislem b WHERE b.musterino = :musterino")
    , @NamedQuery(name = "Bakiyeislem.findByMusteriismi", query = "SELECT b FROM Bakiyeislem b WHERE b.musteriismi = :musteriismi")
    , @NamedQuery(name = "Bakiyeislem.findByPara", query = "SELECT b FROM Bakiyeislem b WHERE b.para = :para")
    , @NamedQuery(name = "Bakiyeislem.findByTur", query = "SELECT b FROM Bakiyeislem b WHERE b.tur = :tur")})
public class Bakiyeislem implements Serializable {

    public Bakiyeislem(Integer islemno, String tarih, Integer musterino, String musteriismi, Double para, String tur) {
        this.islemno = islemno;
        this.tarih = tarih;
        this.musterino = musterino;
        this.musteriismi = musteriismi;
        this.para = para;
        this.tur = tur;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ISLEMNO")
    private Integer islemno;
    @Column(name = "TARIH")
    private String tarih;
    @Column(name = "MUSTERINO")
    private Integer musterino;
    @Column(name = "MUSTERIISMI")
    private String musteriismi;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PARA")
    private Double para;
    @Column(name = "TUR")
    private String tur;

    public Bakiyeislem() {
    }

    public Bakiyeislem(Integer islemno) {
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

    public Integer getMusterino() {
        return musterino;
    }

    public void setMusterino(Integer musterino) {
        this.musterino = musterino;
    }

    public String getMusteriismi() {
        return musteriismi;
    }

    public void setMusteriismi(String musteriismi) {
        this.musteriismi = musteriismi;
    }

    public Double getPara() {
        return para;
    }

    public void setPara(Double para) {
        this.para = para;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
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
        if (!(object instanceof Bakiyeislem)) {
            return false;
        }
        Bakiyeislem other = (Bakiyeislem) object;
        if ((this.islemno == null && other.islemno != null) || (this.islemno != null && !this.islemno.equals(other.islemno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Bakiyeislem[ islemno=" + islemno + " ]";
    }
    
}
