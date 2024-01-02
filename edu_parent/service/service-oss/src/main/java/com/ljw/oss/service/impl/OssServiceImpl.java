package com.ljw.oss.service.impl;

import com.ljw.oss.service.OssService;
import com.ljw.oss.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

/**
 * oss服务impl
 *
 * @author Luo
 */
@Service
public class OssServiceImpl implements OssService {
    /**
     * 上传头像
     *
     * @param file 文件
     * @return {@link String}
     */
    @Override
    public String uploadFileAvatar(MultipartFile file) {

        // 获取阿里云oss需要的参数
        String endpoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            InputStream inputStream = file.getInputStream();
            
            // 文件名
            String originalFilename = file.getOriginalFilename();
            // uuid
            String uuid = UUID.randomUUID().toString();
            uuid = uuid.replaceAll("-", "");
            // 日期
            String dataPath = new DateTime().toString("yyyy/MM/dd");

            String fileName = dataPath + "/" + uuid + originalFilename;

            // 创建PutObject请求。
            ossClient.putObject(bucketName, fileName, inputStream);

            // 手动拼接url
            //https://ljw-edu.oss-cn-guangzhou.aliyuncs.com/2022/12/06/bdee37561c774f90b7a3db321595e27f%E5%B0%8F%E5%B8%85.jpg
            //https://ljw-edu.oss-cn-guangzhou.aliyuncs.com/2022/12/07/93aa449bf4624a61ba8843076783c101file.png
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;

            return url;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
            return null;
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
}
