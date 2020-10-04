package ru.itis.javalab.downloads;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentURLDownloader implements Downloading {

    private final int BUFFER_SIZE = 1024;
    private byte[] byteBuffer;
    Queue<InputStream> inputStreams;
    Queue<OutputStream> outputStreams;

    public ConcurrentURLDownloader() {
        byteBuffer = new byte[BUFFER_SIZE];
        inputStreams = new ConcurrentLinkedQueue<>();
        outputStreams = new ConcurrentLinkedQueue<>();
    }

    public void prepareAndDownload(URL url, Path file) {
        prepareForDownload(url, file);
        download();
    }

    public void prepareForDownload(URL url, Path file) {
        setInputStreamFromURL(url);
        setOutputStreamFromPath(file);
    }

    public void setInputStreamFromURL(URL url) {
        try {
            inputStreams.add(url.openStream());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void setOutputStreamFromPath(Path file) {
        try {
            outputStreams.add(new FileOutputStream(file.toAbsolutePath().toString()));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void download() {
        int byteRead;
        InputStream inputStream = inputStreams.poll();
        OutputStream outputStream = outputStreams.poll();
        try {
            boolean end = false;
            while (!end) {
                synchronized (byteBuffer) {
                    if ((byteRead = inputStream.read(byteBuffer, 0, BUFFER_SIZE)) != -1) {
                        outputStream.write(byteBuffer, 0, byteRead);
                    } else {
                        end = true;
                    }
                }
            }
            inputStream.close();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
            }
    }
}
