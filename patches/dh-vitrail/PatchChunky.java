import java.nio.file.*;
import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

public final class PatchChunky {
    public static void main(String[] args)throws Exception{
        String owner="com/seibel/distanthorizons/core/wrapperInterfaces/modAccessor/AbstractChunkyAccessor";
        ClassReader cr=new ClassReader(Files.readAllBytes(Path.of(args[0])));ClassWriter cw=new ClassWriter(cr,ClassWriter.COMPUTE_MAXS);int[] count={0};
        cr.accept(new ClassVisitor(ASM9,cw){
            @Override public MethodVisitor visitMethod(int access,String name,String descriptor,String signature,String[] exceptions){
                if(!name.equals("tryRunFirstTimeSetup")||!descriptor.equals("()V"))return super.visitMethod(access,name,descriptor,signature,exceptions);
                MethodVisitor m=super.visitMethod(access|ACC_SYNCHRONIZED,name,descriptor,signature,exceptions);m.visitCode();Label done=new Label();
                m.visitVarInsn(ALOAD,0);m.visitFieldInsn(GETFIELD,owner,"listenerBound","Z");m.visitJumpInsn(IFNE,done);
                m.visitMethodInsn(INVOKESTATIC,"com/seibel/distanthorizons/core/wrapperInterfaces/modAccessor/ChunkyReadiness","isReady","()Z",false);m.visitJumpInsn(IFEQ,done);
                m.visitVarInsn(ALOAD,0);m.visitMethodInsn(INVOKEVIRTUAL,owner,"bindOnGenerationProgressEvent","()V",false);
                m.visitVarInsn(ALOAD,0);m.visitInsn(ICONST_1);m.visitFieldInsn(PUTFIELD,owner,"listenerBound","Z");
                m.visitLabel(done);m.visitFrame(F_SAME,0,null,0,null);m.visitInsn(RETURN);m.visitMaxs(0,0);m.visitEnd();count[0]++;return null;
            }
        },0);
        if(count[0]!=1)throw new IllegalStateException("Expected exactly one setup method");
        Files.createDirectories(Path.of(args[1]).getParent());Files.write(Path.of(args[1]),cw.toByteArray());
    }
}
