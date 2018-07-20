package com.example.administrator.ekapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.example.administrator.ekapp.R.id.image;
import static com.example.administrator.ekapp.R.id.parent;

/**
 * Created by Administrator on 2017-07-03.
 */

public class CustomAdapter extends ArrayAdapter<Article> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Article> profData;

    public CustomAdapter(Context context, int layoutResourceId, ArrayList<Article> profData) {
        super(context, layoutResourceId, profData);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.profData = profData;
    }

    //get view overriding
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
        }
        //row, findViewById 로 row 레이아웃설정
//        ImageView imageView = (ImageView) row.findViewById(R.id.custom_row_imageView1);
        TextView tvTitle = (TextView) row.findViewById(R.id.article_list_title);
        TextView tvContent = (TextView) row.findViewById(R.id.article_list_cont);
        TextView tvWriter = (TextView) row.findViewById(R.id.article_list_writer);
        TextView tvId= (TextView) row.findViewById(R.id.article_list_deadline);
        TextView tvNo= (TextView) row.findViewById(R.id.article_list_no);

        //int, position 리스트 순서값으로 set text
        if (profData.get(position).getTitle().length() > 12)
            tvTitle.setText(profData.get(position).getTitle().substring(0, 11) + "...");
        else
            tvTitle.setText(profData.get(position).getTitle());

        if (profData.get(position).getContent().length() > 17)
            tvContent.setText(profData.get(position).getContent().substring(0, 16) + "...");
        else
            tvContent.setText(profData.get(position).getContent());

        tvWriter.setText(profData.get(position).getWriter());
        tvId.setText(profData.get(position).getId());
        tvNo.setText(String.valueOf(profData.get(position).getArticleNumber()));

   //     File imgFile = new  File(profData.get(position).getImgName());

   //     if(imgFile.exists()){

    //        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

  //          ImageView myImage = (ImageView) row.findViewById(R.id.custom_row_imageView1);
  //          Glide.with(context).load(profData.get(position).getImgName()).into(myImage);
 //           myImage.setImageBitmap(myBitmap);

  //      }
        /*
        // 이미지 읽어서 리스트 표시 assets 폴더의 사진을 가져온다
        try {
            InputStream ims = context.getAssets().open(profData.get(position).getImgName());
            Drawable d = Drawable.createFromStream(ims, null);
            imageView.setImageDrawable(d);
        } catch (IOException e) {
            Log.e("ERROR", "ERROR," + e);
        }
        */
        return row;
    }
}