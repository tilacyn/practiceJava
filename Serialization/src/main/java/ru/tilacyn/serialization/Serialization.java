package ru.tilacyn.serialization;

import java.io.*;
import java.lang.reflect.Field;

import org.jetbrains.annotations.NotNull;

/**
 * class with two static functions:
 * serialize - accepts object and output stream. Writes object to the output stream
 * deserialize - accepts Class<T> object and input stream. Returns new object of type T equal to the serialized one
 */
public class Serialization {

    /**
     * exception that is thrown in case of wrong input file format
     * particularly, if type constant before the serialized field is not equal to any of [1 .. 9]
     * then type is considered to be unknown so that we cannot process the next field
     */
    public static class BadFileFormatException extends Exception {
    }

    /**
     * possible field types
     */
    enum FieldType {INT, STRING, CHAR, BOOLEAN, DOUBLE, FLOAT, SHORT, LONG, BYTE}


    /**
     * these fields are re-initialized every time one of the two public functions is called
     */
    private static DataOutputStream os;
    private static DataInputStream is;

    private static String byteArrayToString(byte[] b) {
        StringBuilder res = new StringBuilder();
        for (byte aB : b) {
            res.append((char) aB);
        }
        return res.toString();
    }

    /**
     * writes the object state to the output stream
     * only those objects can be processed
     * which contain only primitive / string type fields
     * <p>
     * in the output file data is contained this way:
     * <serialized_field_1><serialized_field_2>....<serialized_field_n>
     * <p>
     * mind that fields might appear in any order
     * (indeed it is the order of iterator on the someClass.getDeclaredFields())
     * <p>
     * each field is stored this way
     * <type length><type><name length><name>
     * <p>
     * if field type is String then data is stored this way right after type-name part:
     * <field data length><field data>
     * <p>
     * if field type is primitive then only the value is stored:
     * <value>
     *
     * @param o            object that should be written
     * @param outputStream output stream
     * @throws IllegalAccessException if some of the fields that should be serialized cannot be accessed
     * @throws IOException            if problems with writing occurred
     */
    public static void serialize(@NotNull Object o, @NotNull OutputStream outputStream) throws IllegalAccessException, IOException {
        Class c = o.getClass();
        os = new DataOutputStream(outputStream);

        for (Field f : c.getDeclaredFields()) {
            switch (f.getType().getSimpleName()) {
                case "String":
                    serializeString(f, o);
                    break;
                case "int":
                    serializeInt(f, o);
                    break;
                case "boolean":
                    serializeBoolean(f, o);
                    break;
                case "byte":
                    serializeByte(f, o);
                    break;
                case "long":
                    serializeLong(f, o);
                    break;
                case "char":
                    serializeChar(f, o);
                    break;
                case "short":
                    serializeShort(f, o);
                    break;
                case "double":
                    serializeDouble(f, o);
                    break;
                case "float":
                    serializeFloat(f, o);
                    break;
            }
        }
    }


    /**
     * deserializes the object from the input stream
     * all the rules of file format are described in the serialize function description
     *
     * @param inputStream contains serialized object
     * @param c           object class of (type Class<T>)
     * @param <T>         object class
     * @return new object of type T with fields equal to the serialized ones
     * @throws IOException            if problems with reading occurred
     * @throws IllegalAccessException if serialized fields cannot be accessed indeed
     * @throws InstantiationException if problems with instantiating new object of type T occurred (eg. it has not got default constructor)
     * @throws NoSuchFieldException   if there is no such field in the class c
     * @throws BadFileFormatException if file has bad format (type of the next field is incorrect)
     */
    public static <T> T deserialize(@NotNull InputStream inputStream, @NotNull Class<T> c) throws IOException, IllegalAccessException, InstantiationException, NoSuchFieldException, BadFileFormatException {
        is = new DataInputStream(inputStream);
        String type;
        T t = c.newInstance();
        while ((type = readFieldType()) != "eof") {
            switch (type) {
                case "STRING":
                    deserializeString(c, t);
                    break;
                case "INT":
                    deserializeInt(c, t);
                    break;
                case "CHAR":
                    deserializeChar(c, t);
                    break;
                case "BOOLEAN":
                    deserializeBoolean(c, t);
                    break;
                case "BYTE":
                    deserializeByte(c, t);
                    break;
                case "SHORT":
                    deserializeShort(c, t);
                    break;
                case "LONG":
                    deserializeLong(c, t);
                    break;
                case "FLOAT":
                    deserializeFloat(c, t);
                    break;
                case "DOUBLE":
                    deserializeDouble(c, t);
                    break;
                default:
                    throw new BadFileFormatException();
            }
        }
        return t;
    }

    // FieldType processing

    private static void writeFieldType(FieldType ft) throws IOException {
        os.write(ft.toString().getBytes().length);
        os.write(ft.toString().getBytes());
    }

    private static String readFieldType() throws IOException {
        int n = is.read();
        if (n == -1) {
            return "eof";
        }
        byte[] ft = new byte[n];
        for (int i = 0; i < n; i++) {
            ft[i] = (byte) is.read();
        }
        return byteArrayToString(ft);
    }

    // Deserialize functions

    private static <T> void deserializeString(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        int n = is.read();
        byte[] data = new byte[n];
        for (int i = 0; i < n; i++) {
            data[i] = (byte) is.read();
        }
        c.getField(byteArrayToString(name)).set(t, byteArrayToString(data));
    }

    private static <T> void deserializeInt(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readInt());
    }

    private static <T> void deserializeDouble(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readDouble());
    }

    private static <T> void deserializeChar(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readChar());
    }

    private static <T> void deserializeFloat(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readFloat());
    }

    private static <T> void deserializeByte(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readByte());
    }

    private static <T> void deserializeBoolean(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readBoolean());
    }

    private static <T> void deserializeShort(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readShort());
    }

    private static <T> void deserializeLong(@NotNull Class<T> c, @NotNull T t) throws IOException, NoSuchFieldException, IllegalAccessException {
        byte[] name = readName();
        c.getField(byteArrayToString(name)).set(t, is.readLong());
    }


    private static byte[] readName() throws IOException {
        int n = is.read();
        byte[] name = new byte[n];
        for (int i = 0; i < n; i++) {
            name[i] = (byte) is.read();
        }
        return name;
    }

    private static void writeName(Field f) throws IOException {
        byte[] name = f.getName().getBytes();
        os.write(name.length);
        os.write(name);
    }

    // Serialize functions

    private static void serializeString(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.STRING);
        writeName(f);
        byte[] data = ((String) f.get(o)).getBytes();
        os.write(data.length);
        os.write(data);
    }

    private static void serializeInt(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.INT);
        writeName(f);
        os.writeInt((Integer) f.get(o));
    }

    private static void serializeChar(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.CHAR);
        writeName(f);
        os.writeChar((Character) f.get(o));
    }

    private static void serializeDouble(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.DOUBLE);
        writeName(f);
        os.writeDouble((Double) f.get(o));
    }

    private static void serializeByte(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.BYTE);
        writeName(f);
        os.writeByte((Byte) f.get(o));
    }

    private static void serializeBoolean(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.BOOLEAN);
        writeName(f);
        os.writeBoolean((Boolean) f.get(o));
    }

    private static void serializeFloat(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.FLOAT);
        writeName(f);
        os.writeFloat((Float) f.get(o));
    }

    private static void serializeLong(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.LONG);
        writeName(f);
        os.writeLong((Long) f.get(o));
    }

    private static void serializeShort(@NotNull Field f, @NotNull Object o) throws IOException, IllegalAccessException {
        writeFieldType(FieldType.SHORT);
        writeName(f);
        os.writeShort((Short) f.get(o));
    }

}
