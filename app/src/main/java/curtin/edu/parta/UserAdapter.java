package curtin.edu.parta;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.internal.ContextUtils;

import java.util.HashMap;
import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UsersViewHolder> {

    Context context;
    static List<HashMap<String, String>> usersList;


    public UserAdapter(Context context, List<HashMap<String, String>> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

  /*  protected Users(Parcel in) {
        name = in.readString();
        price = in.readString();
        if (in.readByte() == 0) {
            imageUrl = null;
        } else {
            imageUrl = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        if (imageUrl == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(imageUrl);
        }
    }*/

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_details, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {

        holder.id.setText(usersList.get(position).get("id"));
        holder.name.setText(usersList.get(position).get("username"));


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SelectedUserDetails.class);
               // Users selected = usersList.get(position).get("id");

                //i.putExtra("selectedUser", usersList.get(position).get("username"));
                i.putExtra("selectedId", usersList.get(position).get("id"));
               // i.putExtra("selectedEmail", usersList.get(position).get("email"));
                //Users selected = usersList.get(position);
                //i.putExtra("selectedUser", selected);
                //i.putExtra("useridcheck", usersList.get(position).get("id"));
                 i.putExtra("one", "1");
                //i.putExtra("ID", usersList.get(position).get("id"));
                //i.putExtra("USERNAME", usersList.get(position).get("username"));
                context.startActivity(i);

            }
        });
    }
    @Override
    public int getItemCount () {
        return usersList.size();
    }

    public static final class UsersViewHolder extends RecyclerView.ViewHolder  {
       // private final Context context;
        public TextView id, name;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
          //  context = itemView.getContext();
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
        }

      /* public void onClick(View view){
         final Intent intent;
            Intent i = new Intent(context, SelectedUserDetails.class);
            context.startActivity(i);
        }*/
    }
}

