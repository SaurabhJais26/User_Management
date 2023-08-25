package com.example.usermanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagement.ModelResponse.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {
    List<User> userList;

    Context context;

    public UserAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewHolder holder, int position) {

        holder.userName.setText(userList.get(position).getUsername());
        holder.userEmail.setText(userList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView userName,userEmail;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.etusername);
            userEmail = itemView.findViewById(R.id.etuseremail);
        }
    }
}
