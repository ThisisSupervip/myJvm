package com.lgb._native.java.lang;

import com.lgb._native.NativeMethod;
import com.lgb._native.Registry;
import com.lgb.instructions.base.MethodInvokeLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.LocalVariables;
import com.lgb.rtda.OperandStack;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.ShimMethods;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

import java.util.*;

public class _System {
    private final String jlSystem = "java/lang/System";

    public _System() {
        Registry.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arraycopy);
        Registry.register(jlSystem, "registerNatives", "()V", registerNatives);
        Registry.register(jlSystem, "initProperties", "(Ljava/util/Properties;)Ljava/util/Properties;", initProperties);
        Registry.register(jlSystem, "setIn0", "(Ljava/io/InputStream;)V", setIn0);
        Registry.register(jlSystem, "setOut0", "(Ljava/io/PrintStream;)V", setOut0);
        Registry.register(jlSystem, "setErr0", "(Ljava/io/PrintStream;)V", setErr0);
        Registry.register(jlSystem, "currentTimeMillis", "()J", currentTimeMillis);
        Registry.register(null, jlSystem, "mapLibraryName", "(Ljava/lang/String;)Ljava/lang/String;");

    }

    private static Map<String, String> _sysProps = new HashMap<>();

    static {
        _sysProps.put("java.version", "1.8.0");
        _sysProps.put("java.vendor", "jvm.go");
        _sysProps.put("java.vendor.url", "https,//github.com/zxh0/jvm.go");
        _sysProps.put("java.home", "todo");
        _sysProps.put("java.class.version", "52.0");
        _sysProps.put("java.class.path", "todo");
        _sysProps.put("java.awt.graphicsenv", "sun.awt.CGraphicsEnvironment");
        _sysProps.put("os.name", System.getProperty("os.name"));   // todo
        _sysProps.put("os.arch", System.getProperty("os.arch")); // todo
        _sysProps.put("os.version", "");             // todo
        _sysProps.put("file.separator", "/");            // todo os.PathSeparator
        _sysProps.put("path.separator", ",");            // todo os.PathListSeparator
        _sysProps.put("line.separator", "\n");           // todo
        _sysProps.put("user.name", "");             // todo
        _sysProps.put("user.home", "");             // todo
        _sysProps.put("user.dir", ".");            // todo
        _sysProps.put("user.country", "CN");           // todo
        _sysProps.put("file.encoding", "UTF-8");
        _sysProps.put("sun.stdout.encoding", "UTF-8");
        _sysProps.put("sun.stderr.encoding", "UTF-8");
    }

    public NativeMethod registerNatives = (frame) -> {
        // do nothing
    };

    private NativeMethod arraycopy = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object src = vars.getRef(0);
        int srcPos = vars.getInt(1);
        Object dest = vars.getRef(2);
        int destPos = vars.getInt(3);
        int length = vars.getInt(4);

        if (null == src || dest == null) {
            throw new NullPointerException();
        }

        if (!checkArrayCopy(src, dest)) {
            throw new ArrayStoreException();
        }

        if (srcPos < 0 || destPos < 0 || length < 0 ||
                srcPos + length > src.arrayLength() ||
                destPos + length > dest.arrayLength()) {
            throw new IndexOutOfBoundsException();
        }

        System.arraycopy(src.data, srcPos, dest.data, destPos, length);

    };

    private boolean checkArrayCopy(Object src, Object dest) {
        Class srcClass = src.clazz;
        Class destClass = dest.clazz;

        if (!srcClass.isArray() || !destClass.isArray()) {
            return false;
        }

        if (srcClass.componentClass().isPrimitive() || destClass.componentClass().isPrimitive()) {
            return srcClass == destClass;
        }

        return true;

    }

    // private static native Properties initProperties(Properties props);
    // (Ljava/util/Properties;)Ljava/util/Properties;
    private NativeMethod initProperties = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object props = vars.getRef(0);

        OperandStack stack = frame.operandStack;
        stack.pushRef(props);

        // public synchronized Object setProperty(String key, String value)
        Method setPropMethod = props.clazz.getInstanceMethod("setProperty", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
        Thread thread = frame.thread;
        for (Iterator<Map.Entry<String, String>> iterator = _sysProps.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> next = iterator.next();
            Classloader classloader = frame.method.getClazz().getClassloader();
            Object jKey = StringPool.jString(classloader, next.getKey());
            Object jVal = StringPool.jString(classloader, next.getValue());
            OperandStack ops = new OperandStack(3);
            ops.pushRef(props);
            ops.pushRef(jKey);
            ops.pushRef(jVal);
            Frame shimFrame = new Frame(ops, ShimMethods.shimReturnMethod(), thread);
            thread.pushFrame(shimFrame);
            MethodInvokeLogic.invokeMethod(shimFrame, setPropMethod);
        }

    };


    // private static native void setIn0(InputStream in);
    // (Ljava/io/InputStream;)V
    private NativeMethod setIn0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object in = vars.getRef(0);

        Class sysClass = frame.method.getClazz();
        sysClass.setRefVar("in", "Ljava/io/InputStream;", in);
    };

    // private static native void setOut0(PrintStream out);
    // (Ljava/io/PrintStream;)V
    private NativeMethod setOut0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object out = vars.getRef(0);

        Class sysClass = frame.method.getClazz();
        sysClass.setRefVar("out", "Ljava/io/PrintStream;", out);
    };

    // private static native void setErr0(PrintStream err);
    // (Ljava/io/PrintStream;)V
    private NativeMethod setErr0 = (frame) -> {
        LocalVariables vars = frame.localVariables;
        Object err = vars.getRef(0);

        Class sysClass = frame.method.getClazz();
        sysClass.setRefVar("err", "Ljava/io/PrintStream;", err);
    };

    // public static native long currentTimeMillis();
    // ()J
    private NativeMethod currentTimeMillis = (frame) -> {
        long millis = System.currentTimeMillis();
        OperandStack stack = frame.operandStack;
        stack.pushLong(millis);
    };


}
