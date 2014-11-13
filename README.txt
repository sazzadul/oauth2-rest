Source: http://harmeetsingh13.blogspot.no/2014/07/integrate-oauth-20-security-spring.html

cd E:\git\oauth\oauth2-rest
mvn tomcat7:run

POSTMAN:
1) POST: http://localhost:8006/oauth2-rest/oauth/token
	(x-www-form-urlencoded)
	client_id = oauth-rest
	client_secret = 19Snuse99
	grant_type = client_credentials
	
2) Access token returned

3) GET: http://localhost:8006/oauth2-rest/rest/message
	(Http header)
	Authorization = Bearer + Access token returned in 2)