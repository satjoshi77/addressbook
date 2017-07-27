# addressbook_vertx
java and vertx based example service for addressbook

* needs: java 8 and maven 3.3.9

* Build Steps:
 * git clone https://github.com/satjoshi77/addressbook_vertx.git
 * cd addressbook_vertx
 * mvn package

* Run app:
 * java -jar target/addressbook-app-0.0.1-SNAPSHOT-fat.jar

* POST Request:  ```

http://localhost:8090/accounts/111/contacts

	POST /accounts/111/contacts HTTP/1.1
	Host: localhost:8090
	Content-Type: application/json
	

	{
    "id": "222",
    "firstName": "sat",
    "lastName": "jos",
    "email": "abc@gmail.com",
	"address":{
		"street":"123 main street",
		"city":"fremont",
		"state":"CA",
		"country":"US",
		"zip":"94555"
	},
	"phoneNumber":{
		"areaCode":"510",
		"localNumber":"1231234"
	}

  	}
 ```

* GET Request:
 ```
    http://localhost:8090/accounts/111/contacts/222
	GET /accounts/111/contacts/222 HTTP/1.1
	Host: localhost:8090
	Content-Type: application/json

	Response:
	{
    "address": {
        "city": "fremont",
        "country": "US",
        "state": "CA",
        "street": "123 main street",
        "zip": "94555"
    },
    "email": "abc@gmail.com",
    "firstName": "sat",
    "id": "222",
    "lastContactedDate": "Mon Jul 24 01:56:23 PDT 2017",
    "lastName": "jos",
    "phoneNumber": {
        "areaCode": "510",
        "localNumber": "1231234"
    }
	}
 ```
* main class:
  * com.addressbook.server.Main

* property file:
  * addressbook.properties
