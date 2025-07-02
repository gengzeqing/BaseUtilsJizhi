package org.base.file.controller;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author 耿
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
public class FileController {


    /**
     * 主要功能：
     * 文件和目录的创建、删除、移动等操作：
     *
     * Files.createFile(Path path): 创建一个新文件。
     * Files.createDirectories(Path path): 创建指定的目录。如果父目录不存在，则同时创建父目录。
     * Files.delete(Path path): 删除指定的文件。
     * Files.move(Path source, Path target): 移动文件或目录。
     * 读取文件内容：
     *
     * Files.readAllLines(Path path): 读取文件的所有行，返回一个 List<String>。
     * Files.readAllBytes(Path path): 读取文件的所有字节，返回一个字节数组（byte[]）。
     * 写入文件：
     *
     * Files.write(Path path, Iterable<? extends CharSequence> lines): 将字符序列写入文件。
     * Files.write(Path path, byte[] bytes): 将字节数组写入文件。
     * 检查文件的存在性、属性等：
     *
     * Files.exists(Path path): 检查文件或目录是否存在。
     * Files.isDirectory(Path path): 检查给定的路径是否是目录。
     * Files.isRegularFile(Path path): 检查给定的路径是否是常规文件。
     * Files.isReadable(Path path): 检查文件是否可读。
     * Files.isWritable(Path path): 检查文件是否可写。
     * 获取文件或目录的属性：
     *
     * Files.getLastModifiedTime(Path path): 获取文件的最后修改时间。
     * Files.size(Path path): 获取文件的大小。
     * 复制文件：
     *
     * Files.copy(Path source, Path target): 将源文件复制到目标位置。
     * 设置和获取文件权限：
     *
     * Files.setPosixFilePermissions(Path path, Set<PosixFilePermission> perms): 设置文件的 POSIX 权限。
     * Files.getPosixFilePermissions(Path path): 获取文件的 POSIX 权限。
     *
     *
     */


    /**
     * 把读取的文件转换成 字节数组
     * @return
     */
    @SneakyThrows
    @PostMapping("/api/file")
    public void getWeather() {
        // 读取原文件  原始文件路径
        Path sourcePath  = Paths.get("C:\\Users\\耿\\Desktop\\违约金扣除.xlsx");
        // // 目标文件路径
        Path destinationPath  = Paths.get("C:\\Users\\耿\\Desktop\\新建文件夹\\违约金扣除.xlsx");

        // 读取文件内容到字节数组
        byte[] bytes = Files.readAllBytes(sourcePath );

        // 将字节数组写入到新文件
        //
        Files.write(destinationPath, bytes, StandardOpenOption.CREATE_NEW);
    }




}
