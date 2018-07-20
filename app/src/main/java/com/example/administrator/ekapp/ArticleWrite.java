package com.example.administrator.ekapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import java.util.Calendar;
import android.view.View.OnClickListener;

/**
 * Created by Administrator on 2017-07-06.
 */

public class ArticleWrite extends AppCompatActivity implements OnClickListener {
    private EditText etTitle;
    private EditText etContent;
    private EditText etWriter;
    private EditText etId;
    private Button bnUpload;
    private Button bnCancel;
 //   private ImageButton ibPhoto;
    final private static int REQUEST_PHOTO_ALBUM = 1;
    private String filePath;
    private String fileName;
    Calendar oCalendar = Calendar.getInstance();
    final String etDate;

    public ArticleWrite()
    {
        this.etDate = String.valueOf(oCalendar.get(Calendar.YEAR)) + "/" + String.valueOf(oCalendar.get(Calendar.MONTH) + 1) +
                "/" + String.valueOf(oCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_write);

        etTitle = (EditText) findViewById(R.id.article_write_title);
        etContent = (EditText) findViewById(R.id.article_write_content);
        etWriter = (EditText) findViewById(R.id.article_write_writer);
        etId = (EditText) findViewById(R.id.article_write_id);

        bnUpload = (Button) findViewById(R.id.article_write_btUpload);
        bnUpload.setOnClickListener(this);
        bnCancel = (Button) findViewById(R.id.article_button_cancel);
        bnCancel.setOnClickListener(this);

    //    ibPhoto = (ImageButton) findViewById(R.id.article_write_imageButton);
    //    ibPhoto.setOnClickListener(this);
    } //end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()){
   /*         case R.id.article_write_imageButton:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_PHOTO_ALBUM);
                break;*/
            case R.id.article_write_btUpload:
                final ConsultActivity consultActivity = new ConsultActivity(getApplicationContext());
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append("[");
                sb.append("      {");

                int l_num = consultActivity.getArticleList().size() ;
                int b = 0;
                if( l_num == 0){
                    b = 0;
                }
                else {
                    b = consultActivity.getArticleList().get(l_num - 1).getArticleNumber();
                }
                sb.append("         'ArticleNumber':'" + (++b) + "',");
                sb.append("         'Title':'" + etTitle.getText() + "',");
                sb.append("         'Writer':'" + etWriter.getText() + "',");
                sb.append("         'Id':'" + etId.getText() + "',");
                sb.append("         'Content':'" + etContent.getText() + "',");
                sb.append("         'WriteDate':'" + etDate + "',");
                sb.append("         'ImgName':'"+ b+'1' + "'");
                sb.append("      }");
                sb.append("]");
                try {
                    consultActivity.insertJsonData(sb.toString());
                    finish();
                } catch (Exception e) {
                    Log.e("test", "INSERT JSON ERROR! - " + e);
                    e.printStackTrace();
                }
                break;

            case R.id.article_button_cancel:
                finish();
                Log.d("캔슬버튼","눌림");
                break;
        }
    } //end of OnClick

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == REQUEST_PHOTO_ALBUM) {
                Uri uri = getRealPathUri(data.getData());
                filePath = uri.toString();
                fileName = uri.getLastPathSegment();

    //            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
    //            ibPhoto.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            Log.e("test", "onActivityResult Error : " + e);
        }
    }

    private Uri getRealPathUri(Uri uri) {
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = getApplication().getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                filePathUri = Uri.parse(cursor.getString(column_index));
            }
        }
        return filePathUri;
    }
}