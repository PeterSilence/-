package com.juane.peapyoung;
import com.juane.peapyoung.service.AdministrateurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PeapyoungApplicationTests {
    @Autowired
    private AdministrateurService aService;
    @Test
    void getData() {
    }

    public static void main(String[] args) {

    }
}
