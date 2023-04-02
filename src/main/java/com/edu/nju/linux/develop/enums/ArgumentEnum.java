package com.edu.nju.linux.develop.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;

/**
 * 参数枚举类
 *
 * @author zhengyi
 */
@NoArgsConstructor
@AllArgsConstructor
public enum ArgumentEnum {

    /**
     * 展示当前目录
     */
    CUR_DIR("-d"),
    /**
     * 递归展示所有目录
     */
    RECURSIVE("-R"),
    /**
     * 展示隐藏目录
     */
    HIDDEN("-a"),
    /**
     * 展示 inode 号
     */
    INODE("-i"),
    /**
     * 展示详细信息
     */
    DETAIL("-l");

    @Getter
    @Setter
    private String name;

    public static ArgumentEnum getArgByName(String name) {
        return Arrays.stream(ArgumentEnum.values())
                .filter(arg -> arg.getName().equals(name))
                .findAny()
                .orElse(null);
    }
}
