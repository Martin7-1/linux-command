package com.edu.nju.linux.develop.service.impl;

import com.edu.nju.linux.develop.core.FileInfo;
import com.edu.nju.linux.develop.service.ArgumentService;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * -a 输出隐藏目录业务逻辑
 *
 * @author zhengyi
 */
public class HiddenArgumentServiceImpl implements ArgumentService {

	@Override
	public Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) {
		// 展示 .、..、隐藏目录
		for (Map.Entry<String, List<FileInfo>> entry : existFileMap.entrySet()) {
			File path = new File(entry.getKey());
			List<FileInfo> list = entry.getValue();
			for (File subFile : Objects.requireNonNull(path.listFiles())) {
				if (subFile.isHidden()) {
					list.add(FileInfo.builder()
						.file(subFile)
						.fileName(subFile.getName())
						.build());
				}
			}
			// 添加 . 和 ..
			list.add(FileInfo.builder()
				.fileName(".")
				.file(path)
				.build());
			// TODO: 如何获得父目录
			System.out.println(path.getParent());
			list.add(FileInfo.builder()
				.fileName("..")
				.file(path.getParentFile())
				.build());
		}

		return existFileMap;
	}
}
