# Linux Command Implemented By Java

> Using Java to implement Linux useful command

## `ls` Command

实现了 `ls` 命令的以下参数

1. `-d`: 仅查看当前目录
2. `-a`: 查看隐藏文件 / 目录
3. `-l`: 查看文件详细信息（包括权限、用户、用户组、链接数、上一次访问时间、文件名、文件大小等）
4. `-R`: 递归查看所有目录
5. `-i`: 查看文件 Inode 号和同个目录下的所有文件所占的 block 数量

支持参数的乱序输入，不支持连续输入（比如 `-al` 这种）

## `wc` Command
