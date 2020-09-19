package ru.kpfu.itis.Tyshenko.downloads;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;

public class URLDownloader extends Downloader {

    private final int BUFFER_SIZE = 1024;
    private byte[] byteBuffer;
    private InputStream inputStream;
    private OutputStream outputStream;

    public URLDownloader(URL url, Path file) {
        try {
            URLConnection connection = disguiseAsBrowser(url);
            inputStream = connection.getInputStream();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        try {
            outputStream = new FileOutputStream(file.toAbsolutePath().toString());
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }
        byteBuffer = new byte[BUFFER_SIZE];
    }

    private URLConnection disguiseAsBrowser(URL url) {
        URLConnection connection;
        try {
            connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            connection.connect();

        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return connection;
    }

    public static URLDownloader createDownloader(URL url, Path file) {
        return new URLDownloader(url, file);
    }

    @Override
    public void download() {
        int byteRead;
        try {
            while (((byteRead = inputStream.read(byteBuffer, 0, BUFFER_SIZE)) != -1)) {
                outputStream.write(byteBuffer, 0, byteRead);
                Thread.sleep(50);
            }
            outputStream.close();
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException();
        }
    }

}
