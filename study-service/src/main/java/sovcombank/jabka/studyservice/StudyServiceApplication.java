package sovcombank.jabka.studyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
*
* TODO: Важно! Сделать studymaterialsid вместо studymaterials в subjectopenapi И вручную при отдаче клиенту преобразовывать в id-шники
*  (БЕЗ МАПЕРА, так как там рекурсивная зависимость, которую на первый взгяд непонятно как фиксить, а у нас зеро времени)
*
* */
@SpringBootApplication(scanBasePackages = "sovcombank.jabka")
public class StudyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyServiceApplication.class, args);
    }

}
