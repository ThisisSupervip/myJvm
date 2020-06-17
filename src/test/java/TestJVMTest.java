import com.lgb.classfile.ClassFile;
import com.lgb.classpath.Classpath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestJVMTest {

   @Test
   public void test_classpath_loader(){
      Classpath classpath = new Classpath("D:/*");
      byte[] bytes = classpath.readClass("com.alibaba.fastjson.JSON");
      ClassFile classFile = new ClassFile(bytes);
      String hexString = bytesToHex(bytes);
      Assertions.assertTrue(hexString.startsWith("cafebabe"));
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