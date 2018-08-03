package com.swapniljain.bakingapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swapniljain.bakingapp.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientActivityFragment extends Fragment {

    public IngredientActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredient, container, false);
    }
}
