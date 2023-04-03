package com.edu.nju.linux.command.wc.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件相关数目统计工具类
 *
 * @author zhengyi
 */
public class CountUtils {

	private CountUtils() {

	}

	private static final Pattern CHINESE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]");

	public static Long getLineCount(String path) throws IOException {
		long lineCount = 0;
		BufferedReader br = new BufferedReader(new FileReader(path));
		while (br.readLine() != null) {
			lineCount++;
		}
		br.close();

		return lineCount;
	}

	public static Long getWordCount(String path) throws IOException {
		// 单词的定义是通过 whitespace 分隔的单词数量
		String str;
		long engWordCount = 0;
		long zhWordCount = 0;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader br = new BufferedReader(new FileReader(path));
		while ((str = br.readLine()) != null) {
			// 统计中文字数
			Matcher ma = CHINESE_PATTERN.matcher(str);
			while (ma.find()) {
				zhWordCount++;
			}
			// 统计英文单词数
			stringBuilder.append(str);
			String word = stringBuilder.toString().replaceAll("[^a-zA-Z0-9]+", " ");
			String[] words = word.split("\\s+");
			engWordCount = words.length;
		}
		br.close();

		return zhWordCount + engWordCount;
	}

	public static Long getByteCount(String path) {
		File file = new File(path);
		return file.length();
	}
}
