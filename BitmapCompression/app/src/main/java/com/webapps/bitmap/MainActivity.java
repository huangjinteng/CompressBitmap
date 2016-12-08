package com.webapps.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.imageView);
        testJpeg();
    }


    private void testJpeg() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    int quality = 20;
                    InputStream in = getResources().getAssets()
                            .open("teses.jpg");
                    Bitmap bit = BitmapFactory.decodeStream(in);
                    File dirFile = getExternalCacheDir();
                    if (!dirFile.exists()) {
                        dirFile.mkdirs();
                    }
                    File originalFile = new File(dirFile, "original.jpg");
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            originalFile);
                    bit.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream);
                    File jpegTrueFile = new File(dirFile, "jpegtrue.jpg");
                    File jpegFalseFile = new File(dirFile, "jpegfalse.jpg");
                    NativeUtil.compressBitmap(bit, quality,
                            jpegTrueFile.getAbsolutePath(), true);
                    NativeUtil.compressBitmap(bit, quality,
                            jpegFalseFile.getAbsolutePath(), false);
                    try {
                        Log.d("原图大小：", FileUtil.changeUnit(getFileSizes(originalFile)));
                        Log.d("普通压缩大小：", FileUtil.changeUnit(getFileSizes(jpegFalseFile)));
                        Log.d("终极压缩大小：", FileUtil.changeUnit(getFileSizes(jpegTrueFile)));
                        Log.d("终极压缩：", jpegTrueFile.getAbsolutePath());
                        final Bitmap tempBitmap = BitmapFactory.decodeFile(jpegTrueFile.getAbsolutePath());
                        UIUtils.postTaskSafely(new Runnable() {
                            @Override
                            public void run() {
                                mImageView.setImageBitmap(tempBitmap);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static long getFileSizes(File f) throws Exception {
        // 取得文件大小
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = new FileInputStream(f);
            s = fis.available();
            fis.close();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
}
