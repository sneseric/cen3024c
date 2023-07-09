import com.sun.javadoc.*;
import com.sun.tools.javadoc.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class JavaDoc {

    public static void main(String[] args) {
        generateJavadocComments("TextAnalyzerGUI.java");
    }

    public static void generateJavadocComments(String filename) {
        String[] javadocArgs = { "-doclet", CommentGenerator.class.getName(), "-sourcepath", ".", filename };
        Main.execute(javadocArgs);
    }

    /**
     * Generates Javadoc comments for the given source file.
     */
    public static class CommentGenerator {

        private static PrintWriter writer;

        /**
         * Entry point for generating Javadoc comments.
         *
         * @param rootDoc The root document representing the parsed source code.
         * @return true if Javadoc generation is successful, false otherwise.
         */
        public static boolean start(RootDoc rootDoc) {
            try {
                writer = new PrintWriter(new FileWriter("javadoc_comments.html"));
                ClassDoc[] classes = rootDoc.classes();
                for (ClassDoc classDoc : classes) {
                    generateClassComments(classDoc);
                    generateMethodComments(classDoc.methods());
                    generateFieldComments(classDoc.fields());
                }
                writer.close();
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        /**
         * Generates Javadoc comments for a class.
         *
         * @param classDoc The ClassDoc object representing the class.
         */
        private static void generateClassComments(ClassDoc classDoc) {
            String className = classDoc.name();
            writer.println("/**");
            writer.println(" * " + className + " - This class represents " + className + ".");
            writer.println(" */");
        }

        /**
         * Generates Javadoc comments for methods.
         *
         * @param methods The array of MethodDoc objects representing the methods.
         */
        private static void generateMethodComments(MethodDoc[] methods) {
            for (MethodDoc method : methods) {
                String methodName = method.name();
                writer.println("/**");
                writer.println(" * " + methodName + " - This method " + methodName + ".");
                writer.println(" *");
                generateParamComments(method.parameters());
                generateReturnComment(method.returnType());
                writer.println(" */");
            }
        }

        /**
         * Generates Javadoc comments for method parameters.
         *
         * @param parameters The array of Parameter objects 
         */
        private static void generateParamComments(Parameter[] parameters) {
            for (Parameter parameter : parameters) {
                String paramName = parameter.name();
                String paramType = parameter.type().typeName();
                writer.println(" * @param " + paramName + " The " + paramName + " parameter of type " + paramType + ".");
            }
        }

        /**
         * Generates Javadoc comments for the return type of a method.
         *
         * @param returnType The Type object representing the return type.
         */
        private static void generateReturnComment(Type returnType) {
            String returnTypeName = returnType.typeName();
            writer.println(" * @return The " + returnTypeName + " value.");
        }

        /**
         * Generates Javadoc comments for fields.
         *
         * @param fields The array of FieldDoc objects 
         */
        private static void generateFieldComments(FieldDoc[] fields) {
            for (FieldDoc field : fields) {
                String fieldName = field.name();
                String fieldType = field.type().typeName();
                writer.println("/**");
                writer.println(" * " + fieldName + " - This field represents " + fieldName + ".");
                writer.println(" *");
                writer.println(" * @type " + fieldType + " The type of " + fieldName + ".");
                writer.println(" */");
            }
        }
    }
}



