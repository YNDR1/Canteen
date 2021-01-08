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
@Table(name = "MUSTERI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Musteri.findAll", query = "SELECT m FROM Musteri m")
    , @NamedQuery(name = "Musteri.findByMusterino", query = "SELECT m FROM Musteri m WHERE m.musterino = :musterino")
    , @NamedQuery(name = "Musteri.findByIsim", query = "SELECT m FROM Musteri m WHERE m.isim = :isim")
    , @NamedQuery(name = "Musteri.findByBakiye", query = "SELECT m FROM Musteri m WHERE m.bakiye = :bakiye")})
public class Musteri implements Serializable {

    public Musteri(Integer musterino, String isim, Double bakiye) {
        this.musterino = musterino;
        this.isim = isim;
        this.bakiye = bakiye;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "MUSTERINO")
    private Integer musterino;
    @Column(name = "ISIM")
    private String isim;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "BAKIYE")
    private Double bakiye;

    public Musteri() {
    }

    public Musteri(Integer musterino) {
        this.musterino = musterino;
    }

    public Integer getMusterino() {
        return musterino;
    }

    public void setMusterino(Integer musterino) {
        this.musterino = musterino;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public Double getBakiye() {
        return bakiye;
    }

    public void setBakiye(Double bakiye) {
        this.bakiye = bakiye;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (musterino != null ? musterino.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Musteri)) {
            return false;
        }
        Musteri other = (Musteri) object;
        if ((this.musterino == null && other.musterino != null) || (this.musterino != null && !this.musterino.equals(other.musterino))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kantin.Musteri[ musterino=" + musterino + " ]";
    }
    
}
