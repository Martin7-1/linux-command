package com.edu.nju.linux.command.wc;

public class Main {

	public static void main(String[] args) {
		WCCommand command = WCCommand.getInstance();
		command.initAndRun(args);
	}
}
