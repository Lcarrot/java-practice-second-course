package ru.kpfu.itis.Tyshenko.main;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.net.URL;
import java.util.List;

@Parameters(separators = "=")
public class Args {

    @Parameter(names = "--mode", description = "Use one or more threads")
    private String mode = "one-thread";

    @Parameter(names = "--count", description = "How many threads will be start")
    private int count = 1;

    @Parameter(names = "--files", description = "Link for download")
    private String urls;

    @Parameter(names = "--folder", description = "Path on PC")
    private String path;

    public String getMode() {
        return mode;
    }

    public int getCount() {
        return count;
    }

    public String getUrls() {
        return urls;
    }

    public String getPath() {
        return path;
    }

}
