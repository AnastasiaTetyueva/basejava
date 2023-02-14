package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.serializer.ResumeSerializer;
import com.urise.webapp.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;

    private final Serializer serializer = new ResumeSerializer();

    private void doWrite(BufferedOutputStream bufferedOutputStream, Resume resume) throws IOException {
        serializer.doWrite(bufferedOutputStream, resume);
    }

    private Resume doRead(BufferedInputStream bufferedInputStream) throws IOException {
        return serializer.doRead(bufferedInputStream);
    }

    protected PathStorage(Path dir) {
        directory = Paths.get(String.valueOf(dir));
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Path file = Files.createFile(path);
            doWrite(new BufferedOutputStream(Files.newOutputStream(file)), resume);
        } catch (IOException e) {
            throw new StorageException("Couldn't create file ", String.valueOf(path.getFileName()), e);
        }
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            doWrite(new BufferedOutputStream(Files.newOutputStream(path)), resume);
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error", String.valueOf(path), e);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error", null);
        }
        return list.length;
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            Stream<Path> files = Files.list(directory);
            return files.map(this::doGet)
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw new StorageException("Parse file error", null, e);
        }

    }

}
