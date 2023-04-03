package com.edu.nju.linux.command.wc;

import com.edu.nju.linux.command.wc.core.FileCount;
import com.edu.nju.linux.command.wc.utils.CountUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * wc 命令
 *
 * @author zhengyi
 */
public class WCCommand {

	private WCCommand() {
		fileList = new ArrayList<>();
	}

	public static WCCommand getInstance() {
		return Holder.INSTANCE;
	}

	static class Holder {

		static final WCCommand INSTANCE = new WCCommand();
	}

	private final List<String> fileList;

	public void initAndRun(String[] args) {
		// 按照 line word byte 的顺序打印文件
		if (args.length == 0 || !"wc".equals(args[0])) {
			System.out.println("无法识别的指令，当前支持的指令为: wc");
		}

		fileList.addAll(Arrays.asList(args).subList(1, args.length));
		try {
			handleWordCount();
		} catch (IOException e) {
			System.out.println("运行程序异常, msg: " + e.getLocalizedMessage());
		}
	}

	private void handleWordCount() throws IOException {
		List<FileCount> countList = new ArrayList<>();
		for (String path : fileList) {
			FileCount fileCount = FileCount.builder()
				.wordCount(CountUtils.getWordCount(path))
				.lineCount(CountUtils.getLineCount(path))
				.byteCount(CountUtils.getByteCount(path))
				.build();
			countList.add(fileCount);
			System.out.println(fileCount.getLineCount() + "\t" + fileCount.getWordCount() + "\t" + fileCount.getByteCount() + "\t" + path);
		}

		// 如果大于 1 个文件则需要计算总量
		if (fileList.size() > 1) {
			long totalLineCount = countList.stream().map(FileCount::getLineCount).mapToLong(Long::longValue).sum();
			long wordCount = countList.stream().map(FileCount::getWordCount).mapToLong(Long::longValue).sum();
			long byteCount = countList.stream().map(FileCount::getByteCount).mapToLong(Long::longValue).sum();
			System.out.println(totalLineCount + "\t" + wordCount + "\t" + byteCount + "\t" + "total");
		}
	}
}
