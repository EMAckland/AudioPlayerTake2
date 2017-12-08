package me.baconbeans.layoutstest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

/**
 * Created by Emily on 12/7/2017.
 */

public class AlbumsFragment extends Fragment {
	private TableLayout albumsView;
	private RecyclerView recyclerView;
	private List<Album> albumList;
	public AlbumsFragment(){

	}
	public static Fragment newInstance(Context context) {
		AlbumsFragment f = new AlbumsFragment();
		return f;
	}
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.albums_view, null);
		return root;
	}
	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		FragmentManager manager = getFragmentManager();
		albumList = ((BaseActivity) getActivity()).getAlbumList();
		recyclerView = view.findViewById(R.id.album_recycler);
		FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getContext());
		layoutManager.setFlexDirection(FlexDirection.COLUMN);
		layoutManager.setJustifyContent(JustifyContent.FLEX_END);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(new AlbumsAdapter(getContext(), albumList));
	}

}
