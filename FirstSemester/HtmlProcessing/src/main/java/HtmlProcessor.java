import com.google.auto.service.AutoService;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;

@Slf4j
@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        log.info("Start to writing classes in html");
        roundEnv.getElementsAnnotatedWith(HtmlForm.class).forEach(x -> {
            Path filePath = Paths.get(HtmlProcessor.class.getProtectionDomain()
                    .getCodeSource().getLocation().getPath().substring(1) + x.getSimpleName() + ".html");
            log.info("write " + filePath.getFileName());
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                HtmlForm form =  x.getAnnotation(HtmlForm.class);
                writer.write("<form action='" + form.action() + "' method='" + form.method() + "'>");
                x.getEnclosedElements().stream().map(z -> z.getAnnotation(HtmlInput.class)).filter(Objects::nonNull).forEach(z -> {
                    try {
                        writer.write("<input type ='" + z.type() + "' name = '" + z.name() + "' placeholder = '" + z.placeholder() + "'/>");
                        writer.newLine();
                        writer.write("<br>");
                        writer.newLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
                writer.write("</form>");
                writer.flush();
            } catch (IOException e) {
                log.error("Writing classes in html thrown exception: " + e.getMessage(), e);
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}

