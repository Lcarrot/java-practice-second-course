package ru.kpfu.itis.Tyshenko.main;

import com.beust.jcommander.JCommander;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import ru.kpfu.itis.Tyshenko.downloads.Downloader;
import ru.kpfu.itis.Tyshenko.downloads.URLDownloader;
import ru.kpfu.itis.Tyshenko.files.names.FileNamesMaker;
import ru.kpfu.itis.Tyshenko.threads.utils.ThreadPool;

public class Test {
    public static void main(String... argv) {
        Args args = new Args();
        JCommander.newBuilder()
                .addObject(args)
                .build()
                .parse(argv);
        String[] urls = args.getUrls().split(";");
        ThreadPool threadPool = new ThreadPool(args.getCount());
        for (String urlString: urls) {
            threadPool.submit(() -> {
                URL url;
                try {
                    url = new URL(urlString.trim());
                } catch (MalformedURLException e) {
                    throw new IllegalArgumentException();
                }
                String fileName = FileNamesMaker.makeNameFileForFileFromURL(url);
                Path path = Paths.get(args.getPath(), fileName);
                Downloader downloader = new URLDownloader(url, path);
                downloader.download();
                    });
        }
    }
}
