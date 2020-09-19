package ru.kpfu.itis.Tyshenko.files.names;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;

public class FileNamesMaker {

    public static String makeNameFileForFileFromURL(URL url) {
        String fileName = getFileNameFromURL(url);
        if (checkHaveTypeFromName(fileName)) return fileName;
        URLConnection connection;
        try {
            connection = url.openConnection();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
        return getNameFileFromURLConnection(connection, fileName);
    }

    public static String makeNameFileForFileFromURLConnection(URLConnection urlConnection) {
        String fileName = getFileNameFromURL(urlConnection.getURL());
        if (checkHaveTypeFromName(fileName)) return fileName;
        return getNameFileFromURLConnection(urlConnection, fileName);
    }

    private static String getFileNameFromURL(URL url) {
        return Paths.get(url.getFile()).getFileName().toString();
    }

    private static boolean checkHaveTypeFromName(String fileName) {
        String[] partOfFileName = fileName.split("[/.]");
        return partOfFileName.length > 1;
    }

    private static String getNameFileFromURLConnection(URLConnection urlConnection, String fileName) {
        String mimeType = urlConnection.getContentType();
        String type = getType(mimeType);
        return makeNameWithMimeType(fileName, type);
    }

    private static String getType(String mimeType) {
        String[] splitMimeType = mimeType.split("[/;]");
        return splitMimeType[1];
    }

    private static String makeNameWithMimeType(String fileName, String type) {
        return fileName + "." + type;
    }

}
