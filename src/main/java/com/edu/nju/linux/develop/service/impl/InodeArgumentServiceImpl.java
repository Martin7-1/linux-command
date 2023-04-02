package com.edu.nju.linux.develop.service.impl;

import com.edu.nju.linux.develop.core.FileInfo;
import com.edu.nju.linux.develop.service.ArgumentService;
import com.edu.nju.linux.develop.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 输出 Inode 号
 *
 * @author zhengyi
 */
public class InodeArgumentServiceImpl implements ArgumentService {

	@Override
	public Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) throws IOException {
		for (Map.Entry<String, List<FileInfo>> entry : existFileMap.entrySet()) {
			List<FileInfo> list = entry.getValue();
			for (FileInfo fileInfo : list) {
				fileInfo.setInode(FileUtils.getInode(fileInfo.getFile().getPath()));
			}
		}

		return existFileMap;
	}
}
