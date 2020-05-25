package com.andrewhun.object.util;

import com.andrewhun.object.models.DataType;
import com.andrewhun.object.models.JavaObject;
import com.andrewhun.object.services.InputVerificationService;
import nl.flotsam.xeger.Xeger;

import java.io.File;
import java.util.Random;

public class TestUtil {

    public static final String TEST_NAME = "Test";

    public static void deleteObjectData(JavaObject object) throws Exception {

        assert getFile(object, NamedConstants.METADATA_FILE_TYPE).delete();
        assert getFile(object, JavaObject.JAVA_EXTENSION).delete();
        DataType.removeDataType(object.getName());
    }

    public static String createNameWithInvalidFirstCharacter() {

        Xeger xeger = new Xeger("[^a-zA-Z_$\u0005]");
        String name = TEST_NAME;
        while (name.equals(TEST_NAME)) {

            name = xeger.generate() + TEST_NAME;
        }
        return name;
    }

    public static String generateNameWithSpecialCharacter() {

        String specialCharacter = "";
        Xeger xeger = new Xeger("[^a-zA-Z_$0-9\u0005]");
        while (specialCharacter.equals("")) {

            specialCharacter = xeger.generate();
        }
        return TEST_NAME + specialCharacter;
    }

    public static String getRandomReservedWord() {

        Random random = new Random();
        int index = random.nextInt(InputVerificationService.RESERVED_WORDS.size());
        return InputVerificationService.RESERVED_WORDS.get(index);
    }

    public static String generateValidName() {

        return new Xeger("[a-zA-Z_$][a-zA-Z_$0-9]*$").generate();
    }

    public static File getFile(JavaObject object, String extension) {

        return new File(JavaObject.OBJECT_DIRECTORY + object.getName() + extension);
    }

}
