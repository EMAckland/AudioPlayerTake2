package me.baconbeans.layoutstest;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Emily on 11/30/2017.
 */

public class Album {
	private List<AudioFile> tracks;
	private String artist;
	private String title;
	private Long albumID;
	private Long ID;

	public Album(String artist, String title, Long albumID) {
		this.artist = artist;
		this.title = title;
		this.albumID = albumID;

		tracks = new ArrayList<>();
	}

	public Album(String artist, String title, List<AudioFile> tracks, Long albumID) {
		this.artist = artist;
		this.title = title;
		this.albumID = albumID;
		this.tracks = tracks;

	}


	public List<AudioFile> getTracks() {
		return tracks;
	}
	public Long getAlbumID(){return albumID;}
	public String getArtist() {
		return artist;
	}
	public String getTitle() {
		return title;
	}
	public Bitmap getAlbumArt(Context ctx) {
		return AudioUtils.getAlbumArt(ctx, albumID);
	}

	public void setTracks(ArrayList<AudioFile> inTracks) {

		tracks = inTracks;
	}

	public boolean equals(Album a) {
		if (artist.equals(a.getArtist()) && title.equals(a.getTitle()))
			return true;
		else
			return false;
	}
	public int hashCode() {
		return Objects.hash(artist, title);
	}

}