package com.edu.nju.linux.command.wc.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件相关数目统计
 *
 * @author zhengyi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileCount {

	private Long wordCount;
	private Long lineCount;
	private Long byteCount;
}
