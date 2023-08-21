package com.example.johncinema.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.johncinema.HandleData;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.net.URL;
import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {

    HandleData handleData;

    EditText editTextName;
    EditText editTextPhone;

    TextView textViewChangeAvatar;
    TextView textViewChangePassword;
    TextView textViewBack;

    Button buttonUpdate;

    ImageView imageViewAvatar;
    String uriAvatar = "no_image";

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        textViewChangePassword = findViewById(R.id.textViewChangePassword);
        textViewChangeAvatar = findViewById(R.id.textViewChangeAvatar);
        textViewBack = findViewById(R.id.textViewBack);
        imageViewAvatar = findViewById(R.id.imageViewAvatar);
        handleData = new HandleData(this);
        // SQLite
        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();

        updateData();
        setButtonUpdate();
        setTextViewChangePassword();
        changeAvatar();
        back();
    }

    void updateData()
    {
        Integer id_user = handleData.getDataInt("id_user");
        listUser.clear();
        listUser = sqlHandle.UserRead();

        for (int i = 0; i < listUser.size(); i++)
        {
            if (id_user.equals(listUser.get(i).getId()))
            {
                editTextName.setText(listUser.get(i).getName());
                editTextPhone.setText(listUser.get(i).getPhone());
                uriAvatar = listUser.get(i).getUrl_avatar();

                break;
            }
        }

        if (!uriAvatar.equals("no_image"))
        {
            imageViewAvatar.setImageURI(Uri.parse(uriAvatar));
        }
    }

    void saveData()
    {
        Integer id_user = handleData.getDataInt("id_user");
        listUser.clear();
        listUser = sqlHandle.UserRead();

        for (int i = 0; i < listUser.size(); i++)
        {
            if (id_user.equals(listUser.get(i).getId()))
            {
                String name = editTextName.getText().toString();
                String phone = listUser.get(i).getPhone();
                String password = listUser.get(i).getPassword();
                String star = listUser.get(i).getStar();
                String role = listUser.get(i).getRole();

                sqlHandle.UserUpdate(id_user, name, phone, password, star, role, uriAvatar);

                break;
            }
        }
    }

    void setButtonUpdate()
    {
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();

                finish();
            }
        });
    }

    void setTextViewChangePassword()
    {
        textViewChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InfoActivity.this, new PasswordActivity().getClass()));
            }
        });
    }

    void changeAvatar()
    {
        textViewChangeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(InfoActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uriAvatar = data.getData().toString();
        imageViewAvatar.setImageURI(Uri.parse(uriAvatar));
    }

    void back()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}