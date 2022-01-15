package weiagn.encyclopedia

import groovy.io.FileType

/**
 * Class that handles loading textFiles into usable form.
 */

class InformationProcessor {
	/**
	 * takes a directory URL and iterates over every found file.
	 * each filename is used as key for the content Map. The Value is filled with a Map containing the Name of the Object as Key
	 * and the Description as Value
	 * 
	 * @param directory to the Folder containing the API files
	 * @return the Content of the Pathfinder API
	 */
	static HashMap processFiles(directory) {
		HashMap<String,HashMap<String,String>> content = new HashMap()
		def dir = new File(directory)
			dir.eachFileRecurse (FileType.FILES) { file ->
			HashMap<String,String> fileContent = new HashMap()	
				
		file.withReader{ reader ->
			def line
			String data = ""
			String name = ""
			Boolean dataActive = false
			Boolean nameActive = false
		 while ((line = reader.readLine()) != null) { 	
			
			if(line == "_id") {
				if(data != "" && name != "") {
					fileContent.put(name, data)}
				data = name = ""
				}
			
			if(line == "data") {
				dataActive = true
				continue}
			
			if(line == "name") {
				nameActive = true
				continue}
			
			if(dataActive) {
			  if(line == "effects" ) {
					dataActive = false
					continue 
					}
			  	data = data + line}
				
			if(nameActive) {
				if(line == "type" ) {
					  nameActive = false
						 continue
					  }
					name = name + line }
			}
			
		content.put(file.name - ".txt",fileContent)	
		} 
	}
return content	
	}	
}
