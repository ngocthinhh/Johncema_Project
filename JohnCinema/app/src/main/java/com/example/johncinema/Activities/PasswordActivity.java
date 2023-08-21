package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.HandleData;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class PasswordActivity extends AppCompatActivity {

    HandleData handleData;
    EditText editTextPasswordOld;
    EditText editTextPasswordNew;
    EditText editTextPasswordNewAccept;
    TextView textViewMSG;
    TextView textViewBack;
    Button buttonAccept;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;
    UserClass userPresent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        handleData = new HandleData(this);
        editTextPasswordOld = findViewById(R.id.editTextPasswordOld);
        editTextPasswordNew = findViewById(R.id.editTextPasswordNew);
        editTextPasswordNewAccept = findViewById(R.id.editTextPasswordNewAccept);
        buttonAccept = findViewById(R.id.buttonAccept);
        textViewMSG = findViewById(R.id.textViewMSG);
        textViewBack = findViewById(R.id.textViewBack);

        // SQLite
        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();

        setButtonAccept();
        back();
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

    String checkNewPassword()
    {
        if (editTextPasswordNew.getText().toString().equals(editTextPasswordNewAccept.getText().toString()))
        {
            return "";
        }

        return  "Mật khẩu mới không khớp";
    }

    String checkEmptyNewPassword()
    {
        if (editTextPasswordNew.getText().toString().isEmpty())
        {
            return "Vui lòng nhập mật khẩu mới";
        }

        return "";
    }

    String checkOldPassword()
    {
        listUser.clear();
        listUser = sqlHandle.UserRead();
        Integer id_user = handleData.getDataInt("id_user");

        String oldPass = "";

        for (int i = 0; i < listUser.size(); i++)
        {
            if (listUser.get(i).getId().equals(id_user))
            {
                userPresent = listUser.get(i);
                oldPass = listUser.get(i).getPassword();
                break;
            }
        }

        if (oldPass.equals(editTextPasswordOld.getText().toString()))
        {
            return "";
        }

        return "Mật khẩu cũ không khớp";
    }

    void setButtonAccept()
    {
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                msg = checkOldPassword();
                if (msg.equals(""))
                {
                    msg = checkEmptyNewPassword();
                    if (msg.equals(""))
                    {
                        msg = checkNewPassword();
                        if (msg.equals(""))
                        {
                            Integer id_user = handleData.getDataInt("id_user");

                            sqlHandle.UserUpdate(id_user, userPresent.getName(), userPresent.getPhone(),
                                    editTextPasswordNew.getText().toString(), userPresent.getStar(), userPresent.getRole(),
                                    userPresent.getUrl_avatar());

                            finish();
                        }
                        else
                        {
                            Toast.makeText(PasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(PasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(PasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}