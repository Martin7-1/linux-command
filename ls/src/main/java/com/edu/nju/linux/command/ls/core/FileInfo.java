package com.edu.nju.linux.command.ls.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

/**
 * 文件信息
 *
 * @author zhengyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileInfo {

	private File file;
	private String inode;
	private String accessRights;
	private Integer linkNum;
	private String username;
	private String userGroup;
	private Long size;
	private String lastAccessTime;
	private String fileName;
}
