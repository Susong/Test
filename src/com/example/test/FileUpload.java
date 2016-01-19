package com.example.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by dell on 2016/1/18 0018.
 */
public class FileUpload {
	private static final String BOUNDARY = "----WebKitFormBoundaryT1HoybnYeFOGFlBR";

	public void uploadForm(Map<String, String> params, String fileFormName, File uploadFile, String newFileName, String urlStr) throws IOException {
		if (newFileName == null || newFileName.trim().equals("")) {
			newFileName = uploadFile.getName();
		}
		StringBuilder sb = new StringBuilder();

		for (String key : params.keySet()) {
			sb.append("--" + BOUNDARY + "\r\n");
			sb.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n");
			sb.append("\r\n");
			sb.append(params.get(key) + "\r\n");
		}

		sb.append("--" + BOUNDARY + "\r\n");
		sb.append("Content-Disposition: form-data; name=\"" + fileFormName + "\"; filename=\"" + newFileName + "\"" + "\r\n");
		sb.append("Content-Type: image/jpeg" + "\r\n");
		sb.append("\r\n");
		byte[] headInfo = sb.toString().getBytes("UTF-8");
		byte[] endInfo = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
		System.out.println(sb.toString());

		URL url = new URL(urlStr);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		conn.setRequestProperty("Content-Length", String.valueOf(headInfo.length + uploadFile.length() + endInfo.length));
		conn.setDoOutput(true);

		OutputStream out = conn.getOutputStream();
		InputStream in = new FileInputStream(uploadFile);
		out.write(headInfo);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.write(endInfo);

		in.close();
		out.close();

		if (conn.getResponseCode() == 200) {
			System.out.println("上传成功");
		}
	}

}
