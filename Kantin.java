/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kantin;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JOptionPane;

/**
 *
 * @author yusuf
 */
public class Kantin {

    private double ToplamPara = 0;
    public ArrayList<MusteriA> musteriler = new ArrayList<>();
    public ArrayList<UrunA> urunler = new ArrayList<>();
    public ArrayList<UrunA> AnlikSatis = new ArrayList<>();
    public ArrayList<StokislemA> stokIslemler = new ArrayList<>();
    public ArrayList<KasaIslemA> kasaIslemler = new ArrayList<>();
    public ArrayList<BakiyeislemA> bakiyeIslemler = new ArrayList<>();
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("KantinPU");
    EntityManager em = emf.createEntityManager();

    // -------------TARİH------------
    SimpleDateFormat bicim3 = new SimpleDateFormat("dd-MM-yyyy");
    GregorianCalendar gcalender = new GregorianCalendar();
    String tarih = bicim3.format(gcalender.getTime());
//--------------------------------------------------URUN İŞLEMLERİ ---------------------------------------------------------------------------

    public boolean urunEkle(int stokno, int adet, String ad, double alis, double satis, String barkod) {
        boolean sonuc = true;
        for (int i = 0; i < urunler.size(); i++) {
            if (stokno == urunler.get(i).stokNumarasi || barkod.equals(urunler.get(i).barkod)) {
                JOptionPane.showMessageDialog(null, "Bu Stok numarasına ya da barkoda sahip ürun var", "uyarı", JOptionPane.ERROR_MESSAGE);
                sonuc = false;
                break;
            }
        }
        if (sonuc) {
            urunler.add(new UrunA(stokno, adet, ad, alis, satis, barkod));
            urunEkleDB(stokno, ad, adet, alis, satis, barkod);

            sonuc = true;
        }
        return sonuc;
    }

    public void urunSil(int stokNo) {
        for (int i = 0; i < urunler.size(); i++) {
            if (stokNo == urunler.get(i).stokNumarasi) {
                //  int a = JOptionPane.showConfirmDialog(null, " Urunden  " + urunler.get(i).adet + "   adet bulunuyor eminmisiniz ? ", "Uyarı", JOptionPane.WARNING_MESSAGE);
                if (urunler.get(i).adet == 0) {
                    urunler.remove(urunler.get(i));
                    urunSilDB(stokNo);
                    JOptionPane.showMessageDialog(null, "Ürun başarıyla stoktan silindi ", "Başarılı işlem", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, " Urunden  " + urunler.get(i).adet + "   adet bulunuyor silinemez! ", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }

    public void stokEkle(int stokNo, int adet, String aciklama) {
        if (urunler.size() == 0) {
            JOptionPane.showMessageDialog(null, " Stokta hiç ürün yok", "uyarı", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Ürun sayısı " + urunler.size());
            for (int i = 0; i < urunler.size(); i++) {
                if (stokNo == urunler.get(i).stokNumarasi) {
                    urunler.get(i).adet += adet;
                    urunStokGuncelleDB(stokNo, urunler.get(i).adet);
                    stokIslemEkle(tarih, "Stok Girişi", aciklama, adet, stokNo, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat);
                    //     stokIslemler.add(new StokislemA("Stok Girişi", aciklama, tarih, stokNo, adet, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat));
                    //     stokIslemEkleDB(tarih, "Stok Girişi", aciklama, adet, urunler.get(i).stokNumarasi, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat);
                    JOptionPane.showMessageDialog(null, "Stok başarıyla eklendi", "Başarılı işlem", JOptionPane.INFORMATION_MESSAGE);
                }
            }

        }
    }

    public void stokCikisi(int stokNo, int adet, String aciklama) {
        if (urunler.size() == 0) {
            JOptionPane.showMessageDialog(null, " Stokta hiç ürün yok", "uyarı", JOptionPane.ERROR_MESSAGE);
        } else {
            System.out.println("Ürun sayısı " + urunler.size());
            for (int i = 0; i < urunler.size(); i++) {
                if (stokNo == urunler.get(i).stokNumarasi) {
                    urunler.get(i).adet += adet;
                    urunStokGuncelleDB(stokNo, urunler.get(i).adet);
                    stokIslemEkle(tarih, "Stok Çıkışı", aciklama, adet, stokNo, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat);
                    //  stokIslemler.add(new StokislemA("Stok Çıkışı", aciklama, tarih, stokNo, adet, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat));
                    //    stokIslemEkleDB(tarih, "Stok Çıkışı", aciklama, adet, urunler.get(i).stokNumarasi, urunler.get(i).Alisfiyat, urunler.get(i).Satisfiyat);
                    JOptionPane.showMessageDialog(null, "Stok başarıyla güncellendi", "Başarılı işlem", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }

    public boolean stokIslemSil(String aciklama, String tur, String tarih, int stokNo) {
        boolean sonuc = false;
        for (int i = 0; i < stokIslemler.size(); i++) {
            if (stokIslemler.get(i).acıklama.equals(aciklama) && stokIslemler.get(i).tarih.equals(tarih) && (tur.equals(stokIslemler.get(i).tur) && stokNo == stokIslemler.get(i).stokNo)) {
                if (stokIslemler.get(i).tur.equals("Stok Girişi") || stokIslemler.get(i).tur.equals("Stok Çıkışı")) {

                    for (int j = 0; j < urunler.size(); j++) {
                        if (urunler.get(j).stokNumarasi == stokNo) {
                            System.out.println("urun adedi = " + urunler.get(j).adet);
                            System.out.println("stok işlem adedi = " + stokIslemler.get(i).adet);
                            urunler.get(j).adet -= stokIslemler.get(i).adet;
                            urunStokGuncelleDB(stokNo, urunler.get(j).adet);
                        }
                    }
                    stokIslemler.remove(stokIslemler.get(i));
                    stokIslemSilDB(aciklama, tarih, tur, stokNo);
                    sonuc = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Bu işlemi Silemezsiniz", "Uyarı", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return sonuc;
    }

    public void stokIslemEkle(String tarih, String tur, String aciklama, int adet, int stokNo, double alisFiyat, double satisFiyat) {
        boolean sonuc = true;
        for (int i = 0; i < stokIslemler.size(); i++) {
            if (tarih.equals(stokIslemler.get(i).tarih) && aciklama.equals(stokIslemler.get(i).acıklama) && stokNo == (stokIslemler.get(i).stokNo)) {

                stokIslemler.get(i).adet += adet;

                sonuc = false;
            }
        }
        if (sonuc) {
            stokIslemler.add(new StokislemA(tur, aciklama, tarih, stokNo, adet, alisFiyat, satisFiyat));

        }
        stokIslemEkleDB(tarih, tur, aciklama, adet, stokNo, alisFiyat, satisFiyat);
        ////  urunler.get(stokNo).adet -= adet;
        ////  urunStokGuncelleDB(stokNo,urunler.get(stokNo).adet);
    }

    public void urunGuncelle(int stokNo, double alisFiyat, double satisFiyat, String barkod, String ad) {
        if (urunler.size() != 0) {

            boolean sonuc = true;
            for (int i = 0; i < urunler.size(); i++) {
                if (barkod.equals(urunler.get(i).barkod) && urunler.get(i).stokNumarasi != stokNo) {
                    JOptionPane.showMessageDialog(null, "Bu barkoda sahip başka bir ürun var!", "uyarı", JOptionPane.ERROR_MESSAGE);
                    sonuc = false;
                    break;
                }
            }
            if (sonuc) {
                for (int i = 0; i < urunler.size(); i++) {
                    if (stokNo == urunler.get(i).stokNumarasi) {
                        urunler.get(i).ad = ad;
                        urunler.get(i).barkod = barkod;
                        urunler.get(i).Alisfiyat = alisFiyat;
                        urunler.get(i).Satisfiyat = satisFiyat;
                        //   String yenibarkod = urunler.get(i).barkod;
                        //   String yeniad = urunler.get(i).ad;
                        urunGuncelleDB(stokNo, alisFiyat, satisFiyat, barkod, ad);

                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, " Kantinde hiç ürün yok", "uyarı", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean urunİade() {
        boolean sonuc = false;
        int a = JOptionPane.showConfirmDialog(null, "Ürün iadesi yapılsın mı ?  ", "Uyarı", JOptionPane.YES_NO_OPTION);

        if (a == 0) {
            for (int i = 0; i < AnlikSatis.size(); i++) {
                for (int j = 0; j < urunler.size(); j++) {
                    if (AnlikSatis.get(i).stokNumarasi == urunler.get(j).stokNumarasi) {
                        urunler.get(j).adet += AnlikSatis.get(i).adet;

                        kasaIslemEkle(tarih, "Çıkış", "Ürün İade", -AnlikSatis.get(i).adet * AnlikSatis.get(i).Satisfiyat);
                        stokIslemEkle(tarih, "Giriş", "İade", AnlikSatis.get(i).adet, urunler.get(j).stokNumarasi, urunler.get(j).Alisfiyat, urunler.get(j).Satisfiyat);
                        //   stokIslemEkleDB(tarih, "Giriş", "Ürün İade", AnlikSatis.get(i).adet, AnlikSatis.get(i).stokNumarasi, AnlikSatis.get(i).Alisfiyat, AnlikSatis.get(i).Satisfiyat);
                        //  stokIslemler.add(new StokislemA("Giriş", "Ürün İade", tarih, AnlikSatis.get(i).stokNumarasi, AnlikSatis.get(i).adet, AnlikSatis.get(i).Alisfiyat, AnlikSatis.get(i).Satisfiyat));
                        urunStokGuncelleDB(urunler.get(j).stokNumarasi, urunler.get(j).adet);
                        sonuc = true;
                    }
                }

            }
            AnlikSatis.removeAll(AnlikSatis);

        }
        return sonuc;
    }
//-----------------------------------------------------------BAKİYE İŞLEMLERİ ------------------------------------------------------------------

    public void bakiyeGirisi(int MusteriNo, double yeniBakiye) {
        if (musteriler.size() != 0) {
            for (MusteriA m : musteriler) {
                if (m.no == MusteriNo) {

                    kasaIslemEkle(tarih, "Giriş", "Bakiye Girişi", yeniBakiye);
                    bakiyeIslemekleDB(MusteriNo, yeniBakiye, m.isim, "Bakiye Girişi", tarih);
                    musteriBakiyeGuncelleDB(MusteriNo, m.bakiye + yeniBakiye);
                    bakiyeIslemler.add(new BakiyeislemA(MusteriNo, yeniBakiye, m.isim, "Bakiye Girişi", tarih));
                    m.bakiye += yeniBakiye;
                    JOptionPane.showMessageDialog(null, "Yeni bakiye tanımlandı", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kantinde hiç müşteri yok ", "uyarı", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void bakiyeTanımla(int MusteriNo, double yeniBakiye) {

        if (musteriler.size() != 0) {
            for (MusteriA m : musteriler) {
                if (m.no == MusteriNo) {
                    bakiyeIslemekleDB(MusteriNo, yeniBakiye, m.isim, "Bakiye Tanımlama", tarih);
                    musteriBakiyeGuncelleDB(MusteriNo, m.bakiye + yeniBakiye);
                    bakiyeIslemler.add(new BakiyeislemA(MusteriNo, yeniBakiye, m.isim, "Bakiye Tanımlama", tarih));
                    m.bakiye += yeniBakiye;
                    JOptionPane.showMessageDialog(null, "Yeni bakiye tanımlandı", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kantinde hiç müşteri yok ", "uyarı", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void bakiyeIade(int MusteriNo, double yeniBakiye) {
        if (musteriler.size() != 0) {
            for (MusteriA m : musteriler) {
                if (m.no == MusteriNo) {
                    kasaIslemEkle(tarih, "Çıkış", "Bakiye İade", yeniBakiye);
                    bakiyeIslemekleDB(MusteriNo, yeniBakiye, m.isim, "Bakiye İade", tarih);
                    musteriBakiyeGuncelleDB(MusteriNo, m.bakiye + yeniBakiye);
                    bakiyeIslemler.add(new BakiyeislemA(MusteriNo, yeniBakiye, m.isim, "Bakiye İade", tarih));
                    m.bakiye += yeniBakiye;
                    JOptionPane.showMessageDialog(null, "Yeni bakiye tanımlandı", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Kantinde hiç müşteri yok ", "uyarı", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean bakiyeIslemSil(String tarih, String tur, double para, int MusteriNo) {
        boolean sonuc = false;
        for (int i = 0; i < bakiyeIslemler.size(); i++) {
            if (bakiyeIslemler.get(i).tarih.equals(tarih) && (bakiyeIslemler.get(i).tur.equals(tur) && bakiyeIslemler.get(i).para == para) && bakiyeIslemler.get(i).MusteriNo == MusteriNo) {
                if (bakiyeIslemler.get(i).tur.equals("Bakiye Tanımlama")) {
                    bakiyeIslemler.remove(bakiyeIslemler.get(i));
                    bakiyeIslemSilDB(tur, tarih, para, MusteriNo);
                    for (int j = 0; j < musteriler.size(); j++) {
                        if (MusteriNo == musteriler.get(j).no) {
                            musteriler.get(j).bakiye -= para;
                            musteriBakiyeGuncelleDB(MusteriNo, musteriler.get(j).bakiye);
                        }
                    }

                    JOptionPane.showMessageDialog(null, "İşlem başarıyla silindi", "Başarılı işlem", JOptionPane.INFORMATION_MESSAGE);
                    sonuc = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Bu işlemi Silemezsiniz", "Uyarı", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        return sonuc;
    }

    public void musteriEkle(String isim, double bakiye) {
        boolean sonuc = true;
        boolean varmı = true;
        int sayac = 1;
        for (int i = 0; i < musteriler.size(); i++) {
            if (musteriler.get(i).no == sayac) {
                sayac++;
            }
        }
        musteriler.add(new MusteriA(sayac, isim, bakiye));
        musteriEkleDB(sayac, isim, bakiye);
        JOptionPane.showMessageDialog(null, "Müşteri başarıyla eklendi", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
    }

    public boolean musteriSil(int no) {
        boolean sonuc = false;
        for (int i = 0; i < musteriler.size(); i++) {
            if (musteriler.get(i).no == no) {
                String bakiye = "" + musteriler.get(i).bakiye;
                //  int a = JOptionPane.showConfirmDialog(null, "musterinin " + bakiye + "Tl bakiyesi bulunuyor eminmisiniz ? ", "Uyarı", JOptionPane.WARNING_MESSAGE);
                if (musteriler.get(i).bakiye == 0) {
                    musteriler.remove(musteriler.get(i));
                    musteriSilDB(no);
                    JOptionPane.showMessageDialog(null, "Müşteri başarıyla silindi", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                    sonuc = true;
                } else {
                    JOptionPane.showMessageDialog(null, "Müsterinin " + bakiye + "Tl bakiyesi bulunuyor silinemez! ", "Uyarı", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        return sonuc;
    }

    // ----------------------------------------------------SATIŞ İŞLEMLERİ ----------------------------------------------------------------------------
    public void miktarGir(int stokNo, int miktar) {
        for (int i = 0; i < AnlikSatis.size(); i++) {
            if (stokNo == AnlikSatis.get(i).stokNumarasi) {
                AnlikSatis.get(i).adet = miktar;
            }
        }
    }

    public boolean satisIcinUrunEkle(String barkod) {
        boolean varmi = false;
        for (int i = 0; i < AnlikSatis.size(); i++) {
            if (AnlikSatis.get(i).barkod.equals(barkod)) {
                for (UrunA u : urunler) {
                    if (u.barkod.equals(barkod)) {
                        AnlikSatis.get(i).adet++;
                        varmi = true;
                    }
                }
            }
        }
        if (!varmi) {
            for (UrunA u : urunler) {
                if (u.barkod.equals(barkod)) {
                    AnlikSatis.add(new UrunA(u.stokNumarasi, 1, u.ad, u.Alisfiyat, u.Satisfiyat, u.barkod));
                }
            }
        }
        return varmi;
    }

    public boolean SatisListesindenSil(int stokNo) {
        /*
        boolean sonuc = false;
         for (int i = 0; i < AnlikSatis.size(); i++) {
            if(stokNo == AnlikSatis.get(i).stokNumarasi){
                AnlikSatis.get(i).adet--;
                if(AnlikSatis.get(i).adet == 0){
                    sonuc = true;
                }
            }
        }
         */

        boolean sonuc = false;
        for (int i = 0; i < AnlikSatis.size(); i++) {
            if (AnlikSatis.get(i).stokNumarasi == stokNo) {
                AnlikSatis.get(i).adet--;
                System.out.println("kalan Urun adedi = " + AnlikSatis.get(i).adet);
                if (AnlikSatis.get(i).adet == 0) {

                    sonuc = true;
                }
                for (UrunA f : urunler) {
                    f.adet++;
                }
            }

        }
        if (!sonuc) {

        }
        return sonuc;
    }

    public boolean nakitOde() {
        boolean sonuc = false;
        double gecici = 0;
        int a = JOptionPane.showConfirmDialog(null, " Odendi mi ? ", "Uyarı", JOptionPane.YES_NO_OPTION);
        if (a == 0) {
            for (int i = 0; i < AnlikSatis.size(); i++) {
                gecici += (AnlikSatis.get(i).Satisfiyat * AnlikSatis.get(i).adet);
                for (int j = 0; j < urunler.size(); j++) {
                    if (AnlikSatis.get(i).stokNumarasi == urunler.get(j).stokNumarasi) {
                        stokIslemEkle(tarih, "Çıkış", "Satış", -AnlikSatis.get(i).adet, urunler.get(j).stokNumarasi, urunler.get(j).Alisfiyat, urunler.get(j).Satisfiyat);
                        urunler.get(j).adet -= AnlikSatis.get(i).adet;
                        //  stokIslemler.add(new StokislemA("Çıkış", "Satış", tarih, urunler.get(j).stokNumarasi, AnlikSatis.get(i).adet, urunler.get(j).Alisfiyat, urunler.get(j).Satisfiyat));
                        //   stokIslemEkleDB(tarih, "Çıkış", "Satış", AnlikSatis.get(i).adet, urunler.get(j).stokNumarasi, urunler.get(j).Alisfiyat, urunler.get(j).Satisfiyat);
                        urunStokGuncelleDB(urunler.get(j).stokNumarasi, urunler.get(j).adet);
                        sonuc = true;
                    }
                }
                kasaIslemEkle(tarih, "Giriş", "Nakit satış", gecici);
                gecici = 0;

            }
            AnlikSatis.removeAll(AnlikSatis);
        }
        return sonuc;
    }

    public boolean BakiyeOde(int no, double nakit) {
        boolean deger = false;
        boolean sonuc = true;
        double fiyat = 0;
        for (int i = 0; i < musteriler.size(); i++) {
            if (no == musteriler.get(i).no) {
                for (int j = 0; j < AnlikSatis.size(); j++) {
                    fiyat += AnlikSatis.get(j).Satisfiyat * AnlikSatis.get(j).adet;
                }
                if (nakit > fiyat) {
                    System.out.println("bakiye =" + musteriler.get(i).bakiye + " nakit = " + nakit + " fiyat = " + fiyat);
                    JOptionPane.showMessageDialog(null, " Bu işlem yapılamaz, Nakit ödenen tutar toplam fiyattan fazla!", "Uyarı ", JOptionPane.WARNING_MESSAGE);
                } else {
                    if ((fiyat - nakit) > musteriler.get(i).bakiye) {
                        int a = JOptionPane.showConfirmDialog(null, "Bakiye yetersiz borc tanımlansın mı ? ", " Uyarı", JOptionPane.YES_NO_OPTION);
                        if (a != 0) {
                            sonuc = false;
                        }
                    }
                    if (sonuc) {
                        musteriler.get(i).bakiye -= (fiyat - nakit);
                        bakiyeIslemler.add(new BakiyeislemA(musteriler.get(i).no, -(fiyat - nakit), musteriler.get(i).isim, "Alışveriş", tarih));
                        bakiyeIslemekleDB(no, -(fiyat - nakit), musteriler.get(i).isim, "Alışveriş", tarih);
                        musteriBakiyeGuncelleDB(no, musteriler.get(i).bakiye);
                        kasaIslemEkle(tarih, "Giriş", "Nakit satış", nakit);
                        JOptionPane.showMessageDialog(null, musteriler.get(i).isim + " yeni bakiyesi = " + musteriler.get(i).bakiye, "bakiye", JOptionPane.INFORMATION_MESSAGE);
                        deger = true;
                        for (int j = 0; j < AnlikSatis.size(); j++) {
                            for (int k = 0; k < urunler.size(); k++) {
                                if (AnlikSatis.get(j).stokNumarasi == urunler.get(k).stokNumarasi) {
                                    stokIslemEkle(tarih, "Çıkış", "Satış", -AnlikSatis.get(j).adet, urunler.get(k).stokNumarasi, urunler.get(k).Alisfiyat, urunler.get(k).Satisfiyat);
                                    //  *   stokIslemler.add(new StokislemA("Çıkış", "Satış", tarih, urunler.get(k).stokNumarasi, AnlikSatis.get(j).adet, urunler.get(k).Alisfiyat, urunler.get(k).Satisfiyat));
                                    //   *  stokIslemEkleDB(tarih, "Çıkış", "Satış", AnlikSatis.get(j).adet, urunler.get(k).stokNumarasi, urunler.get(k).Alisfiyat, urunler.get(k).Satisfiyat);
                                    urunler.get(k).adet -= AnlikSatis.get(j).adet;
                                    urunStokGuncelleDB(urunler.get(k).stokNumarasi, urunler.get(k).adet);
                                }
                            }
                        }
                        AnlikSatis.removeAll(AnlikSatis);
                    }
                }
                /*
                    if (nakit + musteriler.get(i).bakiye > fiyat) {
                        
                        kasaIslemEkle(tarih, "giris", "bakiye girisi", (nakit + musteriler.get(i).bakiye) -fiyat);
                        System.out.println("bakiye odede kasaya işlem eklendi");
                    }
                 */
            }
        }
        return deger;
    }
//------------------------------------------------------------KASA İŞLEMLERİ --------------------------------------------------------------------------------------------------------------

    public void kasaIslemEkle(String tarih, String tur, String aciklama, double para) {
        boolean sonuc = true;
        for (int i = 0; i < kasaIslemler.size(); i++) {
            if (tarih.equals(kasaIslemler.get(i).tarih) && aciklama.equals(kasaIslemler.get(i).aciklama)) {
                kasaIslemler.get(i).para += para;
                ToplamPara += para;
                sonuc = false;
            }
        }
        if (sonuc) {
            kasaIslemler.add(new KasaIslemA(tarih, tur, aciklama, para));
            ToplamPara += para;
        }
        kasaIslemEkleDB(tur, tarih, aciklama, para);
    }

    public boolean kasaIslemSil(String tarih, String tur, String aciklama) {
        boolean sonuc = false;
        for (int i = 0; i < kasaIslemler.size(); i++) {
            if (tarih.equals(kasaIslemler.get(i).tarih) && aciklama.equals(kasaIslemler.get(i).aciklama)) {
                if (kasaIslemler.get(i).tur.equals("Kasa Çıkışı") || kasaIslemler.get(i).tur.equals("Kasa Girişi")) {
                    kasaIslemler.remove(kasaIslemler.get(i));
                    kasaIslemSilDB(tur, tarih, aciklama);
                    JOptionPane.showMessageDialog(null, "Işlem basarıyla Silindi", "Başarılı işlem", JOptionPane.INFORMATION_MESSAGE);
                    sonuc = true;
                }
            }
        }
        if (!sonuc) {
            JOptionPane.showMessageDialog(null, " Bu işlemi kasa islemlerinden silemezsiniz", "Uyari", JOptionPane.ERROR_MESSAGE);
        }
        return sonuc;
    }
// ------------------------------------------------------------DATABASE IŞLEMLERİ-------------------------------------------------------------------------------------------------------------

    public void arraylisteVeriAktar() {
        Query m = em.createQuery("select m from Musteri m");
        List<Musteri> musterilerDB = m.getResultList();
        for (int i = 0; i < musterilerDB.size(); i++) {
            musteriler.add(new MusteriA(musterilerDB.get(i).getMusterino(), musterilerDB.get(i).getIsim(), musterilerDB.get(i).getBakiye()));
            System.out.println("müşteri eklendi");
        }
        Query u = em.createQuery("select u from Urun u");
        List<Urun> urunlerDB = u.getResultList();
        for (int i = 0; i < urunlerDB.size(); i++) {
            urunler.add(new UrunA(urunlerDB.get(i).getStokno(), urunlerDB.get(i).getAdet(), urunlerDB.get(i).getAd(), urunlerDB.get(i).getAlisfiyat(), urunlerDB.get(i).getSatisfiyat(), urunlerDB.get(i).getBarkod()));
        }
        Query si = em.createQuery("select si from Stokislem si");
        List<Stokislem> stokislemlerDB = si.getResultList();
        for (Stokislem i : stokislemlerDB) {
            stokIslemler.add(new StokislemA(i.getTur(), i.getAciklama(), i.getTarih(), i.getStokno(), i.getAdet(), i.getAlisfiyat(), i.getSatisfiyat()));
        }
        Query ki = em.createQuery("select k from Kasaislem k");
        List<Kasaislem> kasaislemlerDB = ki.getResultList();
        for (Kasaislem k : kasaislemlerDB) {
            kasaIslemler.add(new KasaIslemA(k.getTarih(), k.getTur(), k.getAciklama(), k.getPara()));
        }
        Query ba = em.createQuery("select b from Bakiyeislem b");
        List<Bakiyeislem> bakiyeIslemlerDB = ba.getResultList();
        for (Bakiyeislem b : bakiyeIslemlerDB) {
            bakiyeIslemler.add(new BakiyeislemA(b.getMusterino(), b.getPara(), b.getMusteriismi(), b.getTur(), b.getTarih()));
        }
    }

    public void musteriEkleDB(int no, String isim, double bakiye) {
        em.getTransaction().begin();
        Musteri m = new Musteri(no, isim, bakiye);
        em.persist(m);
        em.getTransaction().commit();
    }

    public void musteriSilDB(int no) {
        Query q = em.createQuery(" Delete From  Musteri m where m.musterino =:no");
        q.setParameter("no", no);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void urunEkleDB(int stokNo, String ad, int adet, double alisFiyat, double satisFiyat, String barkod) {
        em.getTransaction().begin();
        Urun u = new Urun(stokNo, ad, adet, alisFiyat, satisFiyat, barkod);
        em.persist(u);
        em.getTransaction().commit();
    }

    public void urunSilDB(int stokNo) {
        Query q = em.createQuery("delete from Urun u where u.stokno=:stokNo ");
        q.setParameter("stokNo", stokNo);
        em.getTransaction().begin();
        q.executeUpdate();
        em.getTransaction().commit();
    }

    public void urunStokGuncelleDB(int stokNo, int adet) {
        Query q = em.createQuery("select u from Urun u");
        List<Urun> urunlerDB = q.getResultList();
        for (Urun u : urunlerDB) {
            if (u.getStokno() == stokNo) {
                em.getTransaction().begin();
                u.setAdet(adet);
                em.getTransaction().commit();
            }
        }
    }

    public void urunGuncelleDB(int stokNo, double alisFiyat, double satisFiyat, String barkod, String ad) {
        Query q = em.createQuery("select u from Urun u");
        List<Urun> urunlerDB = q.getResultList();
        for (Urun u : urunlerDB) {
            if (u.getStokno() == stokNo) {
                em.getTransaction().begin();
                u.setAlisfiyat(alisFiyat);
                u.setSatisfiyat(satisFiyat);
                u.setBarkod(barkod);
                u.setAd(ad);
                em.getTransaction().commit();
            }
        }
    }

    public void stokIslemEkleDB(String tarih, String tur, String aciklama, int adet, int stokNo, double alisFiyat, double satisFiyat) {
        boolean sonuc = true;
        int boyut = 0;
        int islemNo;
        Query q = em.createQuery("select sislem from Stokislem sislem");
        List<Stokislem> stokIslemlerDB = q.getResultList();
        if (stokIslemlerDB.size() == 0) {
            em.getTransaction().begin();
            Stokislem s = new Stokislem(1, tarih, tur, aciklama, adet, stokNo, alisFiyat, satisFiyat);
            em.persist(s);
            em.getTransaction().commit();
        } else {
            for (Stokislem si : stokIslemlerDB) {
                if (si.getAciklama().equals(aciklama) && si.getTarih().equals(tarih) && si.getStokno() == (stokNo)) {
                    em.getTransaction().begin();
                    si.setAdet(si.getAdet() + adet);
                    em.getTransaction().commit();
                    sonuc = false;
                }

            }
            if (sonuc) {
                boyut = stokIslemlerDB.size();
                islemNo = stokIslemlerDB.get(boyut - 1).getIslemno() + 1;
                em.getTransaction().begin();
                Stokislem s = new Stokislem(islemNo, tarih, tur, aciklama, adet, stokNo, alisFiyat, satisFiyat);
                em.persist(s);
                em.getTransaction().commit();
            }
        }
    }

    public void stokIslemSilDB(String aciklama, String tarih, String tur, int stokNo) {
        Query q = em.createQuery("select u from Stokislem u");
        //   Query p = em.createQuery("delete from Stokislem m where m.islemno=:islemNo");
        //   p.setParameter("islemNo", islemno);
        List<Stokislem> stokIslemlerDB = q.getResultList();
        for (Stokislem s : stokIslemlerDB) {
            if ((s.getAciklama().equals(aciklama) && s.getTarih().equals(tarih)) && ((tur.equals(s.getTur()) && stokNo == s.getStokno()))) {
                int islemno = s.getIslemno();
                Query p = em.createQuery("delete from Stokislem m where m.islemno=:islemNo");
                p.setParameter("islemNo", islemno);
                System.out.println(islemno);
                em.getTransaction().begin();
                p.executeUpdate();
                em.getTransaction().commit();
            }
        }
    }

    public void bakiyeIslemekleDB(int MusteriNo, double para, String musteriAdi, String tur, String tarih) {
        boolean sonuc = true;
        int islemNo = 1;
        Query q = em.createQuery("select bislem from Bakiyeislem bislem");
        List<Bakiyeislem> bakiyeIslemlerDB = q.getResultList();
        if (bakiyeIslemlerDB.size() == 0) {
            em.getTransaction().begin();

            Bakiyeislem b = new Bakiyeislem(islemNo, tarih, MusteriNo, musteriAdi, para, tur);
            em.persist(b);
            em.getTransaction().commit();
        } else {
            int boyut = bakiyeIslemlerDB.size();
            islemNo = bakiyeIslemlerDB.get(boyut - 1).getIslemno() + 1;
            em.getTransaction().begin();
            Bakiyeislem b = new Bakiyeislem(islemNo, tarih, MusteriNo, musteriAdi, para, tur);
            em.persist(b);
            em.getTransaction().commit();
        }
    }

    public void bakiyeIslemSilDB(String tur, String tarih, double para, int musteriNo) {
        Query q = em.createQuery("select u from Bakiyeislem u");

        List<Bakiyeislem> bakiyeislemlerDB = q.getResultList();
        for (Bakiyeislem a : bakiyeislemlerDB) {
            if (a.getTarih().equals(tarih) && (a.getPara() == para && a.getTur().equals(tur)) && a.getMusterino() == musteriNo) {
                int islemno = a.getIslemno();
                Query p = em.createQuery("delete from Bakiyeislem m where m.islemno =:islemNo");
                p.setParameter("islemNo", islemno);
                System.out.println(islemno);
                em.getTransaction().begin();
                p.executeUpdate();
                em.getTransaction().commit();
            }
        }

    }

    public void kasaIslemEkleDB(String tur, String tarih, String aciklama, Double para) {
        boolean sonuc = true;
        int sayac = 1;
        Query q = em.createQuery("select ki from Kasaislem ki");
        List<Kasaislem> kasaIslemlerDB = q.getResultList();
        System.out.println("kasa islemler db geldi");
        if (kasaIslemlerDB.size() == 0) {
            em.getTransaction().begin();
            Kasaislem islem = new Kasaislem(sayac, tur, tarih, aciklama, para);
            em.persist(islem);
            em.getTransaction().commit();
            System.out.println("kasa islem db eklendi");

        } else {
            for (Kasaislem k : kasaIslemlerDB) {
                if (k.getAciklama().equals(aciklama) && k.getTarih().equals(tarih)) {
                    em.getTransaction().begin();
                    k.setPara(k.getPara() + para);
                    em.getTransaction().commit();
                    sonuc = false;
                    break;
                }
            }
            if (sonuc) {
                int boyut = kasaIslemlerDB.size();
                int islemNo = kasaIslemlerDB.get(boyut - 1).getIslemno() + 1;
                em.getTransaction().begin();
                Kasaislem islem = new Kasaislem(islemNo, tur, tarih, aciklama, para);
                em.persist(islem);
                em.getTransaction().commit();
                System.out.println("kasa islem db eklendi");

            }
        }
    }

    public void kasaIslemSilDB(String tur, String tarih, String aciklama) {
        Query a = em.createQuery("select u from Kasaislem u");

        List<Kasaislem> kasaIslemlerDB = a.getResultList();
        for (Kasaislem k : kasaIslemlerDB) {
            if (k.getTarih().equals(tarih) && k.getAciklama().equals(aciklama)) {
                int islemno = k.getIslemno();
                Query q = em.createQuery("delete from Kasaislem u where u.islemno=:islemNo");
                q.setParameter("islemNo", islemno);
                System.out.println(islemno);
                em.getTransaction().begin();
                q.executeUpdate();
                em.getTransaction().commit();
            }
        }
    }

    public void musteriBakiyeGuncelleDB(int musteriNo, double bakiye) {
        Query q = em.createQuery("select mu from Musteri mu");
        List<Musteri> musteriBakiyeDB = q.getResultList();
        for (Musteri m : musteriBakiyeDB) {
            if (m.getMusterino() == musteriNo) {
                em.getTransaction().begin();
                m.setBakiye(bakiye);
                em.getTransaction().commit();
            }
        }
    }
}
