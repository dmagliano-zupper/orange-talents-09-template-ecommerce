package br.com.zup.dmagliano.ecommerce.products.image;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UploaderFake {

    public static Set<String> send(List<MultipartFile> images) {
        return images.stream().map(image -> "http://hostserver.io/" + image.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
