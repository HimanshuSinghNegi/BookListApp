package com.example.mybooklistapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mybooklistapp.Model.Book;

import java.util.ArrayList;

public class MyCustomAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

    ArrayList<Book> list = new ArrayList<>();
    ArrayList<Book> filteredList= new ArrayList<>();


    BookItemClicked listener;
    Context context;
    public MyCustomAdapter( BookItemClicked listener,Context context) {
        this.listener=listener;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_book_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        view.setOnClickListener(v -> listener.onItemClicked(filteredList.get(viewHolder.getAdapterPosition())));


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Book model = filteredList.get(position);

        holder.nameOfBook.setText(model.getNameOfBook());
        holder.author.setText(model.getAuthor());
        holder.pubDate.setText(model.getPubDate());
        Log.d("url1",model.getImageUrl());
        Glide.with(holder.bookImage.getContext()).load(model.getImageUrl()).placeholder(R.drawable.loading).into(holder.bookImage);

    }


    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    //update
    public void updateBooks(ArrayList<Book> updateBooks){

        Log.d("up",updateBooks.toString());
        list.clear();
        list.addAll(updateBooks);
        filteredList.addAll(updateBooks);

        Log.i("updated",list.toString());
        notifyDataSetChanged();
    }


    //to filter the search Results
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Book> filterList = new ArrayList<>();

                if(constraint==null || constraint.length()==-0){
                    results.count = list.size();
                    results.values = list;
                }else
                {
                    for(Book bookModel: list){
                        if(bookModel.getNameOfBook().toLowerCase().contains(constraint.toString().toLowerCase())){
                            filterList.add(bookModel);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                }


//                Log.i("results",);
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList = (ArrayList<Book>) results.values;
                Log.i("filtered",filteredList.toString());

                notifyDataSetChanged();


                if(filteredList.size()==0){

                    Toast.makeText(context, "No Data Found", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }




}

class ViewHolder extends RecyclerView.ViewHolder{
    TextView nameOfBook,author,pubDate;
    ImageView bookImage;
    public ViewHolder(View itemView) {
        super(itemView);
        nameOfBook = itemView.findViewById(R.id.nameOfBook);
        author = itemView.findViewById(R.id.author);
        pubDate = itemView.findViewById(R.id.pubDate);
        bookImage = itemView.findViewById(R.id.bookImage);
    }
}

//interface
interface BookItemClicked{
    void onItemClicked(Book item);
}


