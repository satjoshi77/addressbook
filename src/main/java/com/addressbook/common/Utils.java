package com.addressbook.common;

import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;

public class Utils {
	
	public static JsonObject getConfig(Vertx v) throws IOException {
		Properties p= new Properties();
		Buffer propsBuffer = v.fileSystem().readFileBlocking(Constants.CFG_FILE_NAME);
		if(propsBuffer!=null){
			p.load(new StringReader(propsBuffer.toString()));
		}
		else{
			//TODO log warning no properties found
		}
		JsonObject props = new JsonObject(new HashMap(p));
		return props;
	}
	
	public static String generateId(){
		return UUID.randomUUID().toString();
		
	}
	
	public static String getDate(){
		return new Date().toString();
		
	}
}
