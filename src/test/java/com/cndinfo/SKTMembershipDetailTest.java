package com.cndinfo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

public class SKTMembershipDetailTest {

//	@Test
	public void getMembershipAtDB() {
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
			
			while(!rs.isLast()) {
				rs.next();
				System.out.println(rs.getString("brand_id"));
			}
			
			if(stmt != null) {
				stmt.close();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void getMembershipDetail() {
		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=998";
		
		try {
			Document doc = Jsoup.connect(url).get();
			
//			String fileName = "./docs/MembershipTest/SKTDetailTest1.html";
			
//			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

			// 할인형
			String[] temp = doc.getElementsByTag("dd").first().getElementsByTag("dt").first().toString().split("\n");
			String type = "";
			for(String i : temp) {
				if(i.contains("<")) {
					continue;
				}
				
				type = i.trim();
			}
			
			
			
			String[] tag = doc.getElementsByTag("dd").first().getElementsByTag("dl").first().getElementsByTag("dd").toString().split("\n");
			
			System.out.println(type);
			
			for(String i : tag) {
				if(i.contains("badge")) { 
					String grade = "";
					
					if(i.contains("vip")) {
						if(grade.length() != 0 ) {
							grade = grade.concat("&");
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
					
					System.out.println(grade);
					
					for(int j=i.length() -1 ; j>=0; j--) {
						if(i.charAt(j) == '>') {
							System.out.println(i.substring(j + 1).trim());
							break;
						}
					}
				}
			}
			
			System.out.println();
			
			// 적립형
			
			tag = doc.getElementsByTag("dd").first().getElementsByTag("dl").last().getElementsByTag("dd").toString().split("\n");
			temp = doc.getElementsByTag("dd").first().getElementsByTag("dt").last().toString().split("\n");
			
			for(String i : temp) {
				if(i.contains("<")) {
					continue;
				}
				
				type = i.trim();
			}
			
			System.out.println(type);
			
			for(String i : tag) {
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
					
					System.out.println(grade);
					
					for(int j=i.length() -1 ; j>=0; j--) {
						if(i.charAt(j) == '>') {
							System.out.println(i.substring(j + 1).trim());
							break;
						}
					}
				}
			}
			System.out.println();
			
//			bw.write(doc.getElementsByTag("dd").first().toString());
			
//			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
