package com.shashank.util;

import com.shashank.framework.LogMe;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FilenameUtils;

public class FileUtils {

  private static final String TESTDATA = File.separator + "src" + File.separator + "test"
      + File.separator + "resources" + File.separator + "testdata" + File.separator;

  private static final LogMe logs = new LogMe(FileUtils.class.getName());

  private static String target;

  public FileUtils() {
    //Utility Class .Defeat Instantiation
  }

  public static String getFileLocation(String fileName) {
    return "src" + File.separator + "main" + File.separator + "resources" + File.separator
        + fileName;
  }

  public static String getFileLocationForTests(String fileName) {
    return getFileLocationForTests("testdata", fileName);
  }

  public static String getFileLocationForTests(String dir, String fileName) {
    return System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
        + File.separator + "resources" + File.separator + dir + File.separator + fileName;
  }

  private static File createTargetFile(String baseName, String fileName, File dir) {
    String extension = FilenameUtils.getExtension(fileName);
    String fileNameToUse;
    synchronized (FileUtils.class) {
      fileNameToUse = baseName + "_" + System.currentTimeMillis() + "." + extension;
    }
    target = dir.getAbsolutePath() + File.separator + fileNameToUse;
    return new File(target);
  }

  private static File makeDirectory() {
    File dir = new File(System.getProperty("user.dir") + File.separator + "target" +
        File.separator + "talos" + File.separator + "upload");
    dir.mkdirs();
    return dir;
  }

  public static File getCopiedFilePath(String filename) {
    String fileName = FileUtils.getFileLocationForTests(filename);
    return FileUtils.copyFile(fileName);
  }

  public static File copyFile(String fileName) {
    File to = composeDestinationFile(fileName);
    try {
      org.apache.commons.io.FileUtils.copyFile(new File(fileName), to);
    } catch (IOException e) {
      throw new FileOperatorFailedException(e);
    }
    return to;
  }

  private static File composeDestinationFile(String fileName) {
    File dir = makeDirectory();
    String baseName = FilenameUtils.getBaseName(fileName);
    return createTargetFile(baseName, fileName, dir);
  }

  public static class FileOperatorFailedException extends RuntimeException {

    public FileOperatorFailedException(Throwable cause) {
      super(cause);
    }
  }
}
