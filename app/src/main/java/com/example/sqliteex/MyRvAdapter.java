package com.example.sqliteex;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.MyViewHolder> {
    List<Contact> ls;
    Context c;
    public MyRvAdapter(List<Contact> ls, Context c)
    {
        this.ls=ls;
        this.c=c;
    }
    @NonNull
    @Override
    public MyRvAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(c).inflate(R.layout.row, parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRvAdapter.MyViewHolder holder, final int position) {
        holder.name.setText(ls.get(position).getName());
        holder.email.setText(ls.get(position).getEmail());
        holder.phno.setText(ls.get(position).getPhno());
        holder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ls.get(position).getPhno()));
                c.startActivity(i);
            }
        });

        holder.ll.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setTitle("Do you want to delete?");
                builder.setMessage(ls.get(position).name+"\n"+ls.get(position).getPhno());
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBHelper helper=new MyDBHelper(c);
                        SQLiteDatabase db=helper.getWritableDatabase();
                        String whereClause = ContactContract.Contact._ID+" = ?";
                        String[] args={ls.get(position).id+""};
                        int a=db.delete(ContactContract.Contact._ContactTable,whereClause,args);
                        if(a==1)
                        {
                            Toast.makeText(c,"Removed",Toast.LENGTH_SHORT).show();
                            ls.remove(position);
                            notifyDataSetChanged();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,phno,email;
        LinearLayout ll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            phno=itemView.findViewById(R.id.phno);
            email=itemView.findViewById(R.id.email);
            ll=itemView.findViewById(R.id.ll);
        }
    }
}
