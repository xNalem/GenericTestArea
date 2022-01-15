package weiagn.encyclopedia

class InformationAccessor {
	
	static void main(String[] args) {
		println("Start loading content from Pathfinder API")
		InformationCollector.loadDataFromApi(args[0], args[1])
		println("Preparing Data")
		HashMap<String,HashMap<String,String>> avalibeContent = InformationProcessor.processFiles(args[1])
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))
		println("Preparations Complete!")
		println("Avalible content: "+ avalibeContent.keySet())
		print("Select content: ")
		def userInput
		def activeContent 
		HashMap<String,String> activeContentMap 
		while((userInput = reader.readLine()) != "exit") {
			if(avalibeContent.get(userInput) != null) {
				println("Now wokring with: " + userInput)
				activeContentMap = avalibeContent.get(userInput)
				activeContent = userInput
			} else if(activeContentMap != null && activeContentMap.get(userInput) != null ) {
				def output = activeContentMap.get(userInput).replace("],", "],\n")
				    output = output.replace("</p>", "</p>\n")
				println("Data found for "+userInput+": ")	
				println(output)
			} else if(activeContentMap != null && activeContentMap.get(userInput) == null) {
				println(userInput + " not found in "+ activeContent)
			}
			
			if(userInput == "back") {
				activeContentMap = null
				println("going back to content selection!")
				println("Avalible content: "+ avalibeContent.keySet())
				
			} else {
				println()
				print("Enter "+ activeContent+ " name: ")	
				
			}
		}
	}
}
