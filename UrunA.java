/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kantin;

/**
 *
 * @author yusuf
 */
public class UrunA {
    int stokNumarasi;
    String ad;
    double Satisfiyat,Alisfiyat; 
    String barkod;
     int adet;
   //  String tarih;
    public UrunA(int stokNumara,int adet,String ad,  double Alisfiyat,double Satisfiyat, String barkod) {
        this.ad = ad;
       // this.tarih = tarih;
        this.Satisfiyat = Satisfiyat;
        this.Alisfiyat = Alisfiyat;
        this.barkod = barkod;
        this.stokNumarasi = stokNumara;
        this.adet = adet;
    }

}
