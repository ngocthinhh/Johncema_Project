package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Fragments.AccountFragment;
import com.example.johncinema.HandleData;
import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText editTextPhone;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonLogup;
    Intent myIntent;
    HandleData handleData;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;

    Integer id_user = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handleData = new HandleData(this);

        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogup = findViewById(R.id.buttonLogup);
        // SQLite
        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();

        switchActivity();
    }

    void saveData()
    {
        handleData.saveData("id_user", id_user);
    }

    void switchActivity()
    {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myIntent = new Intent(LoginActivity.this, new MainActivity().getClass());

                String msg = "";
                msg = checkEmpty();
                if (msg.equals(""))
                {
                    msg = checkLogin();
                    if (msg.equals(""))
                    {
                        saveData();
                        startActivity(myIntent);
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonLogup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, new LogupActivity().getClass()));
            }
        });
    }

    String checkLogin()
    {
        listUser.clear();
        listUser = sqlHandle.UserRead();

        for (int i = 0; i < listUser.size(); i++)
        {
            if (editTextPhone.getText().toString().equals(listUser.get(i).getPhone()))
            {
                if (editTextPassword.getText().toString().equals(listUser.get(i).getPassword()))
                {
                    id_user = listUser.get(i).getId();
                    return "";
                }
                else
                {
                    return "Mật khẩu không đúng";
                }
            }
        }

        return "Số điện thoại không tồn tại";
    }

    String checkEmpty()
    {
        if (editTextPhone.getText().toString().isEmpty() || editTextPassword.getText().toString().isEmpty())
        {
            return "Vui lòng không để trống";
        }

        return "";
    }
}