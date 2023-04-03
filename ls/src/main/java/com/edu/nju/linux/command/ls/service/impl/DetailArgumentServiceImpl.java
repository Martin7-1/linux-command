package com.edu.nju.linux.command.ls.service.impl;

import com.edu.nju.linux.command.ls.core.FileInfo;
import com.edu.nju.linux.command.ls.service.ArgumentService;
import com.edu.nju.linux.command.ls.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * -l 输出文件详细信息，包括格式如下:
 *
 * <p>访问权限 链接数 用户名 用户组名 size 上次访问时间 文件名</p>
 *
 * @author zhengyi
 */
public class DetailArgumentServiceImpl implements ArgumentService {

	@Override
	public Map<String, List<FileInfo>> handleArgument(File curPath, Map<String, List<FileInfo>> existFileMap) throws IOException {
		for (Map.Entry<String, List<FileInfo>> entry : existFileMap.entrySet()) {
			for (FileInfo fileInfo : entry.getValue()) {
				String pathName = fileInfo.getFile().getPath();
				fileInfo.setAccessRights(FileUtils.getAccessRights(pathName));
				fileInfo.setLinkNum(FileUtils.getLinkNum(pathName));
				fileInfo.setUsername(FileUtils.getUsername(pathName));
				fileInfo.setUserGroup(FileUtils.getUserGroup(pathName));
				fileInfo.setSize(FileUtils.getSize(pathName));
				fileInfo.setLastAccessTime(FileUtils.getLastAccessTime(pathName));
			}
		}

		return existFileMap;
	}
}
