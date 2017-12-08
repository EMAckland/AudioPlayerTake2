package me.baconbeans.layoutstest;

import android.content.Context;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;
import android.support.v7.widget.RecyclerView.*;

/**
 * Created by Emily on 12/7/2017.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.ViewHolder> {
	List<Album> albumList;
	LayoutInflater inflator;
	Context context;
	int currAlbumIdx =0;
	int remainder;
	private final int NUM_ROW_ELEMENTS = 3;
	public class ViewHolder extends RecyclerView.ViewHolder {

		View layout;
		ImageView album1, album2,album3;
		public ViewHolder(View v){
			super(v);
			layout = v;
			album1 = (ImageView) v.findViewById(R.id.album1);
			album2 = (ImageView) v.findViewById(R.id.album2);
			album3 = (ImageView) v.findViewById(R.id.album3);
		}
	}
	public void add(int position, Album item) {
		albumList.add(item);
		notifyItemInserted(position);
	}

	public void remove(int position) {
		albumList.remove(position);
		notifyItemRemoved(position);
	}
	public AlbumsAdapter(Context ctx, List<Album> albums){
		this.albumList = albums;
		remainder = albumList.size() - (albumList.size()%NUM_ROW_ELEMENTS);
	}
	@Override
	public AlbumsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(
				parent.getContext());
		View v =
				inflater.inflate(R.layout.albums_view, parent, false);
		// set the view's size, margins, paddings and layout parameters;
		return new ViewHolder(v);
	}
	@Override
	public void onBindViewHolder(AlbumsAdapter.ViewHolder holder, int position) {
		if(currAlbumIdx<= albumList.size()){
			final Album album1 = albumList.get(currAlbumIdx);
			holder.album1.setImageBitmap(album1.getAlbumArt(context));
			currAlbumIdx++;
			holder.album1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity)v.getContext()).albumSelected(album1.getTracks());
				}
			});

			if (getCurrAlbumIdx()<= albumList.size()){
				final Album album2 = albumList.get(currAlbumIdx);
				holder.album2.setImageBitmap(album2.getAlbumArt(context));
				currAlbumIdx++;
				holder.album2.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						((MainActivity)v.getContext()).albumSelected(album2.getTracks());
					}
				});

				if (currAlbumIdx<= albumList.size()){
					final Album album3 = albumList.get(getCurrAlbumIdx());
					holder.album3.setImageBitmap(album3.getAlbumArt(context));
					currAlbumIdx++;

					holder.album3.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							((MainActivity)v.getContext()).albumSelected(album3.getTracks());
						}
					});
				}
			}
		}
	}
	@Override
	public int getItemCount() {
		int count = albumList.size()%NUM_ROW_ELEMENTS;
		return count;
	}
	@Override
	public long getItemId(int arg0) {
		return albumList.get(arg0).getAlbumID();
	}

	public void setCurrAlbumIdx(int currAlbumIdx) {
		this.currAlbumIdx = currAlbumIdx;
	}
	public int getCurrAlbumIdx() {
		return currAlbumIdx;
	}

}
