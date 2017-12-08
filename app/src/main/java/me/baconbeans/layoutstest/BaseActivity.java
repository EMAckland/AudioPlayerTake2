package me.baconbeans.layoutstest;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import me.baconbeans.*;

public abstract class   BaseActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {
	protected final String ALBUMS_FRAGMENT = "me.baconbeans.layoutstest";
	protected final String PLAYLISTS_FRAGMENT = "me.baconbeans.layoutstest";
	protected final String ADD_TRACKS_FRAGMENT = "me.baconbeans.layoutstest";
	protected final String TRACKS_FRAGMENT = "me.baconbeans.layoutstest";
	protected final String MAIN_ACTIVITY = ".BaseAcitivity";
	protected boolean audioBound = false, isPlaylist = false, addingTracks = false, paused = true;
	protected Intent playIntent = null;
	protected int addIcon = R.drawable.ic_playlist_add_black_24dp;
	protected int doneIcon = R.drawable.ic_done_black_24dp;
	protected AudioService audioSrv;
	protected SeekBar seekBar;
	protected Handler seekHandler = new Handler();
	protected String[] permissions = new String[]{
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.WAKE_LOCK,
			Manifest.permission.MEDIA_CONTENT_CONTROL
	};
	protected List<AudioFile> tracksList;
	protected List<Playlist> playlistList;
	protected List<Album> albumList;
	protected ImageView skip_next, skip_prev, play_pause;
	protected TextView trackDuration, trackCurrTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
						this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
		drawer.addDrawerListener(toggle);
		toggle.syncState();
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

	}
	public void init(){
		albumList = AudioUtils.getAlbums(this);
		tracksList = AudioUtils.getTracks(this,null,null,null);
		playlistList = AudioUtils.getPlaylists(this);
		play_pause = findViewById(R.id.play_pause);
		skip_next = findViewById(R.id.skip_next);
		skip_prev = findViewById(R.id.skip_prev);
		trackDuration = findViewById(R.id.end_time);
		trackCurrTime = findViewById(R.id.curr_time);
		seekBar = findViewById(R.id.seek_bar);
		if(playIntent==null){
			playIntent = new Intent(this, AudioService.class);
			bindService(playIntent, audioConnection, Context.BIND_IMPORTANT);
		}
		startService(playIntent);
	}

	public List<AudioFile> getTracksList() {
		return tracksList;
	}

	public List<Playlist> getPlaylistList() {
		return playlistList;
	}

	public List<Album> getAlbumList() {
		return albumList;
	}

	@Override
	public void onStart() {
		super.onStart();
		if (playIntent == null) {
			playIntent = new Intent(this, AudioService.class);
			bindService(playIntent, audioConnection, Context.BIND_IMPORTANT);
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(addingTracks){
			getMenuInflater().inflate(R.menu.toolbardone, menu);
		}else
			getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_playlsit) {
			addingTracks = true;
			onAddNewPlaylist();
		} else if (id == R.id.action_done){
			addingTracks  = false;
			onFinishedNewPlaylist();
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		// Handle navigation view item clicks here.
		int id = item.getItemId();
		if (id == R.id.nav_playlists) {
			loadNewFragmentWithBackstack(PLAYLISTS_FRAGMENT, MAIN_ACTIVITY);
		} else if (id == R.id.nav_manage) {

		} else if (id == R.id.nav_albums) {
			loadNewFragmentWithBackstack(ALBUMS_FRAGMENT, MAIN_ACTIVITY);
		}
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}
	public void loadNewFragment(String fragment) {
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.fragment_frame, Fragment.instantiate(BaseActivity.this,
						fragment), fragment);
		tx.commit();
	}
	public void loadNewFragmentWithBackstack(String fragment, String backstack){
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.replace(R.id.fragment_frame, Fragment.instantiate(BaseActivity.this, fragment),
						fragment).addToBackStack(backstack).commit();
	}

	@Override
	public void onBackPressed() {
		int count = getFragmentManager().getBackStackEntryCount();
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else if (count == 0){
			super.onBackPressed();
		}else {
			getFragmentManager().popBackStack();
		}
	}
	abstract void onAddNewPlaylist();
	abstract void onFinishedNewPlaylist();
	private ServiceConnection audioConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			AudioService.AudioBinder binder = (AudioService.AudioBinder)service;
			audioSrv = binder.getService();
			audioBound = true;
			audioSrv.setTracks(tracksList);
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			audioBound = false;
		}
	};

}
