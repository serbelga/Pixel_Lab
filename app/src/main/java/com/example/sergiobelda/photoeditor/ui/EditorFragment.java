package com.example.sergiobelda.photoeditor.ui;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.example.sergiobelda.photoeditor.R;
import com.example.sergiobelda.photoeditor.editableimageview.EditableImageView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditorFragment extends Fragment {
    private EditableImageView editableImageView;
    private BottomNavigationView bottomNavigationView;

    private View toolsBottomSheet;
    private BottomSheetBehavior toolsBottomSheetBehavior;
    private ViewPager toolsViewPager;
    private TabLayout toolsTabLayout;
    public TabItem tabPaint, tabFigure, tabSticker;

    int currentColor;

    public EditorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_editor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editableImageView = view.findViewById(R.id.myImageView);
        toolsBottomSheet = view.findViewById(R.id.toolsBottomSheet);
        bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        toolsTabLayout = view.findViewById(R.id.toolsTabLayout);
        toolsViewPager = view.findViewById(R.id.toolsViewPager);
        tabFigure = view.findViewById(R.id.tabFigure);
        tabPaint = view.findViewById(R.id.tabPaint);
        tabSticker = view.findViewById(R.id.tabSticker);
        initializeTabLayout();
        initializeViewPager();
        initializeBottomSheetBehavior();
        initializeBottomNavigationView();
    }

    private void initializeTabLayout() {
        toolsTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(toolsViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                super.onTabSelected(tab);
                toolsViewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    editableImageView.setEditMode(0);
                } else {
                    editableImageView.setEditMode(1);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                super.onTabUnselected(tab);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                super.onTabReselected(tab);
            }
        });
    }

    private void initializeViewPager() {
        final ToolsViewPagerAdapter pagerAdapter = new ToolsViewPagerAdapter(getFragmentManager(), toolsTabLayout.getTabCount());
        toolsViewPager.setAdapter(pagerAdapter);
        toolsViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolsTabLayout));
    }

    private void initializeBottomSheetBehavior() {
        toolsBottomSheetBehavior = BottomSheetBehavior.from(toolsBottomSheet);
        toolsBottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback());
        toolsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    private BottomSheetBehavior.BottomSheetCallback createBottomSheetCallback() {
        // Set up BottomSheetCallback
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {}
        };
    }

    private void initializeBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tools:
                        toolsBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case R.id.export:
                        saveImage();
                        break;
                }
                return true;
            }
        });
    }


    private void saveImage(){
        editableImageView.setDrawingCacheEnabled(true);
        editableImageView.buildDrawingCache(true);
        Bitmap bitmap = editableImageView.getDrawingCache();
        File root = Environment.getExternalStorageDirectory();
        Random rnd = new Random();
        int i = rnd.nextInt(1000);
        File cachePath = new File(root.getAbsolutePath() + "/DCIM/Camera/image_" + String.valueOf(i) + ".jpg");
        try {
            cachePath.createNewFile();
            FileOutputStream ostream = new FileOutputStream(cachePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.flush();
            ostream.close();
        } catch (Exception e) {
            e.printStackTrace();
            //TODO SnackBar permission not granted
        }
        editableImageView.setDrawingCacheEnabled(false);
    }

    private class ToolsViewPagerAdapter extends FragmentPagerAdapter {
        private int tabsNum;

        ToolsViewPagerAdapter(@NonNull FragmentManager fm, int tabsNum) {
            super(fm);
            this.tabsNum = tabsNum;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            if (position == 0) {
                fragment = new TabPaint();
                ((TabPaint) fragment).setTabPaintListener(new TabPaint.TabPaintListener() {
                    @Override
                    public void onColorSelected(int currentColor) {
                        //editableImageView.setImageDrawable(new ColorDrawable(currentColor));
                    }
                });
            } else if (position == 1) {
                fragment = new TabFigure();
                ((TabFigure) fragment).setTabFigureListener(new TabFigure.TabFigureListener() {
                    @Override
                    public void onFigureSelected(int currentFigure) {
                        editableImageView.setFigureMode(currentFigure);
                    }
                });
            } else if (position == 2) {
                fragment = new TabSticker();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabsNum;
        }
    }
}
