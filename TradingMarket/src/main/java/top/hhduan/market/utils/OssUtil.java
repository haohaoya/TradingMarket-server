package top.hhduan.market.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: duanhaohao
 * @date: 2020/8/13 16:36
 * @description: 阿里云oss工具类
 * @version: 1.0
 */
@Slf4j
public class OssUtil {
	public static String endPoint;
	public static String accessKeyId;
	public static String accessKeySecret;
	public static String bucketName;
	public static String ossUrl;

	public static OSSClient getOssClient() {
		endPoint = PropertiesUtil.get("endPoint");
		accessKeyId = PropertiesUtil.get("accessKeyId");
		accessKeySecret = PropertiesUtil.get("accessKeySecret");
		bucketName = PropertiesUtil.get("bucketName");
		ossUrl = PropertiesUtil.get("ossUrl");
		return new OSSClient(endPoint, accessKeyId, accessKeySecret);
	}

	/**
	 * 提取bucketName
	 * @return
	 */
	public static String getBucketName() {
		return bucketName;
	}

	/**
	 * 提取访问的url
	 * @return
	 */
	public static String getOssUrl() {
		return ossUrl;
	}

	/**
	 * 新建Bucket --Bucket权限:私有
	 *
	 * @param bucketName
	 *            bucket名称
	 * @return true 新建Bucket成功
	 */
	public static boolean createBucket(OSSClient client, String bucketName) {
		Bucket bucket = client.createBucket(bucketName);
		return bucketName.equals(bucket.getName());
	}

	/**
	 * 删除Bucket
	 *
	 * @param bucketName
	 *            bucket名称
	 */
	public static void deleteBucket(OSSClient client, String bucketName) {
		client.deleteBucket(bucketName);
		log.info("删除" + bucketName + "Bucket成功");
	}

	/**
	 * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
	 *
	 * @param client
	 *            OSS客户端
	 * @param file
	 *            上传文件
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            上传文件的目录 --bucket下文件的路径
	 * @return String 唯一MD5数字签名
	 */
	public static boolean uploadObject2Oss(OSSClient client, File file, String bucketName, String diskName) throws FileNotFoundException {
		InputStream is = new FileInputStream(file);
		try {
			String fileName = file.getName();
			// 创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(is.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("UTF-8");
			metadata.setContentType(getContentType(fileName));
			// 上传文件
			client.putObject(bucketName, diskName + "/" + fileName, is, metadata);
		} catch (Exception e) {
			log.error("上传文件到OSS失败", e);
			return false;
		} finally {
			log.info("关闭文件的输入流！");
			try {
				is.close();
			} catch (Exception e) {
				log.error("关闭文件的输入流异常", e);
			}
		}
		return true;
	}

	/**
	 * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
	 *
	 * @param client
	 *            OSS客户端
	 * @param fileName
	 *            上传文件
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            上传文件的目录 --bucket下文件的路径
	 * @return String 唯一MD5数字签名
	 */
	public static boolean uploadInputStreamObject2Oss(OSSClient client, InputStream is, String fileName, String bucketName, String diskName) {
		try {
			log.info("upload start");
			// 创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(is.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("UTF-8");
			metadata.setContentType(getContentType(fileName));
			log.info("uploading" + bucketName + diskName + fileName);
			// 上传文件
			PutObjectResult ddd = client.putObject(bucketName, diskName + "/" + fileName, is, metadata);
			log.info("uploaded");
			log.info("上传文件 result : " + JSONObject.toJSONString(ddd));
		} catch (Exception e) {
			log.error("上传文件到OSS失败", e);
			return false;
		} finally {
			if (null != is) {
				log.info("关闭文件的输入流！");
				try {
					is.close();
				} catch (Exception e) {
					log.error("关闭文件的输入流异常", e);
				}
				client.shutdown();
			}
		}
		return true;
	}


	/**
	 * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
	 *
	 * @param client
	 *            OSS客户端
	 * @param fileName
	 *            上传文件
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            上传文件的目录 --bucket下文件的路径
	 * @return String 唯一MD5数字签名
	 */
	public static StringBuilder batchUploadInputStreamObject2Oss(OSSClient client, MultipartFile[] files, String bucketName, String diskName) {
		StringBuilder builder = new StringBuilder();
	    try {
			log.info("upload start");
			// 创建上传Object的Metadata
			for (MultipartFile file : files) {
			    try {
			        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			        fileName = fileName.trim();
			        InputStream is = file.getInputStream();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(is.available());
                    metadata.setCacheControl("no-cache");
                    metadata.setHeader("Pragma", "no-cache");
                    metadata.setContentEncoding("UTF-8");
                    metadata.setContentType(getContentType(fileName));
                    log.info("uploading" + bucketName + "/" + diskName + "/" + fileName);
                    // 上传文件
                    PutObjectResult ddd = client.putObject(bucketName, diskName + "/" + fileName, is, metadata);
                    log.info("uploaded");
                    log.info("上传文件 result : " + JSONObject.toJSONString(ddd));
                    builder.append(OssUtil.getOssUrl()).append("/").append(diskName).append("/").append(fileName).append(",");
                    is.close();
                    log.info("关闭文件的输入流！");
                } catch (Exception e) {
                    log.error("上传文件到OSS失败", e);
                    return null;
                }

			}

		} catch (Exception e) {
			log.error("上传文件到OSS失败", e);
            return null;
		} finally {
		    client.shutdown();
		}
		return builder;
	}

	/**
	 * 向阿里云的OSS存储中存储文件 --file也可以用InputStream替代
	 *
	 * @param client
	 *            OSS客户端
	 * @param fileName
	 *            上传文件
	 * @param bucketName
	 *            bucket名称
	 *
	 * @return String 唯一MD5数字签名
	 */
	public static boolean uploadInputStreamObject2Oss(OSSClient client, InputStream is, String fileName, String bucketName) {
		try {
			log.info("upload start");
			// 创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentLength(is.available());
			metadata.setCacheControl("no-cache");
			metadata.setHeader("Pragma", "no-cache");
			metadata.setContentEncoding("UTF-8");
			metadata.setContentType(getContentType(fileName));
			log.info("uploading" + bucketName + fileName);
			// 上传文件
			PutObjectResult ddd = client.putObject(bucketName, fileName, is, metadata);
			log.info("uploaded");
			log.info("上传文件 result : " + JSONObject.toJSONString(ddd));
		} catch (Exception e) {
			log.error("上传文件到OSS失败", e);
			return false;
		} finally {
			if (null != is) {
				log.info("关闭文件的输入流！");
				try {
					is.close();
				} catch (Exception e) {
					log.error("关闭文件的输入流异常", e);
				}
				client.shutdown();
			}
		}
		return true;
	}

	/**
	 * 根据key获取OSS服务器上的文件到本地
	 *
	 * @param client
	 *            OSS客户端
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            文件路径
	 * @param key
	 *            Bucket下的文件的路径名+文件名
	 */
	public static void getOss2LocalFile(OSSClient client, String bucketName, String yourLocalFile, String key) {
		client.getObject(new GetObjectRequest(bucketName, key), new File(yourLocalFile));
	}

	/**
	 * 根据key获取OSS服务器上的文件输入流
	 *
	 * @param client
	 *            OSS客户端
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            文件路径
	 * @param key
	 *            Bucket下的文件的路径名+文件名
	 */
	public static InputStream getOss2InputStream(OSSClient client, String bucketName, String diskName, String key) {
		OSSObject ossObj = client.getObject(bucketName, diskName + key);
		return ossObj.getObjectContent();
	}

	/**
	 * 根据key删除OSS服务器上的文件
	 *
	 * @param client
	 *            OSS客户端
	 * @param bucketName
	 *            bucket名称
	 * @param diskName
	 *            文件路径
	 * @param key
	 *            Bucket下的文件的路径名+文件名
	 */
	public static void deleteFile(OSSClient client, String bucketName, String diskName, String key) {
		client.deleteObject(bucketName, diskName + key);
		log.info("删除" + bucketName + "下的文件" + diskName + key + "成功");
	}

	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 *
	 * @param fileName
	 *            文件名
	 * @return 文件的contentType
	 */
	public static String getContentType(String fileName) {
		String bmp = "bmp";
		String gif = "gif";
		String jpeg = "jpeg";
		String jpg = "jpg";
		String png = "png";
		String html = "html";
		String txt = "txt";
		String vsd = "vsd";
		String ppt = "ppt";
		String pptx = "pptx";
		String doc = "doc";
		String docx = "docx";
		String xml = "xml";
		String csv = "csv";
		String pdf = "pdf";
		String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (bmp.equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if (gif.equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if (jpeg.equalsIgnoreCase(fileExtension) || jpg.equalsIgnoreCase(fileExtension) || png.equalsIgnoreCase(fileExtension)) {
			return "image/jpeg";
		}
		if (html.equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if (txt.equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if (vsd.equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if (ppt.equalsIgnoreCase(fileExtension) || pptx.equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if (doc.equalsIgnoreCase(fileExtension) || docx.equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if (xml.equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		if (csv.equalsIgnoreCase(fileExtension) || pdf.equalsIgnoreCase(fileExtension)) {
			return "application/octet-stream";
		}
		return "text/html";
	}


	/**
	 * 获得url链接
	 *
	 * @param key
	 * @return
	 */
	public static String getUrl(String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10

		Date expiration = new Date(System.currentTimeMillis() + 3600L * 24 * 365);
		// 生成URL
		OSSClient ossClient = getOssClient();
		URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;

	}

	/**
	 * 删除oss文件
	 * @param client
	 * @param bucketName
	 * @param key
	 * @return
	 */
	public static Boolean deleteObject(OSSClient client, String bucketName, String key) {
		// 删除文件。
		client.deleteObject(bucketName, key);
		// 判断文件是否存在。
		return client.doesObjectExist(bucketName, key);
	}

	/**
	 * 判断文件是否存在
	 * @param client
	 * @param bucketName
	 * @param key
	 * @return
	 */
	public static Boolean doesObjectExist(OSSClient client, String bucketName, String key) {
		// 判断文件是否存在。
		return client.doesObjectExist(bucketName, key);
	}

	/**
	 * 列举bucketName下面带前缀的文件
	 * @param client
	 * @param bucketName
	 * @param prefix
	 * @return
	 */
	public static List<String> listObjects(OSSClient client, String bucketName, String prefix) {
		List<String> list = new ArrayList<>();
		// 列举文件。 如果不设置KeyPrefix，则列举存储空间下所有的文件。KeyPrefix，则列举包含指定前缀的文件。
		ObjectListing objectListing = client.listObjects(bucketName, prefix);
		List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		for (OSSObjectSummary s : sums) {
			list.add(s.getKey());
		}
		return list;
	}

	/**
	 * 列举bucketName下面目录为prefix的文件
	 * @param client
	 * @param bucketName
	 * @param prefix
	 * @return
	 */
	public static List<String> getListObjects(OSSClient client, String bucketName, String prefix) {
		// 构造ListObjectsRequest请求。
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
		// 设置prefix参数来获取prefix目录下的所有文件。
		listObjectsRequest.setPrefix(prefix);
		// 递归列出fun目录下的所有文件。
		ObjectListing objectListing = client.listObjects(listObjectsRequest);
		List<String> list = new ArrayList<>();
		List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
		for (OSSObjectSummary s : sums) {
			//过滤无字节文件
			long size = s.getSize();
			if (size > 0) {
				list.add(s.getKey());
			}
		}
		return list;
	}
}
