package com.example.administrator.ekapp;

import android.content.Intent;
import android.os.Bundle;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.widget.ProfilePictureView;
import org.json.JSONObject;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;

public class MyPageActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    RelativeLayout mLoginLayout;
    RelativeLayout mUserLayout;
    String userId="";
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.mypage_ek);

        mLoginLayout = (RelativeLayout) findViewById(R.id.login_frame);
        mUserLayout = (RelativeLayout) findViewById(R.id.user_frame);
        tvUserName = (TextView) findViewById(R.id.userName);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.v("result",object.toString());
                        Log.v("response", response.toString());

                        //-----이미지 가져오기
                        try {

                            String name = object.getString("name");         // 이름
                            String gender = object.getString("gender");     // 성별
                            userId = object.getString("id");   //id
                            Log.d("TAG","페이스북 이름 -> " + name);
                            Log.d("TAG","페이스북 성별 -> " + gender);
                            Log.d("TAG","페이스북 유저아이디" + userId);

                            ProfilePictureView profilePictureView;
                            profilePictureView = (ProfilePictureView) findViewById(R.id.userProfile);
                            profilePictureView.setProfileId(userId);

                            URL url = new URL("https://graph.facebook.com/"+userId+"/picture");
                            URLConnection conn = url.openConnection();
                            conn.connect();

                            if(!userId.equals("")) {
                                Log.d("TAG","aaaaaaa");
                                tvUserName.setText(name);
                                mLoginLayout.setVisibility(View.GONE);
                                mUserLayout.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("fail","fail");
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("LoginErr",error.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
