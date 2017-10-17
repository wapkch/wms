package com._520it.wms.util;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;










import java.io.File;
import java.util.UUID;

public class FileUploadUtil {
	public static final String suffix = "_small";

	public static String uploadFile(File file, String fileName)
			throws Exception {
		String uuid = UUID.randomUUID().toString();
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		fileName = uuid + fileType;
		String path = ServletActionContext.getServletContext().getRealPath(
				"/upload");
		File targetFile = new File(path, fileName);
		FileUtils.copyFile(file, targetFile);

		// 缩略图是在文件后面加上_small
		String smallImg = uuid + suffix + fileType;
		File smallTargetFile = new File(path, smallImg);
		// 生成缩略图
		Thumbnails.of(targetFile).scale(0.4f).toFile(smallTargetFile);
		return "/upload/" + fileName;
	}

	/**
	 * 删除文件 根据文件完整路径删除
	 * @param imagePath
	 */
	public static void deleteFile(String imagePath) {
		String path=ServletActionContext.getServletContext().getRealPath("/")+imagePath;
		File file=new File(path);
		if(file.exists()) file.delete();
		path=ServletActionContext.getServletContext().getRealPath("/")+ imagePath.substring(0,imagePath.indexOf("."))+FileUploadUtil.suffix+imagePath.substring(imagePath.indexOf("."));
		file=new File(path);
		if(file.exists()) file.delete();
	}
}
