package gigahex.roulette.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import gigahex.roulette.R;
import gigahex.roulette.database.ResultPoints;



public class CustomDialog extends DialogFragment {
    private EditText nameBox;
    private long userId=0;
    private int points;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        points = getArguments().getInt("points");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View view = layoutInflater.inflate(R.layout.dialog, null);

        nameBox = (EditText) view.findViewById(R.id.name);
        builder.setView(view);
        builder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               String name = nameBox.getText().toString();
               ContentResolver contentResolver = getActivity().getContentResolver();
               ContentValues values = new ContentValues();
               values.put(ResultPoints.Columns.COLUMN_NAME, name);
               values.put(ResultPoints.Columns.COLUMN_POINTS, points);
               Uri uri = contentResolver.insert(gigahex.roulette.database.ResultPoints.CONTENT_URI, values);
           }
       })
               .setNegativeButton("Cancel", null);

        return builder.create();
    }
    interface CallBackData{
        void setText(String name);
    }

}
