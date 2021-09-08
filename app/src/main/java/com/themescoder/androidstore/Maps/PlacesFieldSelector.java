package com.themescoder.androidstore.Maps;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.libraries.places.api.model.Place;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlacesFieldSelector {
    private final List<PlaceField> placeFields;

    public PlacesFieldSelector() {
        this(Arrays.asList(Place.Field.values()));
    }

    public PlacesFieldSelector(List<Place.Field> validFields) {
        placeFields = new ArrayList<>();
        for (Place.Field field : validFields) {
            placeFields.add(new PlaceField(field));
        }
    }

    /**
     * Returns all {@link Place.Field} that are selectable.
     */
    public List<Place.Field> getAllFields() {
        List<Place.Field> list = new ArrayList<>();
        for (PlaceField placeField : placeFields) {
            list.add(placeField.field);
        }

        return list;
    }

    /**
     * Returns all {@link Place.Field} values the user selected.
     */
    public List<Place.Field> getSelectedFields() {
        List<Place.Field> selectedList = new ArrayList<>();
        for (PlaceField placeField : placeFields) {
            if (placeField.checked) {
                selectedList.add(placeField.field);
            }
        }

        return selectedList;
    }

    /**
     * Returns a String representation of all selected {@link Place.Field} values. See {@link
     * #getSelectedFields()}.
     */
    public String getSelectedString() {
        StringBuilder builder = new StringBuilder();
        for (Place.Field field : getSelectedFields()) {
            builder.append(field).append("\n");
        }

        return builder.toString();
    }

    //////////////////////////
    // Helper methods below //
    //////////////////////////

    private static class PlaceField {
        final Place.Field field;
        boolean checked;

        public PlaceField(Place.Field field) {
            this.field = field;
        }
    }

    private static class PlaceFieldArrayAdapter extends ArrayAdapter<PlaceField>
            implements AdapterView.OnItemClickListener {

        public PlaceFieldArrayAdapter(Context context, List<PlaceField> placeFields) {
            super(context, android.R.layout.simple_list_item_multiple_choice, placeFields);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            PlaceField placeField = getItem(position);
            updateView(view, placeField);

            return view;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            PlaceField placeField = getItem(position);
            placeField.checked = !placeField.checked;
            updateView(view, placeField);
        }

        private void updateView(View view, PlaceField placeField) {
            if (view instanceof CheckedTextView) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                checkedTextView.setText(placeField.field.toString());
                checkedTextView.setChecked(placeField.checked);
            }
        }
    }
}
