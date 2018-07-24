package top.defaults.compiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

import top.defaults.annotations.AutoProperty;

import static com.google.auto.common.MoreElements.getPackage;

@AutoService(Processor.class)
public class AutoPropertyProcessor extends AbstractProcessor {

    private static final String CLASS_SUFFIX = "_AutoProperties";
    private static final String METHOD_SUFFIX = "Changed";

    private Filer filer;
    private Elements elementUtils;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv){
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes(){
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(AutoProperty.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion(){
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        autoPropertyClassInfoMap.clear();
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AutoProperty.class);
        for (Element element : elements) {
            if (element.getKind() != ElementKind.FIELD) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to field.");
                return true;
            }

            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String qualifiedName = typeElement.getQualifiedName().toString();
            AutoPropertyClassInfo info = autoPropertyClassInfoMap.get(qualifiedName);
            if (info == null) {
                String packageName = getPackage(typeElement).getQualifiedName().toString();
                String className = typeElement.getQualifiedName().toString().substring(
                        packageName.length() + 1).replace('.', '$');
                ClassName autoPropertyClassName = ClassName.get(packageName, className + CLASS_SUFFIX);
                info = new AutoPropertyClassInfo(autoPropertyClassName);
                autoPropertyClassInfoMap.put(qualifiedName, info);
            }

            TypeMirror typeMirror = variableElement.asType();
            TypeName propertyType = TypeName.get(typeMirror);
            if (propertyType instanceof ParameterizedTypeName) {
                propertyType = ((ParameterizedTypeName) propertyType).rawType;
            }

            AutoProperty annotation = variableElement.getAnnotation(AutoProperty.class);
            String setter = annotation.value();
            info.addProperty(variableElement.getSimpleName().toString(), new AutoPropertyInfo(propertyType, setter));
        }

        ClassName gradientDrawableViewModel = ClassName.get("top.defaults.gradientdrawabletuner", "GradientDrawableViewModel");
        for (Map.Entry<String, AutoPropertyClassInfo> entry : autoPropertyClassInfoMap.entrySet()) {
            AutoPropertyClassInfo info = entry.getValue();

            TypeSpec.Builder autoPropertyClassBuilder = TypeSpec.classBuilder(info.autoPropertyClassName.simpleName())
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            for (Map.Entry<String, AutoPropertyInfo> property : info.autoProperties.entrySet()) {
                String propertyName = property.getKey();
                String setter = property.getValue().setter;
                MethodSpec.Builder propertyMethodBuilder = MethodSpec
                        .methodBuilder(propertyName + METHOD_SUFFIX)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .addParameter(gradientDrawableViewModel, "viewModel")
                        .addParameter(property.getValue().propertyTypeName, "value");
                if (setter == null || setter.length() == 0) {
                    propertyMethodBuilder.addStatement("viewModel.updateProperties(properties -> properties.$L = value)", propertyName);
                } else {
                    propertyMethodBuilder.addStatement("viewModel.updateProperties(properties -> properties.set$L(value))",
                            setter.substring(0, 1).toUpperCase() + setter.substring(1));
                }
                MethodSpec propertyMethod = propertyMethodBuilder.build();
                autoPropertyClassBuilder.addMethod(propertyMethod);
            }

            try {
                JavaFile.builder(info.autoPropertyClassName.packageName(), autoPropertyClassBuilder.build())
                        .build().writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    private Map<String, AutoPropertyClassInfo> autoPropertyClassInfoMap = new HashMap<>();

    private class AutoPropertyClassInfo {
        private ClassName autoPropertyClassName;
        Map<String, AutoPropertyInfo> autoProperties = new HashMap<>();

        AutoPropertyClassInfo(ClassName autoPropertyClassName) {
            this.autoPropertyClassName = autoPropertyClassName;
        }

        void addProperty(String name, AutoPropertyInfo info) {
            autoProperties.put(name, info);
        }
    }

    private class AutoPropertyInfo {
        private final TypeName propertyTypeName;
        private final String setter;

        AutoPropertyInfo(TypeName targetTypeName, String setter) {
            this.propertyTypeName = targetTypeName;
            this.setter = setter;
        }
    }
}
