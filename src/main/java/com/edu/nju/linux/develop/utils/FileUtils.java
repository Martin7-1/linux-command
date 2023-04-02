package com.edu.nju.linux.develop.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class FileUtils {

    private FileUtils() {

    }

    public static String getAccessRights(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);

        return getFileType(attributes) +
                getAccessRights(attributes.permissions());
    }

    public static Integer getLinkNum(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        return (Integer) Files.getAttribute(path, "unix:nlink");
    }

    public static String getUsername(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);
        return attributes.owner().getName();
    }

    public static String getUserGroup(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);
        return attributes.group().getName();
    }

    public static Long getSize(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);
        return attributes.size();
    }

    public static String getLastAccessTime(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);
        FileTime lastAccessTime = attributes.lastAccessTime();

        return lastAccessTime.toString();
    }

    public static String getInode(String pathName) throws IOException {
        Path path = Paths.get(pathName);
        PosixFileAttributes attributes = Files.readAttributes(path, PosixFileAttributes.class, NOFOLLOW_LINKS);

        // 这里获得的是 UnixFileKey 对象，toString 格式为 (dev=1000003,ino=1821216)。
        String objectStr = attributes.fileKey().toString();
        int startIndex = objectStr.indexOf("ino=") + "ino=".length();
        return objectStr.substring(startIndex, objectStr.length() - 1);
    }

    private static String getAccessRights(Set<PosixFilePermission> permissions) {
        StringBuilder rights = new StringBuilder();
        // user
        if (hasRight(permissions, PosixFilePermission.OWNER_READ)) {
            rights.append('r');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.OWNER_WRITE)) {
            rights.append('w');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.OWNER_EXECUTE)) {
            rights.append('x');
        } else {
            rights.append('-');
        }

        // group
        if (hasRight(permissions, PosixFilePermission.GROUP_READ)) {
            rights.append('r');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.GROUP_WRITE)) {
            rights.append('w');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.GROUP_EXECUTE)) {
            rights.append('x');
        } else {
            rights.append('-');
        }

        // others
        if (hasRight(permissions, PosixFilePermission.OTHERS_READ)) {
            rights.append('r');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.OTHERS_WRITE)) {
            rights.append('w');
        } else {
            rights.append('-');
        }
        if (hasRight(permissions, PosixFilePermission.OTHERS_EXECUTE)) {
            rights.append('x');
        } else {
            rights.append('-');
        }

        return rights.toString();
    }

    private static String getFileType(PosixFileAttributes attributes) {
        if (attributes.isDirectory()) {
            return "d";
        } else if (attributes.isRegularFile()) {
            return "-";
        } else if (attributes.isSymbolicLink()) {
            return "l";
        } else {
            return "o";
        }
    }

    private static boolean hasRight(Set<PosixFilePermission> permissions, PosixFilePermission permission) {
        return permissions.contains(permission);
    }
}
