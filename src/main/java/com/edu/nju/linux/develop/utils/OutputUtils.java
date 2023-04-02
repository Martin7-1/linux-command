package com.edu.nju.linux.develop.utils;

import com.edu.nju.linux.develop.core.FileInfo;

public class OutputUtils {

	private OutputUtils() {

	}

	public static String constructOutput(FileInfo detailFileInfo) {
		StringBuilder res = new StringBuilder();
		if (detailFileInfo.getInode() != null) {
			res.append(detailFileInfo.getInode()).append("\t");
		}
		if (detailFileInfo.getAccessRights() != null) {
			res.append(detailFileInfo.getAccessRights()).append("\t");
		}
		if (detailFileInfo.getLinkNum() != null) {
			res.append(detailFileInfo.getLinkNum()).append("\t");
		}
		if (detailFileInfo.getUsername() != null) {
			res.append(detailFileInfo.getUsername()).append("\t");
		}
		if (detailFileInfo.getUserGroup() != null) {
			res.append(detailFileInfo.getUserGroup()).append("\t");
		}
		if (detailFileInfo.getSize() != null) {
			res.append(detailFileInfo.getSize()).append("\t");
		}
		if (detailFileInfo.getLastAccessTime() != null) {
			res.append(detailFileInfo.getLastAccessTime()).append("\t");
		}
		if (detailFileInfo.getFileName() != null) {
			res.append(detailFileInfo.getFileName()).append("\t");
		}

		// 删除最后一个 \t
		return res.deleteCharAt(res.length() - 1).toString();
	}
}
