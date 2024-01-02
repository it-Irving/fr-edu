package com.ljw.oss.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 *
 * @author Luo
 */
@Service
public interface OssService {
    /**
     * 上传头像
     *
     * @param file 文件
     * @return {@link String}
     */
    String uploadFileAvatar(MultipartFile file);
}
