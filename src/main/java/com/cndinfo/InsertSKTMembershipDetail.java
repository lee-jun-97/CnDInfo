package com.cndinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InsertSKTMembershipDetail {
	
	public static void main(String[] args) {
		
		
		try {
			// MySQL Drvier 지정
			Class.forName("com.mysql.cj.jdbc.Driver");
			// ex) jdbc:mysql://localhost:3306/${database}?characterEncoding=UTF-8
			String url = "${DB Url}";
			String user = "${DB username}";
			String pw = "${DB pw}";
			
			Connection conn = DriverManager.getConnection(url, user, pw);
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM membership_brands WHERE telecom='SKT'");
			
			List<List<String>> list = new ArrayList<>();
			
			while(!rs.isLast()) {
				
				rs.next();
				list.add(makeSQL(rs.getString("brand_id"), rs.getString("brand_name")));
			}
			
			int result = 0;
			
			for(List<String> i : list) {
				result += i.size();
				for(String j : i) {
					System.out.println(j);
					stmt.execute(j);
				}
			}
			
			System.out.println(result);
			
			if(stmt != null) {
				stmt.close();
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
	}
	
	public static List<String> makeSQL(String brand_id, String brand_name) throws Exception {
		
		String weburl = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=".concat(brand_id);
		
		Document doc = Jsoup.connect(weburl).get();
		
		String[] temp = doc.getElementsByTag("dd").first().getElementsByTag("dt").first().toString().split("\n");
		String type = "";
		for(String i : temp) {
			if(i.contains("<")) {
				continue;
			}
			
			type = i.trim();
		}
		
		List<String> list = new ArrayList<>();
		
		String[] tag = doc.getElementsByTag("dd").first().getElementsByTag("dl").first().getElementsByTag("dd").toString().split("\n");
		for(String i : tag) {
			String sql = "INSERT INTO membership_brands_detail(telecom, brand_id, brand_name, membership_type, grade, detail) VALUES ('SKT', '"
					.concat(brand_id).concat("', '").concat(brand_name).concat("', '").concat(type).concat("', '");
			if(i.contains("badge")) { 
				String grade = "";
				
				if(i.contains("vip")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("VIP");
				}
				
				if(i.contains("gold")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Gold");
				}
				
				if(i.contains("silver")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Silver");
				}
				
				if(i.contains("lite")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Lite");
				}
				
				for(int j=i.length() -1 ; j>=0; j--) {
					if(i.charAt(j) == '>') {
						String detail = i.substring(j + 1).trim();
						sql = sql.concat(grade).concat("', '").concat(detail).concat("')");
						list.add(sql);
						break;
					}
				}
			}
			
		}
		
		tag = doc.getElementsByTag("dd").first().getElementsByTag("dl").last().getElementsByTag("dd").toString().split("\n");
		temp = doc.getElementsByTag("dd").first().getElementsByTag("dt").last().toString().split("\n");
		
		for(String i : temp) {
			if(i.contains("<")) {
				continue;
			}
			
			type = i.trim();
		}
		
		for(String i : tag) {
			String sql = "INSERT INTO membership_brands_detail(telecom, brand_id, brand_name, membership_type, grade, detail) VALUES ('SKT', '"
					.concat(brand_id).concat("', '").concat(brand_name).concat("', '").concat(type).concat("', '");
			if(i.contains("badge")) { 
				String grade = "";
				
				if(i.contains("vip")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("VIP");
				}
				
				if(i.contains("gold")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Gold");
				}
				
				if(i.contains("silver")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Silver");
				}
				
				if(i.contains("lite")) {
					if(grade.length() != 0 ) {
						grade = grade.concat(" & ");
					}
					grade = grade.concat("Lite");
				}
				
				for(int j=i.length() -1 ; j>=0; j--) {
					if(i.charAt(j) == '>') {
						String detail = i.substring(j + 1).trim();
						sql = sql.concat(grade).concat("', '").concat(detail).concat("')");
						list.add(sql);
						break;
					}
				}
			}
		}
		
		return list;
	}

}
