package com.jd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 优化版本上传文件
 * 
 * @author SIMOBAI
 *
 */
@WebServlet("/io3")
public class IO extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String savePath = request.getServletContext().getRealPath("/static/");
		String savePath = "D:\\自我资源包\\188\\";
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/temp");
		makeFileName(savePath);
		File tmpFile = makeFileNameRe(tempPath);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024);
		factory.setRepository(tmpFile);
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long arg0, long arg1, int arg2) {
				System.out.println("文件大小为：" + arg0 + ",当前已处理：" + arg1);
			}
		});
		Map<String, String> parem = new HashMap<String, String>();
		if (ServletFileUpload.isMultipartContent(request)) {
			upload.setFileSizeMax(1024 * 1024 * 1024);
			upload.setSizeMax(1024 * 1024 * 1024);
			int i = 0;
			try {
				List<FileItem> file = upload.parseRequest(request);
				for (FileItem fileItem : file) {
					if (!fileItem.isFormField()) {
						String filename = fileItem.getName();
						if (filename == null || filename.trim().equals("")) {
							continue;
						}
						filename = filename.substring(filename.lastIndexOf("\\") + 1);
						InputStream in = fileItem.getInputStream();
						String realSavePath =savePath+filename;
						FileOutputStream out = new FileOutputStream(realSavePath);
						byte buffer[] = new byte[1024];
						int len = 0;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
						//上传文件成功一次，那么就这边就反馈
						i++;
						in.close();
						out.close();
					} else {
						//从前台接收的字符内容
						parem.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
					}
					
				}
				if(i>0) {
					request.getRequestDispatcher("test2.html").forward(request, response);
				}else {
					request.getRequestDispatcher("test3.html").forward(request, response);
				}
				
				
				
				
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		}

	}

	// 校验存放目录是否存在
	private void makeFileName(String savePath) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	private File makeFileNameRe(String savePath) {
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

}
