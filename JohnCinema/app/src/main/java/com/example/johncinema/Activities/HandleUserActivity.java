package com.example.johncinema.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.johncinema.Models.UserClass;
import com.example.johncinema.R;
import com.example.johncinema.SQLite.SQLHandle;

import java.util.ArrayList;

public class HandleUserActivity extends AppCompatActivity {

    // Back
    TextView textViewBack;

    // Input
    TextView editTextUserID;
    TextView editTextUserName;
    TextView editTextPhone;
    TextView editTextPassword;
    TextView editTextStar;
    Spinner spinnerRole;
    ArrayList<String> arrayListRole;
    ArrayAdapter<String> arrayAdapterRole;

    // Button
    Button buttonAdd;
    Button buttonUpdate;
    Button buttonDelete;

    // List View
    ListView listViewInfo;

    // SQLite
    SQLHandle sqlHandle;
    ArrayList<UserClass> listUser;
    ArrayList<String> mylist;
    ArrayAdapter<String> myadapter;

    String oldPhone = "non";
    String selectRole = "non";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_user);

        // Back
        textViewBack = findViewById(R.id.textViewBack);
        // Input
        editTextUserID = findViewById(R.id.editTextUserID);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextStar = findViewById(R.id.editTextStar);
        spinnerRole = findViewById(R.id.spinnerRole);
        arrayListRole = new ArrayList<>();
        arrayAdapterRole = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayListRole);
        spinnerRole.setAdapter(arrayAdapterRole);

        // Button
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        // List View
        listViewInfo = findViewById(R.id.listViewInfo);
        // SQLite
        sqlHandle = new SQLHandle(this);
        listUser = new ArrayList<>();
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);

        listViewInfo.setAdapter(myadapter);

        loadData();

        handleButton();
        close();
        select();
        resizeListView();
        setUpRole();
        selectRole();
    }

    void setUpRole()
    {
        arrayListRole.add("Admin");
        arrayListRole.add("Customer");

        arrayAdapterRole.notifyDataSetChanged();
    }

    void selectRole()
    {
        spinnerRole.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectRole = arrayListRole.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    void refeshEditText()
    {
        editTextUserID.setText("");
        editTextUserName.setText("");
        editTextPhone.setText("");
        editTextPassword.setText("");
        editTextStar.setText("");
        spinnerRole.setSelection(0);
    }

    private void resizeListView() {
        ViewGroup.LayoutParams params = listViewInfo.getLayoutParams();
        params.height = (mylist.size() * 135);
    }

    void select()
    {
        listViewInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editTextUserID.setText(listUser.get(position).getId().toString());
                editTextUserName.setText(listUser.get(position).getName());
                editTextPhone.setText(listUser.get(position).getPhone());
                editTextPassword.setText(listUser.get(position).getPassword());
                editTextStar.setText(listUser.get(position).getStar());

                if (listUser.get(position).getRole().equals("Admin"))
                {
                    spinnerRole.setSelection(0);
                }
                else if (listUser.get(position).getRole().equals("Customer"))
                {
                    spinnerRole.setSelection(1);
                }

                oldPhone = listUser.get(position).getPhone();
            }
        });
    }

    void loadData()
    {
        listUser.clear();
        mylist.clear();
        listUser = sqlHandle.UserRead();
        for (int i = 0; i < listUser.size(); i++)
        {
            mylist.add(listUser.get(i).getId() + " - " +
                    listUser.get(i).getName() + " - " +
                    listUser.get(i).getRole());
        }

        myadapter.notifyDataSetChanged();
    }

    void handleButton()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";

                listUser.clear();
                listUser = sqlHandle.UserRead();
                for (int i = 0; i < listUser.size(); i++)
                {
                    if (editTextPhone.getText().toString().equals(listUser.get(i).getPhone()))
                    {
                        msg = "Trùng số điện thoại";
                        break;
                    }
                }

                if (msg.equals(""))
                {
                    msg = sqlHandle.UserInsert(null, editTextUserName.getText().toString(),
                            editTextPhone.getText().toString(),
                            editTextPassword.getText().toString(),
                            editTextStar.getText().toString(),
                            selectRole,
                            "no_image");
                }
                Toast.makeText(HandleUserActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
            }
        });

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = "";
                String url_avatar = "";

                if (!editTextPhone.getText().toString().equals(oldPhone))
                {
                    listUser.clear();
                    listUser = sqlHandle.UserRead();
                    for (int i = 0; i < listUser.size(); i++)
                    {
                        if (editTextPhone.getText().toString().equals(listUser.get(i).getPhone()))
                        {
                            msg = "Trùng số điện thoại";
                            break;
                        }
                    }
                }

                if (msg.equals(""))
                {
                    // SAVE url_avatar
                    for (int i = 0; i < listUser.size(); i++)
                    {
                        if (listUser.get(i).getId().equals(Integer.parseInt(editTextUserID.getText().toString())))
                        {
                            url_avatar = listUser.get(i).getUrl_avatar();
                        }
                    }

                    msg = sqlHandle.UserUpdate(Integer.parseInt(editTextUserID.getText().toString()),
                            editTextUserName.getText().toString(),
                            editTextPhone.getText().toString(),
                            editTextPassword.getText().toString(),
                            editTextStar.getText().toString(),
                            selectRole,
                            url_avatar);
                }

                Toast.makeText(HandleUserActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = sqlHandle.UserDelete(Integer.parseInt(editTextUserID.getText().toString()));
                Toast.makeText(HandleUserActivity.this, msg, Toast.LENGTH_SHORT).show();

                loadData();
                resizeListView();
                refeshEditText();
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
}