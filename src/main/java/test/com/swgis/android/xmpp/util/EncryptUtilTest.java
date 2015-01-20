package test.com.swgis.android.xmpp.util; 

import com.swgis.android.xmpp.util.EncryptUtil;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestSuite; 
import junit.framework.TestCase; 

/** 
* EncryptUtil Tester. 
* 
* @author <Authors name> 
* @since <pre>01/16/2015</pre> 
* @version 1.0 
*/ 
public class EncryptUtilTest extends TestCase {
   private String testin="hell秋风萧瑟天气凉，草木摇落露为霜，群燕辞归鹄南翔。\n" +
           "念君客游思断肠，慊慊思归恋故乡，君何淹留寄他方？\n" +
           "贱妾茕茕守空房，忧来思君不敢忘，不觉泪下沾衣裳。\n" +
           "援琴鸣弦发清商，短歌微吟不能长。\n" +
           "明月皎皎照我床，星汉西流夜未央。\n" +
           "牵牛织女遥相望，尔独何辜限河梁。\n" +
           "\n" +
           "写翻译 写翻译写赏析 写赏析纠错 纠错收藏 收藏分享评分：很差较差还行推荐力荐\n" +
           "参考翻译\n" +
           "写翻译 写翻译\n" +
           "译文及注释\n" +
           "译文秋风萧瑟,天气清冷，草木凋落，白露凝霜。燕群辞归，天鹅南飞。思念出外远游的良人啊，我肝肠寸断。思虑冲冲，怀念故乡。君为何故，淹留他方。贱妾孤零零的空守闺房，忧愁的时候思念君子啊，我不能忘怀。不知不觉中珠泪下落，打湿了我的衣裳。拿过古琴，拨弄琴弦却发出丝丝哀怨。短歌轻吟，似续还...\n" +
           "参考赏析\n" +
           "写赏析 写赏析\n" +
           "赏析\n" +
           "这是曹丕《燕歌行》秋风萧瑟天气凉，草木摇落露为霜，群燕辞归鹄南翔。\n" +
           "念君客游思断肠，慊慊思归恋故乡，君何淹留寄他方？\n" +
           "贱妾茕茕守空房，忧来思君不敢忘，不觉泪下沾衣裳。\n" +
           "援琴鸣弦发清商，短歌微吟不能长。\n" +
           "明月皎皎照我床，星汉西流夜未央。\n" +
           "牵牛织女遥相望，尔独何辜限河梁。\n" +
           "\n" +
           "写翻译 写翻译写赏析 写赏析纠错 纠错收藏 收藏分享评分：很差较差还行推荐力荐\n" +
           "参考翻译\n" +
           "写翻译 写翻译\n" +
           "译文及注释\n" +
           "译文秋风萧瑟,天气清冷，草木凋落，白露凝霜。燕群辞归，天鹅南飞。思念出外远游的良人啊，我肝肠寸断。思虑冲冲，怀念故乡。君为何故，淹留他方。贱妾孤零零的空守闺房，忧愁的时候思念君子啊，我不能忘怀。不知不觉中珠泪下落，打湿了我的衣裳。拿过古琴，拨弄琴弦却发出丝丝哀怨。短歌轻吟，似续还...\n" +
           "参考赏析\n" +
           "写赏析 写赏析\n" +
           "赏析\n" +
           "这是曹丕《燕歌行》二首a秋风萧瑟天气凉，草木摇落露为霜，群燕辞归鹄南翔。\n" +
           "念君客游思断肠，慊慊思归恋故乡，君何淹留寄他方？\n" +
           "贱妾茕茕守空房，忧来思君不敢忘，不觉泪下沾衣裳。\n" +
           "援琴鸣弦发清商，短歌微吟不能长。\n" +
           "明月皎皎照我床，星汉西流夜未央。\n" +
           "牵牛织女遥相望，尔独何辜限河梁。\n" +
           "\n" +
           "写翻译 写翻译写赏析 写赏析纠错 纠错收藏 收藏分享评分：很差较差还行推荐力荐\n" +
           "参考翻译\n" +
           "写翻译 写翻译\n" +
           "译文及注释\n" +
           "译文秋风萧瑟,天气清冷，草木凋落，白露凝霜。燕群辞归，天鹅南飞。思念出外远游的良人啊，我肝肠寸断。思虑冲冲，怀念故乡。君为何故，淹留他方。贱妾孤零零的空守闺房，忧愁的时候思念君子啊，我不能忘怀。不知不觉中珠泪下落，打湿了我的衣裳。拿过古琴，拨弄琴弦却发出丝丝哀怨。短歌轻吟，似续还...\n" +
           "参考赏析\n" +
           "写赏析 写赏析\n" +
           "赏析\n" +
           "这是曹丕《燕歌行》二首中的第一首。《燕歌行》是一个乐府题目，属于《相和歌》中的《平调曲》，它和《齐讴行》、《吴趋行》相类，都是反映各自地区的生活，具有各自地区音乐特点的曲调。燕（Yān）是西周以至春秋战国时期的诸侯国名，辖地约当今北京市以及河北北部、辽宁西南部等一带地区。这里是汉...\n" +
           "历史评价\n" +
           "这是今存最早的一首完整的七言诗。它叙述了一位女子对丈夫的思念。笔致委婉，语言清丽，感情缠绵。这首诗突出的特点是写景与抒情的巧妙交融。诗歌的开头展示了一幅秋色图：秋风萧瑟， 草木零落， 白露为霜，候鸟南飞……。这萧条的景色牵出思妇的怀人之情，映照出她内心的寂寞；最后几句以清冷的月色...中的第一首。《燕歌行》是一个乐府题目，属于《相和歌》中的《平调曲》，它和《齐讴行》、《吴趋行》相类，都是反映各自地区的生活，具有各自地区音乐特点的曲调。燕（Yān）是西周以至春秋战国时期的诸侯国名，辖地约当今北京市以及河北北部、辽宁西南部等一带地区。这里是汉...\n" +
           "历史评价\n" +
           "这是今存最早的一首完整的七言诗。它叙述了一位女子对丈夫的思念。笔致委婉，语言清丽，感情缠绵。这首诗突出的特点是写景与抒情的巧妙交融。诗歌的开头展示了一幅秋色图：秋风萧瑟， 草木零落， 白露为霜，候鸟南飞……。这萧条的景色牵出思妇的怀人之情，映照出她内心的寂寞；最后几句以清冷的月色...二首中的第一首。《燕歌行》是一个乐府题目，属于《相和歌》中的《平调曲》，它和《齐讴行》、《吴趋行》相类，都是反映各自地区的生活，具有各自地区音乐特点的曲调。燕（Yān）是西周以至春秋战国时期的诸侯国名，辖地约当今北京市以及河北北部、辽宁西南部等一带地区。这里是汉...\n" +
           "历史评价\n" +
           "这是今存最早的一首完整的七言诗。它叙述了一位女子对丈夫的思念。笔致委婉，语言清丽，感情缠绵。这首诗突出的特点是写景与抒情的巧妙交融。诗歌的开头展示了一幅秋色图：秋风萧瑟， 草木零落， 白露为霜，候鸟南飞……。这萧条的景色牵出思妇的怀人之情，映照出她内心的寂寞；最后几句以清冷的月色...o";
   private String testout;
public EncryptUtilTest(String name) { 
super(name); 
} 

public void setUp() throws Exception { 
super.setUp();
   testout= EncryptUtil.encrBASE64ByGzip(testin);
} 

public void tearDown() throws Exception { 
super.tearDown(); 
} 

/** 
* 
* Method: decryptUTF8(byte[] in) 
* 
*/ 
public void testDecryptUTF8() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encryptUTF8(String in) 
* 
*/ 
public void testEncryptUTF8() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encrBASE64ByGzip(String str) 
* 
*/ 
public void testEncrBASE64ByGzip() throws Exception { 
//TODO: Test goes here...
   String out = EncryptUtil.encrBASE64ByGzip(testin);
} 

/** 
* 
* Method: decryptBASE64ByGzip(String base64) 
* 
*/ 
public void testDecryptBASE64ByGzip() throws Exception { 
//TODO: Test goes here...
   String reback = EncryptUtil.decryptBASE64ByGzip(testout);
//   Assert.assertEquals(testin,reback);
    int origin=testin.length();
    int result = testout.length();
    Assert.assertTrue(result<origin);
} 

/** 
* 
* Method: encryptBASE64(String str) 
* 
*/ 
public void testEncryptBASE64Str() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encryptBASE64(byte[] in) 
* 
*/ 
public void testEncryptBASE64In() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: decryptBASE64(String str) 
* 
*/ 
public void testDecryptBASE64Str() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: decryptBASE64(byte[] in) 
* 
*/ 
public void testDecryptBASE64In() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encryptGZIP(byte[] in) 
* 
*/ 
public void testEncryptGZIPIn() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: encryptGZIP(String str) 
* 
*/ 
public void testEncryptGZIPStr() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: decryptGZIP(byte[] in) 
* 
*/ 
public void testDecryptGZIP() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: hexStringToBytes(String hexString) 
* 
*/ 
public void testHexStringToBytes() throws Exception { 
//TODO: Test goes here... 
} 

/** 
* 
* Method: bytesToHexString(byte[] src) 
* 
*/ 
public void testBytesToHexString() throws Exception { 
//TODO: Test goes here... 
} 


/** 
* 
* Method: charToByte(char c) 
* 
*/ 
public void testCharToByte() throws Exception { 
//TODO: Test goes here... 
/* 
try { 
   Method method = EncryptUtil.getClass().getMethod("charToByte", char.class); 
   method.setAccessible(true); 
   method.invoke(<Object>, <Parameters>); 
} catch(NoSuchMethodException e) { 
} catch(IllegalAccessException e) { 
} catch(InvocationTargetException e) { 
} 
*/ 
} 


public static Test suite() { 
return new TestSuite(EncryptUtilTest.class); 
} 
} 
