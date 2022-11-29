package cn.whn.demo.minio;

import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.messages.Bucket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Intellij IDEA
 * Date : 2022/11/29
 * Time : 21:05
 *
 * @author : Whn
 */
public class FileUploader {

    private static final String host = "http://127.0.0.1:9000";
    private static final String accessKey = "whnTestAccessKey";
    private static final String secretKey = "puGPP4uNIaUS1wqoFJAKokhv1qckFyQU";

    public static void main(String[] args) throws Throwable{
        try {
            String bucketName = "whntest";
            String fileName = "K:\\project_github\\demo\\demo-minio-jdk8\\src\\main\\resources\\whnTestFile.txt";
            String newFileName = "whnTest14444.txt";
            upload(bucketName, fileName, newFileName);

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void upload(String bucketName, String filePath, String newFileName) throws Throwable {
        MinioClient minioClient = new MinioClient(host, accessKey, secretKey);

        List<Bucket> buckets = minioClient.listBuckets();
        print(buckets);
        boolean isExist = minioClient.bucketExists(bucketName);
        if(!isExist){
            minioClient.makeBucket(bucketName);
            System.out.println("create bucket: "+bucketName);
        }

        File file = new File(filePath);
        if(!file.exists()){
            throw new FileNotFoundException("文件不存在, 路径: "+filePath);
        }
        long size = file.length();
        FileInputStream fileInputStream = new FileInputStream(file);

        PutObjectOptions options = new PutObjectOptions(size, -1L);
        minioClient.putObject(bucketName, newFileName, fileInputStream, options);

    }

    private static void print(List<Bucket> list){
        for(Bucket obj: list){
            System.out.println(obj.name());
        }
    }

}
