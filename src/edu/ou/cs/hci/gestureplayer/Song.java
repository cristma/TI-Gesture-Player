package edu.ou.cs.hci.gestureplayer;

/**
 * Song.java
 * 
 * @author Matthew Crist
 * 
 * This class will encapsulate the information that is associated with each
 * song object that resides in the song location that is specified in the 
 * playlist object.
 */

public class Song {
	/** The title of the song that is associated with this song object. */
	private String songTitle;
	/** The location of the song that is associated with this song object. */
	private String songLocation;
	
	/**
	 * This is the unargumented constructor that will set the default values 
	 * for the song object.
	 * 
	 * @see songTitle
	 * @see songLocation
	 */
	public Song() {
		this.songTitle = "Unknown Track";
		this.songLocation = "unknown";
	}	// end constructor
	
	/**
	 * This is the argumented constructor that will allow for the song object
	 * to be created inline with other statements.
	 * 
	 * @see songTitle
	 * @see songLocation
	 * @param songTitle The title on the song.
	 * @param songLocation The file location of the song.
	 */
	public Song(String songTitle, String songLocation) {
		this.songTitle = songTitle;
		this.songLocation = songLocation;
	}	// end constructor
	
	/**
	 * Sets the title of the song that this song object represents.
	 * 
	 * @see songTitle
	 * @param songTitle The title of the song.
	 */
	public void setTitle(String songTitle) {
		this.songTitle = songTitle;
	}	// end method setTitle
	
	/**
	 * Acquires the title of the song that this song object represents.
	 * 
	 * @see songTitle
	 * @return The title of the song.
	 */
	public String getTitle() {
		return this.songTitle;
	}	// end method getTitle
	
	/**
	 * Sets the location of the song this song object represents.
	 * 
	 * @see songLocation
	 * @param songLocation The location of the music file.
	 */
	public void setLocation(String songLocation) {
		this.songLocation = songLocation;
	}	// end method setLocation
	
	/**
	 * Acquires the location of the song this song object represents.
	 * 
	 * @see songLocation
	 * @return The location of the music file.
	 */
	public String getLocation() {
		return this.songLocation;
	}	// end method getLocation
}	// end class Song