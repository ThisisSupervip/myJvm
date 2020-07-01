import com.lgb.Interpreter;
import com.lgb.JVM;
import com.lgb.classfile.ClassFile;
import com.lgb.classfile.fundamental.MemberInfo;
import com.lgb.classpath.Classpath;
import com.lgb.rtda.heap.methodarea.Class;
import com.lgb.rtda.heap.methodarea.Method;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestJVMTest {

   @Test
   public void test_classpath_loader(){
      Classpath classpath = new Classpath("E:\\Study\\Java\\myJvm\\target\\classes");
      byte[] bytes = classpath.readClass("com.lgb.TestClass");
      ClassFile classFile = new ClassFile(bytes);
      String hexString = bytesToHex(bytes);
      Assertions.assertTrue(hexString.startsWith("cafebabe"));
   }

   @Test
   public void test_interpreter(){
      Classpath classpath = new Classpath("E:\\Study\\Java\\myJvm\\target\\test-classes");
      Class clazz = JVM.loadClass("jvmgo.book.ch07.InvokeDemo", classpath);
      Method mainMethod = JVM.getMainMethod(clazz);
      Interpreter.interpret(mainMethod);
   }


   public static String bytesToHex(byte[] bytes) {
      StringBuffer sb = new StringBuffer();
      for(int i = 0; i < bytes.length; i++) {
         String hex = Integer.toHexString(bytes[i] & 0xFF);
         if(hex.length() < 2){
            sb.append(0);
         }
         sb.append(hex);
      }
      return sb.toString();
   }
}