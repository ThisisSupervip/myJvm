package com.lgb;

import com.lgb.classpath.Classpath;
import com.lgb.instructions.base.ClassInitLogic;
import com.lgb.rtda.Frame;
import com.lgb.rtda.Thread;
import com.lgb.rtda.heap.StringPool;
import com.lgb.rtda.heap.classloader.Classloader;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import com.lgb.rtda.heap.methodarea.Object;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.Objects;

import static com.lgb.Interpreter.interpret;

public class JVM {

    private Classpath classpath;
    private String mainClassName;
    private String[] args;
    private Classloader classloader;
    private Thread mainThread;

    public JVM(String classpath, String mainClassName, String[] args) {
        this.classpath = new Classpath(classpath);
        this.mainClassName = mainClassName;
        this.args = args;
        this.classloader = new Classloader(this.classpath);
        this.mainThread = new Thread();
    }

    public static Method getMainMethod(Class clazz) {
        return clazz.getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    public void start() {
        //setOut();
        initVM();
        execMain();
    }

    private void initVM() {
        Class vmClass = this.classloader.loadClass("sun/misc/VM");
        ClassInitLogic.initClass(mainThread, vmClass);
        interpret(mainThread, false);
    }

    private void execMain() {
        Class mainClass = classloader.loadClass(mainClassName);
        Method mainMethod = getMainMethod(mainClass);
        if (Objects.isNull(mainClass)) {
            System.out.printf("Main method not found in class %s\n", mainClass);
            return;
        }

        Object argsArr = createArgsArray();
        Frame frame = mainThread.newFrame(mainMethod);
        frame.localVariables.setRef(0, argsArr);
        mainThread.pushFrame(frame);
        interpret(mainThread, false);
    }


    private Object createArgsArray() {
        Class stringClass = classloader.loadClass("java/lang/String");
        Object argsArr = stringClass.arrayClass().newArray(args.length);
        Object[] jArgs = argsArr.refs();
        for (int i = 0; i < jArgs.length; i++) {
            jArgs[i] = StringPool.jString(classloader, args[i]);
        }
        return argsArr;
    }


    private void setOut() {
        File file = Paths.get("D:/", "out").toFile();
        try (PrintStream ps = new PrintStream(file)) {
            System.setOut(ps);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
