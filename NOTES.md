# Simply Jetpacks 3 - Notes

- use the inputEvent event which handles keybinds. and you can use `NetworkHooks.openGui`
- guiingameoptions (the escape menu) and there was a line of code that opens a gui


- I believe there is a key press event you can hook into. 
Then it's just sending a packet to the client to open the gui.
- first, create a keybind.
    - Then in the key press event check whether it's that key that got pressed.
    - Now just open the gui, how you open the GUI depends if it is a container gui or a normal gui.
    - Normal guis (such as the main menu, the settings menu and so on) extend the Screen class,
    and can be opened through `Minecraft#displayGuiScreen(Screen);`
 
 
- Opening a gui
    - client player presses the keybind,
    - client sends packet to server,
    - server calls NetworkHooks.openGui to open the container on the server and client
    
``` 
public class KeyboardUtil {
     private static final long MINECRAFT_WINDOW = Minecraft.getInstance().getMainWindow().getHandle();
 
     @OnlyIn(Dist.CLIENT)
     public static boolean isHoldingShift()
     {
         return InputMappings.isKeyDown(MINECRAFT_WINDOW, GLFW.GLFW_KEY_LEFT_SHIFT);
     }
 
     @OnlyIn(Dist.CLIENT)
     public static boolean isHoldingCTRL()
     {
         return InputMappings.isKeyDown(MINECRAFT_WINDOW, GLFW.GLFW_KEY_LEFT_CONTROL);
     }
 }
```

### Credits:
Silk, Caltinor, Commoble