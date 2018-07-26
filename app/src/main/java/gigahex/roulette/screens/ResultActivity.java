package gigahex.roulette.screens;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;


import butterknife.ButterKnife;
import butterknife.OnClick;
import gigahex.roulette.R;
import gigahex.roulette.database.ResultPoints;
import gigahex.roulette.UserAdapter;
import gigahex.roulette.database.model.User;


public class ResultActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int LOADER_ID = 1;
    UserAdapter adapterResults;


    private ListView userList;
    List<User> userArrayList;

    @OnClick({R.id.backResults})
    void onBackClick(View view) {
        Intent i = new Intent(ResultActivity.this, TargetActivity.class);
        startActivity(i);
        }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ButterKnife.bind(this);
        userList = (ListView) findViewById(R.id.list);
        userArrayList = new ArrayList<>();
        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        adapterResults = new UserAdapter(this, R.layout.item_list_results, userArrayList);
        userList.setAdapter(adapterResults);
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                ResultPoints.Columns.COLUMN_ID,
                ResultPoints.Columns.COLUMN_NAME,
                ResultPoints.Columns.COLUMN_POINTS,
        };
        if (id == LOADER_ID)
            return new CursorLoader(this, ResultPoints.CONTENT_URI,
                    projection,
                    null,
                    null,
                    ResultPoints.Columns.COLUMN_NAME);
        else
            throw new InvalidParameterException("Invalid loader id");
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            while (data.moveToNext()) {
                long _id = data.getLong(0);
                String _name = data.getString(1);
                int _points = data.getInt(2);
                userArrayList.add(new User(_id, _name, _points));
            }
            adapterResults.notifyDataSetChanged();
            data.close();}
             else{
                Log.d("MAYTAG", "Cursor is null");
            }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
