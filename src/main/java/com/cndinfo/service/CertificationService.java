package com.cndinfo.service;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.cndinfo.dto.SmsResponseDTO;
import com.cndinfo.repository.UserRepository;
import com.cndinfo.util.SecurityUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class CertificationService {
	
	private static final Logger logger = LoggerFactory.getLogger(CertificationService.class);
	
	private RedisTemplate<String, String> smsRedisRepo;
	private UserRepository memberRepo;
	private SecurityUtil securityUtil;
	
	/* 
	 * application.properties 에서 @Value에 선언되어 있는 Key 값으로 값을 가져옴.
	 *  ※ static 달려 있으면 applications.properties 에서 값 못 받아옴 !!
	 * */
	@Value("${accesskey}")
	private String accessKey;
	@Value("${secretkey}")
	private String secretKey;
	@Value("${serviceid}")
	private String serviceId;
	@Value("${sendPhone}")
	private String sendPhone;
	@Value("${spring.mail.username}")
	private String mailuser;
	@Value("${spring.mail.password}")
	private String mailpw;
	@Value("${spring.mail.port}")
	private String port;
	@Value("${spring.mail.host}")
	private String host;
	@Value("${naver.api.url}")
	private String url;
	
	
	public CertificationService(RedisTemplate<String, String> redisTemplate, UserRepository memberRepo, SecurityUtil securityUtil) {
		this.smsRedisRepo = redisTemplate;
		this.memberRepo = memberRepo;
		this.securityUtil = securityUtil;
	}

	public void reqCertiEmail(String email, String code) {
		
//		logger.info("code : {}", code);
		
		Properties props = System.getProperties();
		
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.stmp.protocol", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		
		Session session = Session.getDefaultInstance(props);
		
		MimeMessage msg = new MimeMessage(session);
		
		Transport transport;
		try {
			transport = session.getTransport();
		
			msg.setFrom(new InternetAddress(mailuser, "CnDInfo"));
			msg.setContent(makeBodyEmail(code), "text/html;charset=UTF-8");
			msg.setSubject("[ CnDInfo ] 인증 코드 메일");
			
			transport.connect(host, mailuser, mailpw);
			
			msg.setRecipients(Message.RecipientType.TO, securityUtil.getEmail());
			
			smsRedisRepo.opsForValue().set(securityUtil.getEmail(), code);
			
			transport.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		} catch (MessagingException e) {
			logger.error(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage());
		}
		
	}
	
	public String makeBodyEmail(String code) {
		
		String body = "";

		body += "<p>";
		body += "인증 번호 [ " + code + " ] 를 입력하세요.";
		body += "</p>";
		body += "\n";
		
		return body;
	}
	
	public void reqCertiSMS(String email, String code) throws JsonProcessingException, InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException {
		
//		logger.info("code : {}", code);
		
		String localTime = Long.toString(System.currentTimeMillis());
		
		try {
			HttpHeaders headers = new HttpHeaders();
			
			headers.set("Content-Type", "application/json; charset=utf-8");
			headers.set("x-ncp-apigw-timestamp", localTime);
			headers.set("x-ncp-iam-access-key", accessKey);
			headers.set("x-ncp-apigw-signature-v2", makeSignature(localTime, accessKey, secretKey));
	        
	        saveCertifiactionNumber(email, code);
			
			String body = makeBodySMS(code);
			
			HttpEntity<String> entity = new HttpEntity<>(body, headers);
			
			RestTemplate restTemplate = new RestTemplate();
		    restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
		    SmsResponseDTO response = restTemplate.postForObject(new URI(url.concat(serviceId).concat("/messages")), entity, SmsResponseDTO.class);
		    
		    logger.info("Response Status Code : {}", response.getStatusCode());
		    logger.info("Response Status Name : {}", response.getStatusName());
		    
		} catch (RestClientException e) {
			logger.error(e.getMessage());
		} catch (URISyntaxException e) {
			logger.error(e.getMessage());
		}
			
	}
	
	private String makeSignature(String time, String aKey, String sKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		
		String space = " ";					// one space
		String newLine = "\n";					// new line
		String method = "POST";					// method
		// url (include query string)
		String url = "/sms/v2/services/";
		String timestamp = time;			// current timestamp (epoch)
		String accessKey = aKey;			// access key id (from portal or Sub Account)
		String secretKey = sKey;
		
		url = url.concat(serviceId).concat("/messages");
		
		logger.info(url);

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);
		
		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

		return encodeBase64String;
	}
	
	private String makeBodySMS(String code) throws JsonProcessingException {
		
		// 추후 해당 유저 번호 가져와서 SMS 보내도록 
		String toPhone = memberRepo.findByEmail(securityUtil.getEmail()).get().getPhone();
		
		JSONObject bodytJson = new JSONObject();
        JSONObject toJson = new JSONObject();
        JSONArray toArr = new JSONArray();
        
        // test용
//        toJson.put("to", sendPhone);
        toJson.put("to", toPhone);
        toArr.put(toJson);

        bodytJson.put("type" , "SMS");
        bodytJson.put("contentType" , "COMM");
        bodytJson.put("countryCode" , "82");
        bodytJson.put("from" , sendPhone);
        bodytJson.put("content" , "본인 확인 인증 번호 [ " + code + " ] 를 화면에 입력해주세요.");
        bodytJson.put("messages" , toArr);
        
        return bodytJson.toString();
	}
	
	public void saveCertifiactionNumber(String email, String code) {
		smsRedisRepo.opsForValue().set(email, code, 180, TimeUnit.SECONDS);
	}
	
	public String checkIsCerti(String email) {
		return memberRepo.findByEmail(email).get().getIscerti();
	}
	
	public boolean checkCertificationNumber(String email, String code) {
		if(smsRedisRepo.opsForValue().get(email).isEmpty()) {
			throw new NoSuchElementException();
		}
		return smsRedisRepo.opsForValue().get(email).equals(code)?true:false;
	}
	
	public void removeCode(String email) {
		smsRedisRepo.delete(email);
	}
	
	public void updateIsCerti(String email) {
		memberRepo.updateIsCerti(email, "Y");
	}
}
