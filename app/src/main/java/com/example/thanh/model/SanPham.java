package com.example.thanh.model;

public class SanPham {
    private int maSp;
    private String tenSp;
 /*   private int maNcc;
    private int maLoai;
    private int soLuongTon;
    private String anhBia;
    private String giaBan;
    private String baoHanh;
    private String moTa;
    private String ngayCapNhat;
    private int slBanRa;
    private String tongDanhGia;*/

    public SanPham() {
    }

    public SanPham(int maSp, String tenSp) {
        this.maSp = maSp;
        this.tenSp = tenSp;
    }

    public int getMaSp() {
        return maSp;
    }

    public void setMaSp(int maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    @Override
    public String toString() {
        return "SanPham{" +
                "maSp=" + maSp +
                ", tenSp='" + tenSp + '\'' +
                '}';
    }
}
