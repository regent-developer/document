package xxx.filewatch.service;

import com.stronglink.condition.FilewatchCondition;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.HiddenFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListener;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * FileWatchService4CommonsIO
 * @Description: 文件监控服务，用于监听文件夹和文件的创建、删除、修改等事件。
 */
@Slf4j
@Component
@Conditional(FilewatchCondition.class)
public class FileWatchService4CommonsIO {

    /** 配置参数服务 */
    protected final ConfigService configService;

    /**
     * 构造函数注入ConfigService
     * @param configService 配置服务
     */
    public FileWatchService4CommonsIO(ConfigService configService) {
        this.configService = configService;
    }

    /**
     * 自动启动文件监听器
     */
    @PostConstruct
    public void init() throws Exception {
        // 获取上传路径，并启动文件监听服务
        this.startFileWatchService(this.configService.getUploadPath());
    }

    /**
     * 启动文件监听服务
     * @param watchPath 要监控的文件夹路径
     * @throws Exception 启动过程中的异常
     */
    public void startFileWatchService(String watchPath) throws Exception {
        // 设置文件检查的轮询间隔，单位为毫秒，10秒一次
        long interval = TimeUnit.SECONDS.toMillis(10);

        // 创建文件变动监听器，监听文件和目录的变动事件
        FileAlterationListener fileListener = new FileAlterationListener(){

            // 目录内容变化时触发
            @Override
            public void onDirectoryChange(File file) {
                log.info("监控到文件夹【{}】变更", file.getAbsoluteFile());
            }

            // 目录创建时触发
            @Override
            public void onDirectoryCreate(File file) {
                log.info("监控到文件夹【{}】创建", file.getAbsoluteFile());
            }

            // 目录删除时触发
            @Override
            public void onDirectoryDelete(File file) {
                log.info("监控到文件夹【{}】删除", file.getAbsoluteFile());
            }

            // 文件内容变化时触发
            @Override
            public void onFileChange(File file) {
                log.info("监控到文件【{}】变更", file.getAbsoluteFile());
            }

            // 文件创建时触发
            @Override
            public void onFileCreate(File file) {
                log.info("监控到文件【{}】创建", file.getAbsoluteFile());
            }

            // 文件删除时触发
            @Override
            public void onFileDelete(File file) {
                log.info("监控到文件【{}】删除", file.getAbsoluteFile());
            }

            // 监控器开始检查事件时触发
            @Override
            public void onStart(FileAlterationObserver fileAlterationObserver) {
                log.info("文件系统观察器开始检查事件。");
            }

            // 监控器停止时触发
            @Override
            public void onStop(FileAlterationObserver fileAlterationObserver) {
                log.info("文件系统观察器完成检查事件。");
            }
        };

        // 创建目录过滤器，排除隐藏目录
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter(), HiddenFileFilter.VISIBLE);

        // 创建文件过滤器，排除隐藏文件
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter(), HiddenFileFilter.VISIBLE);

        // 创建一个总的过滤器，将目录过滤器加入其中
        IOFileFilter filter = directories;

        // 遍历所有的后缀，逐一创建对应的文件后缀过滤器，并合并到总过滤器中
        for (String type : configService.getFileTypeFilter()) {
            // 创建后缀过滤器，匹配指定后缀的文件
            IOFileFilter suffixFilter = FileFilterUtils.suffixFileFilter("." + type);
            // 使用 notFileFilter 排除这些后缀的文件
            files = FileFilterUtils.and(files, FileFilterUtils.notFileFilter(suffixFilter));
        }

        // 最终过滤器是目录过滤器和文件过滤器的组合
        filter = FileFilterUtils.or(filter, files);

        // 创建文件观察者，用于家你听指定路径的变化，并使用构建的过滤器
        FileAlterationObserver observer = new FileAlterationObserver(watchPath, filter);
        // 注册文件监听器
        observer.addListener(fileListener);

        // 创建文件监控器，指定检查频率（10秒一次）
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval);
        // 为监控器添加观察者
        monitor.addObserver(observer);

        // 启动文件监控
        monitor.start();
    }
}
