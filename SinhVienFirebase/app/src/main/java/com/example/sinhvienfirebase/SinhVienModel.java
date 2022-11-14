package com.example.sinhvienfirebase;

public class SinhVienModel {
    String anh, hoTen, lop, maSV;
    SinhVienModel(){}

    public SinhVienModel(String anh, String hoTen, String lop, String maSV) {
        this.anh = anh;
        this.hoTen = hoTen;
        this.lop = lop;
        this.maSV = maSV;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getLop() {
        return lop;
    }

    public void setLop(String lop) {
        this.lop = lop;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMaSV(String maSV) {
        this.maSV = maSV;
    }
}
