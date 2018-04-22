package main;

import java.io.IOException;

class NonExistentFileException extends IOException {

    NonExistentFileException() {
        super("Non-existent file.");
    }

}
