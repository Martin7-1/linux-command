package com.edu.nju.linux.develop;

import com.edu.nju.linux.develop.core.FileInfo;
import com.edu.nju.linux.develop.enums.ArgumentEnum;
import com.edu.nju.linux.develop.service.ArgumentFactory;
import com.edu.nju.linux.develop.service.ArgumentService;
import com.edu.nju.linux.develop.utils.OutputUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LsCommand {

    private LsCommand() {
        argList = new ArrayList<>();
    }

    public static LsCommand getInstance() {
        return Holder.INSTANCE;
    }

    static class Holder {

        static final LsCommand INSTANCE = new LsCommand();
    }

    private final List<ArgumentEnum> argList;

    public void initAndRun(String[] args) {
        if (args.length == 0 || !"ls".equals(args[0])) {
            System.out.println("无法识别的指令，当前支持的指令为: ls");
        }
        boolean[] argRecognized = new boolean[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            String argName = args[i];
            ArgumentEnum argEnum = ArgumentEnum.getArgByName(argName);
            if (argEnum != null) {
                argRecognized[i - 1] = true;
                argList.add(argEnum);
            }
        }

        for (boolean recognized : argRecognized) {
            if (!recognized) {
                System.out.println("无法识别参数");
                return;
            }
        }

        // 首先需要先处理 -a -d -R，获取需要分析的所有文件
        // -d 会屏蔽 -R -a，即如果有 -d 的话 -R -a 是不起作用的
        File curPath = new File("." + File.separator);
        Map<String, List<FileInfo>> fileMap;
        // 首先处理和文件数量有关的参数，即 -d -a -R
        try {
            if (containsArg(ArgumentEnum.CUR_DIR)) {
                fileMap = handleCurDirArgument(curPath);
            } else if (containsArg(ArgumentEnum.HIDDEN) || containsArg(ArgumentEnum.RECURSIVE)) {
                fileMap = initCurFileMap(curPath);
                fileMap = handleHiddenAndRecursiveArgument(curPath, fileMap);
            } else {
                fileMap = initCurFileMap(curPath);
            }

            // 处理和文件展示信息有关的参数，即 -l -i
            fileMap = handleInfo(curPath, fileMap);
        } catch (IOException e) {
            System.out.println("程序错误, msg: " + e.getLocalizedMessage());
            return;
        }

        output(fileMap);
    }

    private boolean containsArg(ArgumentEnum argEnum) {
        return argList.stream().anyMatch(arg -> arg.equals(argEnum));
    }

    private Map<String, List<FileInfo>> handleCurDirArgument(File curPath) throws IOException {
        ArgumentService argumentService = ArgumentFactory.produceArgumentService(ArgumentEnum.CUR_DIR);
        return argumentService.handleArgument(curPath, null);
    }

    /**
     * 如下格式输出
     *
     * <ul>
     *     <li>如果是 -l 模式下每个文件为一行</li>
     *     <li>如果是 -R 需要输出每个路径名</li>
     *     <li>输出 inode</li>
     * </ul>
     *
     * @param fileMap 要输出的 (文件所属目录, 文件列表) 映射
     */
    private void output(Map<String, List<FileInfo>> fileMap) {
        if (containsArg(ArgumentEnum.DETAIL)) {
            // 一个文件一行输出
            // 如果有 -R 需要输出一下当前的目录路径
            if (containsArg(ArgumentEnum.RECURSIVE)) {
                for (Map.Entry<String, List<FileInfo>> entry : fileMap.entrySet()) {
                    System.out.println(entry.getKey() + ":");
                    for (FileInfo info : entry.getValue()) {
                        System.out.println(OutputUtils.constructOutput(info));
                    }
                    System.out.println();
                }
            } else {
                for (Map.Entry<String, List<FileInfo>> entry : fileMap.entrySet()) {
                    for (FileInfo info : entry.getValue()) {
                        System.out.println(OutputUtils.constructOutput(info));
                    }
                }
            }
        } else {
            // 按 \t 输出
            // 如果有 -R 需要输出一下当前的目录路径
            if (containsArg(ArgumentEnum.RECURSIVE)) {
                for (Map.Entry<String, List<FileInfo>> entry : fileMap.entrySet()) {
                    System.out.println(entry.getKey() + ":");
                    for (FileInfo info : entry.getValue()) {
                        System.out.print(OutputUtils.constructOutput(info) + "\t");
                    }
                    System.out.println();
                }
            } else {
                for (Map.Entry<String, List<FileInfo>> entry : fileMap.entrySet()) {
                    for (FileInfo info : entry.getValue()) {
                        System.out.print(OutputUtils.constructOutput(info) + "\t");
                    }
                }
            }
        }
    }

    private Map<String, List<FileInfo>> handleHiddenAndRecursiveArgument(File curPath, Map<String, List<FileInfo>> fileMap) throws IOException {
        // 先处理 -R，再处理 -a
        if (containsArg(ArgumentEnum.RECURSIVE)) {
            ArgumentService argumentService = ArgumentFactory.produceArgumentService(ArgumentEnum.RECURSIVE);
            fileMap = argumentService.handleArgument(curPath, fileMap);
        }
        if (containsArg(ArgumentEnum.HIDDEN)) {
            ArgumentService argumentService = ArgumentFactory.produceArgumentService(ArgumentEnum.HIDDEN);
            fileMap = argumentService.handleArgument(curPath, fileMap);
        }

        return fileMap;
    }

    private Map<String, List<FileInfo>> initCurFileMap(File curPath) {
        Map<String, List<FileInfo>> curMap = new HashMap<>();
        List<FileInfo> list = new ArrayList<>();
        for (File subFile : Objects.requireNonNull(curPath.listFiles())) {
            // 默认不展示隐藏目录
            if (!subFile.isHidden()) {
                list.add(FileInfo.builder()
                        .fileName(subFile.getName())
                        .file(subFile)
                        .build());
            }
        }

        curMap.put(curPath.getName(), list);
        return curMap;
    }

    private Map<String, List<FileInfo>> handleInfo(File curPath, Map<String, List<FileInfo>> fileMap) throws IOException {
        for (ArgumentEnum arg : argList) {
            if (ArgumentEnum.DETAIL.equals(arg) || ArgumentEnum.INODE.equals(arg)) {
                ArgumentService argumentService = ArgumentFactory.produceArgumentService(arg);
                fileMap = argumentService.handleArgument(curPath, fileMap);
            }
        }

        return fileMap;
    }
}
