package com.edu.nju.linux.develop.service;

import com.edu.nju.linux.develop.core.FileInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 参数处理业务逻辑
 *
 * @author zhengyi
 */
public interface ArgumentService {

    /**
     * 处理参数
     *
     * @param curPath      当前执行的目录路径
     * @param existFileMap 已经处理出的文件信息，（文件目录路径，该路径下文件信息的映射）
     * @return 根据参数处理出的文件信息
     * @throws IOException IO 异常
     */
    Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) throws IOException;
}
