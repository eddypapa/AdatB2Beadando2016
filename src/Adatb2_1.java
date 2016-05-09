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
     System.out.println("Sikeres driver regisztr�l�s\n");
  } catch(Exception ex) { 
     System.err.println(ex.getMessage());}
}
//--------------------------------------
public void Kapcs(){
  String url = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
  try {
     conn = DriverManager.getConnection(url,"d3f1kk", "D3F1KK");
     System.out.println("Sikeres kapcsol�d�s\n");
  } catch(Exception ex) { 
     System.err.println(ex.getMessage());}
}
//--------------------------------------
public void LeKapcs(){
  if (conn != null) {
     try {
        conn.close();
        System.out.println("Sikeres lekapcsol�d�s");
     } catch(Exception ex) { System.err.println(ex.getMessage());}
  }
}
//==================Lek�rdez================

public static void getAutoTable(){
	String command = "select * from Auto";
	System.out.println ("Az Auto t�bla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("RSZ") + " - " + rs.getString("TIPUS") + " - " + rs.getString("SZIN") + " - " + rs.getString("EVJARAT") + " - " + rs.getString("AR"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lek�rdez�si hiba!");
	}
}

public static void getTulajTable(){
	String command = "select * from Tulaj";
	System.out.println ("A Tulaj t�bla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("adoszam") + " - " + rs.getString("nev") + " - " + rs.getString("cim") + " - " + rs.getString("szuld"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lek�rdez�si hiba!");
	}
}

public static void getNaploTable(){
	String command = "select * from naploAuto";
	System.out.println ("A Naplo t�bla tartalma:");
	try {
		s = conn.createStatement();
		s.executeQuery(command);
		rs = s.getResultSet();
		while (rs.next()){
			System.out.println(rs.getString("NAPLO_ID") + " - " + rs.getString("idopont") + " - " + rs.getString("esemeny") + " - " + rs.getString("adatok"));	
		}
	}
	catch (Exception e) {
		System.out.println("Lek�rdez�si hiba!");
	}
}
//==================Felt�lt�s================
public static void keziBevitelAuto() {
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("K�rem adja meg az aut� rendsz�m�t: ");
	String rsz = sc.nextLine();
	System.out.println("Adja meg az aut� t�pus�t: ");
	String tipus = sc.nextLine();
	System.out.println("Adja meg az aut� sz�n�t: ");
	String szin = sc.nextLine();
	System.out.println("Adja meg az aut� kor�t: ");
	int evjarat = Integer.parseInt(sc.nextLine());
	System.out.println("Adja meg az aut� �r�t: ");
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
		System.out.println("Aut� adatok felt�ltve!");
	}
	catch(Exception ex) {
		System.out.println("Felt�lt�si hiba!");
	}
	
	sc.close();
}

public static void keziBevitelTulaj() {
	
	Scanner sc = new Scanner(System.in);
	
	System.out.println("K�rem adja meg az ad�sz�mot: ");
	String adosz = sc.nextLine();
	System.out.println("Adja meg a nev�t: ");
	String nev = sc.nextLine();
	System.out.println("Adja meg a c�m�t: ");
	String cim = sc.nextLine();
	System.out.println("Adja meg a sz�let�si idej�t (2000.01.01): ");
	String szuld = sc.nextLine();

	String command ="INSERT into Tulaj values(?,?,?,to_date(?,'yyyy.mm.dd.'))";
	try {
		ps = conn.prepareStatement(command);
		ps.setString(1,adosz);
		ps.setString(2,nev);
		ps.setString(3,cim);
		ps.setString(4,szuld);
		ps.executeUpdate();
		System.out.println("Tulaj adatok felt�ltve!");
	}
	catch(Exception ex) {
		System.out.println("Felt�lt�si hiba!");
	}
	
	sc.close();
}
//=============T�rl�s====================

public static void keziTorlesAuto() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg az aut� rendsz�m�t amit t�r�lni akar: ");
	String rsz = sc.nextLine();

	String command ="DELETE FROM auto WHERE RSZ = '" + rsz + "'";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + rsz + " rendsz�m� aut� t�r�lve!");
	}
	catch(Exception ex) {
		System.out.println("T�rl�si hiba!");
	}
	
	sc.close();
}

public static void keziTorlesTulaj() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg azt a Tulaj ad�sz�m�t akit t�r�lni akar: ");
	String ado = sc.nextLine();

	String command ="DELETE FROM tulaj WHERE ADOSZAM = '" + ado + "'";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + ado + " ad�sz�m� Tulajdonos t�r�lve!");
	}
	catch(Exception ex) {
		System.out.println("T�rl�si hiba!");
	}
	
	sc.close();
}

//================M�dos�t�s==============

public static void keziModositasAuto() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg az aut� rendsz�m�t amit m�dos�tani akar: ");
	String modrsz = sc.nextLine();
	System.out.println("K�rem adja meg az aut� rendsz�m�t: ");
	String rsz = sc.nextLine();
	System.out.println("Adja meg az aut� t�pus�t: ");
	String tipus = sc.nextLine();
	System.out.println("Adja meg az aut� sz�n�t: ");
	String szin = sc.nextLine();
	System.out.println("Adja meg az aut� �vj�rat�t: ");
	int evjarat = Integer.parseInt(sc.nextLine());
	System.out.println("Adja meg az aut� �r�t: ");
	int ar = Integer.parseInt(sc.nextLine());

	String command ="UPDATE auto SET RSZ='"+rsz+"'," + "TIPUS='"+tipus+"'," + "SZIN='"+szin+"'," + "EVJARAT="+evjarat+"," + "AR="+ar+" "+ "WHERE RSZ='"+modrsz+"'";
	System.out.println(command);
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + modrsz + " rendsz�m� aut� m�dos�tva!");
	}
	catch(Exception ex) {
		System.out.println("M�dos�t�si hiba!");
	}
	
	sc.close();
}

public static void keziModositasTulaj() {
	
	Scanner sc = new Scanner(System.in);

	System.out.println("Adja meg azon Tulaj ad�sz�m�t melyet m�dos�tani akar: ");
	String modadosz = sc.nextLine();
	System.out.println("K�rem adja meg az ad�sz�mot: ");
	String adosz = sc.nextLine();
	System.out.println("Adja meg a nev�t: ");
	String nev = sc.nextLine();
	System.out.println("Adja meg a c�m�t: ");
	String cim = sc.nextLine();
	System.out.println("Adja meg a sz�let�si idej�t (2000.01.01): ");
	String szuld = sc.nextLine();

	String command ="UPDATE tulaj SET ADOSZAM='"+adosz+"'," + "NEV='"+nev+"'," + "CIM='"+cim+"'," + "SZULID ='"+szuld+"'"+ "WHERE ADOSZAM='"+modadosz+"'";
	//String command ="UPDATE tulaj SET (ADOSZAM = ?, NEV = ?, CIM = ?, SZULID = to_date(?,'yyyy.mm.dd.')) WHERE ADOSZAM = ?";
	try {
		s = conn.createStatement();
		s.executeUpdate(command);
		System.out.println("A(z) " + modadosz + " rendsz�m� aut� m�dos�tva!");
	}
	catch(Exception ex) {
		System.out.println("M�dos�t�si hiba!");
	}
	
	sc.close();
}
//==========T�rolt elj�r�s h�v�sa========

public void taroltEsemeny(){
	try{
		String command = "{call p2()}";
		cst = conn.prepareCall(command);
		System.out.println("A f�ggv�ny lefutott!");
	}
	catch (SQLException e){
		System.out.println("Hiba a f�ggv�nyh�v�sban!");
	}
}

//=============T�BLA LETREHOZASA=========
public void autoTulajLetrehoz(){
	
	String tulajuj = "CREATE table tulaj(adoszam char(10) primary key, nev char(20), cim char(50), szuld date)";
	String autouj = "CREATE table auto(rsz char(6) primary key, tipus char(10), szin char(10), ar number(8), kor number(2),tulaj references tulaj)";
	
	try {
		s = conn.createStatement();
		s.executeUpdate(tulajuj);
		System.out.println("Tulaj t�bla l�trehoz�sa sikares!");
		s.executeUpdate(autouj);
		System.out.println("Auto t�bla l�trehoz�sa sikares!");
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
} //D3F1KK a program v�ge!