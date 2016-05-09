//package db2;

import java.sql.*;
import java.util.Scanner;
public class Adatb2_1 {

  private static Connection conn = null;
  private static Statement s = null;
  private static ResultSet rs = null;
  //private static java.io.Console d = System.console();
  private static PreparedStatement ps = null;
  private static CallableStatement cst = null;
//================KAPCSOLAT==============
public void DrReg(){
  try {
     Class.forName("oracle.jdbc.driver.OracleDriver");
     System.out.println("Sikeres driver regisztrálás\n");
  } catch(Exception ex) { 
     System.err.println(ex.getMessage());}
}
//--------------------------------------
public void Kapcs(){
  String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
  try {
     conn = DriverManager.getConnection(url,"d3f1kk", "D3F1KK");
     System.out.println("Sikeres kapcsolódás\n");
  } catch(Exception ex) { 
     System.err.println(ex.getMessage());}
}
//--------------------------------------
public void LeKapcs(){
  if (conn != null) {
     try {
        conn.close();
        System.out.println("Sikeres lekapcsolódás");
     } catch(Exception ex) { System.err.println(ex.getMessage());}
  }
}
//==================Lekérdez================

public static void getAutoTable(){
	String command = "select * from Auto";
	System.out.println ("Az Auto tábla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("RSZ") + " - " + rs.getString("TIPUS") + " - " + rs.getString("SZIN") + " - " + rs.getString("EVJARAT") + " - " + rs.getString("AR"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lekérdezési hiba!");
	}
}

public static void getTulajTable(){
	String command = "select * from Tulaj";
	System.out.println ("A Tulaj tábla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("adoszam") + " - " + rs.getString("nev") + " - " + rs.getString("cim") + " - " + rs.getString("szuld"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lekérdezési hiba!");
	}
}

public static void getNaploTable(){
	String command = "select * from naploAuto";
	System.out.println ("A Naplo tábla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("NAPLO_ID") + " - " + rs.getString("idopont") + " - " + rs.getString("esemeny") + " - " + rs.getString("adatok"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lekérdezési hiba!");
	}
}
//==================Feltöltés================
public static void keziBevitelAuto() {
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Kérem adja meg az autó rendszámát: ");
	String rsz = sc.nextLine();
	System.out.println("Adja meg az autó típusát: ");
	String tipus = sc.nextLine();
	System.out.println("Adja meg az autó színét: ");
	String szin = sc.nextLine();
	System.out.println("Adja meg az autó korát: ");
	int evjarat = Integer.parseInt(sc.nextLine());
	System.out.println("Adja meg az autó árát: ");
	int ar = Integer.parseInt(sc.nextLine());

	String command ="INSERT into Auto values(?,?,?,?,?)";
	try {
		ps = conn.prepareStatement(command);
		ps.setString(1,rsz);
		ps.setString(2,tipus);
		ps.setString(3,szin);
		ps.setInt(4,evjarat);
		ps.setInt(5,ar);
		ps.executeUpdate();
		System.out.println("Autó adatok feltöltve!");
	}
	catch(Exception ex) {
		System.out.println("Feltöltési hiba!");
	}
	
	sc.close();
}

public static void keziBevitelTulaj() {
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("Kérem adja meg az adószámot: ");
	String adosz = sc.nextLine();
	System.out.println("Adja meg a nevét: ");
	String nev = sc.nextLine();
	System.out.println("Adja meg a címét: ");
	String cim = sc.nextLine();
	System.out.println("Adja meg a születési idejét (2000.01.01): ");
	String szuld = sc.nextLine();

	String command ="INSERT into Tulaj values(?,?,?,to_date(?,'yyyy.mm.dd.'))";
	try {
		ps = conn.prepareStatement(command);
		ps.setString(1,adosz);
		ps.setString(2,nev);
		ps.setString(3,cim);
		ps.setString(4,szuld);
		ps.executeUpdate();
		System.out.println("Tulaj adatok feltöltve!");
	}
	catch(Exception ex) {
		System.out.println("Feltöltési hiba!");
	}
	
	sc.close();
}
//=============Törlés====================

public static void keziTorlesAuto() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg az autó rendszámát amit törölni akar: ");
	String rsz = sc.nextLine();

	String command ="DELETE FROM auto WHERE RSZ = '" + rsz + "'";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + rsz + " rendszámú autó törölve!");
	}
	catch(Exception ex) {
		System.out.println("Törlési hiba!");
	}
	
	sc.close();
}

public static void keziTorlesTulaj() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg azt a Tulaj adószámát akit törölni akar: ");
	String ado = sc.nextLine();

	String command ="DELETE FROM tulaj WHERE ADOSZAM = '" + ado + "'";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + ado + " adószámú Tulajdonos törölve!");
	}
	catch(Exception ex) {
		System.out.println("Törlési hiba!");
	}
	
	sc.close();
}

//================Módosítás==============

public static void keziModositasAuto() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg az autó rendszámát amit módosítani akar: ");
	String modrsz = sc.nextLine();
	System.out.println("Kérem adja meg az autó rendszámát: ");
	String rsz = sc.nextLine();
	System.out.println("Adja meg az autó típusát: ");
	String tipus = sc.nextLine();
	System.out.println("Adja meg az autó színét: ");
	String szin = sc.nextLine();
	System.out.println("Adja meg az autó évjáratát: ");
	int evjarat = Integer.parseInt(sc.nextLine());
	System.out.println("Adja meg az autó árát: ");
	int ar = Integer.parseInt(sc.nextLine());

	String command ="UPDATE auto SET RSZ='"+rsz+"'," + "TIPUS='"+tipus+"'," + "SZIN='"+szin+"'," + "EVJARAT="+evjarat+"," + "AR="+ar+" "+ "WHERE RSZ='"+modrsz+"'";
	System.out.println(command);
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + modrsz + " rendszámú autó módosítva!");
	}
	catch(Exception ex) {
		System.out.println("Módosítási hiba!");
	}
	
	sc.close();
}

public static void keziModositasTulaj() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg azon Tulaj adószámát melyet módosítani akar: ");
	String modadosz = sc.nextLine();
	System.out.println("Kérem adja meg az adószámot: ");
	String adosz = sc.nextLine();
	System.out.println("Adja meg a nevét: ");
	String nev = sc.nextLine();
	System.out.println("Adja meg a címét: ");
	String cim = sc.nextLine();
	System.out.println("Adja meg a születési idejét (2000.01.01): ");
	String szuld = sc.nextLine();

	String command ="UPDATE tulaj SET ADOSZAM='"+adosz+"'," + "NEV='"+nev+"'," + "CIM='"+cim+"'," + "SZULID ='"+szuld+"'"+ "WHERE ADOSZAM='"+modadosz+"'";
	//String command ="UPDATE tulaj SET (ADOSZAM = ?, NEV = ?, CIM = ?, SZULID = to_date(?,'yyyy.mm.dd.')) WHERE ADOSZAM = ?";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + modadosz + " rendszámú autó módosítva!");
	}
	catch(Exception ex) {
		System.out.println("Módosítási hiba!");
	}
	
	sc.close();
}
//==========Tárolt eljárás hívása========

public void taroltEsemeny(){
	try{
		String command = "{call p2()}";
		cst = conn.prepareCall(command);
		System.out.println("A függvény lefutott!");
	}
	catch (SQLException e){
		System.out.println("Hiba a függvényhívásban!");
	}
}

//=============TÁBLA LETREHOZASA=========
public void autoTulajLetrehoz(){
	
	String tulajuj = "CREATE table tulaj(adoszam char(10) primary key, nev char(20), cim char(50), szuld date)";
	String autouj = "CREATE table auto(rsz char(6) primary key, tipus char(10), szin char(10), ar number(8), kor number(2),tulaj references tulaj)";
	
	try {
		s = conn.createStatement();
		s.executeUpdate(tulajuj);
		System.out.println("Tulaj tábla létrehozása sikares!");
		s.executeUpdate(autouj);
		System.out.println("Auto tábla létrehozása sikares!");
	}
	catch(Exception ex){
		System.err.println(ex.getMessage());
	}
}
//=================MAIN=================
  public static void main(String args[]){
	Adatb2_1 abk = new Adatb2_1();
	abk.DrReg();
	abk.Kapcs();
	 
	abk.taroltEsemeny();
	abk.getAutoTable();
	abk.getTulajTable();
		
	abk.LeKapcs();
  }
} //D3F1KK a program vége!