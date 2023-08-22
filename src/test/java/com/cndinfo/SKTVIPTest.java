package com.cndinfo;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;

public class SKTVIPTest {
	
	@Test
	public void getVIPTest() {
		
		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/program/vippick.do";
		
		String fileName = "./docs/MembershipTest/SKTVIPTest.html";
		
		
		try {
			Document doc = Jsoup.connect(url).get();
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			
			bw.write(doc.getAllElements().toString());
			
			bw.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
