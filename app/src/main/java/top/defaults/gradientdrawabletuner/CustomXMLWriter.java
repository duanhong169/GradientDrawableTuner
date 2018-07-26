package top.defaults.gradientdrawabletuner;

import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Namespace;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.dom4j.tree.NamespaceStack;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;

public class CustomXMLWriter extends XMLWriter {

    CustomXMLWriter(Writer writer, OutputFormat format) {
        super(writer, format);
    }

    @Override
    protected void writeAttributes(Element element) throws IOException {
        try {
            Field namespaceStackField = Reflections.resolveField(XMLWriter.class, "namespaceStack");
            NamespaceStack namespaceStack = (NamespaceStack) namespaceStackField.get(this);
            Field indentLevelField = Reflections.resolveField(XMLWriter.class, "indentLevel");
            int indentLevel = indentLevelField.getInt(this);
            setIndentLevel(indentLevel + 1);
            for (int i = 0, size = element.attributeCount(); i < size; i++) {
                println();
                indent();

                Attribute attribute = element.attribute(i);
                Namespace ns = attribute.getNamespace();

                if ((ns != null) && (ns != Namespace.NO_NAMESPACE)
                        && (ns != Namespace.XML_NAMESPACE)) {
                    String prefix = ns.getPrefix();
                    String uri = namespaceStack.getURI(prefix);

                    if (!ns.getURI().equals(uri)) {
                        writeNamespace(ns);
                        namespaceStack.push(ns);
                    }
                }

                // If the attribute is a namespace declaration, check if we have
                // already written that declaration elsewhere (if that's the case,
                // it must be in the namespace stack
                String attName = attribute.getName();

                if (attName.startsWith("xmlns:")) {
                    String prefix = attName.substring(6);

                    if (namespaceStack.getNamespaceForPrefix(prefix) == null) {
                        String uri = attribute.getValue();
                        namespaceStack.push(prefix, uri);
                        writeNamespace(prefix, uri);
                    }
                } else if (attName.equals("xmlns")) {
                    if (namespaceStack.getDefaultNamespace() == null) {
                        String uri = attribute.getValue();
                        namespaceStack.push(null, uri);
                        writeNamespace(null, uri);
                    }
                } else {
                    char quote = getOutputFormat().getAttributeQuoteCharacter();
                    writer.write(attribute.getQualifiedName());
                    writer.write("=");
                    writer.write(quote);
                    writeEscapeAttributeEntities(attribute.getValue());
                    writer.write(quote);
                }
            }
            setIndentLevel(indentLevel);
        } catch (NoSuchFieldException e) {
            super.writeAttributes(element);
        } catch (IllegalAccessException e) {
            super.writeAttributes(element);
        }
    }

    @Override
    protected void writeNamespace(String prefix, String uri) throws IOException {
        if ((prefix != null) && (prefix.length() > 0)) {
            writer.write("xmlns:");
            writer.write(prefix);
            writer.write("=\"");
        } else {
            writer.write("xmlns=\"");
        }

        writer.write(uri);
        writer.write("\"");
    }
}
