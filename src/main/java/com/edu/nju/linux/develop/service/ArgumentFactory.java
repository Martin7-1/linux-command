package com.edu.nju.linux.develop.service;

import com.edu.nju.linux.develop.enums.ArgumentEnum;
import com.edu.nju.linux.develop.service.impl.*;

/**
 * 参数工厂类
 *
 * @author zhengyi
 */
public class ArgumentFactory {

	private ArgumentFactory() {

	}

	public static ArgumentService produceArgumentService(ArgumentEnum argEnum) {
		switch (argEnum) {
			case CUR_DIR:
				return new CurDirArgumentServiceImpl();
			case INODE:
				return new InodeArgumentServiceImpl();
			case DETAIL:
				return new DetailArgumentServiceImpl();
			case HIDDEN:
				return new HiddenArgumentServiceImpl();
			case RECURSIVE:
				return new RecursiveArgumentServiceImpl();
			default:
				throw new UnsupportedOperationException("不支持的参数: " + argEnum.getName());
		}
	}
}
