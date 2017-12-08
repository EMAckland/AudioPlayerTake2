package me.baconbeans.layoutstest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

/**
 * Created by Emily on 12/7/2017.
 */

public class MainActivity extends BaseActivity
		implements NavigationView.OnNavigationItemSelectedListener {


	protected AudioService audioSrv;
	public MainActivity() {
		super();
	}
	@Override
	public void onCreate(Bundle state){
		super.onCreate(state);
	}
	@Override
	public void onStart() {
		super.onStart();
	}
	@Override
	protected void onPause() {
		super.onPause();
		paused = true;
		seekHandler.removeCallbacks(run);
		updateView();
	}
	@Override
	protected void onResume() {
		super.onResume();
		paused = false;
		updateSeek();
	}
	@Override
	protected void onStop() {
		seekHandler.removeCallbacks(run);
		super.onStop();
	}
	@Override
	protected void onDestroy() {
		stopService(playIntent);
		audioSrv = null;
		seekHandler.removeCallbacks(run);
		super.onDestroy();
	}

	public void pause() {
		paused = true;
		audioSrv.pausePlayer();
	}

	public int getDuration() {
		return Integer.parseInt(audioSrv.getCurrentTrack().getDuration());
	}


	public int getCurrentPosition() {
		if (audioSrv != null && audioBound && audioSrv.isPlaying())
			return audioSrv.getPositon();
		else return 0;
	}
	private void updateSeek() {

	}
	private void updateView() {

	}

	private void playNext() {
		paused = false;
		audioSrv.playNext();
	}

	@Override
	public void onAddNewPlaylist() {

	}

	@Override
	public void onFinishedNewPlaylist() {

	}
	public void albumSelected(List<AudioFile> albumTracks){
		// TO-DO
	}
	Runnable run = new Runnable() {
		@Override
		public void run() {
			if(audioSrv==null){
				seekHandler.removeCallbacks(run);
			}
			else if (!paused && audioSrv.isPlaying() && audioBound) {
				updateSeek();
			} else {
				seekHandler.removeCallbacks(run);
			}
		}
	};
}
