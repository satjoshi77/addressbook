# addressbook rest service
java and vertx based example service for addressbook

* Software requirement: java 8 and maven 3.3.9

* Build Steps:
 * git clone https://github.com/satjoshi77/addressbook.git
 * cd addressbook
 * mvn package
   * Note: It will run 29 juints
* Start addressbook service:
   * java -jar target/addressbook-app-0.0.1-SNAPSHOT-fat.jar
   * Note: addressbook  REST service will run at 8090 port
* Send Request:
  * Using the postman collection "addressbook.postman_collection.json" aavailable in root folder of this git repo.  Import the collection to postman and run. It has 11 integration test.

* Resource urls:
 * retrieve contact(s)- GET /accounts/{accountId}/contacts/{contactId}
 * add a new contact- POST /accounts/{accountId}/contacts
 * update an existing contact- PUT /accounts/{accountId}/contacts/{contactId}
 * delete a contact- DELETE /accounts/{accountId}/contacts/{contactId}
 * other search and filter interface:
   * GET /accounts/{accountId}/contacts?state=CA,VA
   * GET /accounts/{accountId}/contacts?startDate=07/03/2017&endDate=07/05/2017 [Not implemented]
   * GET /accounts/{accountId}/contacts?areaCode=510

```
********************************************
Health Check example:

http://localhost:8090/accounts/status

GET /accounts/status HTTP/1.1
Host: localhost:8090

Response:
HTTP Status: 200
{
    "status": "passing from ...."
}
********************************************
Post example:
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
	"phoneNumber":"510-123-1234"

  }

Response:
HTTP Status: 201
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
    "phoneNumber": "510-123-1234"
}

********************************************
Get example:

http://localhost:8090/accounts/111/contacts/222
GET /accounts/111/contacts/222 HTTP/1.1
Host: localhost:8090
Content-Type: application/json

Response:
HTTP Status: 200
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
    "phoneNumber": "510-123-1234"
}

********************************************
Put example:
http://localhost:8090/accounts/111/contacts/222
PUT /accounts/111/contacts/222 HTTP/1.1
Host: localhost:8090
Content-Type: application/json

{
    "id": "222",
    "firstName": "sat",
    "lastName": "jos",
    "email": "abc@gmail.com",
	"address":{
		"street":"2 main street",
		"city":"fremont",
		"state":"CA",
		"country":"US",
		"zip":"94555"
	},
	"phoneNumber":"510-123-1234"

  }
Response:
HTTP Status: 200
{
    "address": {
        "city": "fremont",
        "country": "US",
        "state": "CA",
        "street": "2 main street",
        "zip": "94555"
    },
    "email": "abc@gmail.com",
    "firstName": "sat",
    "id": "222",
    "lastContactedDate": null,
    "lastName": "jos",
    "phoneNumber": "510-123-1234"
}
********************************************
Delete example:
http://localhost:8090/accounts/111/contacts/222
DELETE /accounts/111/contacts/222 HTTP/1.1
Host: localhost:8090
Content-Type: application/json


{
    "id": "222",
    "firstName": "sat",
    "lastName": "jos",
    "email": "abc@gmail.com",
	"address":{
		"street":"111 main street",
		"city":"fremont",
		"state":"CA",
		"country":"US",
		"zip":"94555"
	},
	"phoneNumber":"510-123-1234"

  }
  Response:
  HTTP Status: 204
********************************************

 ```
* main class:
  * com.addressbook.server.Main

* property file:
  * addressbook.properties
