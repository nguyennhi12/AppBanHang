package com.example.appbanhangnew.controller.User_Admin;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhangnew.action.User_Admin.Account.AccountActivity;
import com.example.appbanhangnew.action.User_Admin.Account.DangKiActivity;
import com.example.appbanhangnew.until.APIUtils;
import com.example.appbanhangnew.until.DataClient;
import com.example.appbanhangnew.until.Server;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserController {
    public UserController (){

    }
    public void setTILs(TextInputEditText txthoten, TextInputLayout TILhoten, TextInputEditText txtDiachi, TextInputLayout TILDiachi,
                        TextInputEditText txtEmail, TextInputLayout TILEmail, TextView txtNgaySinh, TextInputLayout TILngaysinh,
                        TextInputEditText txtsodienthoai, TextInputLayout TILsodienthoai){
        txthoten.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILhoten.setError("B???n b???t bu???c ph???i nh???p tr?????ng n??y");
                } else {
                    TILhoten.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtDiachi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILDiachi.setError("B???n b???t bu???c ph???i nh???p tr?????ng n??y");
                } else {
                    TILDiachi.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILEmail.setError("B???n b???t bu???c ph???i nh???p tr?????ng n??y");
                } else {
                    TILEmail.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        txtNgaySinh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILngaysinh.setError("B???n b???t bu???c ph???i nh???p tr?????ng n??y");
                } else {
                    TILngaysinh.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        txtsodienthoai.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    TILsodienthoai.setError("B???n b???t bu???c ph???i nh???p tr?????ng n??y");
                } else {
                    TILsodienthoai.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public String getRealPathFromURI(Uri contentUri,Context context) {
        String path = null;
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        //Toast.makeText(getApplicationContext(),contentUri.toString(),Toast.LENGTH_SHORT).show();
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(proj[0]);
            path = cursor.getString(column_index);
            //Toast.makeText(getApplicationContext(),String.valueOf(cursor.getCount()),Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(),cursor.getString(column_index),Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        return path;
    }
    public void Chonngay(TextView  txtNgaySinh,Context context) {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                calendar.set(year, month, dayOfMonth);
                txtNgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    public void SaveInformation(int iduser,String hoten, String ngaysinh, String email, String Diachi, String sodienthoai,Context context,String realpath){
        if (hoten.length() > 0 && ngaysinh.length() > 0 && email.length() > 0 && Diachi.length() > 0) {
            //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT);
            File file = new File(realpath);
            //???????ng d???n
            String file_path = file.getAbsolutePath();
            String[] mangtenfile = file_path.split("\\.");

            file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
            Log.d("FilePath", file_path);

            //Ki???u d??? li???u c???a file
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);

            MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
            DataClient dataClient = APIUtils.getData();
            retrofit2.Call<String> callback = dataClient.UploadImage(body);

            //Khi s??? d???ng d???ng call tr??? v??? 2 gt l?? th??nh c??ng v?? th???t b???o
            callback.enqueue(new Callback<String>() {
                @Override
                public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                    if (response != null) {
                        String message = response.body();
                        Log.d("BBBB", message.toString());
                        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                        if (message.length() > 0) {
                            Log.d("BBBB", "true");
                            //g???i nh???ng interface c???a dataClient l??n server.
                            //Sau khi d??? li???u ???? c?? s??? t???o ra ?????i t?????ng object s??? nh???n dl b??n trong class dataClient.

                            //up h??nh ???nh t??? th?? m???c: l???y ip + server+ image+ t??n file
                            String duongdan = Server.duongdanSaveImageToDatabase + "image/" + message;
                            new PostToserver(hoten, ngaysinh, Diachi, sodienthoai, email, duongdan, iduser,context).execute(Server.duongdansetInformation);
                        }


                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    //Toast.makeText(getApplicationContext(),call.toString(),Toast.LENGTH_SHORT).show();
                    Log.d("BBB4", t.getMessage());
                }
            });

        } else {
            Toast.makeText(context, "Th??ng tin ch??a nh???p ?????", Toast.LENGTH_SHORT);
        }
    }
    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truy???n d??? li???u user v?? password
        String hoten, ngaysinh, diachi, sodienthoai, email, image;
        int iduser;
        Context context;

        public PostToserver(String hoten, String ngaysinh, String diachi, String sodienthoai, String email, String image, int iduser,Context context) {
            this.hoten = hoten;
            this.ngaysinh = ngaysinh;
            this.diachi = diachi;
            this.sodienthoai = sodienthoai;
            this.email = email;
            this.image = image;
            this.iduser = iduser;
            this.context=context;
        }

        @Override
        protected String doInBackground(String... strings) {
            // g???i dl l??n server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //g???i l??n server qua key;
                    .addFormDataPart("iduser", String.valueOf(iduser))
                    .addFormDataPart("Ten", hoten)
                    .addFormDataPart("Ngaysinh", ngaysinh)
                    .addFormDataPart("Diachi", diachi)
                    .addFormDataPart("sodienthoai", sodienthoai)
                    .addFormDataPart("Email", email)
                    .addFormDataPart("image", image).
                            setType(MultipartBody.FORM)
                    .build();
            //G???i request; ch???n tv c???a okhttp3;
            Request request = new Request.Builder()
                    //Truy???n url;
                    .url(Server.duongdansetInformation).post(requestBody).build();
            try {
                //Tr??? gi?? tr??? d???; Th???c hi???n request;
                okhttp3.Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nh???n d??? li???u;
            //g??n layout;

            Log.d("AAA", s);

            int success = 0;
            String message = "";
            Log.d("AAA",s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");


            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if (success == 1) {
                Intent intent = new Intent(context, AccountActivity.class);
                context.startActivity(intent);
                Activity activity=(Activity) context;
                activity.finish();
            } else {

            }

            super.onPostExecute(s);
        }
    }

}
