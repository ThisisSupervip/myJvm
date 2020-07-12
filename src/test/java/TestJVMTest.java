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
      String hexString = bytesToHex(bytes);
      Assertions.assertTrue(hexString.startsWith("cafebabe"));
   }

   @Test
   public void test_interpreter(){
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch07.InvokeDemo", new String[]{});
      jvm.start();
   }

   @Test
   public void ch08_test_bubble_sort(){
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch08.BubbleSortTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch08_print_args() {
      String[] args = "foo bar 你好，世界".split(" ");
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch08.PrintArgs", args);
      jvm.start();
   }

   @Test
   public void ch09_getClassTest() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch09.GetClassTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch09_stringTest() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch09.StringTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch09_cloneTest() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch09.CloneTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch09_boxTest() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch09.BoxTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch10_parseIntTest() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch10.ParseIntTest", new String[]{});
      jvm.start();
   }

   @Test
   public void ch11_helloWorld() {
      JVM jvm = new JVM("E:\\Study\\Java\\myJvm\\target\\test-classes",
              "jvmgo.book.ch11.HelloWorld", new String[]{});
      jvm.start();
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