package com.cndinfo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// SKT 멤버십 혜택 브랜드 목록 Save
public class InsertSKTMembership {
	
	static String category_id;
	
	private static final Logger logger = LoggerFactory.getLogger(InsertSKTMembership.class);
	
	public static class Node {
		String brand_id;
		String brand_name;
		
		public Node(String brand_id, String brand_name) {
			this.brand_id = brand_id;
			this.brand_name = brand_name;
		}
	}
	
	public static void main(String[] args) {
		
		try {
			// 직접 조회할 페이지 리스트들을 담을 파일을 읽음.
			BufferedReader br = new BufferedReader(new FileReader("./docs/Membership_Url_List/SKT_List.txt"));
			
			String telecom = "SKT";
			String category_name = "";
			
			// MySQL Drvier 지정
			Class.forName("com.mysql.cj.jdbc.Driver");
			// ex) jdbc:mysql://localhost:3306/${database}?characterEncoding=UTF-8
			String url = "${DB Url}";
			String user = "${DB username}";
			String pw = "${DB password}";
			
			Connection conn = DriverManager.getConnection(url, user, pw);
			
			while(true) {
				String input = br.readLine();
				if(input == null) {
					break;
				} else {
					if(input.startsWith("http")) {
						
						for(Node i : parseHtml(input)) {
							String sql = "INSERT INTO membership_brands(category_id, category_name, brand_id, brand_name, telecom)";
							
							Statement stmt = conn.createStatement();
							
							stmt.execute(sql.concat(" VALUES ('").concat(category_id).concat("', '").concat(category_name).concat("', '").concat(i.brand_id).concat("', '").concat(i.brand_name).concat("', '").concat(telecom).concat("')"));
							
							if(stmt != null) {
								stmt.close();
							}
						}
					} else {
						category_name = input;
					}
				}
			}
			
			if(conn != null) {
				conn.close();
			}
			
			br.close();
			
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}
	
	static List<Node> parseHtml(String url) throws IOException {
		
		Document doc = Jsoup.connect(url).get();
		
		List<Node> brand_list = new ArrayList<>();
		
		String[] temp = doc.getElementById("sel-list").toString().split("\n");
		String[] arr = doc.getElementById("categoryMid").toString().split(" ");
		
		for(String i : arr) {
			if(i.startsWith("value=")) {
				String[] i_temp = i.split("=");
				category_id = i_temp[1].replaceAll("\"", "").replace(">","");
			}
		}
		
		for(int i=1; i<temp.length - 1; i++) {
			int start_idx = temp[i].indexOf("=") + 2;
			int end_idx = start_idx + 1;
			while(true) {
				String brand_id = "";
				String brand_name = "";
				if(temp[i].charAt(end_idx) == '\"') {
					brand_id = temp[i].substring(start_idx, end_idx);
					if(temp[i].substring(end_idx + 2).contains("data-selected")) {
						brand_name = temp[i].replaceAll("data-selected", "").substring(end_idx + 6).replaceAll("<a>", "").replaceAll("</a>", "").replaceAll("</li>","").trim().replaceAll("&amp;", "&");
					} else {
						brand_name = temp[i].substring(end_idx + 2).replaceAll("<a>", "").replaceAll("</a>", "").replaceAll("</li>","").trim().replaceAll("&amp;", "&");
					}
					
					brand_list.add(new Node(brand_id, brand_name));
					break;
				}
				end_idx++;
			}
		}
		
		return brand_list;
	}

}
