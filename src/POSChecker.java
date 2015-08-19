import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;


public class POSChecker {

	public static void main(String[] args) {
		String posInputListLocation = args[0];
		String logDirectoryLocation = args[1];
		File outFile = new File("posstatus.txt");
		FileWriter fileOutput = null;
		PrintWriter printWriter = null;		
		LinkedList<POSLane> posLanesLinkedList = new LinkedList<POSLane>();
		
		try {
			fileOutput = new FileWriter(outFile,true);
			printWriter = new PrintWriter(fileOutput);
			FileReader fin = new FileReader(posInputListLocation);

			Scanner fileRead = new Scanner(fin);

			while(fileRead.hasNext() == true) {
				String posLaneID = fileRead.nextLine();
				POSLane pos = new POSLane(posLaneID);
				posLanesLinkedList.add(pos);
				System.out.println(pos);
			}
			
			fileRead.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		Scanner fileRead = null;
		boolean entryFound = false;
		FileReader finLog = null;
		boolean fileExists = true;
		
		System.out.println("LinkedList Size: " + posLanesLinkedList.size());
		
		for(int i = 0; i < posLanesLinkedList.size(); i++){
				fileExists = true;
				entryFound = false;
				System.out.println("LinkedList index: " + i);
				String currentLane = posLanesLinkedList.get(i).getLaneId();
			try {
				finLog = new FileReader(logDirectoryLocation + "\\"+ currentLane +"\\StateNavigationLog.log");
			} catch (FileNotFoundException e) {
				System.out.println("State Navigation File was not retrieved");
				fileExists = false;
			}
			if(fileExists == true) {
				System.out.println("CurrentLane: " + currentLane);
				fileRead = new Scanner(finLog);
				fileRead.nextLine(); //clear empty line of log
				while(fileRead.hasNext() == true) {
					String currentLogLineDate = fileRead.nextLine();
					String currentLogLine = "";
					if(fileRead.hasNext() == true){	
						currentLogLine = fileRead.nextLine();
					}
					if(currentLogLine.contains("CurrentState = LoginMode")) {
						String timestamp = currentLogLineDate.substring(11, 19);
						int hour = Integer.parseInt(timestamp.substring(0,2));
						int minute = Integer.parseInt(timestamp.substring(3,5));
						//int second = Integer.parseInt(timestamp.substring(6,8));
						if(hour == 3 && minute >=30 && entryFound == false) {
							//System.out.println(currentLane);
							//System.out.println(currentLogLineDate);	
							//System.out.println(currentLogLine);
							POSLane tempPos = new POSLane(currentLane);
							tempPos.setStatus(true);
							System.out.println("removing: " + i);

							//posLanesLinkedList.set(i,tempPos);					
							entryFound = true;
						}						
					}
				}
			}
		}

		fileRead.close();
		printWriter.println(posLanesLinkedList);
		printWriter.close();
	}
}
