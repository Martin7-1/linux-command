package com.edu.nju.linux.develop.service.impl;

import com.edu.nju.linux.develop.core.FileInfo;
import com.edu.nju.linux.develop.service.ArgumentService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * -d 参数，仅输出目录而不输出文件（即 .）
 *
 * @author zhengyi
 */
public class CurDirArgumentServiceImpl implements ArgumentService {

    @Override
    public Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) {
        Map<String, List<FileInfo>> ans = new HashMap<>(1);
        List<FileInfo> fileList = new ArrayList<>(1);
        String curDirName = curPath.getName();
        fileList.add(FileInfo.builder()
                .fileName(curDirName)
                .file(curPath)
                .build());
        ans.put(curDirName, fileList);
        return ans;
    }
}
