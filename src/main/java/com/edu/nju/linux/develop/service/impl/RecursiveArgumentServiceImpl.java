package com.edu.nju.linux.develop.service.impl;

import com.edu.nju.linux.develop.core.FileInfo;
import com.edu.nju.linux.develop.service.ArgumentService;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 递归输出目录下的文件，注意要包括一个 total
 *
 * @author zhengyi
 */
public class RecursiveArgumentServiceImpl implements ArgumentService {

    @Override
    public Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) {
        // 保证在执行该方法时，existFileMap 的 size 为 1
        // 保证该方法在 -a 之前执行
        List<FileInfo> list = existFileMap.get(curPath.getName());
        handleDir(list, existFileMap);

        return existFileMap;
    }

    private void handleDir(List<FileInfo> list, Map<String, List<FileInfo>> existFileMap) {
        if (notContainsDir(list.stream().map(FileInfo::getFile).collect(Collectors.toList()))) {
            return;
        }

        for (FileInfo fileInfo : list) {
            File file = fileInfo.getFile();
            if (!file.isHidden() && file.isDirectory()) {
                List<FileInfo> subFileList = Arrays.stream(Objects.requireNonNull(file.listFiles()))
                        .map(info -> FileInfo.builder()
                                .file(info)
                                .fileName(info.getName())
                                .build())
                        .collect(Collectors.toList());
                existFileMap.put(file.getPath(), subFileList);
                // 递归处理
                handleDir(subFileList, existFileMap);
            }
        }
    }

    private boolean notContainsDir(List<File> fileList) {
        // 对非隐藏的文件夹做过滤
        return fileList.stream().filter(file -> !file.isHidden()).noneMatch(File::isDirectory);
    }
}
