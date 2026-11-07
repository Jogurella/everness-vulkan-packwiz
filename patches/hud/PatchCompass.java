import java.nio.file.*;
import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

public final class PatchCompass {
    static final String OWNER="org/polyfrost/evergreenhud/client/hud/direction/DirectionHud";
    static final String VIEW="L"+OWNER+"$View;";
    static final String COMPOSER="Landroidx/compose/runtime/Composer;";
    static final String CONTEXT="Lorg/polyfrost/compose/render/RenderContext;";
    public static void main(String[] args)throws Exception{
        ClassReader cr=new ClassReader(Files.readAllBytes(Path.of(args[0])));
        ClassWriter cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);int[] changed={0};
        cr.accept(new ClassVisitor(ASM9,cw){
            @Override public MethodVisitor visitMethod(int access,String name,String desc,String signature,String[] exceptions){
                if(name.equals("Compass")&&desc.equals("("+VIEW+COMPOSER+"I)V")){
                    MethodVisitor m=super.visitMethod(access,name,desc,signature,exceptions);m.visitCode();
                    m.visitVarInsn(ALOAD,0);m.visitVarInsn(ALOAD,1);m.visitVarInsn(ALOAD,2);m.visitVarInsn(ILOAD,3);
                    m.visitMethodInsn(INVOKESTATIC,"org/polyfrost/evergreenhud/client/hud/direction/LunarCompassStyle","render","(L"+OWNER+";Ljava/lang/Object;"+COMPOSER+"I)V",false);
                    m.visitInsn(RETURN);m.visitMaxs(0,0);m.visitEnd();changed[0]++;return null;
                }
                if(name.equals("Ticks$lambda$0$0")&&desc.equals("("+VIEW+"FL"+OWNER+";"+CONTEXT+"FFFF)Lkotlin/Unit;")){
                    MethodVisitor m=super.visitMethod(access,name,desc,signature,exceptions);m.visitCode();
                    for(String getter:new String[]{"getBearing","getSpan","getShowTicks","getShowIntercardinals","getFadeEdges"}){
                        m.visitVarInsn(ALOAD,0);m.visitMethodInsn(INVOKEVIRTUAL,OWNER+"$View",getter,"()"+(getter.startsWith("getShow")||getter.equals("getFadeEdges")?"Z":"F"),false);
                    }
                    m.visitVarInsn(ALOAD,2);m.visitVarInsn(ALOAD,3);for(int i=4;i<=7;i++)m.visitVarInsn(FLOAD,i);
                    m.visitMethodInsn(INVOKESTATIC,"org/polyfrost/evergreenhud/client/hud/direction/LunarCompassStyle","draw","(FFZZZL"+OWNER+";"+CONTEXT+"FFFF)Lkotlin/Unit;",false);
                    m.visitInsn(ARETURN);m.visitMaxs(0,0);m.visitEnd();changed[0]++;return null;
                }
                return super.visitMethod(access,name,desc,signature,exceptions);
            }
        },0);
        if(changed[0]!=2)throw new IllegalStateException("Expected two compass render methods; got "+changed[0]);
        Files.createDirectories(Path.of(args[1]).getParent());Files.write(Path.of(args[1]),cw.toByteArray());
        System.out.println("Compass render methods updated: "+changed[0]);
    }
}
