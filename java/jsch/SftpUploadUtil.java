package com.example.demo.util;

import com.jcraft.jsch.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SftpUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(SftpUploadUtil.class);

    private Session session;
    private ChannelSftp channel;

    /**
     * 连接到SFTP服务器
     * @param host SFTP服务器主机名
     * @param port SFTP服务器端口
     * @param username 用户名
     * @param password 密码
     * @throws JSchException 如果连接失败
     */
    public void connect(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();
        session = jsch.getSession(username, host, port);
        session.setPassword(password);

        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);

        session.connect();
        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }

    /**
     * 上传文件到SFTP服务器
     * @param localFilePath 本地文件路径
     * @param remoteFilePath 远程文件路径
     * @throws Exception 如果上传失败
     */
    public void uploadFile(String localFilePath, String remoteFilePath) throws Exception {
        File localFile = new File(localFilePath);
        String remoteDir = remoteFilePath.substring(0, remoteFilePath.lastIndexOf('/'));
        try {
            channel.cd(remoteDir);
        } catch (SftpException e) {
            logger.info("Creating remote directory: {}", remoteDir);
            createRemoteDirectory(remoteDir);
        }
        try (FileInputStream fis = new FileInputStream(localFile)) {
            channel.put(fis, remoteFilePath);
        }
    }

    /**
     * 从SFTP服务器下载文件
     * @param remoteFilePath 远程文件路径
     * @param localFilePath 本地文件路径
     * @throws Exception 如果下载失败
     */
    public void downloadFile(String remoteFilePath, String localFilePath) throws Exception {
        channel.get(remoteFilePath, localFilePath);
    }

    /**
     * 删除SFTP服务器上的文件
     * @param remoteFilePath 远程文件路径
     * @throws SftpException 如果删除失败
     */
    public void deleteFile(String remoteFilePath) throws SftpException {
        channel.rm(remoteFilePath);
    }

    /**
     * 在SFTP服务器上创建远程目录
     * @param path 远程目录路径
     * @throws SftpException 如果创建失败
     */
    private void createRemoteDirectory(String path) throws SftpException {
        String[] folders = path.split("/");
        StringBuilder currentPath = new StringBuilder();
        for (String folder : folders) {
            if (!folder.isEmpty()) {
                currentPath.append("/").append(folder);
                try {
                    channel.cd(currentPath.toString());
                } catch (SftpException e) {
                    channel.mkdir(currentPath.toString());
                    logger.info("Created directory: {}", currentPath.toString());
                }
            }
        }
    }

    /**
     * 断开与SFTP服务器的连接
     */
    public void disconnect() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }
}
