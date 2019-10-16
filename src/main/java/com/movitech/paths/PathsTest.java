package com.movitech.paths;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathsTest {

    public static void main(String[] args) throws URISyntaxException {
        Path path = Paths.get("C:/", "Xmp");

        path = Paths.get("F:/springcloud-pros/hs_err_pid1648.log");
        System.out.println(path.getFileName());

        URI u = URI.create("file:///F:/springcloud-pros/test2019.log");
        path = Paths.get(u);
        System.out.println(path.getFileName());

        path = FileSystems.getDefault().getPath("C:/", "access.log");

        path = FileSystems.getDefault().getPath("access.log");

        /**
         * 循环1万次执行这两个方法，结果如下：
         *
         * 1. Path p = Paths.get(file.toURI());  //耗时: 300ms
         * 2. Path p = file.toPath(); //耗时: 15ms
         */
    }
}