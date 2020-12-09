import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;


@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"HtmlForm", "HtmlInput"})
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElementsForm = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element annotatedClass : annotatedElementsForm) {
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            path = path.substring(1) + annotatedClass.getSimpleName().toString() + ".html";
            Path out = Paths.get(path);
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
                HtmlForm annotation = annotatedClass.getAnnotation(HtmlForm.class);
                writer.write("<form action='" + annotation.action() + "' method='" + annotation.method() + "'>");
                writer.newLine();
                List<? extends Element> enclosedElements = annotatedClass.getEnclosedElements();
                for (Element field : enclosedElements) {
                    HtmlInput htmlInput = field.getAnnotation(HtmlInput.class);
                    if (htmlInput != null) {
                        writer.write("<input type ='" + htmlInput.type() + "' name = '" + htmlInput.name() + "' placeholder = '" + htmlInput.placeholder() + "'/>");
                        writer.newLine();
                        writer.write("<br>");
                        writer.newLine();
                    }
                }
                writer.write("</form>");
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return true;
    }
}

