package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;

import creature.Inhabitant;
import core.Game;

public class PrintStatus {

	/**
	 * @return  current time and date in String format
	 */
	public static String getTimeAndDate(){
		LocalDateTime timeDate = java.time.LocalDateTime.now();
		return timeDate.toString();
	}
	
	/**
	 * Open a text file and write status of a Game to it.
	 * First line printed should be "Game ending <timestamp>"
	 * where <timestamp> can be obtained from PrintStatus.getTimeAndDate() above.
	 * Subsequent lines report the status of each player in the Game.
	 * If dead, print "<player name> is dead."
	 * otherwise print "<player name> is awake/asleep in Cave <id>."
	 * 
	 * @param G		a Hunt-the-Wumpus Game object
	 * @param fname filename String
	 */
	public static void printToFile(Game G,String fname)
	{
		PrintWriter outputStream = null;
		
		/* Store the PrintWriter object in the outputStream 
		 * variable.*/		
		try 
		{
			outputStream = new PrintWriter (new FileOutputStream(fname));
		}
		
		/* Raise an exception and quit if the program encounters an
		 * error when opening the file.*/		
		catch (FileNotFoundException e) 
		{
			System.out.println("Error opening the file.");
			System.exit(0);
		}
		
		/* The following statements will record the time of the game being 
		 * played, loop through the list of players and record individual 
		 * player's status in the game.*/		
		outputStream.println("Game ending " + PrintStatus.getTimeAndDate());
		
		Inhabitant[] listOfplayers = G.getArrayOfPlayers();
		
		for(int i = 0; i < listOfplayers.length; i++) 
		{
			Inhabitant aPlayer = listOfplayers[i];
			
			if(aPlayer != null) 
			{
				if(aPlayer.isAlive() && aPlayer.isAwake()) 
				{
					outputStream.println(aPlayer.getName() + " is awake in cave #" + 
										 aPlayer.getLocation().getID());
				}
				
				else if (aPlayer.isAlive() && !aPlayer.isAwake()) 
				{
					outputStream.println(aPlayer.getName() + " is sleep in cave #" + 
										 aPlayer.getLocation().getID());
				}
				
				else 
				{
					outputStream.println(aPlayer.getName() + " is dead.");
				}
			}
		
		}
		
		outputStream.close();
	}

	/**
	 * default filename
	 */
	private static final String defaultFilename = "gameStatus.txt";
	/**
	 * Same as PrintStatus.printToFile(Game,String) but uses the default filename 
	 * specified by defaultFilename.
	 * @param G   the Game object
	 */
	public static void printToFile(Game G)
	{
		/* This method has the exact behaviours of the previous method 
		 * except in here, it has a defined file name. Please refer
		 * to the previous method for the break down explanation.*/
		
		PrintWriter outputStream = null;
		
		try 
		{
			outputStream = new PrintWriter (new FileOutputStream(defaultFilename));
		}
		
		catch (FileNotFoundException e) 
		{
			System.out.println("Error opening the file.");
			System.exit(0);
		}
		
		outputStream.println("Game ending " + PrintStatus.getTimeAndDate());
		
		Inhabitant[] listOfplayers = G.getArrayOfPlayers();
		
		for(int i = 0; i < listOfplayers.length; i++) 
		{
			
			Inhabitant aPlayer = listOfplayers[i];
			
			if(aPlayer != null) 
			{
				if(aPlayer.isAlive() && aPlayer.isAwake()) 
				{
					outputStream.println(aPlayer.getName() + " is awake in cave #" + 
										 aPlayer.getLocation().getID());
				}
				
				else if (aPlayer.isAlive() && !aPlayer.isAwake()) 
				{
					outputStream.println(aPlayer.getName() + " is sleep in cave #" + 
										 aPlayer.getLocation().getID());
				}
				
				else 
				{
					outputStream.println(aPlayer.getName() + " is dead.");
				}
			}
		}
		
		outputStream.close();
	}
}
