package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class MainFile {
    public static void main(String[] args) {
    /*    String filePath = ".\\.gitignore";

        File file = new File(filePath);

        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/сom/urise/webapp");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    */
        walkTree(new File("/Users/nastya/Desktop/java/basejava/src/com/urise/webapp"), "");
    }

    public static void walkTree(File dir, String indentation) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            List<File> list = Arrays.asList(Objects.requireNonNull(files));
            list.sort(Comparator.comparing(File::isDirectory));
            for (File file: list) {
                if (file.isHidden()) { continue; }
                System.out.print(indentation);
                System.out.printf(file.isDirectory() ? "+ %s \n" : "  %s \n", file.getName(), "\n");
                if (file.isDirectory()) {
                    walkTree(file, indentation.concat("    "));
                }
            }
        }
    }
}
