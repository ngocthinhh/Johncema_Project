package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class LogupActivity extends AppCompatActivity {

    Button buttonLogin;
    Button buttonFinish;

    EditText editTextName;
    EditText editTextPhone;
    EditText editTextPassword;
    EditText editTextPasswordAccept;
    TextView textViewBack;
    TextView textViewMSG;

    String msg = "";

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;

    ImageView imageViewSecret;
    String role = "Customer";
    Integer count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logup);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonFinish = findViewById(R.id.buttonFinish);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordAccept = findViewById(R.id.editTextPasswordAccept);
        textViewBack = findViewById(R.id.textViewBack);
        imageViewSecret = findViewById(R.id.imageViewSecret);
        role = "Customer";

        // SQLite
        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();

        secret();
        switchActivity();
        close();
    }

    void secret()
    {
        imageViewSecret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count += 1;
                if (count >= 10)
                {
                    Toast.makeText(LogupActivity.this, "Đã kích hoạt Admin", Toast.LENGTH_SHORT).show();
                    role = "Admin";
                }
            }
        });
    }

    void close()
    {
        textViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    String checkPhone()
    {
        listUser.clear();
        listUser = sqlHandle.UserRead();

        for (int i = 0; i < listUser.size(); i++)
        {
            if (editTextPhone.getText().toString().equals(listUser.get(i).getPhone()))
            {
                return "Số điện thoại này đã được đăng ký";
            }
        }

        return "";
    }

    void switchActivity()
    {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogupActivity.this, new LoginActivity().getClass()));
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = checkEmpty();
                if (msg.equals(""))
                {
                    msg = checkPhone();
                    if (msg.equals(""))
                    {
                        msg = checkPassword();
                        if (msg.equals(""))
                        {
                            sqlHandle.UserInsert(0, editTextName.getText().toString(), editTextPhone.getText().toString(),
                                    editTextPassword.getText().toString(), "0", role, "no_image");

                            startActivity(new Intent(LogupActivity.this, new LoginActivity().getClass()));
                        }
                        else
                        {
                            Toast.makeText(LogupActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(LogupActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(LogupActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    String checkEmpty()
    {
        if (editTextPhone.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty() ||
        editTextPassword.getText().toString().isEmpty() || editTextPasswordAccept.getText().toString().isEmpty())
        {
            return "Vui lòng không để trống";
        }

        return "";
    }

    String checkPassword()
    {
        if (editTextPassword.getText().toString().equals(editTextPasswordAccept.getText().toString()))
        {
            return "";
        }

        return "Mật khẩu mới không khớp.";
    }
}