package com.exchange.rate.util;

import com.exchange.rate.model.exception.FileNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FileManager {

    public byte[] readFileAsByteArray(String path) {
        try {
            InputStream in = new ClassPathResource(path).getInputStream();
            return IOUtils.toByteArray(in);
        } catch (IOException e) {
            throw new FileNotFoundException("Access error to file", e);
        }
    }
}
