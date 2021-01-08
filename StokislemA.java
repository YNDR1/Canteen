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
public class StokislemA {
 String tur,acıklama, tarih;
 int stokNo, adet;
 double alisfiyat,satisfiyat;

    public StokislemA(String tur, String acıklama, String tarih, int stokNo, int adet, double alisfiyat, double satisfiyat) {
        this.tur = tur;
        this.acıklama = acıklama;
        this.tarih = tarih;
        this.stokNo = stokNo;
        this.adet = adet;
        this.alisfiyat = alisfiyat;
        this.satisfiyat = satisfiyat;
    }
}
