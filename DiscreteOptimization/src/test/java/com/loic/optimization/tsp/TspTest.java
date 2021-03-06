package com.loic.optimization.tsp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

class TspTest {

  @TestFactory
  @Disabled
  Stream<DynamicTest> fileInputsTest() {
    //TspResolver resolver = new LocalSearchTspResolver(false);
    TspResolver resolver = new TwoOptTspResolver(10_000);
    File inputsFolder = new File(getClass().getResource("/tsp").getFile());
    return Stream.of(inputsFolder)
      .flatMap(f -> Arrays.stream(f.listFiles()))
      .map(f -> DynamicTest.dynamicTest("test " + f.getName(), () -> testInputFile(f, resolver)));
  }

  @Test
  @Disabled
  void guiTest() throws IOException {
    TspResolver resolver = new TwoOptTspResolver(100_0000);
    resolver.setListener(new TspGui());
    File input = new File(getClass().getResource("/tsp/tsp_100_3").getFile());
    testInputFile(input, resolver);
    System.in.read();
  }

  @Test
  @Disabled
  void swapTest() throws IOException {
    TspResolver resolver = new SwapTspResolver(100_0000);
    resolver.setListener(new TspGui());
    File input = new File(getClass().getResource("/tsp/tsp_2152_1").getFile());
    testInputFile(input, resolver);
    System.in.read();
  }

  private void testInputFile(File inputFile, TspResolver resolver) {
    try (Scanner scanner = new Scanner(inputFile)) {
      int size = scanner.nextInt();
      List<Point> list = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
        Point p = new Point(scanner.nextDouble(), scanner.nextDouble());
        list.add(p);
      }
      List<Integer> result = resolver.resolve(list);
      Assertions.assertEquals(list.size(), result.stream().distinct().count());
      DistanceManager disManager = new DistanceManager(list);
      double distance = 0;
      for (int i = 0; i < list.size(); i++) {
        distance += disManager.distance(result.get(i), result.get((i + 1) % list.size()));
      }
      System.out.println("best distance : " + distance);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}