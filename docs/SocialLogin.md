#<a href="https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api">REST API Kakao Login</a>

1. OAuth2.0 기반의 로그인 서비스이다.
2. 로그인 완료 시 토근 발급
3. 사전 설정해야하는 항목들이 존재함.<br>
	<a href="https://developers.kakao.com/docs/latest/ko/getting-started/app#platform">플랫폼 등록</a>&rarr; Domain으로 등록함.<br>
		 <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#kakao-login-activate">카카오 로그인 활성화</a><br>
		 <a href="https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#kakao-login-redirect-uri">Redirect URI 등록</a><br>
		<a href="https://developers.kakao.com/docs/latest/ko/kakaologin/prerequisite#consent-item">동의 항목</a>
	
4. 사용자 로그인 처리는 서비스에서 자체 구현해야 함. 

&rarr; Security 적용 가능한 것 같음.(참고 사항 제공)

#<a href="https://developers.naver.com/docs/login/devguide/devguide.md#%EB%84%A4%EC%9D%B4%EB%B2%84%20%EB%A1%9C%EA%B7%B8%EC%9D%B8-%EA%B0%9C%EB%B0%9C%EA%B0%80%EC%9D%B4%EB%93%9C">Naver Login</a>

1. OAuth 2.0 오픈 프로토콜을 통해 서비스 됨.
2. JS plugin(SDK) 제공 <br>&rarr; jQuery 1.10.0 이상, IE 7 버전 이상, 기타 모던 브라우저에서 작동<br>
3. 점검 사항
	- application 등록 ( 사용 API에 네이버 로그인 선택 필수 )<br>
	- 승인 여부 ( 검수 승인 완료 시에만 사용 가능 )
4. 접근 토큰 이라는 것을 발급함.
5. 네이버 로그인 요청 시 Response 값이 로그인 성공에 대한 code 와 status만 전달 되는 것 같음. Security 적용은 아직 잘 모르겠음.

# <a href="https://docs.github.com/ko/apps/oauth-apps/building-oauth-apps/creating-an-oauth-app">GitHub Login</a>

1. OAuth 토큰 방식을 사용.
2. 앱 등록 후 권한 부여.
3. kakao, naver와 같은 REST 방식의 로그인 요청 가능.

# <a href="https://cloud.google.com/identity-platform/docs/use-rest-api?hl=ko#section-verify-custom-token">Google Login</a>

&rarr; 위 소셜 로그인 들과 크게 다르지 않음.( OAuth 토큰 사용 및 REST API 사용 )

>결론 : 위 4가지 소셜 로그인 모두 가능하다고 생각됨. 그러나 배포 후 도메인 주소가 필요함.
추후 배포 하여 운영하게 된다면 소설 로그인 적용하는 것으로 결정.