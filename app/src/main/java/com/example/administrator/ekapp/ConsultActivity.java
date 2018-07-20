package com.example.administrator.ekapp;

/**
 * Created by Administrator on 2017-06-30.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ConsultActivity extends AppCompatActivity {
    private Context context;
    private SQLiteDatabase database;

    public ConsultActivity(Context context){
        this.context = context;

        //db 생성
        database = context.openOrCreateDatabase("EKdb.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);

        //table 생성
        try{
            String sql = "CREATE TABLE IF NOT EXISTS Articles(ID integer primary key autoincrement,"
                    + "                                       ArticleNumber integer UNIQUE not null, "
                    + "                                       Title text not null, "
                    + "                                       WriterName text not null, "
                    + "                                       WriterID text not null, "
                    + "                                       Content text not null, "
                    + "                                       WriteDate text not null, "
                    + "                                       ImgName text); ";
            database.execSQL(sql);
        }catch(Exception e){
            Log.e("test", "CREATE TABLE FAILED! - " + e);
            e.printStackTrace();
        }
    } // end of Consult

    public  void  insertJsonData(String jsonData){
        // json data parsing 시 사용할 임시 변수
        int articleNumber;
        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;

        try{
            JSONArray jArr = new JSONArray(jsonData);
            for( int i=0; i < jArr.length(); i++ ){
                // getJSONObject(index) 한 배열로부터 한 데이터 꺼내옴
                JSONObject jObj = jArr.getJSONObject(i);
                articleNumber = jObj.getInt("ArticleNumber");
                title = jObj.getString("Title");
                writer = jObj.getString("Writer");
                id = jObj.getString("Id");
                content = jObj.getString("Content");
                writeDate = jObj.getString("WriteDate");
                imgName = jObj.getString("ImgName");

                //test parsing
                Log.i("test", "ArticleNumber : " + articleNumber + "- Title :" + title);

                //DB에 data 넣기
                String sql = "INSERT INTO Articles(ArticleNumber, Title, WriterName, WriterID, Content, WriteDate, ImgName)"
                            + " Values(" + articleNumber + ", '" + title + "', '" + writer + "', '" + id
                            + "', '" + content + "', '" + writeDate + "', '" + imgName + "');";
                try {
                    database.execSQL(sql);
                }catch(Exception e){
                    Log.e("test", "DB ERROR! - " + e);
                    e.printStackTrace();
                }
            }
        }catch (JSONException e){
            Log.e("test", "JSON ERROR! - " + e);
            e.printStackTrace();
        }
    }

    //DB에서 data를 꺼내 ArrayList<Article>형태로 변환
    public ArrayList<Article> getArticleList(){
        ArrayList<Article> articleList = new ArrayList<Article>();

        int articleNumber;
        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;
        // db에서 부터 데이터 받아 저장할 임시변수

        String sql = "SELECT * FROM Articles;";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()){
            articleNumber = cursor.getInt(1);
            title = cursor.getString(2);
            writer = cursor.getString(3);
            id = cursor.getString(4);
            content = cursor.getString(5);
            writeDate = cursor.getString(6);
            imgName = cursor.getString(7);

            articleList.add(new Article(articleNumber, title, writer, id, content, writeDate, imgName));
        }
        cursor.close();

        return articleList;
    }

    //DB에서 data를 꺼내 Article형태로 변환
    public Article getArticleByArticleNumber(int articleNumber){
        Article article = null;

//        int articleNumber;
        String title;
        String writer;
        String id;
        String content;
        String writeDate;
        String imgName;

        // 데이터 선택
        String sql = "SELECT * FROM Articles WHERE ArticleNumber = " + articleNumber +";";
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToNext();

        articleNumber = cursor.getInt(1);
        title = cursor.getString(2);
        writer = cursor.getString(3);
        id = cursor.getString(4);
        content = cursor.getString(5);
        writeDate = cursor.getString(6);
        imgName = cursor.getString(7);

        article = new Article(articleNumber, title, writer, id, content, writeDate, imgName);
        cursor.close();

        return article;
    }

    // Data 추가
    public void insertData(String voca){
        String sql = "INSERT INTO Articles VALUES(NULL, '" + voca +"');";
        database.execSQL(sql);
    }

    // Data 삭제
    public void removeData(int index){
        String sql = "DELETE FROM Articles WHERE ArticleNumber = '" + index + "';";
        try {
            database.execSQL(sql);
        }catch(Exception e){
            Log.e("test", "DB DELETE ERROR! - " + e);
            e.printStackTrace();
        }
    }

    // Data 수정
    public void modifyData(String title, String id, String writer, String content, int index){
        String sql = "UPDATE Articles SET TITLE = '" + title + "', WriterID = '" + id + "', WriterName = '" + writer + "', CONTENT = '" + content + "' WHERE ArticleNumber = '" + index + "';";
        try {
            database.execSQL(sql);
        }catch(Exception e){
            Log.e("test", "DB MODIFY ERROR! - " + e);
            e.printStackTrace();
        }
    }
}
