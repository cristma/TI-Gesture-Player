package edu.ou.cs.hci.gestureplayer;

import java.util.*;

import edu.ou.cs.hci.gestureplayer.R;

import android.app.*;
import android.content.*;
import android.media.*;
import android.media.MediaPlayer.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class GesturePlayer extends Activity implements OnCompletionListener, SeekBar.OnSeekBarChangeListener {
	private ImageButton btnPlay;
	private ImageButton btnNext;
	private ImageButton btnPrevious;
	private ImageButton btnPlaylist;
	private SeekBar songProgressBar;
	private TextView songTitleLabel;
	private TextView songCurrentDurationLabel;
	private TextView songTotalDurationLabel;
	
	private MediaPlayer mp;
	
	private Handler mHandler = new Handler();
	private PlaylistController playlistController;
	private Utilities utils;
	private int currentSongIndex = 0;
	private boolean isShuffle = false;
	private boolean isRepeat = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);

		btnPlay = (ImageButton)findViewById(R.id.btnPlay);
		btnNext = (ImageButton)findViewById(R.id.btnNext);
		btnPrevious = (ImageButton)findViewById(R.id.btnPrevious);
		btnPlaylist = (ImageButton)findViewById(R.id.btnPlaylist);
		songProgressBar = (SeekBar)findViewById(R.id.songProgressBar);
		songTitleLabel = (TextView)findViewById(R.id.songTitle);
		songCurrentDurationLabel = (TextView)findViewById(R.id.songCurrentDurationLabel);
		songTotalDurationLabel = (TextView)findViewById(R.id.songTotalDurationLabel);
		
		
		mp = new MediaPlayer();
		playlistController = new PlaylistController();
		utils = new Utilities();
		
		//songProgressBar.setOnSeekBarChangeListener(this);
		mp.setOnCompletionListener(this);
		
		playSong(0);
		
		//int test = btnPlay.getHeight();
		
		
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				if(mp.isPlaying()) {
					if(mp!=null) {
						mp.pause();
						btnPlay.setImageResource(R.drawable.img_btn_play);
					}
				} else {
					if(mp != null) {
						mp.start();
						btnPlay.setImageResource(R.drawable.img_btn_pause);
					}
				}
			}
		});

		btnNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(currentSongIndex < (playlistController.playlist.getPlaylist().size() - 1)) {
					playSong(currentSongIndex + 1);
					currentSongIndex = currentSongIndex + 1;
				} else {
					playSong(0);
					currentSongIndex =  0;
				}
			}
		});
		
		btnPrevious.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if(currentSongIndex > 0) {
					playSong(currentSongIndex - 1);
					currentSongIndex = currentSongIndex - 1;
				} else {
					playSong(playlistController.playlist.getPlaylist().size() - 1);
					currentSongIndex = playlistController.playlist.getPlaylist().size() - 1; 
				}
			}
		});
		
		btnPlaylist.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent i = new Intent(getApplicationContext(), PlaylistController.class);
				startActivityForResult(i, 100);
			}
		});
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 100) {
			currentSongIndex = data.getExtras().getInt("songIndex");
			playSong(currentSongIndex);
		}
	}
	
	public void playSong(int songIndex) {
		try {
			mp.reset();
			mp.setDataSource(playlistController.playlist.getPlaylist().get(songIndex).getLocation());
			mp.prepare();
			mp.start();
			
			String songTitle = playlistController.playlist.getPlaylist().get(songIndex).getTitle();
			songTitleLabel.setText(songTitle);
			
			btnPlay.setImageResource(R.drawable.img_btn_pause);
			
			songProgressBar.setProgress(0);
			songProgressBar.setMax(100);
			updateProgressBar();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void updateProgressBar() {
		mHandler.postDelayed(mUpdateTimeTask, 100);
	}
	
	private Runnable mUpdateTimeTask = new Runnable() {
		public void run() {
			long totalDuration = mp.getDuration();
			long currentDuration = mp.getCurrentPosition();
			
			songTotalDurationLabel.setText("" + utils.milliSecondsToTimer(totalDuration));
			songCurrentDurationLabel.setText("" + utils.milliSecondsToTimer(currentDuration));
			
			int progress = (int)(utils.getProgressPercentage(currentDuration, totalDuration));
			songProgressBar.setProgress(progress);
			mHandler.postDelayed(this, 100);
		}
	};
	
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
	}
	
	public void onStartTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
	}
	
	public void onStopTrackingTouch(SeekBar seekBar) {
		mHandler.removeCallbacks(mUpdateTimeTask);
		int totalDuration = mp.getDuration();
		int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
		
		mp.seekTo(currentPosition);
		updateProgressBar();
	}
	
	public void onCompletion(MediaPlayer arg0) {
		if(isRepeat) {
			playSong(currentSongIndex);
		} else if(isShuffle) {
			Random rand = new Random();
			currentSongIndex = rand.nextInt((playlistController.playlist.getPlaylist().size() - 1) - 0 + 1) + 0;
			playSong(currentSongIndex);
		} else {
			if(currentSongIndex < (playlistController.playlist.getPlaylist().size() - 1)) {
				playSong(currentSongIndex + 1);
				currentSongIndex = currentSongIndex + 1;
			} else {
				playSong(0);
				currentSongIndex = 0;
			}
		}
	}
	
	public void onDestroy() {
		super.onDestroy();
		mp.release();
	}
}
