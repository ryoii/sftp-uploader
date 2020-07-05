package com.github.ryoii.uploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ANSIHelper {

    static {
        try {
            File temp = File.createTempFile("ansi", ".dll");
            temp.deleteOnExit();
            try (InputStream dll = ANSIHelper.class.getClassLoader().getResourceAsStream("native/ansi.dll");
                 FileOutputStream fos = new FileOutputStream(temp)) {

                assert dll != null;
                UtilsKt.transform(dll, fos);
            }

            System.load(temp.getAbsolutePath());

        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
    }

    native static void enableANSI();

    native static void upLine(int n);
}
