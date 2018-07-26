package gigahex.roulette;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gigahex.roulette.database.model.User;


public class UserAdapter extends ArrayAdapter<User> {
    private LayoutInflater inflater;
    private int layout;
    private List<User> userList;

    public UserAdapter(Context context, int resource, List<User> users) {
        super(context, resource, users);
        this.userList = users;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final User user = userList.get(position);

        viewHolder.positionView.setText(String.valueOf(position+1) + ".");
        viewHolder.nameView.setText(user.getName());
        viewHolder.pointsView.setText(String.valueOf(user.getPoints()));

        return convertView;
    }

    private String formatValue(int count, String unit){
        return String.valueOf(count) + " " + unit;
    }
    private class ViewHolder {
        final TextView positionView, nameView, pointsView;
        ViewHolder(View view){
            positionView = (TextView) view.findViewById(R.id.textViewPos);
            nameView = (TextView) view.findViewById(R.id.textViewName);
            pointsView = (TextView) view.findViewById(R.id.textViewPoints);
        }
    }
}
