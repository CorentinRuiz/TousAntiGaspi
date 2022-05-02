package edu.poly.tousantigaspi.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.object.CodeScannerProduct;
import edu.poly.tousantigaspi.object.Product;
import edu.poly.tousantigaspi.util.factory.ProductFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BarCodeScannerActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 101;

    private CodeScanner codeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);

        setupPermissions();
        codeScanner();

    }

    private void codeScanner(){
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        codeScanner = new CodeScanner(this,scannerView);

        codeScanner.setCamera(CodeScanner.CAMERA_BACK);
        codeScanner.setFormats(CodeScanner.ALL_FORMATS);
        codeScanner.setAutoFocusMode(AutoFocusMode.SAFE);
        codeScanner.setScanMode(ScanMode.CONTINUOUS);
        codeScanner.setAutoFocusEnabled(true);
        codeScanner.setFlashEnabled(false);



        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setProductName(result.getText().toString());

                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Throwable thrown) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Main","Camera error :" + thrown.getMessage());
                    }
                });
            }

        });

    }

    private void setupPermissions(){
        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest();
        }
    }

    private void makeRequest() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, getString(R.string.camera_permission_needed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setProductName(String barcode){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://world.openfoodfacts.org/api/v0/product/" + barcode +".json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    assert responseBody != null;
                    JSONObject res = new JSONObject(responseBody.string());
                    final String name = res.getJSONObject("product").getString("product_name");

                    BarCodeScannerActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            openProductPopUp(name,findViewById(R.id.PorductContainer));
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void openProductPopUp(String name, View view){
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewPopupWindow = layoutInflater.inflate(R.layout.popup_scanner_decode,null);
        final PopupWindow popupWindow = new PopupWindow(viewPopupWindow,1000,240,true);

        popupWindow.setAnimationStyle(R.style.popup_window_animation);
        popupWindow.setElevation(5);
        popupWindow.showAtLocation(view, Gravity.BOTTOM,0,200);

        TextView text = (TextView) viewPopupWindow.findViewById(R.id.productName);
        text.setText(name);

        ImageButton button = viewPopupWindow.findViewById(R.id.arrowButton);

       button.setOnClickListener(click ->{
            try {
                CodeScannerProduct product = new CodeScannerProduct("",name,"",1);
                getIntent().putExtra("productScanner",product);
                setResult(Activity.RESULT_OK,getIntent());
                finish();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });

    }
}
