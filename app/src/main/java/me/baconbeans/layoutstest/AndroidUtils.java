package me.baconbeans.layoutstest;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.DrawableRes;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.FileDescriptor;
import java.util.List;

/**
 * Created by Emily on 12/7/2017.
 */

public class AndroidUtils {
	public static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
	public static final Uri ALBUM_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
	public static final Uri PLAYLISTS_URI = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
	public static final String[] TRACK_PROJECTION  = new String[]{
			MediaStore.Audio.Media.ARTIST,
			MediaStore.Audio.Media.DURATION,
			MediaStore.Audio.Media._ID,
			MediaStore.Audio.Media.ALBUM,
			MediaStore.Audio.Media.TITLE,
			MediaStore.Audio.Media.ALBUM_ID
	};
	public static final String[] ALBUM_PROJECTION  = new String[]{
			MediaStore.Audio.Albums.ALBUM_ART,
			MediaStore.Audio.Albums.ALBUM,
			MediaStore.Audio.Albums.ALBUM_ID,
			MediaStore.Audio.Albums.ARTIST,
	};
	public static final String[] PLAYLIST_PROJECTION = new String[]{
			MediaStore.Audio.Playlists._ID,
			MediaStore.Audio.Playlists.NAME,
			MediaStore.Audio.Playlists.Members.AUDIO_ID,
			MediaStore.Audio.Playlists.Members.ARTIST,
			MediaStore.Audio.Playlists.Members.DISPLAY_NAME
	};
	public static boolean havePermissions(FragmentActivity activity, Context ctx, String[] permissions) {
		boolean permission = false;
		if (ActivityCompat.checkSelfPermission(ctx,
				Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
				ActivityCompat.checkSelfPermission(ctx,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
				ActivityCompat.checkSelfPermission(ctx,
						Manifest.permission.WAKE_LOCK) != PackageManager.PERMISSION_GRANTED) {
			permission = false;
			if (activity.shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
				activity.requestPermissions(permissions, 1);
			}
		} else
			permission = true;
		return permission;
	}

	public static Bitmap getBitmapFromDrawable(Context context, @DrawableRes int drawableId) {
		Drawable drawable = ContextCompat.getDrawable(context, drawableId);

		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		} else if (drawable instanceof VectorDrawable || drawable instanceof VectorDrawableCompat) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(bitmap);
			drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
			drawable.draw(canvas);

			return bitmap;
		} else {
			throw new IllegalArgumentException("unsupported drawable type");
		}
	}
	public static void print(String msg) {
		Log.v("DEBUG", msg);
	}

	public static Cursor getCursor(Context context, android.net.Uri uri, java.lang.String[] projection,
	 java.lang.String selection, java.lang.String[] selectionArgs, java.lang.String sortOrder){
		ContentResolver resolver = context.getContentResolver();
		return resolver.query(uri,projection,selection,selectionArgs,sortOrder);
	}

}
