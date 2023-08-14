package com.cndinfo.service;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MembershipServiceTest {
	
	@Test
	public void sktTest() throws IOException {
		
//		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/list-tab1.do";
//		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=998";
		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=1053";
		
		Document doc = Jsoup.connect(url).get();
		
//		String fileName = "./docs/MembershipTest/SKTTest.html";
//		String fileName = "./docs/MembershipTest/SKTTest2.html";
		
//		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		
		String[] temp = doc.getElementById("sel-list").toString().split("\n");
//		List<Integer> id_list = new ArrayList<>();
		
		// 하나의 브랜드에서 카테고리 id를 제외하고 브랜드 별 id 뽑아내기
		for(int i=1; i<temp.length; i++) {
			int start_idx = temp[i].indexOf("=") + 2;
			int end_idx = start_idx;
			while(true) {
				end_idx++;
				if(temp[i].charAt(end_idx) == '\"') {
					System.out.println(temp[i].substring(start_idx, end_idx));
					break;
				}
			}
		}
		
//		bw.write(doc.getAllElements().toString());
//		bw.close();
	}

}
