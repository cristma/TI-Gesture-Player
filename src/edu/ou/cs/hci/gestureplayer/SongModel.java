package edu.ou.cs.hci.gestureplayer;

/**
 * SongModel.java
 * 
 * @author Matthew Crist
 * 
 * This class is responsible for the additions to the playlist and the information that
 * is associated with each song to be added to the playlist.  It will keep an ArrayList 
 * of all the songs that are to be loaded from fileLocation.
 */

import java.io.*;
import java.util.*;

public class SongModel {
	/** The list of songs that are to be associated with this model component. */
	private ArrayList<Song> playlist;
	/** The location that houses the music files to be loaded. */
	private String fileLocation;

	/**
	 * Default unargumented constructor that will assign the playlist object
	 * to an empty ArrayList for population.
	 * 
	 *  @see playlist
	 *  @see loadPlaylist
	 */
	public SongModel() {
		this.playlist = new ArrayList<Song>();
	}	// end constructor
	
	/**
	 * Argumented constructor that will set the folder that holds the files that 
	 * will be used in the playlist for the media player.
	 * 
	 * @see fileLocation
	 * @param fileLocation The directory where the music files reside.
	 */
	public SongModel(String fileLocation) {
		this.playlist = new ArrayList<Song>();
		this.fileLocation = fileLocation;
	}	// end constructor
	
	/**
	 * Acquires the playlist that is associated with this model component.
	 * 
	 * @see playlist
	 * @return The playlist of this model.
	 */
	public ArrayList<Song> getPlaylist() {
		return this.playlist;
	}	// end method getPlaylist
	
	/**
	 * Sets the location for the files that will be loaded into the playlist.
	 * 
	 * @see fileLocation
	 * @param fileLocation The location of the directory that houses the music files.
	 */
	public void setLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}	// end method setLocation
	
	/**
	 * Acquires the location for the files that will be loaded into the playlist.
	 * 
	 * @return The directory that houses the music files.
	 */
	public String getLocation() {
		return this.fileLocation;
	}	// end method getLocation
	
	/**
	 * Loads a playlist from the specified location.
	 */
	public void loadPlaylist() {
		File musicDirectory = new File(this.fileLocation);
		
		if(musicDirectory.listFiles(new MP3Filter()).length > 0) {
			for(File mp3 : musicDirectory.listFiles(new MP3Filter())) {
				String filename = mp3.getName();
				this.playlist.add(new Song(filename.substring(0, filename.lastIndexOf('.')), mp3.getPath()));
			}	// end for/each
		}
	}	// end method loadPlaylist
	
	/**
	 * MP3Filter
	 * @author Matthew Crist
	 * 
	 * This class will override the FilenameFilter method to accept the input for 
	 * the files to be loaded into a playlist.
	 */
	class MP3Filter implements FilenameFilter {
		/**
		 * Determines if the file is acceptable for file filtration.
		 * 
		 * @param directory The directory the files reside.
		 * @param filename The filename that is being checked.
		 */
		public boolean accept(File directory, String filename) {
			return filename.endsWith(".mp3") || filename.endsWith(".MP3");
		}	// end method accept
	}	// end class MP3Filter
}	// end class SongModel
