package com.cndinfo.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MembershipServiceTest {
	
//	@Test
	public void sktTest() throws IOException {
		
//		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/list-tab1.do";
//		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=998";
		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/detail.do?brandId=1053";
		
		Document doc = Jsoup.connect(url).get();
		
//		String fileName = "./docs/MembershipTest/SKTTest.html";
//		String fileName = "./docs/MembershipTest/SKTTest2.html";
		
//		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		
		String[] temp = doc.getElementById("sel-list").toString().split("\n");
		String[] arr = doc.getElementById("categoryMid").toString().split(" ");
		
		for(String i : arr) {
			if(i.startsWith("value=")) {
				String[] i_temp = i.split("=");
				System.out.println(i_temp[1].replaceAll("\"", "").replace(">",""));
			}
		}
//		List<Integer> id_list = new ArrayList<>();
		
		// 하나의 브랜드에서 카테고리 id를 제외하고 브랜드 별 id 뽑아내기
		for(int i=1; i<temp.length - 1; i++) {
			int start_idx = temp[i].indexOf("=") + 2;
			int end_idx = start_idx + 1;
			while(true) {
				String brand_id = "";
				String brand_name = "";
				if(temp[i].charAt(end_idx) == '\"') {
					brand_id = temp[i].substring(start_idx, end_idx);
					if(temp[i].substring(end_idx + 2).contains("data-selected")) {
						brand_name = temp[i].replaceAll("data-selected", "").substring(end_idx + 6).replaceAll("<a>", "").replaceAll("</a>", "").replaceAll("</li>","").trim();
					} else {
						brand_name = temp[i].substring(end_idx + 2).replaceAll("<a>", "").replaceAll("</a>", "").replaceAll("</li>","").trim();
					}
					break;
				}
				
				
				end_idx++;
			}
		}
		
//		bw.write(doc.getAllElements().toString());
//		bw.close();
	}
	
//	@Test
	public void KTTest() throws IOException {
		
		String url = "https://membership.kt.com/discount/partner/PartnerList.do";
		
		Document doc = Jsoup.connect(url).get();
		
		String fileName = "./docs/MembershipTest/KTTest1.html";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		
		bw.write(doc.getAllElements().toString());
		
		bw.close();
	}
	
	@Test
	public void LGTest() throws IOException {
		
		String url = "https://www.lguplus.com/benefit-membership";
		
		Document doc = Jsoup.connect(url).get();
		
		String fileName = "./docs/MembershipTest/LGTest1.html";
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
		
		bw.write(doc.getAllElements().toString());
		
		bw.close();
	}

}
