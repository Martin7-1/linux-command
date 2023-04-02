package com.edu.nju.linux.develop;

/**
 * 主类
 *
 * @author zhengyi
 */
public class Main {

	public static void main(String[] args) {
		LsCommand lsCommand = LsCommand.getInstance();
		lsCommand.initAndRun(args);
	}
}
