package com.example.nettyim_demo.netty.protocol;

/**
 * 序列化接口定义
 * 目前只实现了 Java 原生序列化，未来可以扩展为 JSON 来实现前后端连接。
 */
public interface Serializer {
    /**
     * 序列化和反序列化的接口定义
     * 提供通用的序列化和反序列化方法，支持不同的序列化算法。
     */
    // 序列化：对象 -> 字节数组
    <T> byte[] serialize(T object);

    // 反序列化：字节数组 -> 对象
    <T> T deserialize(Class<T> clazz, byte[] bytes);


    /**
     * 枚举实现（Java原生序列化）
     * 目前仅实现了 Java 原生序列化，未来可以扩展为 JSON
     */
    enum Algorithm implements Serializer {
        // 使用 JDK 原生序列化
        JAVA {
            @Override
            public <T> byte[] serialize(T object) {
                try (java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
                     java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(out)) {
                    oos.writeObject(object);
                    return out.toByteArray();
                } catch (Exception e) {
                    throw new RuntimeException("Java serialize error", e);
                }
            }

            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try (java.io.ByteArrayInputStream in = new java.io.ByteArrayInputStream(bytes);
                     java.io.ObjectInputStream ois = new java.io.ObjectInputStream(in)) {
                    return (T) ois.readObject();
                } catch (Exception e) {
                    throw new RuntimeException("Java deserialize error", e);
                }
            }
        }

    }
}


// 序列化：对象 -> 字节
// ByteArrayOutputStream out = new ByteArrayOutputStream();
// ObjectOutputStream oos = new ObjectOutputStream(out);
// oos.writeObject(object);
// return out.toByteArray();

// 反序列化：字节 -> 对象
// ByteArrayInputStream in = new ByteArrayInputStream(bytes);
// ObjectInputStream ois = new ObjectInputStream(in);
// return (T) ois.readObject();