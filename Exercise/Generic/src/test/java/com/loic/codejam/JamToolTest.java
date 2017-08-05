package com.loic.codejam;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.google.common.io.Files;
import org.testng.annotations.Test;

public class JamToolTest {

    @Test
    public void testInput() throws Exception {
        String path = "src/test/resources/codejam";

        JamTool.main(new String[]{"com.loic.codejam.JamMainTest$IntResolver", path});

        Arrays.stream(new File(path).listFiles())
            .filter(f -> f.getName().endsWith("out"))
            .peek(f -> {
                List<String> lines = null;
                try {
                    lines = Files.readLines(f, Charset.forName("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < lines.size(); i++) {
                    assertEquals(lines.get(i), "Case #" + (i + 1) + ": 100");
                }

            }).forEach(f -> assertTrue(f.delete()));
    }

    public static final class IntResolver implements Resolver<Integer> {
        @Override
        public Integer solve(Scanner in) {
            return 100;
        }
    }
}
