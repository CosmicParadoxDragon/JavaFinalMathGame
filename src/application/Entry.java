/**
 * This is the vector Entry for the leaderboard class that is designed to build the object
 * a will use to store each player's information
 * 
 * @author SohrabKazak
 * @prof Gao
 * @Course CS170 - Intermediate Java
 * @Org Ohlone College
 * 
 * @date May 11, 2022
 * 
 */

package application;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Entry implements Comparable<Entry>,  Iterable<Entry>
{
	private List<Entry> entries = new ArrayList<Entry>();
	private String name;
	long time;
	private int score;
	/**
	 * Constructor 
	 * @param name Player's name
	 * @param time Player's time
	 * @param score Player's score
	 */
	Entry(String name, long time, int score)
	{
		this.name = name;
		this.time = time;
		this.score = score;
	}
	/**
	 * Getter for the score
	 * @return score
	 */
	int getScore()
	{
		return score;
	}
	/**
	 * Getter for the name
	 * @return name
	 */
	String getName()
	{
		return name;
	}
	/**
	 * Getter for the time
	 * @return time
	 */
	long getTime()
	{
		return time;
	}
	/**
	 * compareTo for the comparable interface
	 * @param o is the entry to compare this to
	 */
	@Override
	public int compareTo(Entry o) {
		if (score < o.getScore())
		{
			return 1;
		}
		else if(score > o.getScore())
		{
			return -1;
		}
		return 0;
	}
	/**
	 * interator for the interface
	 * 
	 * @return the iterator for the iterable interface
	 */
	@Override
	public Iterator<Entry> iterator() 
	{
		return this.entries.iterator();
	}
	
	/**
	 * Poor man's greater than operator overload
	 * @param other Entry to compare to 
	 * @return true if this is greater
	 * @return false if this is smaller
	 */
	boolean operator_greater_than(Entry other)
	{
		if (this.score > other.getScore())
		{			
			return true;
		}
		return false;
		
	}
	
}