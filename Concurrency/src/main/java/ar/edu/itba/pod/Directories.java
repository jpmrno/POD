package ar.edu.itba.pod;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directories {

  private static final ExecutorService pool = Executors.newFixedThreadPool(5);

  public static void main(String[] args) {
    System.out.println("Total lines: " + filesLines("/Users/juanm/Coding/GitHub/POD/P2E6/"));
    pool.shutdown();
  }

  public static long filesLines(final String directoryPath) {
    final Path directory = Paths.get(directoryPath);
    final Set<Path> files;
    try (Stream<Path> paths = Files.walk(directory, 1)) {
      files = paths
          .filter(Files::isRegularFile)
          .collect(Collectors.toSet());
    } catch (final IOException exception) {
      exception.printStackTrace();
      return -1L;
    }

    Set<Future<Long>> filesLines = new HashSet<>(files.size());
    for (final Path file : files) {
      filesLines.add(pool.submit(() -> Files.lines(file, StandardCharsets.ISO_8859_1).count()));
    }

    while (!filesLines.stream().allMatch(Future::isDone)) {
    }

    long ret = 0L;
    for (final Future<Long> fileLines : filesLines) {
      try {
        ret += fileLines.get();
      } catch (final InterruptedException | ExecutionException exception) {
        exception.printStackTrace();
      }
    }

    return ret;
  }
}
