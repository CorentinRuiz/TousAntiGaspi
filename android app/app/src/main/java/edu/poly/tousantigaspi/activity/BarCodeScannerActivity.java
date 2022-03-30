package edu.poly.tousantigaspi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.AutoFocusMode;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.DecodeCallback;
import com.budiyev.android.codescanner.ErrorCallback;
import com.budiyev.android.codescanner.ScanMode;
import com.google.zxing.Result;

import edu.poly.tousantigaspi.R;
import edu.poly.tousantigaspi.util.ProductRequester;

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
        codeScanner = new CodeScanner(this,findViewById(R.id.scanner));

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
                        ProductRequester requester = new ProductRequester(result.getText(),getBaseContext());
                        TextView text = (TextView) findViewById(R.id.productName);
                        text.setText(requester.getProductName());
                    }
                });
            }
        });

        codeScanner.setErrorCallback(new ErrorCallback() {
            @Override
            public void onError(@NonNull Exception error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Main","Camera error :" + error.getMessage());
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
                Toast.makeText(this, "You need the camera permission to use this feature !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
