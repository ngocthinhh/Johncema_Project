package com.example.johncinema.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.johncinema.Activities.HandleMovie;
import com.example.johncinema.Models.ChairClass;
import com.example.johncinema.Models.MovieClass;
import com.example.johncinema.Models.ScheduleClass;
import com.example.johncinema.Models.Ticket;
import com.example.johncinema.Models.UserClass;

import java.util.ArrayList;

public class SQLHandle extends AppCompatActivity {
    SQLiteDatabase mydatabase;

    public SQLHandle (Context context)
    {
        mydatabase = context.openOrCreateDatabase("qlmovie.db", MODE_PRIVATE, null);

        try
        {
//            Drop("Movie");
            String sql = "CREATE TABLE Movie (id_movie TEXT PRIMARY KEY, name_movie TEXT, director_movie TEXT, duration_movie TEXT, debut_movie TEXT," +
                    " url_poster_movie TEXT, rating_movie TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception ex)
        {

        }

        try
        {
//            Drop("Schedule");
            String sql = "CREATE TABLE Schedule (id_schedule TEXT PRIMARY KEY, id_movie_schedule TEXT, start_hour_schedule TEXT," +
                    " theater_schedule TEXT, date_schedule TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception ex)
        {

        }

        try
        {
            String sql = "CREATE TABLE User (id_user INTEGER PRIMARY KEY AUTOINCREMENT, name_user TEXT, phone_user TEXT, password_user TEXT," +
                    " star_user TEXT, role_user TEXT, url_avatar_user TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception ex)
        {

        }

        try
        {
//            Drop("Chair");
            String sql = "CREATE TABLE Chair (id_chair TEXT PRIMARY KEY, row_chair INTEGER, column_chair INTEGER," +
                    " id_schedule_chair TEXT, state_chair TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception ex)
        {

        }

        try
        {
//            Drop("Ticket");
            String sql = "CREATE TABLE Ticket (id_ticket TEXT PRIMARY KEY, id_user_ticket TEXT, id_chair_ticket TEXT, time_buy_ticket TEXT," +
                    "title_ticket TEXT, id_image_ticket TEXT, theater_name_ticket TEXT, time_ticket TEXT, day_ticket TEXT, money_ticket TEXT," +
                    "rating_ticket TEXT)";
            mydatabase.execSQL(sql);
        }
        catch (Exception ex)
        {

        }
    }

    void Drop(String nameTable)
    {
        String sql = "DROP TABLE " + nameTable;
        mydatabase.execSQL(sql);
    }

    public String MovieInsert(String id, String name, String director, String duration, String debut, String urlPoster, String rating)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_movie", id);
        myvalue.put("name_movie", name);
        myvalue.put("director_movie", director);
        myvalue.put("duration_movie", duration);
        myvalue.put("debut_movie", debut);
        myvalue.put("url_poster_movie", urlPoster);
        myvalue.put("rating_movie", rating);

        if (mydatabase.insert("Movie", null, myvalue) == -1)
        {
            return "Thêm thất bại";
        }
        else
        {
            return "Thêm thành công";
        }
    }

    public String MovieDelete(String id)
    {
        if (mydatabase.delete("Movie","id_movie = ?",new String[]{id}) != 0)
        {
            return "Xoá thành công";
        }
        else
        {
            return "Xoá thất bại";
        }
    }

    public String MovieUpdate(String id, String name, String director, String duration, String debut, String urlPoster, String rating)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("name_movie", name);
        myvalue.put("director_movie", director);
        myvalue.put("duration_movie", duration);
        myvalue.put("debut_movie", debut);
        myvalue.put("url_poster_movie", urlPoster);
        myvalue.put("rating_movie", rating);

        if (mydatabase.update("Movie", myvalue, "id_movie = ?", new String[] {id}) != 0)
        {
            return "Cập nhật thành công";
        }
        else
        {
            return "Cập nhật thất bại";
        }
    }

    public ArrayList<MovieClass> MovieRead()
    {
        ArrayList<MovieClass> list = new ArrayList<>();

        String id = "";
        String name = "";
        String director = "";
        String duration = "";
        String debut = "";
        String urlPoster = "";
        String rating = "";

        Cursor c = mydatabase.query("Movie",null,null,null,null,null,null);
        c.moveToNext();
        while (c.isAfterLast() == false)
        {
            id = c.getString(0);
            name = c.getString(1);
            director = c.getString(2);
            duration = c.getString(3);
            debut = c.getString(4);
            urlPoster = c.getString(5);
            rating = c.getString(6);

            c.moveToNext();
            list.add(new MovieClass(id, name, director, duration, debut, urlPoster, rating));
        }
        c.close();

        return list;
    }

    public String ScheduleInsert(String id, String id_movie, String hour, String theater, String date)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_schedule", id);
        myvalue.put("id_movie_schedule", id_movie);
        myvalue.put("start_hour_schedule", hour);
        myvalue.put("theater_schedule", theater);
        myvalue.put("date_schedule", date);

        if (mydatabase.insert("Schedule", null, myvalue) == -1)
        {
            return "Thêm thất bại";
        }
        else
        {
            return "Thêm thành công";
        }
    }

    public String ScheduleDelete(String id)
    {
        if (mydatabase.delete("Schedule","id_schedule = ?",new String[]{id}) != 0)
        {
            return "Xoá thành công";
        }
        else
        {
            return "Xoá thất bại";
        }
    }

    public String ScheduleUpdate(String id, String id_movie, String hour, String theater, String date)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_movie_schedule", id_movie);
        myvalue.put("start_hour_schedule", hour);
        myvalue.put("theater_schedule", theater);
        myvalue.put("date_schedule", date);

        if (mydatabase.update("Schedule", myvalue, "id_schedule = ?", new String[] {id}) != 0)
        {
            return "Cập nhật thành công";
        }
        else
        {
            return "Cập nhật thất bại";
        }
    }

    public ArrayList<ScheduleClass> ScheduleRead()
    {
        ArrayList<ScheduleClass> list = new ArrayList<>();

        String id = "";
        String id_movie = "";
        String hour = "";
        String theater = "";
        String date = "";

        Cursor c = mydatabase.query("Schedule",null,null,null,null,null,null);
        c.moveToNext();
        while (c.isAfterLast() == false)
        {
            id = c.getString(0);
            id_movie = c.getString(1);
            hour = c.getString(2);
            theater = c.getString(3);
            date = c.getString(4);

            c.moveToNext();
            list.add(new ScheduleClass(id, id_movie, hour, theater, date));
        }
        c.close();

        return list;
    }

    public String UserInsert(Integer id, String name, String phone, String password, String star, String role, String url_avatar)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("name_user", name);
        myvalue.put("phone_user", phone);
        myvalue.put("password_user", password);
        myvalue.put("star_user", star);
        myvalue.put("role_user", role);
        myvalue.put("url_avatar_user", url_avatar);

        if (mydatabase.insert("User", null, myvalue) == -1)
        {
            return "Thêm thất bại";
        }
        else
        {
            return "Thêm thành công";
        }
    }

    public String UserDelete(Integer id)
    {
        if (mydatabase.delete("User","id_user = ?",new String[]{id.toString()}) != 0)
        {
            return "Xoá thành công";
        }
        else
        {
            return "Xoá thất bại";
        }
    }

    public String UserUpdate(Integer id, String name, String phone, String password, String star, String role, String url_avatar)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("name_user", name);
        myvalue.put("phone_user", phone);
        myvalue.put("password_user", password);
        myvalue.put("star_user", star);
        myvalue.put("role_user", role);
        myvalue.put("url_avatar_user", url_avatar);

        if (mydatabase.update("User", myvalue, "id_user = ?", new String[] {id.toString()}) != 0)
        {
            return "Cập nhật thành công";
        }
        else
        {
            return "Cập nhật thất bại";
        }
    }

    public ArrayList<UserClass> UserRead()
    {
        ArrayList<UserClass> list = new ArrayList<>();

        Integer id = -1;
        String name = "";
        String phone = "";
        String password = "";
        String star = "";
        String role = "";
        String url_avatar = "";

        Cursor c = mydatabase.query("User",null,null,null,null,null,null);
        c.moveToNext();
        while (c.isAfterLast() == false)
        {
            id = c.getInt(0);
            name = c.getString(1);
            phone = c.getString(2);
            password = c.getString(3);
            star = c.getString(4);
            role = c.getString(5);
            url_avatar = c.getString(6);

            c.moveToNext();
            list.add(new UserClass(id, name, phone, password, star, role, url_avatar));
        }
        c.close();

        return list;
    }

    public String ChairInsert(String id_chair, Integer row, Integer column, String id_schedule, String state)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_chair", id_chair);
        myvalue.put("row_chair", row);
        myvalue.put("column_chair", column);
        myvalue.put("id_schedule_chair", id_schedule);
        myvalue.put("state_chair", state);

        if (mydatabase.insert("Chair", null, myvalue) == -1)
        {
            return "Thêm không thành công";
        }
        else
        {
            return "Thêm thành công";
        }
    }

    public String ChairDelete(String id_chair)
    {
        if (mydatabase.delete("Chair","id_chair = ?",
                new String[]{id_chair}) != 0)
        {
            return "Xoá thành công";
        }
        else
        {
            return "Xoá thất bại";
        }
    }

    public String ChairUpdate(String id_chair,  Integer row, Integer column, String id_schedule, String state)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("state_chair", state);

        if (mydatabase.update("Chair", myvalue, "id_chair = ?",
                new String[] {id_chair}) != 0)
        {
            return "Cập nhật thành công";
        }
        else
        {
            return "Cập nhật thất bại";
        }
    }

    public ArrayList<ChairClass> ChairRead()
    {
        ArrayList<ChairClass> list = new ArrayList<>();

        String id_chair = "";
        Integer row = -1;
        Integer column = -1;
        String id_schedule = "";
        String state = "";

        Cursor c = mydatabase.query("Chair",null,null,null,null,null,"column_chair");
        c.moveToNext();
        while (c.isAfterLast() == false)
        {
            id_chair = c.getString(0);
            row = c.getInt(1);
            column = c.getInt(2);
            id_schedule = c.getString(3);
            state = c.getString(4);

            c.moveToNext();
            list.add(new ChairClass(id_chair, row, column, id_schedule, state));
        }
        c.close();

        return list;
    }

    public String TicketInsert(String id, String id_user, String id_chair, String time_buy, String title,
                               String id_image, String theater_name, String time,String day, String money, String rating)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_ticket", id);
        myvalue.put("id_user_ticket", id_user);
        myvalue.put("id_chair_ticket", id_chair);
        myvalue.put("time_buy_ticket", time_buy);
        myvalue.put("title_ticket", title);
        myvalue.put("id_image_ticket", id_image);
        myvalue.put("theater_name_ticket", theater_name);
        myvalue.put("time_ticket", time);
        myvalue.put("day_ticket", day);
        myvalue.put("money_ticket", money);
        myvalue.put("rating_ticket", rating);

        if (mydatabase.insert("Ticket", null, myvalue) == -1)
        {
            return "Đặt vé thất bại";
        }
        else
        {
            return "Đặt vé thành công";
        }
    }

    public String TicketDelete(String id)
    {
        if (mydatabase.delete("Ticket","id_ticket = ?",
                new String[]{id}) != 0)
        {
            return "Xoá thành công";
        }
        else
        {
            return "Xoá thất bại";
        }
    }

    public String TicketUpdate(String id, String id_user, String id_chair, String time_buy, String title,
                               String id_image, String theater_name, String time,String day, String money, String rating)
    {
        ContentValues myvalue = new ContentValues();
        myvalue.put("id_user_ticket", id_user);
        myvalue.put("id_chair_ticket", id_chair);
        myvalue.put("time_buy_ticket", time_buy);
        myvalue.put("title_ticket", title);
        myvalue.put("id_image_ticket", id_image);
        myvalue.put("theater_name_ticket", theater_name);
        myvalue.put("time_ticket", time);
        myvalue.put("day_ticket", day);
        myvalue.put("money_ticket", money);
        myvalue.put("rating_ticket", rating);

        if (mydatabase.update("Ticket", myvalue, "id_ticket = ?",
                new String[] {id}) != 0)
        {
            return "Cập nhật thành công";
        }
        else
        {
            return "Cập nhật thất bại";
        }
    }

    public ArrayList<Ticket> TicketRead()
    {
        ArrayList<Ticket> list = new ArrayList<>();

        String id = "";
        String id_user = "";
        String id_chair = "";
        String time_buy = "";
        String title = "";
        String id_image = "";
        String theater_name = "";
        String time = "";
        String day = "";
        String money = "";
        String rating = "";

        Cursor c = mydatabase.query("Ticket",null,null,null,null,null,null);
        c.moveToNext();
        while (c.isAfterLast() == false)
        {
            id = c.getString(0);
            id_user = c.getString(1);
            id_chair = c.getString(2);
            time_buy = c.getString(3);
            title = c.getString(4);
            id_image = c.getString(5);
            theater_name = c.getString(6);
            time = c.getString(7);
            day = c.getString(8);
            money = c.getString(9);
            rating = c.getString(10);

            c.moveToNext();
            list.add(new Ticket(id, id_user, id_chair, time_buy, title, id_image, theater_name, time, day, money, rating));
        }
        c.close();

        return list;
    }
}
