import java.nio.file.*;
import org.objectweb.asm.*;
import static org.objectweb.asm.Opcodes.*;

/** Do not warm DH shaders while the pack was compiled without DH's feature flag. */
public final class PatchDistantWarm {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader(Files.readAllBytes(Path.of(args[0])));
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        int[] changed = {0};
        cr.accept(new ClassVisitor(ASM9, cw) {
            @Override public MethodVisitor visitMethod(int access, String name,
                    String desc, String signature, String[] exceptions) {
                MethodVisitor original = super.visitMethod(access, name, desc, signature, exceptions);
                if (!name.equals("warmAhead") || !desc.equals("(Ldev/vitrail/render/AheadCompiler;)Z")) return original;
                return new MethodVisitor(ASM9, original) {
                    @Override public void visitCode() {
                        super.visitCode();
                        Label enabled = new Label();
                        super.visitMethodInsn(INVOKESTATIC, "dev/vitrail/dh/DhDepth", "present", "()Z", false);
                        super.visitJumpInsn(IFNE, enabled);
                        super.visitInsn(ICONST_0);
                        super.visitInsn(IRETURN);
                        super.visitLabel(enabled);
                        super.visitFrame(F_SAME, 0, null, 0, null);
                        changed[0]++;
                    }
                };
            }
        }, 0);
        if (changed[0] != 1) throw new IllegalStateException("Expected exactly one warmAhead method");
        Files.createDirectories(Path.of(args[1]).getParent());
        Files.write(Path.of(args[1]), cw.toByteArray());
    }
}
