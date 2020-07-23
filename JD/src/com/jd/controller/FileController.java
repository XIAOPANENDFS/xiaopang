package com.jd.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

@WebServlet("/File")
public class FileController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> parem = new HashMap<String, String>();
		request.setCharacterEncoding("utf-8");
		// 1.设置存放目录
//		String savePath = request.getServletContext().getRealPath("/WEB-INF/upload");
		String savePath = "D:\\自我资源包\\188\\";
		// 临时存放目录
		String tempPath = request.getServletContext().getRealPath("/WEB-INF/temp");
		// 2.创建对应目录
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			tmpFile.mkdirs();
		}
		// 使用Apache文件上传组件处理文件上传步骤：
		// 1、创建一个DiskFileItemFactory工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中。
		factory.setSizeThreshold(1024 * 1024);
		// 设置上传时生成的临时文件的保存目录
		factory.setRepository(tmpFile);
		// 2、创建一个文件上传解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 监听上传进度
		upload.setProgressListener(new ProgressListener() {
			@Override
			public void update(long arg0, long arg1, int arg2) {
				System.out.println("文件大小为：" + arg0 + ",当前已处理：" + arg1);
			}
		});
		// 解决上传文件名的中文乱码
		upload.setHeaderEncoding("UTF-8");
		// 3、判断提交上来的数据是否是上传表单的数据
		if (!ServletFileUpload.isMultipartContent(request)) {
			// 按照传统方式获取数据
			return;
		}
		// 设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
		upload.setFileSizeMax(1024 * 1024 * 1024);
		// 设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
		upload.setSizeMax(1024 * 1024 * 1024);
		// 4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
		try {
			
			List<FileItem> file = upload.parseRequest(request);
			for (FileItem fileItem : file) {
				//判断这个接收是否是文件
				if (!fileItem.isFormField()) {
					String filename = fileItem.getName();
					if (filename == null || filename.trim().equals("")) {
						continue;
					}
					
					// 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如： c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					// 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\") + 1);
					String fileExtName = filename.substring(filename.lastIndexOf(".") + 1);
					// 获取item中的上传文件的输入流
					InputStream in = fileItem.getInputStream();
					// 得到文件保存的名称
					String saveFilename = makeFileName(filename);
					// 得到文件的保存目录
					String realSavePath = makeFileName(savePath);
					// 创建一个文件输出流
					FileOutputStream out = new FileOutputStream(realSavePath + "\\" + saveFilename);
					// 创建一个缓冲区
					byte buffer[] = new byte[1024];
					// 判断输入流中的数据是否已经读完的标识
					int len = 0;
					while ((len = in.read(buffer)) > 0) {
						// 使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					// 关闭输入流
					in.close();
					// 关闭输出流
					out.close();
					

				}else {
					//这里是接收  从前端发送的字符内容
					parem.put(fileItem.getFieldName(),fileItem.getString("UTF-8"));
					System.out.println(fileItem.getFieldName()+":"+fileItem.getString("UTF-8"));
				}
			

			}

		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private String makeFileName(String filename) {
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		
		File file = new File(filename);
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return  filename;
	}

	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录
		String dir = savePath + "\\" + dir1 + "\\" + dir2; // upload\2\3 upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
}
