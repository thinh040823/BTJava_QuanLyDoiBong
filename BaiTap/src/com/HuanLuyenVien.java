package com;

import java.util.Date;

public class HuanLuyenVien extends NhanVien {
	
	//properties:
	private int soNamKinhNghiem;
	private double heSoLuong;
	private double phuCap;
	
	// constructors:
	public HuanLuyenVien(){}
	public HuanLuyenVien(String ten, String quocTich, boolean gioiTinh, Date ngaySinh, Date ngayVaoLam,
			int soNamKinhNghiem, double heSoLuong, double phuCap){
		super(ten, quocTich, gioiTinh, ngaySinh, ngayVaoLam);
		this.soNamKinhNghiem = soNamKinhNghiem;
		this.heSoLuong = heSoLuong;
		this.phuCap = phuCap;
	}
	
	// get and set:
	public int getSoNamKinhNghiem() {
		return soNamKinhNghiem;
	}
	public void setSoNamKinhNghiem(int soNamKinhNghiem) {
		this.soNamKinhNghiem = soNamKinhNghiem;
	}
	public double getHeSoLuong() {
		return heSoLuong;
	}
	public void setHeSoLuong(double heSoLuong) {
		this.heSoLuong = heSoLuong;
	}
	public double getPhuCap() {
		return phuCap;
	}
	public void setPhuCap(double phuCap) {
		this.phuCap = phuCap;
	}
	
	//methods:
	// tinh phu cap tham nien:
	public double tinhLuong(){
		return (this.heSoLuong * 1050000) + this.phuCap + super.tinhPhuCapThamNien();
	}
}
