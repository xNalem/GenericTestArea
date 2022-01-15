package weiagn.communication;

import groovyx.net.http.AuthConfig
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient

/*
 * this class checks what endpoints are avalible at the pathfinder2 api.
 * For each endpoint the Informations get loaded and saved in a txt file with the according name of the endpoint.
 * 
 * Required arguments are the API-Key as first argument and the location for the txt files as second argument  
 * 
 */

class InformationCollector {
	
	static void main(String[] args) {
		def url = "https://api.pathfinder2.fr"
		def basePath = "/v1/pf2/"
		RESTClient client = new RESTClient("https://api.pathfinder2.fr")
		
		//since the api is scuffed and dosent has a valid certificate we just ignore that
		client.ignoreSSLIssues()
		client.setHeaders('Authorization' : args[0])
		def response = client.get(path: basePath)
		for(item in response.responseData) {
			def	path = item - url
			def content = client.get(path: path)
			
			for(data in content.responseData) {
				if(data.value instanceof ArrayList) {
					def file = new File(args[1],path - basePath + ".txt").withWriter("utf-8") {  writer ->
						data.value.each { lazyMap ->
						 lazyMap.each { key,entry ->
							 writer.writeLine key as String
							 writer.writeLine entry as String
							 writer.writeLine ""
						  }
						}
					}
					
				}
			}
		
		}
		
	}

}

