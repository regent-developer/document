package com.example;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ShellCommandExecution {

    private static final Logger logger = LoggerFactory.getLogger(ShellCommandExecution.class);

    public static Session getSession(String username, String password, String host, int port) {
        // 创建JSch对象
        JSch jSch = new JSch();
        Session jSchSession = null;
        try {

            // 根据主机账号、ip、端口获取一个Session对象
            jSchSession = jSch.getSession(username, host, port);

            // 存放主机密码
            jSchSession.setPassword(password);

            Properties config = new Properties();

            // 去掉首次连接确认
            config.put("StrictHostKeyChecking", "no");

            jSchSession.setConfig(config);

            // 超时连接时间为3秒
            jSchSession.setTimeout(3000);
            // 进行连接
            jSchSession.connect();

        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return jSchSession;
    }

    /**
     * 下载文件，将 sourceFile 文件下载至 destinationFile
     * @param jSchSession
     * @param sourceFile
     * @param destinationFile
     * @throws Exception
     */
    public static void downloadFiles(Session jSchSession, String sourceFile, String destinationFile) throws Exception {
        // Opening an SFTP Channel
        ChannelSftp chSftp = (ChannelSftp)jSchSession.openChannel("sftp");

        // Example Establish a connection to the SFTP channel
        chSftp.connect();

        // Set encoding format
        chSftp.setFilenameEncoding("UTF-8");

        /**
         * 说明：
         * 1、当前上读取文件信息没有任何反馈，如果没有异常则代表成功
         * 2、如果需要判断是否读取成功的进度，可参考https://blog.csdn.net/coding99/article/details/52416373?locationNum=13&fps=1
         * 3、将src文件下载到dst路径中
         */
        chSftp.get(sourceFile, destinationFile);

        logger.info("download file " + sourceFile + " success，location is：" + destinationFile);
    }

    /**
     * 将 sourceFile 上传至 destinationFile
     * @param jSchSession
     * @param sourceFile
     * @param destinationFile
     */
    public static void uploadFiles(Session jSchSession, String sourceFile, String destinationFile) throws Exception{
        // Opening an SFTP Channel
        ChannelSftp chSftp = (ChannelSftp)jSchSession.openChannel("sftp");

        // Example Establish a connection to the SFTP channel
        chSftp.connect();

        // Set encoding format
        chSftp.setFilenameEncoding("UTF-8");

        /**
         * 说明：
         * 1、当前上读取文件信息没有任何反馈，如果没有异常则代表成功
         * 2、如果需要判断是否读取成功的进度，可参考https://blog.csdn.net/coding99/article/details/52416373?locationNum=13&fps=1
         * 3、将src文件下载到dst路径中
         */
        chSftp.put(sourceFile, destinationFile);

        logger.info("upload file " + sourceFile + " success，location is：" + destinationFile);
    }

    /**
     * 执行命令
     * @param command
     * @param jSchSession
     * @return
     * @throws Exception
     */
    public static StringBuffer execCommand(String command, Session jSchSession) throws Exception{
        Channel exec = null;
        StringBuffer result = new StringBuffer();
        try {
            exec = jSchSession.openChannel("exec");

            ((ChannelExec) exec).setCommand(command);

            exec.setInputStream(null);
            // 错误信息输出流，用于输出错误的信息，当exitstatus<0的时候
            ((ChannelExec) exec).setErrStream(System.err);

            // 执行命令，等待结果
            exec.connect();

            // 获取命令执行结果
            InputStream in = exec.getInputStream();

            // 推出状态码
            int exitStatus = 0;
            /**
             * 通过channel获取信息的方式，采用官方Demo代码
             */
            byte[] tmp = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) {
                        break;
                    }
                    result.append(new String(tmp, 0, i));
                }
                // 从channel获取全部信息之后，channel会自动关闭
                if (exec.isClosed()) {
                    if (in.available() > 0) {
                        continue;
                    }
                    exitStatus = exec.getExitStatus();
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (Exception ee) {
                }
            }
            logger.info("命令：【" + command + "】的执行结果为：" + result);

            // 如果状态码不是0，则表示执行失败
            if (exitStatus != 0) {
                logger.error("状态码为：" + exitStatus + "，命令执行失败：" + result);
                throw new Exception("命令：【" + command + "】执行失败，执行结果为：" + result);
            }
        } finally {
            // 关闭 jschChannel 流
            if (exec != null && exec.isConnected()) {
                exec.disconnect();
            }
        }
        return result;
    }
}

