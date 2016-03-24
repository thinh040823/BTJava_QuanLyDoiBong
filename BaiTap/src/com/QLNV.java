package com;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class QLNV {
	//properties:
	private ArrayList<NhanVien> listNVs;
	private Scanner sc;
	
	//constructors:
	public QLNV(){
		listNVs = new ArrayList<>();
		sc = new Scanner(System.in);
	}
	
	//methods:	
	// a) them, sua, xoa nhan vien:
	// them:
	public boolean them(NhanVien nv){
		if(listNVs.add(nv))
			return true;
		else
			return false;
	}
	// sua:
	public boolean sua(int index, NhanVien nv){
		if(listNVs.set(index, nv) != null)
			return true;
		else
			return false;
	}
	// xoa:
	public boolean xoa(int index){
		if(listNVs.remove(index) != null)
			return true;
		else
			return false;
	}
	
	// b) hien thi bang danh sach nhan vien:
	public void show(){
		if(listNVs.size() == 0){
			System.out.println("Danh sach rong!");
			return;
		}
		this.xuat(listNVs);
	}
	
	// c) tim kiem cau thu voi vi tri thi dau duoc chi dinh:
	public ArrayList<Integer> timKiemViTriThiDau(String viTri){
		ArrayList<Integer> arr = new ArrayList<>();
		for(int i=0; i< listNVs.size(); i++)
			if(listNVs.get(i) instanceof CauThu && ((CauThu)listNVs.get(i)).getViTriThiDau().trim().equalsIgnoreCase(viTri.trim())){
				arr.add(i);
			}
		return arr;
	}
	
	// menu:
	public void menu(){
		int op = -1;
		boolean itf;
		do{
			String leftAlignFormat = "| %-2s | %-50s | %n";
			String line = "+----+----------------------------------------------------+%n";
			System.out.format(line);
			System.out.format("| #  | Chuc nang                                          |%n");
			System.out.format(line);
			
			System.out.format(leftAlignFormat, 1, "Them");
			System.out.format(leftAlignFormat, 2, "Xoa");
			System.out.format(leftAlignFormat, 3, "Sua");
			System.out.format(leftAlignFormat, 4, "Tim nhan vien theo vi tri(*)");
			System.out.format(leftAlignFormat, 5, "Tim kiem cau thu theo vi tri thi dau");
			System.out.format(leftAlignFormat, 6, "Hien thi");
			System.out.format(leftAlignFormat, 7, "Tao du lieu tu dong(*)");
			System.out.format(leftAlignFormat, 0, "Thoat");
			System.out.format(line);
			
			// nhap:
			do{
				System.out.print(">");
				try{
					op = Integer.parseInt(sc.nextLine());
					itf = true;
				}catch(Exception ex){
					itf = false;
				}
			}while(itf != true);
			
			// lua chon:
			switch(op){
			case 1:
				this.input();
				break;
			case 2:
				this.xoaNhanVien();
				break;
			case 3:
				this.suaNhanVien();
				break;
			case 4:
				this.xuatNhanVienByIndex();
				break;
			case 5:
				this.timKiemCauThuTheoViTriThiDau();
				break;
			case 6:
				this.show();
				break;
			case 7:
				this.nhapDuLieuTuDong();;
				break;
			}
		}while(op != 0);
	}
	
	// xoa nhan vien theo vi tri:
	private void xoaNhanVien(){
		if(listNVs.size()<1){
			System.out.println("Danh sach rong!");
			return;
		}
		boolean tf;
		int index = this.indexByName();
		if(index >=0 && index < listNVs.size()){
			do{
				System.out.print("Ban co muon xoa khong (y/n) >");
				String yn = sc.nextLine();
				if(yn.trim().equalsIgnoreCase("y"))
					if(this.xoa(index)){
						System.out.println("Xoa thanh cong!");
						tf = true;
					}
					else{
						System.out.println("Xoa that bai!");
						tf = true;
					}
				else if(yn.trim().equalsIgnoreCase("n"))
					tf = true;
				else
					tf = false;
				
			}while(tf != true);
		}
		else
			System.out.println("Khong tim thay nhan vien nay!!");
		
	}
	
	// Lay index bang ten nhan vien:nhap ten -> index xoa sua
	private int indexByName(){
		boolean tf = false;
		String name;
		ArrayList<Integer> arrCT = new ArrayList<>();
		ArrayList<Integer> arrHLV = new ArrayList<>();
		System.out.print("Nhap vao ten nhan vien >");
		name = sc.nextLine();
			
		// id ds nhan vien:
		for(int i=0; i< listNVs.size(); i++){
			if(listNVs.get(i) instanceof CauThu && listNVs.get(i).getTen().trim().equalsIgnoreCase(name.trim())){
				arrCT.add(i);
			}
			else if(listNVs.get(i) instanceof HuanLuyenVien && listNVs.get(i).getTen().trim().equalsIgnoreCase(name.trim())){
				arrHLV.add(i);
			}
		}
		
		// nhap hoac ko nhap id:
		if(arrCT.size() + arrHLV.size() ==1){
			// bang ket qua nhan vien:
			if(arrCT.size() == 1){
				this.tbCauThuByIndex(arrCT.get(0));
				return arrCT.get(0);
			}
			else{
				this.tbHuanLuyenVienByIndex(arrHLV.get(0));
				return arrHLV.get(0);
			}
			
		}
		else if(arrCT.size() + arrHLV.size() >1){
			int index = -1;
			// bang ket qua nhan vien:
			if(arrCT.size() >= 1){
				this.tbCauThu(arrCT);
			}
			if(arrHLV.size() >= 1){
				this.tbHuanLuyenVien(arrHLV);
			}
			// lua chon 1 nhan vien duy nhat:
			System.out.println("Co "+(arrCT.size()+ arrHLV.size()) + " nhan vien co cung ten:");
			do{
				System.out.print("\tIndex >");
				try{
					int at = Integer.parseInt(sc.nextLine());
					if(arrCT.size()>=1){
						for(int a:arrCT){
							if(at == a){
								index = at;
								break;
							}
						}
					}
					if(arrHLV.size()>=1){
						for(int a:arrHLV){
							if(at == a){
								index = at;
								break;
							}
						}
					}
					if(index >-1){
						tf= true;
						if(listNVs.get(index) instanceof CauThu)
							this.tbCauThuByIndex(index);
						else
							this.tbHuanLuyenVienByIndex(index);
					}else{
						System.out.println("\tIndex khong tim thay trong "+(arrCT.size()+arrHLV.size())+" nhan vien nay!");
						tf = false;
					}
				}catch(Exception ex){
					tf = false;
				}
			}while(tf != true);
			return index;
		}
		else{
			System.out.println("Khong tim thay nhan vien ten :"+name +" !");
			return -1;
		}
	}
	
	// input cau thu| huan luyen vien:
	private void input(){
		
		int op = -1;
		boolean itf = false;
		do{
			String leftAlignFormat = "| %-2s | %-50s | %n";
			String line = "+----+----------------------------------------------------+%n";
			System.out.format(line);
			System.out.format("| #  | Chuc nang                                          |%n");
			System.out.format(line);
			
			System.out.format(leftAlignFormat, 1, "Them cau thu");
			System.out.format(leftAlignFormat, 2, "Them huan luyen vien:");
			System.out.format(leftAlignFormat, 0, "Quay lai");
			System.out.format(line);
			// chuc nang:
			do{
				System.out.print("\nThem >");
				try{
					op = Integer.parseInt(sc.nextLine());
					itf = true;
				}catch(Exception ex){
					itf = false;
				}
			}while(itf != true);
			
			//cau thu| huan luyen vien
			switch(op){
				case 1:
					System.out.println("[ Them cau thu ]");
					CauThu ct = this.nhapCauThu();
					if(this.them(ct))
						System.out.println("Them thanh cong");
					else
						System.out.println("Tham that bai!");
					break;
				case 2:
					System.out.println("[ Them huan luyen vien ]");
					HuanLuyenVien hlv = this.nhapHuanLuyenVien();
					if(this.them(hlv))
						System.out.println("Them thanh cong");
					else
						System.out.println("Them that bai!");
					break;
			}
			
		}while(op != 0);
	}
	
	// nhap Cau Thu:
	private CauThu nhapCauThu(){
		CauThu ct = new CauThu();
		System.out.println("Ho va ten:");
		ct.setTen(sc.nextLine());
		System.out.println("Quoc tich:");
		ct.setQuocTich(sc.nextLine());
		System.out.println("Gioi tinh:");
		ct.setGioiTinh(this.convertStringToMale());
		System.out.println("Ngay sinh:");
		ct.setNgaySinh(this.convertStringToDate());
		System.out.println("Ngay vao lam:");
		ct.setNgayVaoLam(this.convertStringToDate());
		System.out.println("Vi tri thi dau:");
		ct.setViTriThiDau(sc.nextLine());
		System.out.println("So luot tran tham gia:");
		ct.setSoLuotTranThamGia(this.inputInt());
		System.out.println("So ban thang:");
		ct.setBanThang(this.inputInt());
		System.out.println("Luong thoa thuan:");
		ct.setLuongThoaThuan(this.inputDouble());
		return ct;		
	}

	// nhap huan luyen vien:
	private HuanLuyenVien nhapHuanLuyenVien(){
		HuanLuyenVien hlv = new HuanLuyenVien();
		System.out.println("Ho va ten:");
		hlv.setTen(sc.nextLine());
		System.out.println("Quoc tich:");
		hlv.setQuocTich(sc.nextLine());
		System.out.println("Gioi tinh:");
		hlv.setGioiTinh(this.convertStringToMale());
		System.out.println("Ngay sinh:");
		hlv.setNgaySinh(this.convertStringToDate());
		System.out.println("Ngay vao lam:");
		hlv.setNgayVaoLam(this.convertStringToDate());
		System.out.println("So nam kinh nghiem:");
		hlv.setSoNamKinhNghiem(this.inputInt());
		System.out.println("He so luong:");
		hlv.setHeSoLuong(this.inputDouble());
		System.out.println("Phu cap:");
		hlv.setPhuCap(this.inputDouble());
		return hlv;	

	}
	
	// tao du lieu tu dong:
	private void nhapDuLieuTuDong(){
		CauThu ct;
		try {
			ct = new CauThu("Nguyen Van A","Viet Nam",true,new SimpleDateFormat("dd/MM/yyyy").parse("28/12/1994"),new SimpleDateFormat("dd/MM/yyyy").parse("10/1/2014")
					,"Tien dao",23,20,20000000);
			listNVs.add(ct);
			HuanLuyenVien hlv = new HuanLuyenVien("UHkw","Phap",false,new SimpleDateFormat("dd/MM/yyyy").parse("5/3/1980"),new SimpleDateFormat("dd/MM/yyyy").parse("2/4/2010")
					, 10, 5.0, 3000000);
			listNVs.add(hlv);
			CauThu ct1 = new CauThu("Phan Van B","Viet Nam",true,new SimpleDateFormat("dd/MM/yyyy").parse("6/2/1994"),new SimpleDateFormat("dd/MM/yyyy").parse("12/3/2014")
					,"Hau ve",23,2,12000000);
			listNVs.add(ct1);
			CauThu ct2 = new CauThu("Tran Van Q","Viet Nam",true,new SimpleDateFormat("dd/MM/yyyy").parse("6/4/1994"),new SimpleDateFormat("dd/MM/yyyy").parse("2/1/2014")
					,"Trung ve",23,0,12400000);
			listNVs.add(ct2);
			////
			CauThu ct3 = new CauThu("Phan van b","Sig",true,new SimpleDateFormat("dd/MM/yyyy").parse("12/2/1993"),new SimpleDateFormat("dd/MM/yyyy").parse("1/5/2010")
					,"Hau ve",3,4,12000000);
			listNVs.add(ct3);
			CauThu ct4 = new CauThu("uhkw","Thai",true,new SimpleDateFormat("dd/MM/yyyy").parse("20/2/1992"),new SimpleDateFormat("dd/MM/yyyy").parse("1/5/2010")
					,"Hau ve",12,4,22000000);
			listNVs.add(ct4);
			HuanLuyenVien hlv1 = new HuanLuyenVien("UHkw","Nga",false,new SimpleDateFormat("dd/MM/yyyy").parse("5/3/1980"),new SimpleDateFormat("dd/MM/yyyy").parse("2/4/2010")
					, 10, 5.0, 3000000);
			listNVs.add(hlv1);
			HuanLuyenVien hlv2 = new HuanLuyenVien("UHkw","My",false,new SimpleDateFormat("dd/MM/yyyy").parse("5/3/1980"),new SimpleDateFormat("dd/MM/yyyy").parse("2/4/2010")
					, 10, 5.0, 3000000);
			listNVs.add(hlv2);
			System.out.println("Nap du lieu thanh cong!");
		} catch (ParseException e) {
			System.out.println("Nap du lieu that bai!");
		}
		
	}
	
	// tim kiem cau thu theo vi tri thi dau
	private void timKiemCauThuTheoViTriThiDau(){
		if(listNVs.size()<1){
			System.out.println("Danh sach rong!");
			return;
		}
		System.out.print("Nhap vi tri thi dau >");
		String viTri = sc.nextLine();
		ArrayList<Integer> arrIndex = this.timKiemViTriThiDau(viTri);
		if(arrIndex.size()>0){
			System.out.println("[Danh sach cau thu thi dau o vi tri: "+viTri+" ]");
			this.tbCauThu(arrIndex);
		}
		else
			System.out.println("Khong co cau thu nao thi dau o vi tri: "+viTri);
	}
	
	// hien thi nhan vien o vi tri xac dinh:
	private void xuatNhanVienByIndex(){
		if(listNVs.size()<1){
			System.out.println("Danh sach rong!");
			return;
		}
		int index = -1;
		boolean tf;
		do{
			System.out.print("Vi tri nhan vien >");
			try{
				index = Integer.parseInt(sc.nextLine());
				if(index >= 0 && index < listNVs.size()){
					if(listNVs.get(index) instanceof CauThu){
						this.tbCauThuByIndex(index);
					}
					else{
						this.tbHuanLuyenVienByIndex(index);
					}
					tf = true;
				}
				else{
					System.out.println("Vi tri nay ko ton tai!");
					tf = false;
				}
				
			}catch(Exception ex){
				tf = false;
			}
		}while(tf != true);
						
		
				
	}
	
	// xuat nhan vien:
	private void xuat(ArrayList<NhanVien> nv){
		ArrayList<Integer> lsCt = new ArrayList<>();
		ArrayList<Integer> lsHLV = new ArrayList<>();
		for(int i=0; i< nv.size(); i++)
			if(nv.get(i) instanceof CauThu){
				lsCt.add(i);
			}
			else{
				lsHLV.add(i);
			}
		if(lsCt.size()>0){
			System.out.println("[ Danh sach cau thu ]");
			this.tbCauThu(lsCt);
		}
		
		if(lsHLV.size()>0){
			System.out.println("[ Danh sach huan luyen vien ]");
			this.tbHuanLuyenVien(lsHLV);
		}
	}

	// in ra dang bang Cau thu:
	private void tbCauThu(ArrayList<Integer> arr){
		String leftAlignFormat = "| %-7s | %-13s | %-9s | %-9s | %-10s | %-13s | %-13s | %-7s | %-9s | %-16s |%n";
		String line = "+---------+---------------+-----------+-----------+------------+---------------+---------------+---------+-----------+------------------+%n";
		System.out.format(line);
		System.out.format("| Index   | Ho va ten     | Quoc tich | Gioi tinh | Ngay sinh  | Ngay vao lam  | Vi tri        | So tran | Ban thang | Luong thoa thuan |%n");
		System.out.format(line);
		for (int i = 0; i < arr.size(); i++) {
			CauThu ct = (CauThu)listNVs.get(arr.get(i));
			System.out.format(leftAlignFormat,arr.get(i) ,ct.getTen(), ct.getQuocTich(),this.convertBoolToGioiTinh(ct.getGioiTinh()),
		    		this.convertDatetoString(ct.getNgaySinh()),this.convertDatetoString(ct.getNgayVaoLam())
		    		,ct.getViTriThiDau(),ct.getSoLuotTranThamGia(),ct.getBanThang(),this.formatMoney(ct.getLuongThoaThuan()));
		}
		System.out.format(line);
	}
	
	// in ra dang bangHuan luyen vien:
	private void tbHuanLuyenVien(ArrayList<Integer> arr){
		String leftAlignFormat = "| %-7s | %-13s | %-9s | %-9s | %-9s | %-13s | %-11s | %-11s | %-15s |%n";
		String line = "+---------+---------------+-----------+-----------+------------+---------------+-------------+-------------+-----------------+%n";
		System.out.format(line);
		System.out.format("| Index   | Ho va ten     | Quoc tich | Gioi tinh | Ngay sinh  | Ngay vao lam  | Kinh nghiem | He so luong | Phu cap         |%n");
		System.out.format(line);
		for (int i = 0; i < arr.size(); i++) {
			HuanLuyenVien hlv = (HuanLuyenVien)listNVs.get(arr.get(i));
			System.out.format(leftAlignFormat,arr.get(i) ,hlv.getTen(), hlv.getQuocTich(),this.convertBoolToGioiTinh(hlv.getGioiTinh()),
		    		this.convertDatetoString(hlv.getNgaySinh()),this.convertDatetoString(hlv.getNgayVaoLam())
		    		,hlv.getSoNamKinhNghiem(),hlv.getHeSoLuong(),this.formatMoney(hlv.getPhuCap()));
		}
		System.out.format(line);
	}
	
	// sua nhan vien:
	private void suaNhanVien(){
		if(listNVs.size()<1){
			System.out.println("Danh sach rong!");
			return;
		}
		boolean tf;
		int index = this.indexByName();
		if(index >= 0 && index < listNVs.size()){
			do{
				System.out.print("Ban co muon sua nhan vien nay khong (y/n) >");
				String yn = sc.nextLine();
				if(yn.trim().equalsIgnoreCase("y")){
					if(listNVs.get(index) instanceof CauThu){
						CauThu ct = this.nhapCauThu();
						if(this.sua(index, ct))
							System.out.println("Sua thanh cong!");
						else
							System.out.println("Sua that bai!");
					}
					else{
						HuanLuyenVien hlv = this.nhapHuanLuyenVien();
						if(this.sua(index, hlv))
							System.out.println("Sua thanh cong!");
						else
							System.out.println("Sua that bai!");
					}
					tf = true;
				}
				else if(yn.trim().equalsIgnoreCase("n"))
					tf = true;
				else
					tf = false;
				
			}while(tf != true);
		}
		else
			System.out.println("Vi tri nay khong ton tai");
	}
	
	// chuyen doi chuoi sang ngay:
	private Date convertStringToDate() {
		
		SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
		Date dt = new Date();
		boolean ri = false;
		do{
			System.out.print("\tNgay/thang/nam >");
			try {
				dt =  sd.parse(sc.nextLine());
				ri = true;
			} catch (ParseException e) {
				ri = false;
			}
		}while(ri != true);
		return dt;
	}
	
	// in ra dang bangHuan luyen vien theo vi tri:
	private void tbHuanLuyenVienByIndex(int index){
		HuanLuyenVien hlv = (HuanLuyenVien)listNVs.get(index);
		String leftAlignFormat = "| %-7s | %-13s | %-9s | %-9s | %-9s | %-13s | %-11s | %-11s | %-15s |%n";
		String line = "+---------+---------------+-----------+-----------+------------+---------------+-------------+-------------+-----------------+%n";
		System.out.format(line);
		System.out.format("| Index   | Ho va ten     | Quoc tich | Gioi tinh | Ngay sinh  | Ngay vao lam  | Kinh nghiem | He so luong | Phu cap         |%n");
		System.out.format(line);
		System.out.format(leftAlignFormat,index , hlv.getTen(), hlv.getQuocTich(),this.convertBoolToGioiTinh(hlv.getGioiTinh()),
	    		this.convertDatetoString(hlv.getNgaySinh()),this.convertDatetoString(hlv.getNgayVaoLam())
	    		,hlv.getSoNamKinhNghiem(),hlv.getHeSoLuong(),this.formatMoney(hlv.getPhuCap()));
		System.out.format(line);
	}
	
	// bang cau thu theo vi tri:
	private void tbCauThuByIndex(int index){
		CauThu ct = (CauThu)listNVs.get(index);
		String leftAlignFormat = "| %-7s | %-13s | %-9s | %-9s | %-10s | %-13s | %-13s | %-7s | %-9s | %-16s |%n";
		String line = "+---------+---------------+-----------+-----------+------------+---------------+---------------+---------+-----------+------------------+%n";
		System.out.format(line);
		System.out.format("| Index   | Ho va ten     | Quoc tich | Gioi tinh | Ngay sinh  | Ngay vao lam  | Vi tri        | So tran | Ban thang | Luong thoa thuan |%n");
		System.out.format(line);
		System.out.format(leftAlignFormat,index , ct.getTen(), ct.getQuocTich(),this.convertBoolToGioiTinh(ct.getGioiTinh()),
	    		this.convertDatetoString(ct.getNgaySinh()),this.convertDatetoString(ct.getNgayVaoLam())
	    		,ct.getViTriThiDau(),ct.getSoLuotTranThamGia(),ct.getBanThang(),this.formatMoney(ct.getLuongThoaThuan()));
		System.out.format(line);
	}
	
	// gioi tinh : nhap vao gioitinh va chuyen ve boonlean
	private boolean convertStringToMale() {
		boolean ri = false, gioiTinh = false;
		String strGioiTinh;
		do{
			System.out.print("\tnu|0 => \"nu\"\n\tnam|1 => \"nam\"\n>");
			strGioiTinh = sc.nextLine();
			if(strGioiTinh.trim().equalsIgnoreCase("nam") || strGioiTinh.trim().equalsIgnoreCase("1")){
				gioiTinh = true;
				ri = true;
			}
			else if(strGioiTinh.trim().equalsIgnoreCase("nu") || strGioiTinh.trim().equalsIgnoreCase("0")){
				gioiTinh = false;
				ri = true;
			}
			else{
				ri = false;
				System.out.println("Chi duoc nhap nam hoac nu hay 1 hoac 0 !");
			}
		}while(ri != true);
		return gioiTinh;
	}

	
	// chuyen doi ngay sang chuoi:
	private String convertDatetoString(Date ngay){
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(ngay);
	}
		
	// chuyen boolean sang chuoi:
	private String convertBoolToGioiTinh(Boolean gioiTinh){
		if(gioiTinh== true)
			return "Nam";
		else
			return "Nu";
	}
	
	// dinh giang kieu tien VND:
	private String formatMoney(double number) {
        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replaceAll(",", ".");
            return resp+" VND";
        } catch (Exception e) {
            return "ERROR";
        }
    }
	
	// nhap vao kieu integer:
	private int inputInt(){
		boolean tf = false;
		int val = 0;
		do{
			System.out.print(">");
			try{
				val = Integer.parseInt(sc.nextLine());
				tf = true;
			}catch(Exception ex){
				tf = false;
				System.out.println("\tSai dinh dang!");
			}
		}while(tf != true);
		return val;
	}
	
	// nhap vao kieu double:
	private double inputDouble(){
		boolean tf = false;
		double val = 0;
		do{
			System.out.print(">");
			try{
				val = Double.parseDouble(sc.nextLine());
				tf = true;
			}catch(Exception ex){
				tf = false;
				System.out.println("\tSai dinh dang!");
			}
		}while(tf != true);
		return val;
	}
}
