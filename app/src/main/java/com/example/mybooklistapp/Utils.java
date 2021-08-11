package com.example.mybooklistapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mybooklistapp.Model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public final class Utils {

    // Empty constructor
    private Utils() {
    }

    //method to fetch data from the Api with help of volley Library
    public static void fetchBookData(String url, Context context, MyCustomAdapter adapter, ProgressBar progressBar) {


        // jsonObject Request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            // create a list
            ArrayList<Book> book_list = new ArrayList<>();

            //here parsing the data
            try {
                String input = response.getString("items");
//                    Log.i("items", input);
                JSONArray jsonArray = new JSONArray(input);

                Log.i("jsonArr" , String.valueOf(jsonArray.length()));



                for (int i = 0; i < jsonArray.length(); i++) {


                    Log.i("listItem",String.valueOf(i));

                    JSONObject currentBook = jsonArray.getJSONObject(i);

                    JSONObject bookInfo = currentBook.getJSONObject("volumeInfo");

                    //Extracting the name of the book
                    String nameOfBook = bookInfo.getString("title");
                    Log.i("title ", nameOfBook);


                    StringBuilder  author = new StringBuilder(" ");;

                    if(!bookInfo.isNull("authors")){
                    JSONArray authorArray = bookInfo.getJSONArray("authors");
                    // Extracting the name of the author books

                    if(authorArray.length()>1) {
                        for (int j = 0; j < authorArray.length(); j++) {
                            Log.i("count",String.valueOf(j));
                            // condition if there are more than one author
                            author.append(authorArray.get(j)).append(", ");
                            }
                    } else {
                        author = author.append(authorArray.get(0));
                    }
                        // removing the last comma
                        author.deleteCharAt(author.length()-2);
                    }
                    else {
                        author.append("N.A");
                    }


                    Log.i("aut",author.toString());

                    //Extracting the publish date of the book
                    String pubDate;
                    if(!bookInfo.isNull("publishedDate")) {

                        pubDate = bookInfo.getString("publishedDate");
                    }else {

                        pubDate ="N.A";
                    }

                    Log.i("publ",pubDate);


                    //Here extracting the url of image
                    JSONObject thumbNailImageObject = bookInfo.getJSONObject("imageLinks");

                    String imageUrl = thumbNailImageObject.getString("smallThumbnail");


                    // Now extracting the preview Link
                    String previewLink = bookInfo.getString("previewLink");

                    //making a book type object
                    Book book = new Book(nameOfBook, author.toString(), pubDate, imageUrl, previewLink);

                    //adding the above object into the list
                    book_list.add(book);
                }
        } catch (JSONException e) {
                e.printStackTrace();
            }

            //to disable the progress bar
            progressBar.setVisibility(View.GONE);


            Log.d("booksize",String.valueOf(book_list.size()));


            //now setting adapter
            adapter.updateBooks(book_list);


        }, error -> Log.d("response", error.toString()));


        // Add the request to the RequestQueue.
        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

}



