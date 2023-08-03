import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
public class DB {
	static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	static Date date = new Date();
	public static String datearr[] = formatter.format(date).split("-");
	public static String dateee=datearr[0]+"-"+datearr[1]+"-"+datearr[2];
	public static Connection con;
	public static boolean hasData=false;
	public static Statement stmt;
	public static ResultSet rs;
	public static PreparedStatement pstmt;
	public void Connect()
	{
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("JDBC:sqlite:FarmManagement.db");
			initialise();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void initialise()
	{
		if(!hasData)
			hasData=true;
		try {
			String q="SELECT name FROM sqlite_master WHERE type='table' AND name='composite'";
			stmt = con.createStatement();
			rs = stmt.executeQuery(q);
			if(!rs.next())
			{
				System.out.println("please wait while we Initialise the Database!");
				String query="CREATE TABLE composite(id integer PRIMARY KEY AUTOINCREMENT NOT NULL,name varchar(255),price bigint,quantity bigint);";
				Statement stmt2 = con.createStatement();
				stmt2.execute(query);
				
				query="CREATE TABLE lenderlist(id integer PRIMARY KEY AUTOINCREMENT NOT NULL ,name varchar(255),address varchar(255),mobileno varchar(255),acres varchar(255));";
				Statement stmt3 = con.createStatement();
				stmt3.execute(query);
				
				query="CREATE TABLE lenderledger(id integer,tid integer PRIMARY KEY AUTOINCREMENT NOT NULL,amount varchar(255),description varchar(255),dates date,foreign key(id) references lenderlist(id));";
				Statement stmt4 = con.createStatement();
				stmt4.execute(query);
				
				query="CREATE TABLE crops(id integer,tid integer,priceperkg varchar(255),quantityinkg varchar(255),profit varchar(255),dates date,foreign key(id) references lenderlist(id));";
				Statement stmt5 = con.createStatement();
				stmt5.execute(query);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public boolean addComposite(String name,String price,String quantity)
	{
		try {
			String q="INSERT INTO composite(name,price,quantity) values(?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, name);
			pstmt.setLong(2, Long.parseLong(price));
			pstmt.setLong(3,Long.parseLong(quantity));
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean UpdateComposites(String id,String name,String price,String quantity)
	{
		try {
			String q="UPDATE composite SET name=?,price=?,quantity=? WHERE id=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, name);
			pstmt.setString(2, price);
			pstmt.setString(3, quantity);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean DeleteComposites(String id,String name,String price,String quantity)
	{
		try {
			String q="DELETE FROM composite WHERE id=? and name=? and price=? and quantity=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1,id);
			pstmt.setString(2, name);
			pstmt.setString(3, price);
			pstmt.setString(4, quantity);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean addlenderlist(String name,String address,String mob, String acres)
	{
		try {
			String q="INSERT INTO lenderlist(name,address,mobileno,acres) values(?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, mob);
			pstmt.setString(4, acres);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean DeleteLenderList(String id,String name)
	{
		try {
			String q1="DELETE FROM lenderledger WHERE id=?;";
			pstmt = con.prepareStatement(q1);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			
			String q2="DELETE FROM crops WHERE id=?;";
			pstmt = con.prepareStatement(q2);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			
			
			String q="DELETE FROM lenderlist WHERE id=? and name=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1,id);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean UpdateLender(String id,String name,String address,String mob, String acres)
	{
		try {
			String q="UPDATE lenderlist SET name=?,address=?,mobileno=?,acres=? WHERE id=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, name);
			pstmt.setString(2, address);
			pstmt.setString(3, mob);
			pstmt.setString(4, acres);
			pstmt.setString(5, id);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String addProfit(String id,String profit,String desc,String date)
	{
		String tid=-1+"";
		try {
			String q="INSERT INTO lenderledger(id,amount,description,dates) values(?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, profit);
			pstmt.setString(3, desc);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
			
			q="Select tid from lenderledger where id=? and amount=? and description=? and dates=?";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, profit);
			pstmt.setString(3, desc);
			pstmt.setString(4, date);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				tid=rs.getString(1);
			}
			return tid;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tid;
	}
	public boolean giveComposite(String id,String minus,String desc,String date)
	{
		try {
			String q="INSERT INTO lenderledger(id,amount,description,dates) values(?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, minus);
			pstmt.setString(3, desc);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean lendmoney(String id,long val,String desc,String date)
	{
		try {
			String q="INSERT INTO lenderledger(id,amount,description,dates) values(?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, val+"");
			pstmt.setString(3, desc);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean receivemoney(String id,long val,String desc,String date)
	{
		try {
			String q="INSERT INTO lenderledger(id,amount,description,dates) values(?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, val+"");
			pstmt.setString(3, desc);
			pstmt.setString(4, date);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String balance(String id,String y1,String y2)
	{
		String sum="";
		try {
			//String d1=y1+"-"+04+"-"+01;
			//String d2=y2+"-"+03+"-"+31;
			String q="Select SUM(amount) FROM lenderledger WHERE id=?";// AND dates BETWEEN ? AND ?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, id);
			//pstmt.setString(2, d1);
			//pstmt.setString(3, d2);
			rs = pstmt.executeQuery();
			String s="";
			while(rs.next())
			{
				s=rs.getString(1);
				if(s!=null)
					return s;
				else 
					return "0";
			}
			return sum;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return sum;
	}
	public boolean editTransaction(String tid,String amount,String desc,String datee)
	{
		try {
			String q="UPDATE lenderledger SET id=?,amount=?,description=?,dates=? WHERE tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, lenderlistadd.details[0]);
			pstmt.setString(2, amount+"");
			pstmt.setString(3, desc);
			pstmt.setString(4, datee);
			pstmt.setString(5, tid);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteTransaction(String tid)
	{
		try {
			String q="DELETE FROM lenderledger WHERE id=? and tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, lenderlistadd.details[0]);
			pstmt.setString(2, tid);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public HashMap<String,String> getComposites()
	{
		HashMap<String,String>map = new HashMap<>();
		try {
			String q="SELECT name,price FROM composite;";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String comp=rs.getString(1);
				String price=rs.getString(2);
				map.put(comp, price);
			}
			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	public HashMap<String,String> getQuantity()
	{
		HashMap<String,String>map = new HashMap<>();
		try {
			String q="SELECT name,quantity FROM composite;";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				String comp=rs.getString(1);
				String quantity=rs.getString(2);
				map.put(comp, quantity);
			}
			return map;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return map;
	}
	public boolean reduceCompositeQuantity(String name,String price,long quantity)
	{
		try {
			String q="UPDATE composite SET quantity=? WHERE name=? and price=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, quantity+"");
			pstmt.setString(2, name);
			pstmt.setString(3, price);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public double totalReceive()
	{
		double sum=0;
		try {
			String q="SELECT SUM(amount) FROM lenderledger;";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			String s="";
			while(rs.next())
			{
				s=rs.getString(1);
				if(s!=null)
					sum=Double.parseDouble(s);
			}
			return sum;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	public long totallending()
	{
		long sum=-1;
		try {
			String q="SELECT SUM(amount) FROM lenderledger WHERE amount<0;";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			String s="";
			while(rs.next())
			{
				s=rs.getString(1);
				if(s!=null)
					sum=Long.parseLong(s);
			}
			return sum;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	public boolean addToCrops(String id,String tid,String price,String quantity,String profit,String date)
	{
		try {
			String q="INSERT INTO crops(id,tid,priceperkg,quantityinkg,profit,dates) values(?,?,?,?,?,?);";
			pstmt = con.prepareStatement(q);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.setString(2, tid);
			pstmt.setString(3, price);
			pstmt.setString(4, quantity);
			pstmt.setString(5, profit);
			pstmt.setString(6, date);
			pstmt.executeUpdate();
			return true;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean editCrops(String id,String ppkg,String tkg,String date,String tid,String desc)
	{
		double pkg = Double.parseDouble(ppkg),kg=Double.parseDouble(tkg);
		double profit=(pkg*kg)/2;
		Formatter formatter = new Formatter();
		formatter.format("%.2f", profit);
		String amount=formatter.toString();
		try {
			String q="UPDATE crops SET priceperkg=?,quantityinkg=?,profit=?,dates=? WHERE id=? and tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, ppkg);
			pstmt.setString(2, tkg);
			pstmt.setString(3, amount);
			pstmt.setString(4, date);
			pstmt.setString(5, id);
			pstmt.setString(6, tid);
			pstmt.executeUpdate();
			
			q="UPDATE lenderledger SET amount=?,description=?,dates=? WHERE id=? and tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, amount);
			pstmt.setString(2, desc);
			pstmt.setString(3, date);
			pstmt.setString(4, id);
			pstmt.setString(5, tid);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public boolean deleteCrops(String id,String ppkg,String tkg,String date,String tid)
	{
		try {
			String q="DELETE FROM crops WHERE id=? and priceperkg=? and quantityinkg=? and dates=? and tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, id);
			pstmt.setString(2, ppkg);
			pstmt.setString(3, tkg);
			pstmt.setString(4, date);
			pstmt.setString(5, tid);
			pstmt.executeUpdate();
			
			q="DELETE FROM lenderledger WHERE id=? and tid=? and dates=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, id);
			pstmt.setString(2, tid);
			pstmt.setString(3, date);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	public String getTotalAcres()
	{
		String total="";
		try {
			String q="Select sum(acres) from lenderlist;";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				total=rs.getString(1);
			}
			if(total!=null)
				return total;
			else
				return "0";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return total;
	}
	public String getYearlyProfit(int year)
	{
		String profit="";
		try {
			String q="Select sum(profit) from crops where dates like '%"+year+"%'";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				profit=rs.getString(1);
			}
			
			String minus="";
			q="Select sum(amount) from lenderledger where description like '%composite%' and dates like '%"+year+"%'";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				minus=rs.getString(1);
			}
			if(minus==null)
				minus=0+"";
			if(profit!=null)
				return (Double.parseDouble(profit)+Double.parseDouble(minus))+"";
			else
				return "0";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return profit;
	}
	public String getProduction(String year) {
		String profit="";
		try {
			String q="Select sum(quantityinkg) from crops where dates like '%"+year+"%'";
			pstmt = con.prepareStatement(q);
			rs = pstmt.executeQuery();
			while(rs.next())
			{
				profit=rs.getString(1);
			}
			double r=0;
			if(profit!=null)
				r = Double.parseDouble(profit)/1;
			Formatter formatter = new Formatter();
			formatter.format("%.2f", r);
			if(profit!=null)
				return formatter.toString();
			else
				return 0+"";
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return 0+"";
	}
	public boolean deleteCropsUsingLender(String id,String tid)
	{
		try {
			String q="DELETE FROM crops WHERE id=? and tid=?";
			pstmt = con.prepareStatement(q);
			pstmt.setString(1, id);
			pstmt.setString(2, tid);
			pstmt.executeUpdate();
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
