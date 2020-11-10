package cn.jdcloud.medicine.mall.api.controller;

import cn.jdcloud.framework.core.vo.ApiResult;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;

@RestController
@RequestMapping("/file")
@Api(tags = "文件上传")
public class FileController {

	    @Value("${oss.aliyun.endpoint}")
		private  String endpoint;
	    @Value("${oss.aliyun.accessKeyId}")
	    private  String accessKeyId;
	    @Value("${oss.aliyun.ccessKeySecret}")
	    private  String accessKeySecret;
	    @Value("${oss.aliyun.bucketName}")
	    private  String bucketName;
	    @Value("${oss.aliyun.picLocation}")
	    private  String picLocation;

	 @ApiOperation(value = "文件上传")
	 @PostMapping(value = "/fileUpload")
	 public ApiResult<String> fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {
		 OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		 // 获取文件名
		 String fileName = file.getOriginalFilename();
		 // 获取文件后缀名
		 String suffixName = fileName.substring(fileName.lastIndexOf("."));
		 // 最后上传生成的文件名
		 String finalFileName = System.currentTimeMillis() + "" + new SecureRandom().nextInt(0x0400) + suffixName;
		 // oss中的文件夹名
		 String objectName = picLocation + File.separator + finalFileName;
		 // 创建上传文件的元信息，可以通过文件元信息设置HTTP header(设置了才能通过返回的链接直接访问)。
		 ObjectMetadata objectMetadata = new ObjectMetadata();
		 objectMetadata.setContentType("image/jpg");
		 // 文件上传
		 ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(file.getBytes()),objectMetadata);
		 // 设置URL过期时间为1小时。
		 Date expiration = new Date(System.currentTimeMillis() + 3600 * 1000);
		 String url = ossClient.generatePresignedUrl(bucketName, objectName, expiration).toString();
		 ossClient.shutdown();
		System.out.println("url==="+url);
		return ApiResult.ok(url);

	 }



}
