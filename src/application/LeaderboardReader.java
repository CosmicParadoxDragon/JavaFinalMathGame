/**
 * This is the leaderboard class that is designed to build a 
 * leaderboard to store the data for the MathGame project
 * 
 * @author SohrabKazak
 * @prof Gao
 * @Course CS170 - Intermediate Java
 * @Org Ohlone College
 * 
 * @date May 10, 2022
 * 
 */

package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class LeaderboardReader extends Thread
{
	private final int LEADERBOARD_START_X = 40;
	private final int LEADERBOARD_START_Y = 30;
	private Leaderboard leaderboard;
	private File leaderboard_file;
	String line;
	BufferedReader in;
	BufferedWriter out;
	MathGameSubscene lss;
	VBox entries;
	String[] leaderboard_entires; 
	/**
	 * Constructor for the LeaderboardReader thread object
	 * @param input file object to read and write to
	 * @param le This is the leaderboard sunscene reference to be able to write to it
	 */
	public LeaderboardReader(File input, MathGameSubscene le)
	{
		leaderboard = new Leaderboard();
		leaderboard_file = input;
		lss = le;
		setName("leaderboard-reader-game");
	}
	/**
	 * run for the Thread superclass
	 */
	@Override
	public void run()
	{
		System.out.println("Thread running");
		load();
		process();
	}
	/**
	 * Loads the leaderboard file into a String called line
	 */
	private void load()
	{
		entries = new VBox();
		try 
		{
			in = new BufferedReader(new FileReader(leaderboard_file));
			line = in.readLine();
			
			in.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		entries.getChildren().add(new Entries("  | Name.....Time.....Score"));
		System.out.println("Load Function Complete");
	}
	/**
	 * writes a new entry to the leaderboard datastructure if less than 5 entries exist
	 * if there are 5 checks scores against the new and is one is greater replaces that entry
	 * 
	 * @param new_entry From the lastest play of the game
	 * @throws IOException
	 */
	public void write(Entry new_entry) throws IOException
	{
		if (leaderboard.leaderboard.size() < 5)
		{
			leaderboard.leaderboard.add(new_entry);
		}
		for (Entry entry : leaderboard)
		{
			if (entry.operator_greater_than(new_entry))
			{
				leaderboard.leaderboard.add(leaderboard.leaderboard.indexOf(entry), entry);
				break;
			}
		}
		leaderboard.leaderboard.sort(null);
		rewrite_line();
		out = new BufferedWriter(new FileWriter(leaderboard_file));
		out.write(line);
		out.close();
	}
	/**
	 * builds the line that is written to the leaderboard file
	 */
	private void rewrite_line()
	{
		line = "";
		for (Entry entry : leaderboard.leaderboard)
		{
			line += entry.getName()+"="+entry.getTime()+"="+entry.getScore()+":";
		}
	}
	/**
	 * Processed the file and adds the information to the 
	 * given Pane
	 */
	private void process()
	{
		if (line != null)
		{
			leaderboard_entires = line.split(":");
			for (int i = 0; i < leaderboard_entires.length; i++)
			{
				String[] tmp = leaderboard_entires[i].split("=");
				String new_time = convertToRealTime(Long.parseLong(tmp[1]));
				String rejoined_entry = (i+1)+ " | "+tmp[0] + "...." + new_time + "...." + tmp[2];
				leaderboard.leaderboard.add(new Entry(tmp[0], 
						Long.parseLong(tmp[1]), Integer.parseInt(tmp[2])));
				entries.getChildren().add(new Entries(rejoined_entry));
			}
			System.out.println("Process Function Complete");
		}
		
		entries.setLayoutX(LEADERBOARD_START_X);
		entries.setLayoutY(LEADERBOARD_START_Y);
		entries.setAlignment(Pos.CENTER_LEFT);
		lss.getPane().getChildren().add(entries);
	}
	/**
	 * Leaderboard class is a data structure to hold the information
	 * from the Leaderboard file
	 *
	 */
	public class Leaderboard implements Iterable<Entry>
	{
		List<Entry> leaderboard;
		
		public Leaderboard()
		{
			leaderboard = new ArrayList<Entry>();
		}
		public void sort()
		{
			leaderboard.sort(null);
		}
		public List<Entry> getLeaderboard()
		{
			return leaderboard;
		}
		public void add(Entry new_entry)
		{
			leaderboard.add(new_entry);
		}
		@Override
		public Iterator<Entry> iterator() {
			return this.leaderboard.iterator();
		}
	}
	
	/**
	 * Helper function to display the time elapsed 
	 * @param time Takes in the current time
	 * @return The time in format of seconds only in a string
	 */
	private String convertToRealTime(long time)
	{ 	
		return Long.toString(time / (long)1000000000.0) + ":" + Long.toString(time % (long)10000.0);
	}
}
