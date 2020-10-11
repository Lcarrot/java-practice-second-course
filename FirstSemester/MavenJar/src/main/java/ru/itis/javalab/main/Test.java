package ru.itis.javalab.main;

import com.beust.jcommander.JCommander;
import ru.itis.javalab.downloads.ConcurrentURLDownloader;
import ru.itis.javalab.files.names.FileNamesMaker;
import ru.itis.javalab.threads.utils.ThreadPool;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;



public class Test {
    public static void main(String... argv) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        String[] urls = args.getUrls().split(";");
        ThreadPool threadPool = new ThreadPool(args.getCount());
        ConcurrentURLDownloader downloader = new ConcurrentURLDownloader();
        for (int i = 0; i < urls.length; i++){
            int finalI = i;
            threadPool.submit(() -> {
                URL url;
                try {
                    url = new URL(urls[finalI].trim());
                } catch (MalformedURLException e) {
                    throw new IllegalArgumentException();
                }
                String fileName = FileNamesMaker.makeNameFileForFileFromURL(url);
                Path path = Paths.get(args.getPath(), fileName);
                System.out.println("Starting download " + fileName);
                downloader.prepareAndDownload(url, path);
                System.out.println("End download " + fileName);
                    });
        }
    }
}
