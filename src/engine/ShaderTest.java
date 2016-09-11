package engine;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Riggy on 9/11/2016.
 */
public class ShaderTest
{
    public static void main(String[] args)
    {
        //Create Display
        try {
            Display.setDisplayMode(new DisplayMode(640, 480));
            Display.setTitle("Shader Testing");
            Display.create();
        } catch (LWJGLException e){
            System.err.println("Display failed.");
            Display.destroy();
            System.exit(1);
        }

        //Build Shaders
        int shaderProgram = glCreateProgram();
        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        StringBuilder vertexShaderSource = new StringBuilder();
        StringBuilder fragmentShaderSource = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/shaders/shader.vert"));
            String line;
            while((line=reader.readLine())!=null)
            {
                vertexShaderSource.append(line).append("\n");
            }
            reader.close();
            System.out.println(vertexShaderSource);
        }catch(IOException e){
            System.err.println("Vertex Shader Failed.");
            Display.destroy();
            System.exit(1);
        }
        try{
            BufferedReader reader = new BufferedReader(new FileReader("src/shaders/shader.frag"));
            String line;
            while((line=reader.readLine())!=null)
            {
                fragmentShaderSource.append(line).append("\n");
            }
            reader.close();
            System.out.println(fragmentShaderSource);
        }catch(IOException e){
            System.err.println("Fragment Shader Failed.");
            Display.destroy();
            System.exit(1);
        }

        //Compile Shaders
        glShaderSource(vertexShader,vertexShaderSource);
        glCompileShader(vertexShader);
        glShaderSource(fragmentShader,vertexShaderSource);
        glCompileShader(fragmentShader);
        if(glGetShaderi(vertexShader,GL_COMPILE_STATUS)==GL_TRUE){
            System.out.println("Vertex Shader Compiled");
        }else{
            System.err.println("Vertex Shader Not Compiled. Log:");
            System.err.println(glGetShaderInfoLog(vertexShader,1024));
        }
        if(glGetShaderi(vertexShader,GL_COMPILE_STATUS)==GL_TRUE){
            System.out.println("Fragment Shader Compiled. Log:");
        }else{
            System.err.println("Fragment Shader Not Compiled");
            System.err.println(glGetShaderInfoLog(fragmentShader,1024));
        }

        //Attach Shaders
        glAttachShader(shaderProgram,vertexShader);
        glAttachShader(shaderProgram,fragmentShader);
        glLinkProgram(shaderProgram);
        glValidateProgram(shaderProgram);

        if(glGetProgrami(shaderProgram,GL_LINK_STATUS)==GL_TRUE){
            System.out.println("Program Linked");
        }else{
            System.err.println("Program Not Linked. Log:");
            System.err.println(glGetProgramInfoLog(shaderProgram,1024));
        }

        //Run
        while(!Display.isCloseRequested())
        {
            //Start Shader Program
            glUseProgram(shaderProgram);
            //Draw Simple Triangle
            glBegin(GL_TRIANGLES);
            glColor3f(1,0,0);
            glVertex2f(-0.5f,-0.5f);
            glColor3f(0,1,0);
            glVertex2f(0.5f,-0.5f);
            glColor3f(0,0,1);
            glVertex2f(0,0.5f);
            glEnd();

            //End Shader Program
            glUseProgram(0);

            Display.update();
            Display.sync(60);
        }

        //Clean Up
        glDeleteProgram(shaderProgram);
        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);
        Display.destroy();
        System.exit(0);
    }
}
