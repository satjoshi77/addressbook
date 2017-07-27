package com.addressbook.common;

public interface Constants {
	static final int DEFAULT_PORT=8080;
	static final String CFG_HTTP_PORT="http.port";
	static final String MAIN_VERTICLE_NAME="com.addressbook.server.vertx.MainVerticle";
	static final String CFG_FILE_NAME="addressbook.properties";
	
	static final String PATH_ADDRESS_BOOK = "/accounts/:accountId/contacts";
	static final String APPLICATION_JSON = "application/json";
	
	static final String HEADER_CONTENT_TYPE="content-type";
	static final String PERSISTENCE_TYPE="persistence.type";
	static final String PERSISTENCE_DEFAULT_MEMORY="memory";
	
	static final String CONTACT_ID="contactId";
	static final String ACCOUNT_ID="accountId";
	static final String CONTACT_KEY="contactKey";
	static final String STATE = "state";
	static final String AREA_CODE = "areaCode";
}
