package ru.timestop.storage;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.file.Path;
import java.nio.file.Paths;


public class SimpleTest {
    @Test
    public void test1() {
        Path rootLocation = Paths.get("fer", "her");
        Path destinationFile = rootLocation.resolve(
                        Paths.get("some.txt"))
                .normalize().toAbsolutePath();

        System.out.println(destinationFile);
    }

    @Test
    public void test2(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("user"));
    }
}
