package curtin.edu.parta;

import android.content.Context;
import android.content.Intent;
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


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    Context context;
    static List<HashMap<String, String>> postsList;


    public PostAdapter(Context context, List<HashMap<String, String>> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.post_details, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        holder.userid.setText(postsList.get(position).get("userId"));
        holder.postid.setText(postsList.get(position).get("id"));
        holder.postB.setText(postsList.get(position).get("body"));
        holder.postT.setText(postsList.get(position).get("title"));

        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SelectedUserDetails.class);
                //HashMap<String, String> selected = usersList.get(position);

               // i.putExtra("one", "1");
                //i.putExtra("ID", usersList.get(position).get("id"));
                //i.putExtra("USERNAME", usersList.get(position).get("username"));
                context.startActivity(i);

            }
        });*/
    }
    @Override
    public int getItemCount () {
        return postsList.size();
    }

    public static final class PostViewHolder extends RecyclerView.ViewHolder  {
        // private final Context context;
        public TextView userid, postid, postB, postT;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            //  context = itemView.getContext();
            userid = itemView.findViewById(R.id.userid);
            postB = itemView.findViewById(R.id.pBody);
            postT = itemView.findViewById(R.id.pTitle);
            postid = itemView.findViewById(R.id.postid);
        }
    }
}
