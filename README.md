#Environment
Suited for Eclipse IDE and JDK1.8+

#Components
	1. DataStore: JPA (Hibernate)
	2. Configuration: SimpleXML
	3. Logging: slf4j (api), log4j2 (core)
	4. Api: jersey (rest/json) + gson
	5. Api doc: swagger

#Project properties
	1. build.properties: jdk, war name
	2. tomcat.properties: url, username, password (tomcat7:redeploy goal)
	3. webapp.properties: path, api package, name, etc...