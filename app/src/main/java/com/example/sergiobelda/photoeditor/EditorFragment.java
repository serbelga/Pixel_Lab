package com.example.sergiobelda.photoeditor;


import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import androidx.annotation.*;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.widget.CompoundButtonCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditorFragment extends Fragment {
    EditableImageView editableImageView;
    View bottomSheetPersistent;
    BottomSheetBehavior bottomSheetBehavior;
    Button squareToolButton;
    private RadioGroup colorsRadioGroup;
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
        bottomSheetPersistent = view.findViewById(R.id.bottom_drawer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetPersistent);
        squareToolButton = view.findViewById(R.id.squareToolButton);
        bottomSheetBehavior.setBottomSheetCallback(createBottomSheetCallback());
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tools:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        break;
                    case R.id.export:
                }
                return true;
            }
        });
        squareToolButton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    editableImageView.setSquareTool(true);
                                                }
                                            }

        );



        colorsRadioGroup = view.findViewById(R.id.colorsRadioGroup);
        initializeColors(
                colorsRadioGroup);
    }

    private BottomSheetBehavior.BottomSheetCallback createBottomSheetCallback() {
        // Set up BottomSheetCallback
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
                new BottomSheetBehavior.BottomSheetCallback() {
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
        return bottomSheetCallback;
    }

    private void initializeColors(
            RadioGroup group) {
        int[] colorsArray = getResources().getIntArray(R.array.palette);

        for (int i = 0; i < colorsArray.length; i++) {
            AppCompatRadioButton button = new AppCompatRadioButton(getContext());

            CompoundButtonCompat.setButtonTintList(
                    button, ColorStateList.valueOf(convertToDisplay(colorsArray[i])));

            group.addView(button);
        }
    }

    @ColorInt
    private int convertToDisplay(@ColorInt int color) {
        return color == Color.WHITE ? Color.BLACK : color;
    }
}
