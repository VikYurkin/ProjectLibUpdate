
FROM tomcat
ENV DB_USERNAME=name
ENV DB_PASSWORD=pass
ENV DB_URL=url

COPY target/*.war /usr/local/tomcat/webapps/projectlibupdate.war
