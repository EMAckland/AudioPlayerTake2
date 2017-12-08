package me.baconbeans.layoutstest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emily on 12/7/2017.
 */

public class Playlist {
	private List<AudioFile> tracks;
	private String id;
	private String plName;

	public Playlist(List<AudioFile> tracks, String name, String id) {
		this.id = id;
		plName = name;
		this.tracks = tracks;
	}

	public Playlist(String plName, String id) {
		this.plName = plName;
		this.id = id;
		tracks = new ArrayList<>();
	}

	public void setTracks(List<AudioFile> tracks) {
		this.tracks = tracks;
	}

	public List<AudioFile> getTracks() {
		return tracks;
	}

	public void addTrack(AudioFile track) {
		tracks.add(track);
	}

	public String getId() {
		return id;
	}

	public String getPlName() {
		return plName;
	}

	public void setId(String id) {
		this.id = id;
	}
}
