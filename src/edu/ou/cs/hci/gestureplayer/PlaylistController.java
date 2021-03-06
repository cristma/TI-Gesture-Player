package edu.ou.cs.hci.gestureplayer;

import java.util.*;

import edu.ou.cs.hci.gestureplayer.R;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;

public class PlaylistController extends ListActivity {
	/** The playlist that was loaded into memory. */
	public SongModel playlist;
	
	public PlaylistController()
	{
		this.playlist = new SongModel(Environment.getExternalStorageDirectory().toString() + "/music");
		this.playlist.loadPlaylist();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.playlist);
		
		ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		
		for(int i = 0; i < this.playlist.getPlaylist().size(); i++) {
			Song song = this.playlist.getPlaylist().get(i);
			
			HashMap<String, String> item = new HashMap<String, String>();
			item.put("songTitle", song.getTitle());
			item.put("songLocation", song.getLocation());
			listData.add(item);
		}	// end for
		
		ListAdapter listAdapter = new SimpleAdapter(this, listData, R.layout.playlist_item, new String[] { "songTitle"}, new int[] { R.id.songTitle });
		setListAdapter(listAdapter);
		
		ListView view = this.getListView();
		
		view.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				int songIndex = position;
				
				Intent in = new Intent(getApplicationContext(), GesturePlayer.class);
				
				in.putExtra("songIndex", songIndex);
				setResult(100, in);
				finish();
			}	// end method onItemClick
		});	// end anonymous class OnItemClickListener
	}
}	// end class PlaylistController
