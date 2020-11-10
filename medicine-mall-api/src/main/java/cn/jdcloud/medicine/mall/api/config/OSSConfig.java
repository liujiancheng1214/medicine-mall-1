package cn.jdcloud.medicine.mall.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

public class OSSConfig {
	

    @Value("${cms.web.domain}")
    private String webDomain;
    @Value("${cms.web.domain}")
	private  String endpoint;  
	 					  
	 @Value("${aliyun.oss.accessKeyId}")
    private  String accessKeyId;   
	// @Value("${aliyun.oss.accessKeySecret}")
    private  String accessKeySecret;   
	// @Value("${aliyun.oss.bucketName}")
    private  String bucketName;    
	 //@Value("${aliyun.oss.picLocation}")
    private  String picLocation;   
    
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getAccessKeySecret() {
		return accessKeySecret;
	}
	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getPicLocation() {
		return picLocation;
	}
	public void setPicLocation(String picLocation) {
		this.picLocation = picLocation;
	}
}
