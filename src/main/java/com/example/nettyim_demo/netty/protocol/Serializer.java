package com.example.nettyim_demo.netty.protocol;

public interface Serializer {

    // 序列化：对象 -> 字节数组
    <T> byte[] serialize(T object);

    // 反序列化：字节数组 -> 对象
    <T> T deserialize(Class<T> clazz, byte[] bytes);

    // 定义支持的算法类型
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

        // 未来还可以拓展：JSON、Protobuf、Hessian
    }
}
