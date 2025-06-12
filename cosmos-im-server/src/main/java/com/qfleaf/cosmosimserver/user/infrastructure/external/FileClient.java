package com.qfleaf.cosmosimserver.user.infrastructure.external;

import cn.dev33.satoken.stp.StpUtil;
import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FileClient {

    private final MinioClient minioClient;

    // 从配置文件中注入MinIO配置
//    @Value("${minio.bucket.name}")
    private final String bucketName = "user-avatar";

    //    @Value("${minio.default.folder}")
    private final String defaultFolder = "avatar";

//    @Value("${minio.endpoint}")
//    private String minioEndpoint;

    /**
     * 上传文件
     *
     * @param file 上传的文件
     * @return 文件访问路径
     */
    public String uploadFile(MultipartFile file) throws Exception {
        return uploadFile(file, defaultFolder);
    }

    /**
     * 上传文件到指定目录
     *
     * @param file   上传的文件
     * @param folder 目标目录
     * @return 文件访问路径
     */
    public String uploadFile(MultipartFile file, String folder) throws Exception {
        String objectName = folder + "/" + StpUtil.getLoginIdAsLong();

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return getFileUrl(objectName);
        }
    }

    /**
     * 获取文件临时访问URL
     *
     * @param objectName 文件名(包含路径)
     * @param expiry     过期时间(秒)
     * @return 临时URL
     */
    public String getPresignedObjectUrl(String objectName, int expiry) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(expiry, TimeUnit.SECONDS)
                        .build());
    }

    /**
     * 获取文件持久化URL
     *
     * @param objectName 文件名(包含路径)
     * @return 文件URL
     */
    public String getFileUrl(String objectName) {
        return String.format("%s/%s/%s", "", bucketName, objectName);
    }

    /**
     * 下载文件
     *
     * @param objectName 文件名(包含路径)
     * @return 文件流
     */
    public InputStream downloadFile(String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }

    /**
     * 删除文件
     *
     * @param objectName 文件名(包含路径)
     */
    public void deleteFile(String objectName) throws Exception {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build());
    }

    /**
     * 检查文件是否存在
     *
     * @param objectName 文件名(包含路径)
     * @return 是否存在
     */
    public boolean fileExists(String objectName) {
        try {
            minioClient.statObject(
                    StatObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
