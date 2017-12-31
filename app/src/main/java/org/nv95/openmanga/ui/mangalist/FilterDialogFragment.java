package org.nv95.openmanga.ui.mangalist;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.nv95.openmanga.R;
import org.nv95.openmanga.content.MangaGenre;
import org.nv95.openmanga.ui.common.HeaderDividerItemDecoration;
import org.nv95.openmanga.utils.AnimationUtils;
import org.nv95.openmanga.utils.CollectionsUtils;

import java.util.ArrayList;

/**
 * Created by koitharu on 31.12.17.
 */

public final class FilterDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

	private RecyclerView mRecyclerView;
	private Toolbar mToolbar;
	private AppBarLayout mAppBar;
	private FilterSortAdapter mAdapter;

	private int[] mSorts;
	private MangaGenre[] mGenres;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Bundle args = getArguments();
		assert args != null;
		mSorts = args.getIntArray("sorts");
		mGenres = (MangaGenre[]) args.getParcelableArray("genres");
	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.dialog_filter, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		mRecyclerView = view.findViewById(R.id.recyclerView);
		mToolbar = view.findViewById(R.id.toolbar);
		mAppBar = view.findViewById(R.id.appbar);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		final Activity activity = getActivity();
		assert activity != null;
		mAdapter = new FilterSortAdapter(activity, mSorts, mGenres);
		mRecyclerView.setAdapter(mAdapter);
		mRecyclerView.addItemDecoration(new HeaderDividerItemDecoration(activity));
		mToolbar.setNavigationOnClickListener(this);

		/*final BottomSheetBehavior behavior = BottomSheetBehavior.from(getDialog().findViewById(android.support.design.R.id.design_bottom_sheet));
		behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
			@Override
			public void onStateChanged(@NonNull View bottomSheet, int newState) {
				AnimationUtils.setVisibility(mAppBar, newState == BottomSheetBehavior.STATE_EXPANDED ? View.VISIBLE : View.GONE);
			}

			@Override
			public void onSlide(@NonNull View bottomSheet, float slideOffset) {

			}
		});*/
	}

	@Override
	public void onClick(View v) {
		dismiss();
	}

	@Override
	public void onDismiss(DialogInterface dialog) {
		Activity activity = getActivity();
		if (activity != null && activity instanceof FilterCallback) {
			ArrayList<MangaGenre> genres = CollectionsUtils.getIfTrue(mGenres, mAdapter.getSelectedGenres());
			((FilterCallback) activity).setFilter(mSorts[mAdapter.getSelectedSort()], genres.toArray(new MangaGenre[genres.size()]));
		}
		super.onDismiss(dialog);
	}
}