package me.baconbeans.layoutstest;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emily on 12/7/2017.
 */
public class AudioUtils {


	public static List<AudioFile> getAlbumTracks(Context context, Long albumID){
		String selection = MediaStore.Audio.Media.ALBUM_ID + "=?";
		String[] selectionArgs = new String[]{albumID.toString()};
		return getTracks(context, selection, selectionArgs, null);
	}
	public static List<AudioFile> getPlaylistTracks(Context context, Long playlistID){
		// TO-DO
		String selection = MediaStore.Audio.Playlists._ID+ "=?";
		String[] selectionArgs = new String[]{playlistID.toString()};
		Cursor cursor = AndroidUtils.getCursor(
				context,
				AndroidUtils.PLAYLISTS_URI,
				AndroidUtils.PLAYLIST_PROJECTION,
				selection,
				selectionArgs,
				null);
		return null;
	}
	public static Bitmap getAlbumArt(Context context, Long album_id) {
		return AndroidUtils.getBitmapFromDrawable(context,R.drawable.ic_wallpaper_black_24dp);
	}
	public static List<Album> getAlbums(Context context){
		List<Album> albumList = new ArrayList<>();
		Cursor cursor = AndroidUtils.getCursor(
				context,
				AndroidUtils.ALBUM_URI,
				AndroidUtils.ALBUM_PROJECTION,
				null,
				null,
				null);
		if(cursor!=null && cursor.moveToFirst()){
			do{
				Album track = new Album(
						cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)),
						cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)),
						getAlbumTracks(context,cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))),
						cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ID))
				);
				albumList.add(track);
			}while (cursor.moveToNext());
			cursor.close();
		}
		return albumList;
	}
	public static List<AudioFile> getTracks(Context context, String selection, String[] selectionArgs, String sortOrder){
		List<AudioFile> tracksList = new ArrayList<>();
		Cursor cursor = AndroidUtils.getCursor(context,
				AndroidUtils.MEDIA_URI,
				AndroidUtils.TRACK_PROJECTION,
				selection,
				selectionArgs,sortOrder
		);
		if(cursor!=null && cursor.moveToFirst()){
			do{
				tracksList.add(getTrack(cursor));
			}while (cursor.moveToNext());
			cursor.close();
		}
		return tracksList;
	}
	public static List<Playlist> getPlaylists(Context context){
		List<AudioFile> tracksList; new ArrayList<>();
		return new ArrayList<>();
	}
	public static String timeInMilliSecToFormattedTimeMM_SS(int time) {
		String formattedTime;
		try {
			int seconds = time / 1000;
			int minutes = seconds / 60;
			seconds = seconds % 60;
			if (seconds < 10) {
				formattedTime = " " + String.valueOf(minutes) + ":0" + String.valueOf(seconds) + " ";
			} else {
				formattedTime = " " + String.valueOf(minutes) + ":" + String.valueOf(seconds) + " ";
			}
		} catch (NumberFormatException e) {
			formattedTime = "error";
		}
		return formattedTime;
	}
	public static void print(String msg) {
		Log.v("DEBUG", msg);
	}

	public static AudioFile getTrack(Cursor cursor){
		return new AudioFile(
				cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID)),
				cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)),
				cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
				cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)),
				cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
				cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))
		);
	}
}
