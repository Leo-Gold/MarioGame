package jade;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {
    private int width;
    private int height;
    private String title;

    private float r, g, b, a;
    private boolean fadeToBlack = false;

    private long glfwWindow;
    private static Window window = null;

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window(CONST.WIDTH, CONST.HEIGHT, CONST.TITLE);
        }
        return Window.window;
    }

    public void run(){
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");
        init();
        loop();

        // Free the memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and the free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init(){
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();
        // Initial GLFW
        if ( !glfwInit() ){
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);

        // create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);

        if (glfwWindow == NULL) {
            throw new IllegalStateException("Fail to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwShowWindow(glfwWindow);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        GL.createCapabilities();
    }

    public void loop(){
        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwWaitEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (fadeToBlack) {
                r = Math.max(r - 0.01f, 0);
                g = Math.max(g - 0.01f, 0);
                b = Math.max(b - 0.01f, 0);
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_SPACE)){
                fadeToBlack = true;
            }

            glfwSwapBuffers(glfwWindow);
        }
    }
}
