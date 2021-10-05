
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.*;

public class WallpaperChanger {
    public interface User32 extends Library {
        User32 INSTANCE = Native.load("user32",User32.class,W32APIOptions.DEFAULT_OPTIONS);
        boolean SystemParametersInfo (int one, int two, String s ,int three);
    }
    public static void main(String[] args) {
        User32.INSTANCE.SystemParametersInfo(0x0014, 0, "D:\\NGC4676_HubbleOstling_4555.jpg" , 1);
    }
}