package com.example.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new Thread() {
			public void run() {
				uploadFile();
			};
		}.start();
	}

	private void uploadFile() {
		try {
			FileUpload fileUpload = new FileUpload();
			Map<String, String> params = new HashMap<>();
			params.put("username", "jack");
			InputStream inputStream = getResources().openRawResource(R.raw.rawtest);
			String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "rawtext.txt";
			System.out.println(filePath);
			FileOutputStream fileOutputStream = new FileOutputStream(filePath);

			byte[] buf = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(buf)) != -1) {
				fileOutputStream.write(buf, 0, len);
			}
			inputStream.close();
			fileOutputStream.close();
			File uploadFile = new File(filePath);
			fileUpload.uploadForm(params, "uploadFile", uploadFile, "新建文本文档test.txt", "http://192.168.1.63:8080/strurts2fileupload/uploadAction");
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
