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
		
		String url = "https://sktmembership.tworld.co.kr/mps/pc-bff/benefitbrand/list-tab1.do";
		
		Document doc = Jsoup.connect(url).get();
		
		System.out.println(doc.getAllElements());
	}

}
